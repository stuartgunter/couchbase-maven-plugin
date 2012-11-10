package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Collections;
import java.util.Map;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;

/**
 * This class provides a simple API to the Couchbase REST API, abstracting away the details of the HTTP requests
 * that must be made.
 */
public class CouchbaseRestClient {

    private final String host;
    private final String username;
    private final String password;

    private final Client restClient;

    public CouchbaseRestClient(String host, String username, String password, Log mavenLog) {
        this.host = host;
        this.username = username;
        this.password = password;

        restClient = createClient();
        if (mavenLog.isDebugEnabled()) {
            // the LoggingFilter writes INFO messages, but the MavenLoggerAdapter translates these to DEBUG messages
            restClient.addFilter(new LoggingFilter(new MavenLoggerAdapter(getClass().getSimpleName(), mavenLog)));
        }
    }

    @VisibleForTesting
    protected Client createClient() {
        return Client.create();
    }

    @VisibleForTesting
    protected WebResource.Builder createWebResource(String path) {
        return restClient.resource(host)
                .path(path)
                .header(AUTHORIZATION, "Basic " + new String(Base64.encode(username + ":" + password), Charsets.US_ASCII));
    }

    /**
     * Creates a new bucket
     * @param bucketName The name of the bucket to create
     * @param bucketType The type of bucket to create (memcached or couchbase)
     * @param authType The type of authorisation to use (none or sasl)
     * @param ramQuotaMB The RAM quota to assign to this bucket
     * @param replicaNumber The number of replicas for this bucket (only valid for couchbase buckets)
     * @param proxyPort The proxy port for this bucket
     * @throws CouchbaseException if the Couchbase request returns unsuccessfully
     */
    public void createBucket(String bucketName, String bucketType,
                                String authType, String ramQuotaMB,
                                String replicaNumber, String proxyPort) {

        MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
        formData.add("name", bucketName);
        formData.add("bucketType", bucketType);
        formData.add("authType", authType);
        formData.add("ramQuotaMB", ramQuotaMB);
        formData.add("replicaNumber", replicaNumber);
        formData.add("proxyPort", proxyPort);

        ClientResponse response = createWebResource("/pools/default/buckets")
                .post(ClientResponse.class, formData);

        if (response.getClientResponseStatus().getFamily() != SUCCESSFUL) {
            throw new CouchbaseException("Unable to create bucket '" + bucketName + "'",
                    getErrors(response.getEntity(String.class)));
        }
    }

    /**
     * Deletes a bucket
     * @param bucketName The name of the bucket to delete
     * @throws CouchbaseException if the Couchbase request returns unsuccessfully
     */
    public void deleteBucket(String bucketName) {
        ClientResponse response = createWebResource("/pools/default/buckets/" + bucketName)
                .delete(ClientResponse.class);

        if (response.getClientResponseStatus().getFamily() != SUCCESSFUL) {
            throw new CouchbaseException("Unable to delete bucket '" + bucketName + "'",
                    getErrors(response.getEntity(String.class)));
        }
    }

    private Map<String, String> getErrors(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> responseData = objectMapper.readValue(responseBody,
                    new TypeReference<Map<String, Object>>() {});

            return (Map<String, String>) responseData.get("errors");
        } catch (Exception ex) {
            return Collections.emptyMap();
        }
    }
}
