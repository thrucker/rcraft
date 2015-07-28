package com.rplan.minecraft;

public enum LinkType {
    START_START,
    START_END,
    END_START,
    END_END;

    public static LinkType fromString(String s) {
        if (s.equals("start-start")) {
            return START_START;
        } else if (s.equals("start-end")) {
            return START_END;
        } else if (s.equals("end-start")) {
            return END_START;
        } else if (s.equals("end-end")) {
                return END_END;
        } else {
            throw new IllegalArgumentException("Invalid link type '" + s + "'");
        }
    }
}
