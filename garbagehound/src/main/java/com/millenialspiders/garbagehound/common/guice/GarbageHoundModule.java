package com.millenialspiders.garbagehound.common.guice;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.millenialspiders.garbagehound.common.config.AppConfig;

/**
 * Responsible for producing dependencies for GarbageHound
 */
public class GarbageHoundModule extends AbstractModule {
    private static final String PROPERTIES_GCLOUD = "config/common.gcloud.properties";

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public AppConfig provideGCloudAppConfig() throws IOException {
        return getAppConfig(PROPERTIES_GCLOUD);
    }

    @Provides
    @Singleton
    public GcsService provideGcsService() {
        return GcsServiceFactory.createGcsService(new RetryParams.Builder()
                .initialRetryDelayMillis(10)
                .retryMaxAttempts(10)
                .totalRetryPeriodMillis(15000)
                .build());
    }

    private AppConfig getAppConfig(String configName) throws IOException {
        try (InputStream fis = GarbageHoundModule.class.getClassLoader().getResourceAsStream(configName)) {
            Properties properties = new Properties();
            properties.load(fis);

            return AppConfig.AppConfigBuilder.newBuilder()
                    .withDbUserName(properties.getProperty("db.username"))
                    .withDbPassword(properties.getProperty("db.password"))
                    .withHostname(properties.getProperty("db.hostname"))
                    .build();
        }
    }

}
