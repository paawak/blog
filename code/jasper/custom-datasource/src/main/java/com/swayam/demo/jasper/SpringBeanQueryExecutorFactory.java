package com.swayam.demo.jasper;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.query.AbstractQueryExecuterFactory;
import net.sf.jasperreports.engine.query.JRQueryExecuter;

import org.springframework.context.ApplicationContext;

/**
 * In the applicationContext-rest-services.xml:
 * 
 * <pre>
 *     <util:list id="queryLanguagesCe">
 *         <value>sql</value>
 *         <value>hql</value>
 *         <value>domain</value>
 *         <value>HiveQL</value>
 *         <value>MongoDbQuery</value>
 *         <value>cql</value>
 * 	<value>SpringBeanQuery</value>
 *     </util:list>
 * </pre>
 * 
 */
public class SpringBeanQueryExecutorFactory extends AbstractQueryExecuterFactory {

    public static final String APPLICATION_CONTEXT = "APPLICATION_CONTEXT";
    public static final String BEAN_NAME = "BEAN_NAME";
    public static final String METHOD_NAME = "METHOD_NAME";

    @Override
    public Object[] getBuiltinParameters() {
	return new Object[] { APPLICATION_CONTEXT, ApplicationContext.class, BEAN_NAME, String.class, METHOD_NAME, String.class };
    }

    @Override
    public JRQueryExecuter createQueryExecuter(JasperReportsContext jasperReportsContext, JRDataset dataset, Map<String, ? extends JRValueParameter> parameters) throws JRException {
	return new SpringBeanQueryExecutor(jasperReportsContext, dataset, parameters);
    }

    @Override
    public boolean supportsQueryParameterType(String className) {
	return true;
    }

}
