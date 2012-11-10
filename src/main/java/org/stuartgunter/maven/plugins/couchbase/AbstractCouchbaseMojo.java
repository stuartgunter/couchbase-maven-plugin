package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.annotations.VisibleForTesting;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;

/**
 * This class provides some common functionality that all Couchbase Mojos will benefit from.
 */
public abstract class AbstractCouchbaseMojo extends AbstractMojo {

    /**
     * The Couchbase Server host to connect to (e.g. "http://localhost:8091")
     *
     * @since 1.0.0
     */
    @Parameter(property = "couchbase.host", required = true)
    protected String host;

    /**
     * The username to connect to Couchbase Server
     *
     * @since 1.0.0
     */
    @Parameter(property = "couchbase.username", required = true)
    protected String username;

    /**
     * The password to connect to Couchbase Server
     *
     * @since 1.0.0
     */
    @Parameter(property = "couchbase.password", required = true)
    protected String password;

    /**
     * Indicates whether the build should fail in the event of an error
     *
     * @since 1.0.0
     */
    @Parameter(defaultValue = "true")
    private boolean failOnError;

    protected CouchbaseRestClient getCouchbaseClient() {
        return new CouchbaseRestClient(host, username, password, getLog());
    }

    protected void logFailure(CouchbaseException ex) throws MojoExecutionException {
        if (failOnError) {
            throw new MojoExecutionException(ex.getMessage(), ex);
        } else {
            getLog().error(ex.getMessage());
            for (Map.Entry<String, String> error : ex.getErrors().entrySet()) {
                getLog().error("\t" + error.getKey() + ":\t" + error.getValue());
            }
        }
    }

    @VisibleForTesting
    void setFailOnError(boolean failOnError) {
        this.failOnError = failOnError;
    }
}
