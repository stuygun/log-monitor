package com.lseg.monitoring.logmonitor.service;

import com.lseg.monitoring.logmonitor.model.Job;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LogParser.class)
public class LogParserTest {
    @Autowired
    private LogParser parser;

    @TempDir
    private Path tempDir;

    @Test
    public void parseJobs_shouldReturnSingleJobForValidPair() throws IOException {
        File csv = tempDir.resolve("test.csv").toFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csv))) {
            bufferedWriter.write("11:35:23,scheduled task 032,START,37980\n");
            bufferedWriter.write("11:35:56,scheduled task 032,END,37980\n");
        }

        List<Job> jobs = parser.parseLogFile((csv.getAbsolutePath()));

        assertEquals(1, jobs.size(), "Should parse one job");
        Job job = jobs.get(0);
        assertEquals("37980", job.pid());
        assertEquals("scheduled task 032", job.description());
        assertEquals(LocalTime.parse("11:35:23"), job.startTime());
        assertEquals(LocalTime.parse("11:35:56"), job.endTime());
    }
}
