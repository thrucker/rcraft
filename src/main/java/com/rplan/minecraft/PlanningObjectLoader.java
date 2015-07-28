package com.rplan.minecraft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.util.ChatComponentText;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Instant;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by rheinicke on 28/07/15.
 */
public class PlanningObjectLoader {
    private String authCookie;
    private static ObjectMapper mapper = new ObjectMapper();

    public PlanningObjectLoader(String sessionCookie) {
        this.authCookie = sessionCookie;
    }

    public String getRootId() {
        try {
            String json = RestClient.get(url("user/current/root"), authCookie);
            Map<String, Object> map = mapper.readValue(json, Map.class);
            return map.get("rootPlanningObjectId").toString();
        } catch (Exception ex) {
            return null;
        }
    }

    public PlanningObject getRoot() {
        return getPlanningObject(getRootId());
    }

    public PlanningObject getPlanningObject(String id) {
        try {
            String json = RestClient.get(url("planning-objects/" + id), authCookie);
            Map<String, Object> value = mapper.readValue(json, Map.class);
            return PlanningObject.parse(value);
        } catch (Exception ex) {
            return null;
        }
    }

    public PlanningObject[] getChildren(String parentId) {
        try {
            String json = RestClient.get(url("planning-objects/" + parentId + "/children"), authCookie);

            Map<String, Object>[] values = mapper.readValue(json, Map[].class);
            PlanningObject[] result = new PlanningObject[values.length];
            for (int i = 0; i < values.length; i++) {
                result[i] = PlanningObject.parse(values[i]);
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PlanningObject[] getAll() {
        ArrayDeque<PlanningObject> queue = new ArrayDeque<PlanningObject>();
        queue.push(getRoot());
        PlanningObject po;

        Date minDate = new Date(Long.MAX_VALUE);

        ArrayList<PlanningObject> result = new ArrayList<PlanningObject>();
        while ((po = queue.pollFirst()) != null) {
            if (!"ROOT".equals(po.parentId)) {
                if (po.start.before(minDate)) {
                    minDate = po.start;
                }
                result.add(po);
            }

            PlanningObject[] children = getChildren(po);
            for (int i = children.length - 1; i >= 0; i--) {
                queue.addFirst(children[i]);
            }
        }


        PlanningObject[] arr = result.toArray(new PlanningObject[0]);

        for (int i = 0; i < arr.length; i++) {
            arr[i].lineNumber = i;
            arr[i].offsetDays = Days.daysBetween(
                    new DateTime(minDate.getTime()),
                    new DateTime(arr[i].start.getTime())).getDays();
        }

        return arr;
    }

    public PlanningObject[] getChildren(PlanningObject po) {
        return getChildren(po.id);
    }

    private URL url(String path) throws MalformedURLException {
        return new URL("https://rplan.com/api/" + path);
    }
}
