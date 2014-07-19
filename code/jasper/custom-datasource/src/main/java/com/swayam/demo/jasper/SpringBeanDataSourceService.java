package com.swayam.demo.jasper;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jaspersoft.jasperserver.api.metadata.jasperreports.service.ReportDataSourceService;

public class SpringBeanDataSourceService implements ReportDataSourceService {

    private final ApplicationContext applicationContext;
    private final String beanName;
    private final String methodName;

    public SpringBeanDataSourceService(ApplicationContext applicationContext, String beanName, String methodName) {
	this.applicationContext = applicationContext;
	this.beanName = beanName;
	this.methodName = methodName;
    }

    @Override
    public void closeConnection() {
	// do nothing
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setReportParameterValues(@SuppressWarnings("rawtypes") Map paramMap) {
	paramMap.put(SpringBeanQueryExecutorFactory.APPLICATION_CONTEXT, applicationContext);
	paramMap.put(SpringBeanQueryExecutorFactory.BEAN_NAME, beanName);
	paramMap.put(SpringBeanQueryExecutorFactory.METHOD_NAME, methodName);
    }

}
