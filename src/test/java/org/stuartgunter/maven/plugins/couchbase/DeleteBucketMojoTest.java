package org.stuartgunter.maven.plugins.couchbase;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class DeleteBucketMojoTest {

    private static final String BUCKET_NAME = "bucket-name";

    @Mock
    private CouchbaseRestClient mockCouchbaseClient;

    private DeleteBucketMojo mojo = new TestDeleteBucketMojo();

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    public void shouldDeleteBucketViaRestClient() throws Exception {
        mojo.setBucketName(BUCKET_NAME);

        mojo.execute();

        verify(mockCouchbaseClient).deleteBucket(BUCKET_NAME);
        verifyNoMoreInteractions(mockCouchbaseClient);
    }

    class TestDeleteBucketMojo extends DeleteBucketMojo {
        @Override
        protected CouchbaseRestClient getCouchbaseClient() {
            return mockCouchbaseClient;
        }
    }

}
