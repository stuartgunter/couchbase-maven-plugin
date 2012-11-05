package org.stuartgunter.maven.plugins.couchbase;

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

    @Parameter(required = true)
    private String bucketName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final boolean successful = getCouchbaseClient().deleteBucket(bucketName);

        logOutcome(successful,
                "Deleted bucket '" + bucketName + "'",
                "Unable to delete bucket '" + bucketName + "'");
    }
}
