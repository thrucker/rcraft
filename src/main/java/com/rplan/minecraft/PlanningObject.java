package com.rplan.minecraft;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rheinicke on 28/07/15.
 */
public class PlanningObject {
    public String name;
    public String id;
    public String parentId;
    public int duration;
    public Date start;
    public int offsetDays;
    public int lineNumber;
    public boolean isProject;
    public boolean isSummaryTask;
    public List<Link> links;

    public static PlanningObject parse(Map<String, Object> obj) {
        PlanningObject po = new PlanningObject();
        po.id = obj.get("id").toString();
        po.name = obj.get("name").toString();
        po.parentId = obj.get("parent").toString();

        Object x = obj.get("childrenIds");
        po.isProject = (Boolean)obj.get("isProject");
        po.isSummaryTask = ((ArrayList)obj.get("childrenIds")).size() > 0;

        if (po.isProject) {
            po.duration = getDuration(obj, "userDefinedDuration", "calculatedDuration");
            po.start = getStart(obj, "userDefinedStart", "calculatedStart");
        } else {
            po.duration = getDuration(obj, "calculatedDuration", "userDefinedDuration");
            po.start = getStart(obj, "calculatedStart", "userDefinedStart");
        }

        po.links = new ArrayList<Link>();

        List<Map<String, Object>> linkObjs = (List<Map<String, Object>>) obj.get("link");

        for (Map<String, Object> linkObj : linkObjs) {
            po.links.add(Link.parse(linkObj));
        }

        return po;
    }

    private static int getDuration(Map<String, Object> obj, String fieldA, String fieldB) {
        Object calculatedDuration = obj.get(fieldA);
        if (calculatedDuration != null) {
            return (Integer)calculatedDuration;
        } else {
            return (Integer)obj.get(fieldB);
        }
    }

    private static Date getStart(Map<String, Object> obj, String fieldA, String fieldB) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Object calculatedStart = obj.get(fieldA);
            if (calculatedStart != null) {
                return formatter.parse(calculatedStart.toString());
            } else {
                return formatter.parse(obj.get(fieldB).toString());
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public String toString() {
        return name;
    }
}
