package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.collect.ImmutableMap;
import org.apache.maven.plugin.logging.Log;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class CreateBucketMojoTest {

    public static final String BUCKET_NAME = "bucket-name";
    public static final String AUTH_TYPE = "auth-type";
    public static final String BUCKET_TYPE = "bucket-type";
    public static final String PROXY_PORT = "proxy-port";
    public static final String RAM_QUOTA = "ram-quota";
    public static final String REPLICA_NUMBER = "replica-number";

    @Mock
    private CouchbaseRestClient mockCouchbaseClient;
    @Mock
    private Log mockMavenLog;

    @Spy
    private CreateBucketMojo mojo = new TestCreateBucketMojo();

    private Map<String, String> errors = ImmutableMap.of("error", "description");

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);

        mojo.setAuthType(AUTH_TYPE);
        mojo.setBucketName(BUCKET_NAME);
        mojo.setBucketType(BUCKET_TYPE);
        mojo.setProxyPort(PROXY_PORT);
        mojo.setRamQuotaMB(RAM_QUOTA);
        mojo.setReplicaNumber(REPLICA_NUMBER);
    }

    public void shouldHandleSuccessfulBucketCreation() throws Exception {
        mojo.execute();

        verify(mockCouchbaseClient)
                .createBucket(BUCKET_NAME, BUCKET_TYPE, AUTH_TYPE, RAM_QUOTA, REPLICA_NUMBER, PROXY_PORT);
        verify(mockMavenLog).info("Created bucket '" + BUCKET_NAME + "'");

        verifyNoMoreInteractions(mockCouchbaseClient, mockMavenLog);
    }

    public void shouldHandleUnsuccessfulBucketCreation() throws Exception {
        final CouchbaseException ex = new CouchbaseException("Oops!", errors);
        doThrow(ex)
                .when(mockCouchbaseClient)
                .createBucket(anyString(), anyString(), anyString(), anyString(), anyString(), anyString());

        mojo.execute();

        verify(mockCouchbaseClient)
                .createBucket(BUCKET_NAME, BUCKET_TYPE, AUTH_TYPE, RAM_QUOTA, REPLICA_NUMBER, PROXY_PORT);
        verify(mojo).logFailure(ex);

        verifyNoMoreInteractions(mockCouchbaseClient);
    }

    class TestCreateBucketMojo extends CreateBucketMojo {
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
