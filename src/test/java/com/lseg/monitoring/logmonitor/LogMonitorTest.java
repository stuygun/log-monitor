package com.lseg.monitoring.logmonitor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LogMonitorTest {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private CommandLineRunner runner;

    @TempDir
    private static Path tempDir;

    private static Path inputFile;
    private static Path outputFile;

    @Test
    void contextLoads() {
        assertNotNull(context);
        assertTrue(context.containsBean("logMonitor"));
    }

    @DynamicPropertySource
    private static void registerProperties(DynamicPropertyRegistry registry) throws Exception {
        // Prepare input CSV before context loads otherwise it will exit before the test run
        inputFile = tempDir.resolve("logs.csv");
        Files.write(inputFile, List.of(
                "10:00:00,Job-1,START,200",
                "10:06:00,Job-1,END,200",
                "22:00:00,Job-2,START,201",
                "22:12:00,Job-2,END,201"));
        outputFile = tempDir.resolve("report.txt");

        registry.add("logPath", () -> inputFile.toString());
        registry.add("reportPath", () -> outputFile.toString());
    }


    @Test
    public void integrationTestToGenerateReport() throws Exception {
        assertTrue(Files.exists(outputFile), "Report file should be created");
        List<String> lines = Files.readAllLines(outputFile);
        assertEquals(2, lines.size(), "One warning and one error entry");
        assertTrue(lines.get(0).startsWith("[WARNING]"));
        assertTrue(lines.get(1).startsWith("[ERROR]"));
    }
}
