package org.stuartgunter.maven.plugins.couchbase;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.apache.maven.plugin.logging.Log;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class CouchbaseRestClientTest {

    @Mock
    private Log mockMavenLog;

    @Mock
    private WebResource.Builder mockWebResourceBuilder;

    @Mock
    private ClientResponse mockClientResponse;

    @Mock
    private Client mockClient;

    private CouchbaseRestClient couchbaseClient;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    public void shouldInitialiseWithDebugLogging() {
        when(mockMavenLog.isDebugEnabled()).thenReturn(true);

        couchbaseClient = new TestCouchbaseRestClient("host", "username", "password", mockMavenLog);

        verify(mockClient).addFilter(any(LoggingFilter.class));
    }

    public void shouldInitialiseWithoutDebugLogging() {
        when(mockMavenLog.isDebugEnabled()).thenReturn(false);

        couchbaseClient = new TestCouchbaseRestClient("host", "username", "password", mockMavenLog);

        verify(mockClient, never()).addFilter(any(ClientFilter.class));
    }

    class TestCouchbaseRestClient extends CouchbaseRestClient {

        public TestCouchbaseRestClient(String host, String username, String password, Log mavenLog) {
            super(host, username, password, mavenLog);
        }

        @Override
        protected Client createClient() {
            return mockClient;
        }

        @Override
        protected WebResource.Builder createWebResource(String path) {
            return mockWebResourceBuilder;
        }
    }
}
