package com.lseg.monitoring.logmonitor.ideas.rules;

import com.lseg.monitoring.logmonitor.ideas.rules.core.Rule;
import com.lseg.monitoring.logmonitor.ideas.rules.core.RuleDefinition;
import com.lseg.monitoring.logmonitor.ideas.rules.exception.ValidationException;
import com.lseg.monitoring.logmonitor.model.Job;
import com.lseg.monitoring.logmonitor.model.Level;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RuleDefinition(level = Level.ERROR)
public class TooLongJobRule implements Rule<Job> {
    private static final Duration ERROR_THRESHOLD = Duration.ofMinutes(10);

    @Override
    public void validate(Job job) throws ValidationException {
        Duration duration = Duration.between(job.startTime(), job.endTime());

        if (duration.compareTo(ERROR_THRESHOLD) > 0) {
            long durationInSeconds = duration.getSeconds();
            throw new ValidationException(
                    String.format("Job %s (%s) exceeded error threshold with %d seconds", job.pid(), job.description(), durationInSeconds)
            );
        }
    }
}