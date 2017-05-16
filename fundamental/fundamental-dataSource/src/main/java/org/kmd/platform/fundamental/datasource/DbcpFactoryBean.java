package org.kmd.platform.fundamental.datasource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.kmd.platform.fundamental.config.FundamentalConfigProvider;


import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class DbcpFactoryBean implements FactoryBean<BasicDataSource>,
		DisposableBean, InitializingBean {


	private BasicDataSource ds;
	private Properties properties;
	private String dbname;

	@Override
	public void afterPropertiesSet() throws Exception {
        setProperties(FundamentalConfigProvider.getProp());
	}

	public BasicDataSource getObject() {
		ds = new BasicDataSource();

		this.tryToSetProperties();

		return ds;
	}

	public void destroy() {
		if (ds == null) {
			return;
		}
		try {
			ds.close();
			ds = null;
		} catch (Exception ex) {
            System.out.println("close dbcp error:"+ex);
		}
	}

	public Class<?> getObjectType() {
		return BasicDataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setDbname(String dbname) {
        this.dbname = dbname;
    }

	protected void tryToSetProperties() {
		String configPrefix = "dbcp.";
		if (dbname != null && dbname.trim().length() > 0) {
			configPrefix = configPrefix + dbname + ".";
            System.out.println("configPrefix:"+configPrefix);
		}
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
            System.out.println("error to set property : key : " + key+ ", value : " + value);
            if (!key.startsWith(configPrefix)) {
				continue;
			}
			String propertyName = key.substring(configPrefix.length());
			try {
				tryToSetProperty(propertyName, value);
			} catch (Exception ex) {

			}
		}
	}

	protected void tryToSetProperty(String propertyName, String propertyValue)
			throws Exception {
		String setterName = "set" + propertyName.substring(0, 1).toUpperCase(Locale.CHINA)
				+ propertyName.substring(1);
		Method[] methods = BasicDataSource.class.getMethods();
		for (Method method : methods) {
			if (!method.getName().equals(setterName)) {
				continue;
			}
			Class<?>[] parameterTypes = method.getParameterTypes();
			if (parameterTypes.length == 1) {
				Class<?> parameterType = parameterTypes[0];
				this.invokeSetValue(method, parameterType, propertyValue);
			}
		}
	}

	private void invokeSetValue(Method method, Class<?> parameterType,
			String propertyValue) throws Exception {
        System.out.println("name :"+method.getName()+" value :"+propertyValue);
		if (parameterType == String.class) {
			method.invoke(ds, propertyValue);
		} else if (parameterType == Integer.class || parameterType == int.class) {
			method.invoke(ds, Integer.parseInt(propertyValue));
		} else if (parameterType == Long.class || parameterType == long.class) {
			method.invoke(ds, Long.parseLong(propertyValue));
		} else if (parameterType == Boolean.class
				|| parameterType == boolean.class) {
			method.invoke(ds, Boolean.valueOf(propertyValue));
		} else {
            System.out.println("cannot process parameterType : [" + parameterType
					+ "]");
		}
	}

}
