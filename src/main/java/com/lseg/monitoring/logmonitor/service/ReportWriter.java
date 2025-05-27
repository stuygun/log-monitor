package com.lseg.monitoring.logmonitor.service;

import com.lseg.monitoring.logmonitor.model.ReportEntry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ReportWriter {
    public void writeReport(String reportPath, List<ReportEntry> reportEntries) throws IOException {
        Files.write(Path.of(reportPath), reportEntries.stream().map(ReportEntry::toString).toList());
    }
}
