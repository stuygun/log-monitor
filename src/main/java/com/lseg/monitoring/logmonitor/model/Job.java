package com.lseg.monitoring.logmonitor.model;

import java.time.LocalTime;

public record Job(String pid, String description, LocalTime startTime, LocalTime endTime) {
}
