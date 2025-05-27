package com.lseg.monitoring.logmonitor.service;

import com.lseg.monitoring.logmonitor.model.Job;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogParser {
    public List<Job> parseLogFile(String csvPath) throws IOException {
        List<Job> jobs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            Map<String, LocalTime> jobsStartTime = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);

                LocalTime timestamp = LocalTime.parse(parts[0].trim());
                String jobDescription = parts[1].trim();
                String entryType = parts[2].trim();
                String pid = parts[3].trim();

                if ("START".equalsIgnoreCase(entryType)) {
                    jobsStartTime.put(pid, timestamp);
                } else if ("END".equalsIgnoreCase(entryType)) {
                    LocalTime start = jobsStartTime.remove(pid);
                    if (start != null) {
                        jobs.add(new Job(pid, jobDescription, start, timestamp));
                    }
                }
            }
        }

        return jobs;
    }
}
