package com.spring.microservice.domain;

/**
 * Enumeration of the region of California.
 */
public enum Region {

    Central_Coast("Central Coast"), Southern_California("Southern California"),
    Northern_california("Northern California"), Varies("Varies");

    private String label;

    Region(String label) {
        this.label = label;
    }

    public static Region findByLabel(String byLabel) {
        for (Region r : Region.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }

}
