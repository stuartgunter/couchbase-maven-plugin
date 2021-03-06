package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.annotations.VisibleForTesting;
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
     *
     * @since 1.0.0
     */
    @Parameter(required = true)
    private String bucketName;

    /**
     * The type of bucket to create
     *
     * @since 1.0.0
     */
    @Parameter(defaultValue = "memcached")
    private String bucketType;

    /**
     * The RAM quota to assign to the bucket
     *
     * @since 1.0.0
     */
    @Parameter(defaultValue = "100")
    private String ramQuotaMB;

    /**
     * The number of replicas to use for this bucket (only relevant for couchbase buckets)
     *
     * @since 1.0.0
     */
    @Parameter(defaultValue = "1")
    private String replicaNumber;

    /**
     * The auth type to use
     *
     * @since 1.0.0
     */
    @Parameter(defaultValue = "none")
    private String authType;

    /**
     * The proxy port to assign to this bucket
     *
     * @since 1.0.0
     */
    @Parameter(required = true)
    private String proxyPort;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getCouchbaseClient().createBucket(bucketName, bucketType, authType, ramQuotaMB, replicaNumber, proxyPort);
            getLog().info("Created bucket '" + bucketName + "'");
        } catch (CouchbaseException ex) {
            logFailure(ex);
        }
    }

    @VisibleForTesting
    void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @VisibleForTesting
    void setBucketType(String bucketType) {
        this.bucketType = bucketType;
    }

    @VisibleForTesting
    void setRamQuotaMB(String ramQuotaMB) {
        this.ramQuotaMB = ramQuotaMB;
    }

    @VisibleForTesting
    void setReplicaNumber(String replicaNumber) {
        this.replicaNumber = replicaNumber;
    }

    @VisibleForTesting
    void setAuthType(String authType) {
        this.authType = authType;
    }

    @VisibleForTesting
    void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }
}
