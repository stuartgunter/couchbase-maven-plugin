package org.stuartgunter.maven.plugins.couchbase;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyString;
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

    private CreateBucketMojo mojo = new TestCreateBucketMojo();

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    public void shouldCreateBucketViaRestClient() throws Exception {
        mojo.setAuthType(AUTH_TYPE);
        mojo.setBucketName(BUCKET_NAME);
        mojo.setBucketType(BUCKET_TYPE);
        mojo.setProxyPort(PROXY_PORT);
        mojo.setRamQuotaMB(RAM_QUOTA);
        mojo.setReplicaNumber(REPLICA_NUMBER);

        mojo.execute();

        verify(mockCouchbaseClient)
                .createBucket(BUCKET_NAME, BUCKET_TYPE, AUTH_TYPE, RAM_QUOTA, REPLICA_NUMBER, PROXY_PORT);
        verifyNoMoreInteractions(mockCouchbaseClient);
    }

    class TestCreateBucketMojo extends CreateBucketMojo {
        @Override
        protected CouchbaseRestClient getCouchbaseClient() {
            return mockCouchbaseClient;
        }
    }
}
