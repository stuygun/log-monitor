package com.lseg.monitoring.logmonitor.ideas.rules.core;

import com.lseg.monitoring.logmonitor.ideas.rules.exception.ValidationException;
import com.lseg.monitoring.logmonitor.model.ReportEntry;

import java.util.ArrayList;
import java.util.List;

public class RuleEngine<T> { //better to use parametrised to have a context instead of job only rule exection
    private final List<Rule<T>> rules;

    public RuleEngine(List<Rule<T>> rules) {
        this.rules = new ArrayList<>(rules);
        //TODO: Rules should be prioritised, e.g. TooLongJobRule should have a precedence over LongRunningJobRule
    }

    public List<ReportEntry> execute(T context) {
        List<ReportEntry> report = new ArrayList<>();
        for (Rule<T> rule : rules) {
            RuleDefinition def = rule.getClass().getAnnotation(RuleDefinition.class);
            if (def == null) {
                continue;
            }
            try {
                rule.validate(context);
            } catch (ValidationException e) {
                report.add(new ReportEntry(e.getMessage(), def.level()));
            }
        }
        return report;
    }
}
