package com.lseg.monitoring.logmonitor.ideas.rules;

import com.lseg.monitoring.logmonitor.ideas.rules.core.Rule;
import com.lseg.monitoring.logmonitor.ideas.rules.core.RuleDefinition;
import com.lseg.monitoring.logmonitor.ideas.rules.exception.ValidationException;
import com.lseg.monitoring.logmonitor.model.Job;
import com.lseg.monitoring.logmonitor.model.Level;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RuleDefinition(level = Level.WARNING)
public class LongRunningJobRule implements Rule<Job> {
    private static final Duration WARNING_THRESHOLD = Duration.ofMinutes(5);

    @Override
    public void validate(Job job) throws ValidationException {
        Duration duration = Duration.between(job.startTime(), job.endTime());

        if (duration.compareTo(WARNING_THRESHOLD) > 0) {
            long durationInSeconds = duration.getSeconds();
            throw new ValidationException(
                    String.format("Job %s (%s) ran for %d seconds", job.pid(), job.description(), durationInSeconds)
            );
        }
    }
}