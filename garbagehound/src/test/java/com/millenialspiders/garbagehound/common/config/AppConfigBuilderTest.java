package com.millenialspiders.garbagehound.common.config;


import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class AppConfigBuilderTest {
    private final String username;
    private final String password;
    private final String hostname;
    private final boolean isExceptionExpected;

    public AppConfigBuilderTest(String username, String password, String hostname, boolean isExceptionExpected) {
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.isExceptionExpected = isExceptionExpected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "username", "password", "hostname", false },
                { null, "password", "hostname", true },
                { "username", null, "hostname", true },
                { "username", "password", null, true},
        });
    }

    @Test
    public void builderWithVariousParams() {
        NullPointerException exception = null;

        try {
            AppConfig config = AppConfig.AppConfigBuilder.newBuilder()
                    .withDbUserName(username)
                    .withDbPassword(password)
                    .withHostname(hostname)
                    .build();

            assertEquals(config.getDbPassword(), password);
            assertEquals(config.getDbHostname(), hostname);
            assertEquals(config.getDbUsername(), username);
        } catch (NullPointerException e) {
            exception = e;
        }

        if (isExceptionExpected && exception == null) {
            fail("Expecting exception but didn't happen");
        }

        if (!isExceptionExpected && exception != null) {
            fail("Not expecting exception but happened: " + exception.getMessage());
        }

    }

}
