package com.rplan.minecraft;

import java.util.Map;

public class Link {
    public String sourceId;
    public String targetId;
    public LinkType type;
    public String autolink;

    public static Link parse(Map<String, Object> obj) {
        Link link = new Link();

        link.sourceId = obj.get("source").toString();
        link.targetId = obj.get("target").toString();
        link.type = LinkType.fromString(obj.get("type").toString());
        if (obj.get("autolink") != null) {
            link.autolink = obj.get("autolink").toString();
        }

        return link;
    }
}
