package com.swayam.demo.rmi.server.core.reggie;

import java.net.URL;

import net.jini.config.Configuration;
import net.jini.config.ConfigurationException;
import net.jini.config.ConfigurationFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class ConfigurationFileWithLogging implements Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationFileWithLogging.class);

    private final Configuration delegatingConfiguration;

    public ConfigurationFileWithLogging(URL pathToConfiguration) throws ConfigurationException {
        LOGGER.info("Loading configuration from: {}", pathToConfiguration);
        delegatingConfiguration = new ConfigurationFile(new String[] { pathToConfiguration.getPath() });
    }

    @Override
    public Object getEntry(String component, String name, Class type) throws ConfigurationException {
        Object result = delegatingConfiguration.getEntry(component, name, type);
        LOGGER.info("component:{}, name:{}, type:{}, result:{}", new Object[] { component, name, type, result });
        return result;
    }

    @Override
    public Object getEntry(String component, String name, Class type, Object defaultValue) throws ConfigurationException {
        Object result = delegatingConfiguration.getEntry(component, name, type, defaultValue);
        LOGGER.info("component:{}, name:{}, type:{}, defaultValue:{}, result:{}", new Object[] { component, name, type, defaultValue, result });
        return result;
    }

    @Override
    public Object getEntry(String component, String name, Class type, Object defaultValue, Object data) throws ConfigurationException {
        Object result = delegatingConfiguration.getEntry(component, name, type, defaultValue, data);
        LOGGER.info("component:{}, name:{}, type:{}, defaultValue:{}, data:{}, result:{}", new Object[] { component, name, type, defaultValue, data, result });
        return result;
    }

}
