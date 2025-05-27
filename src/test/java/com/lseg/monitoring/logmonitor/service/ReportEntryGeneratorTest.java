package com.lseg.monitoring.logmonitor.service;

import com.lseg.monitoring.logmonitor.model.Job;
import com.lseg.monitoring.logmonitor.model.Level;
import com.lseg.monitoring.logmonitor.model.ReportEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReportEntryGenerator.class)
public class ReportEntryGeneratorTest {
    @Autowired
    private ReportEntryGenerator reportEntryGenerator;

    @Test
    public void generateReportEntries_shouldProduceWarningAndError() {
        Job warnJob = new Job("100", "warnTask",
                LocalTime.of(0, 0, 0), LocalTime.of(0, 7, 0));
        Job errorJob = new Job("200", "errorTask",
                LocalTime.of(0, 0, 0), LocalTime.of(0, 12, 0));

        List<ReportEntry> entries = reportEntryGenerator.generateReportEntries(List.of(warnJob, errorJob));

        assertEquals(2, entries.size(), "Should produce two report entries");

        ReportEntry first = entries.get(0);
        assertEquals(Level.WARNING, first.level());
        assertTrue(first.message().contains("ran for 420 seconds"));

        ReportEntry second = entries.get(1);
        assertEquals(Level.ERROR, second.level());
        assertTrue(second.message().contains("exceeded error threshold"));
    }
}
