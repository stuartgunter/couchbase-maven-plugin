package org.stuartgunter.maven.plugins.couchbase;

import org.apache.maven.plugin.logging.Log;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class MavenLoggerAdapterTest {

    @Mock
    private Log mockMavenLog;

    @InjectMocks
    private MavenLoggerAdapter adapter;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    public void shouldWriteToMavenLog() {
        // given

        // when
        adapter.info("some message");

        // then
        verify(mockMavenLog).debug(eq("some message"));
        verifyNoMoreInteractions(mockMavenLog);
    }
}
