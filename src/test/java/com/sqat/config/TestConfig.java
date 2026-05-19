package com.sqat.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public final class TestConfig {

    private static final Properties PROPS = load();

    private TestConfig() {}

    private static Properties load() {
        Properties p = new Properties();
        try (InputStream in = TestConfig.class.getResourceAsStream("/config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            p.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config.properties", e);
        }
        return p;
    }

    private static String get(String key) {
        String sys = System.getProperty(key);
        return sys != null ? sys : PROPS.getProperty(key);
    }

    public static String baseUrl() {
        return get("base.url");
    }

    public static Duration explicitWait() {
        return Duration.ofSeconds(Long.parseLong(get("explicit.wait.seconds")));
    }

    public static Duration pageLoadTimeout() {
        return Duration.ofSeconds(Long.parseLong(get("page.load.timeout.seconds")));
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless"));
    }
}
