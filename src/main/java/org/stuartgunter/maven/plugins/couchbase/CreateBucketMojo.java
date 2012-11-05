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

    /**
     * The name of the bucket to create
     */
    @Parameter(required = true)
    private String bucketName;

    /**
     * The type of bucket to create
     */
    @Parameter(defaultValue = "memcached")
    private String bucketType;

    /**
     * The RAM quota to assign to the bucket
     */
    @Parameter(defaultValue = "100")
    private String ramQuotaMB;

    /**
     * The number of replicas to use for this bucket (only relevant for couchbase buckets)
     */
    @Parameter(defaultValue = "1")
    private String replicaNumber;

    /**
     * The auth type to use
     */
    @Parameter(defaultValue = "none")
    private String authType;

    /**
     * The proxy port to assign to this bucket
     */
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
