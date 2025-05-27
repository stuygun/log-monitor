package com.lseg.monitoring.logmonitor.service;

import com.lseg.monitoring.logmonitor.model.Job;
import com.lseg.monitoring.logmonitor.model.Level;
import com.lseg.monitoring.logmonitor.model.ReportEntry;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportEntryGenerator {
    private static final Duration WARNING_THRESHOLD = Duration.ofMinutes(5);
    private static final Duration ERROR_THRESHOLD = Duration.ofMinutes(10);

    public List<ReportEntry> generateReportEntries(List<Job> jobs) {
        List<ReportEntry> reportEntries = new ArrayList<>();

        for (Job job : jobs) {
            Duration duration = Duration.between(job.startTime(), job.endTime());
            long durationInSeconds = duration.getSeconds();

            if (duration.compareTo(ERROR_THRESHOLD) > 0) { //precedence over warning level
                reportEntries.add(new ReportEntry(
                        String.format("Job %s (%s) exceeded error threshold with %d seconds",
                                job.pid(), job.description(), durationInSeconds),
                        Level.ERROR)
                );
            } else if (duration.compareTo(WARNING_THRESHOLD) > 0) {
                reportEntries.add(new ReportEntry(
                        String.format("Job %s (%s) ran for %d seconds",
                                job.pid(), job.description(), durationInSeconds),
                        Level.WARNING)
                );
            }
        }

        return reportEntries;
    }
}
