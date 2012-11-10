package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * This exception type contains a map of errors returned from a failed Couchbase REST API request
 */
public class CouchbaseException extends RuntimeException {

    private final Map<String, String> errors;

    public CouchbaseException(String message, Map<String, String> errors) {
        super(message);
        this.errors = ImmutableMap.copyOf(errors);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
