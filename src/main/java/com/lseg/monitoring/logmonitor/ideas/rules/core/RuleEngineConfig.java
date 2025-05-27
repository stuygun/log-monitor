package com.lseg.monitoring.logmonitor.ideas.rules.core;

import com.lseg.monitoring.logmonitor.model.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RuleEngineConfig {
    @Bean
    public RuleEngine<Job> ruleEngine(List<Rule<Job>> rules) { //to collect all the beans implemeting Rule<Job>
        return new RuleEngine<>(rules);
    }
}
