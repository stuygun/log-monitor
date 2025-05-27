package com.lseg.monitoring.logmonitor.model;

public record ReportEntry(String message, Level level) {

    @Override
    public String toString() {
        return String.format("[%s] %s", level, message);
    }
}
