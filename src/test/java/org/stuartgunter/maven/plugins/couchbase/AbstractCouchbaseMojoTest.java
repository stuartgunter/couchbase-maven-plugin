package org.stuartgunter.maven.plugins.couchbase;

import com.google.common.collect.ImmutableMap;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.mockito.Answers;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class AbstractCouchbaseMojoTest {

    private static final String FAIL_MESSAGE = "fail-message";
    public static final String ERROR_1 = "error1";
    public static final String ERROR_2 = "error2";
    public static final String DESCRIPTION_1 = "description1";
    public static final String DESCRIPTION_2 = "description2";

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private AbstractCouchbaseMojo mojo;

    @Mock
    private Log mockMavenLog;

    private Map<String, String> errors = ImmutableMap.of(
            ERROR_1, DESCRIPTION_1,
            ERROR_2, DESCRIPTION_2);

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        when(mojo.getLog()).thenReturn(mockMavenLog);
    }

    public void shouldLogFailureMessage() throws Exception {
        mojo.setFailOnError(false);
        mojo.logFailure(new CouchbaseException(FAIL_MESSAGE, errors));

        verify(mockMavenLog).error(FAIL_MESSAGE);
        verify(mockMavenLog).error("\t" + ERROR_1 + ":\t" + DESCRIPTION_1);
        verify(mockMavenLog).error("\t" + ERROR_2 + ":\t" + DESCRIPTION_2);
        verifyNoMoreInteractions(mockMavenLog);
    }

    @Test(expectedExceptions = MojoExecutionException.class)
    public void shouldThrowException() throws Exception {
        mojo.setFailOnError(true);
        mojo.logFailure(new CouchbaseException(FAIL_MESSAGE, errors));
    }
}
