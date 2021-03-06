package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.annotations.VisibleForTesting;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Deletes a bucket from a running Couchbase Server instance. The mojo requires the target Couchbase Server
 * instance to be running and accessible at the configured host and port.
 */
@Mojo(name = "delete-bucket", requiresProject = false)
public class DeleteBucketMojo extends AbstractCouchbaseMojo {

    /**
     * The name of the bucket to delete
     *
     * @since 1.0.0
     */
    @Parameter(required = true)
    private String bucketName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getCouchbaseClient().deleteBucket(bucketName);
            getLog().info("Deleted bucket '" + bucketName + "'");
        } catch (CouchbaseException ex) {
            logFailure(ex);
        }
    }

    @VisibleForTesting
    void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
