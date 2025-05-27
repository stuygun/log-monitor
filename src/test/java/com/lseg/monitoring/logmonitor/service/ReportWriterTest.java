package com.lseg.monitoring.logmonitor.service;

import com.lseg.monitoring.logmonitor.model.Level;
import com.lseg.monitoring.logmonitor.model.ReportEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReportWriter.class)
public class ReportWriterTest {
    @Autowired
    private ReportWriter reportWriter;

    @TempDir
    private Path tempDir;

    @Test
    public void writeReport_shouldWriteEntriesToFile() throws IOException {
        List<ReportEntry> entries = List.of(
                new ReportEntry("a", Level.WARNING),
                new ReportEntry("b", Level.ERROR)
        );
        File out = tempDir.resolve("report.txt").toFile();

        reportWriter.writeReport(out.getAbsolutePath(), entries);

        List<String> lines = Files.readAllLines(out.toPath());
        assertEquals(2, lines.size());
        assertEquals("[WARNING] a", lines.get(0));
        assertEquals("[ERROR] b", lines.get(1));
    }
}
