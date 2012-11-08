package org.stuartgunter.maven.plugins.couchbase;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.mockito.Answers;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class AbstractCouchbaseMojoTest {

    private static final String SUCCESS_MESSAGE = "success-message";
    private static final String FAIL_MESSAGE = "fail-message";

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private AbstractCouchbaseMojo mojo;

    @Mock
    private Log mockMavenLog;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        when(mojo.getLog()).thenReturn(mockMavenLog);
    }

    public void shouldLogSuccessMessage() throws Exception {
        mojo.logOutcome(true, SUCCESS_MESSAGE, FAIL_MESSAGE);

        verify(mockMavenLog).info(SUCCESS_MESSAGE);
        verifyNoMoreInteractions(mockMavenLog);
    }

    public void shouldLogFailureMessage() throws Exception {
        mojo.setFailOnError(false);
        mojo.logOutcome(false, SUCCESS_MESSAGE, FAIL_MESSAGE);

        verify(mockMavenLog).info(FAIL_MESSAGE);
        verifyNoMoreInteractions(mockMavenLog);
    }

    @Test(expectedExceptions = MojoExecutionException.class)
    public void shouldThrowException() throws Exception {
        mojo.setFailOnError(true);
        mojo.logOutcome(false, SUCCESS_MESSAGE, FAIL_MESSAGE);
    }
}
