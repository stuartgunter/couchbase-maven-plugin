package org.stuartgunter.maven.plugins.couchbase;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * This class provides some common functionality that all Couchbase Mojos will benefit from.
 */
public abstract class AbstractCouchbaseMojo extends AbstractMojo {

    @Parameter(property = "couchbase.host", required = true)
    protected String host;

    @Parameter(property = "couchbase.username", required = true)
    protected String username;

    @Parameter(property = "couchbase.password", required = true)
    protected String password;

    @Parameter(defaultValue = "true")
    private boolean failOnError;

    protected CouchbaseRestClient getCouchbaseClient() {
        return new CouchbaseRestClient(host, username, password, getLog());
    }

    protected void logOutcome(boolean successful, String successMsg, String failMsg) throws MojoExecutionException {
        if (successful) {
            getLog().info(successMsg);
        } else if (failOnError) {
            throw new MojoExecutionException(failMsg);
        } else {
            getLog().info(failMsg);
        }
    }
}
