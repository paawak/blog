package com.swayam.demo.jasper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;

import org.springframework.context.ApplicationContext;

public class SpringBeanQueryExecutor extends JRAbstractQueryExecuter {

    private final ApplicationContext applicationContext;
    private final Map<String, ?> reportParametrsMap;
    private final String beanName;
    private final String methodName;

    protected SpringBeanQueryExecutor(JasperReportsContext jasperReportsContext, JRDataset dataset, Map<String, ? extends JRValueParameter> parametersMap) {
	super(jasperReportsContext, dataset, parametersMap);

	reportParametrsMap = (Map<String, ?>) parametersMap.get(JRParameter.REPORT_PARAMETERS_MAP).getValue();
	applicationContext = (ApplicationContext) reportParametrsMap.get(SpringBeanQueryExecutorFactory.APPLICATION_CONTEXT);
	beanName = (String) reportParametrsMap.get(SpringBeanQueryExecutorFactory.BEAN_NAME);
	methodName = (String) reportParametrsMap.get(SpringBeanQueryExecutorFactory.METHOD_NAME);

	parseQuery();
    }

    @Override
    public JRDataSource createDatasource() throws JRException {
	String queryString = getQueryString();
	String[] queryTokens = queryString.split("=");
	Object serviceInterfaceImpl = applicationContext.getBean(beanName);

	System.out.println("SpringBeanQueryExecutor.createDatasource() queryString=" + queryString + ", serviceInterfaceImpl=" + serviceInterfaceImpl
	        + ", beanName=" + beanName + ", methodName=" + methodName);

	Method method;
	try {
	    method = serviceInterfaceImpl.getClass().getDeclaredMethod(methodName, Class.forName(queryTokens[1]));
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

	String language = (String) reportParametrsMap.get(queryTokens[0]);

	List<?> data;

	try {
	    data = (List<?>) method.invoke(serviceInterfaceImpl, Language.valueOf(language.toUpperCase()));
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}

	return new JRBeanCollectionDataSource(data);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean cancelQuery() throws JRException {
	return false;
    }

    @Override
    protected String getParameterReplacement(String parameterName) {
	return null;
    }

}
