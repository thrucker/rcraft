package com.rplan.minecraft;

import java.util.Map;

/**
 * Created by rheinicke on 28/07/15.
 */
public class PlanningObject {
    public String name;
    public String id;

    public static PlanningObject parse(Map<String, Object> obj) {
        PlanningObject po = new PlanningObject();
        po.id = obj.get("id").toString();
        po.name = obj.get("name").toString();
        return po;
    }

    public String toString() {
        return name;
    }
}
