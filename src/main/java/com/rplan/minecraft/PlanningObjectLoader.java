package com.rplan.minecraft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.util.ChatComponentText;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

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
            return null;
        }
    }

    public PlanningObject[] getChildren(PlanningObject po) {
        return getChildren(po.id);
    }

    private URL url(String path) throws MalformedURLException {
        return new URL("https://rplan.com/api/" + path);
    }
}
