package com.lseg.monitoring.logmonitor;

import com.lseg.monitoring.logmonitor.model.Job;
import com.lseg.monitoring.logmonitor.model.ReportEntry;
import com.lseg.monitoring.logmonitor.service.LogParser;
import com.lseg.monitoring.logmonitor.service.ReportEntryGenerator;
import com.lseg.monitoring.logmonitor.service.ReportWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LogMonitor {
    public static void main(String[] args) {
        SpringApplication.run(LogMonitor.class, args);
    }

    @Bean
    public CommandLineRunner run(@Value("${logPath:}") String logPath,
                                 @Value("${reportPath:}") String reportPath,
                                 LogParser logParser,
                                 ReportEntryGenerator reportEntryGenerator,
                                 ReportWriter reportWriter) {
        return args -> {
            String in = logPath;
            String out = reportPath;

            if (in.isEmpty() && args.length >= 1) in = args[0];
            if (out.isEmpty() && args.length >= 2) out = args[1];
            if (in.isEmpty() || out.isEmpty()) {
                System.err.println("Usage: java -jar <jar> --logPath=<log.csv> --reportPath=<report.txt>");
                System.exit(1);
            }

            List<Job> jobs = logParser.parseLogFile(in);
            List<ReportEntry> reportEntries = reportEntryGenerator.generateReportEntries(jobs);
            reportWriter.writeReport(out, reportEntries);
        };
    }
}