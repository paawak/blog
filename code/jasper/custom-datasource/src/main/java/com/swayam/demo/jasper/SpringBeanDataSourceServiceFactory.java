package com.swayam.demo.jasper;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jaspersoft.jasperserver.api.engine.jasperreports.util.CustomDataSourceDefinition;
import com.jaspersoft.jasperserver.api.engine.jasperreports.util.CustomDelegatedDataSourceServiceFactory;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.ReportDataSource;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.domain.client.CustomReportDataSourceImpl;
import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService;

public class SpringBeanDataSourceServiceFactory implements CustomDelegatedDataSourceServiceFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setCustomDataSourceDefinition(CustomDataSourceDefinition customDataSourceDefinition) {

    }

    @Override
    public ReportDataSourceService createService(ReportDataSource reportDataSource) {
	if (!(reportDataSource instanceof CustomReportDataSourceImpl)) {
	    throw new IllegalArgumentException("Expecting an instance of " + CustomReportDataSourceImpl.class + ", but found " + reportDataSource.getClass());
	}

	CustomReportDataSourceImpl customDataSource = (CustomReportDataSourceImpl) reportDataSource;
	Map map = customDataSource.getPropertyMap();
	String beanName = (String) map.get("beanName");
	String methodName = (String) map.get("methodName");
	return new SpringBeanDataSourceService(applicationContext, beanName, methodName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
	this.applicationContext = applicationContext;
    }

}
