package org.stuartgunter.maven.plugins.couchbase;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Creates a bucket in a running Couchbase Server instance. The mojo requires the target Couchbase Server
 * instance to be running and accessible at the configured host and port.
 */
@Mojo(name = "create-bucket", requiresProject = false)
public class CreateBucketMojo extends AbstractCouchbaseMojo {

    @Parameter(required = true)
    private String bucketName;

    @Parameter(defaultValue = "memcached")
    private String bucketType;

    @Parameter(defaultValue = "100")
    private String ramQuotaMB;

    @Parameter(defaultValue = "1")
    private String replicaNumber;

    @Parameter(defaultValue = "none")
    private String authType;

    @Parameter(required = true)
    private String proxyPort;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final boolean successful = getCouchbaseClient().createBucket(bucketName, bucketType,
                authType, ramQuotaMB, replicaNumber, proxyPort);

        logOutcome(successful,
                "Created bucket '" + bucketName + "'",
                "Unable to create bucket '" + bucketName + "'");
    }
}
