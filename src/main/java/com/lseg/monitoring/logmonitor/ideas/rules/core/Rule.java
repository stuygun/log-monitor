package com.lseg.monitoring.logmonitor.ideas.rules.core;

import com.lseg.monitoring.logmonitor.ideas.rules.exception.ValidationException;

public interface Rule<T> {
    void validate(T context) throws ValidationException;
}
