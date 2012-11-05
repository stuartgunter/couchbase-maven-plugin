package org.stuartgunter.maven.plugins.couchbase;

import org.apache.maven.plugin.logging.Log;

import java.util.logging.Logger;

/**
 * This class minimally adapts the {@link Logger} to write to the Maven {@link Log}. This is used only to allow
 * the Jersey {@link com.sun.jersey.api.client.filter.LoggingFilter} to write to the Maven log.
 *
 * Only the {@link #info(String)} method has been overridden, as this is the only method invoked from the LoggingFilter.
 */
public class MavenLoggerAdapter extends Logger {

    private final Log log;

    public MavenLoggerAdapter(String name, Log log) {
        super(name, null);
        this.log = log;
    }

    /**
     * Overridden to write info messages to the Maven debug log. This translation from info to debug is done so
     * that the log messages appear in the correct output category in the Maven build log.
     * @param msg The message to log
     */
    @Override
    public void info(String msg) {
        log.debug(msg);
    }
}