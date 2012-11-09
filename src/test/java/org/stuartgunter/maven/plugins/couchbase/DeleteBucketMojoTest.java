package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.collect.ImmutableMap;
import org.apache.maven.plugin.logging.Log;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class DeleteBucketMojoTest {

    private static final String BUCKET_NAME = "bucket-name";

    @Mock
    private CouchbaseRestClient mockCouchbaseClient;
    @Mock
    private Log mockMavenLog;

    @Spy
    private DeleteBucketMojo mojo = new TestDeleteBucketMojo();

    private Map<String, String> errors = ImmutableMap.of("error", "description");

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);

        mojo.setBucketName(BUCKET_NAME);
    }

    public void shouldHandleSuccessfulBucketDeletion() throws Exception {
        mojo.execute();

        verify(mockCouchbaseClient).deleteBucket(BUCKET_NAME);
        verify(mockMavenLog).info("Deleted bucket '" + BUCKET_NAME + "'");

        verifyNoMoreInteractions(mockCouchbaseClient, mockMavenLog);
    }

    public void shouldHandleUnsuccessfulBucketDeletion() throws Exception {
        final CouchbaseException ex = new CouchbaseException("Oops!", errors);
        doThrow(ex).when(mockCouchbaseClient).deleteBucket(anyString());

        mojo.execute();

        verify(mockCouchbaseClient).deleteBucket(BUCKET_NAME);
        verify(mojo).logFailure(ex);

        verifyNoMoreInteractions(mockCouchbaseClient);
    }

    class TestDeleteBucketMojo extends DeleteBucketMojo {
        @Override
        protected CouchbaseRestClient getCouchbaseClient() {
            return mockCouchbaseClient;
        }

        @Override
        public Log getLog() {
            return mockMavenLog;
        }
    }

}
