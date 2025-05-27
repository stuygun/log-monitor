package com.lseg.monitoring.logmonitor.ideas.rules.core;

import com.lseg.monitoring.logmonitor.model.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RuleDefinition {
    Level level() default Level.ERROR;
}