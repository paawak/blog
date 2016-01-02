package com.swayam.demo.jini.unsecure.streaming.server.core.reggie;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jini.config.Configuration;
import net.jini.config.ConfigurationException;

abstract class SimpleAbstractConfiguration implements Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleAbstractConfiguration.class);

    @Override
    public Object getEntry(String component, String name, Class type) throws ConfigurationException {
	return getEntryInternal(component, name, type, null, null);
    }

    @Override
    public Object getEntry(String component, String name, Class type, Object defaultValue) throws ConfigurationException {
	return getEntryInternal(component, name, type, defaultValue, null);
    }

    @Override
    public Object getEntry(String component, String name, Class type, Object defaultValue, Object data) throws ConfigurationException {
	return getEntryInternal(component, name, type, defaultValue, data);
    }

    private Object getEntryInternal(String component, String name, Class type, Object defaultValue, Object data) throws ConfigurationException {

	checkConfiguration(component);

	Optional<Object> optionalResult = getEntry(name, type);

	Object result = null;

	if (optionalResult.isPresent()) {
	    result = optionalResult.get();
	}

	if ((defaultValue != null) && (defaultValue != Configuration.NO_DEFAULT)) {
	    result = defaultValue;
	}

	LOGGER.debug("component:{}, name:{}, type:{}, defaultValue:{}, data:{}, result:{}", new Object[] { component, name, type, defaultValue, data, result });

	return result;
    }

    abstract Optional<Object> getEntry(String name, Class type) throws ConfigurationException;

    abstract String getTargetedComponentName();

    private void checkConfiguration(String component) {
	if (!getTargetedComponentName().equals(component)) {
	    throw new IllegalArgumentException("The configuration from " + component + " is not supported");
	}
    }

}
