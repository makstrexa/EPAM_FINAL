package com.conference.model;

public enum ReportStatus {
    ACTIVE("active"),
    NON_ACTIVE("non-active");

    private String value;

    ReportStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ReportStatus fromString(String value) {
        if (value != null) {
            for (ReportStatus reportStatus :  ReportStatus.values()) {
                if (value.equalsIgnoreCase(reportStatus.value)) {
                    return reportStatus;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }

}
