package org.kmd.platform.fundamental.config;

import org.apache.commons.io.IOUtils;
import java.io.*;
import java.util.Properties;

public class FundamentalConfigProvider {

    public static final String FILE_PREFIX = "file:";
    public static final String CLASSPATH_PREFIX = "classpath:";
    public static final String DEFAULT_CONFIG_PATH = "classpath:/fundamental.config.properties";

    private static Properties prop = null;

    public static Properties getProp() {
        if (prop == null) {
            init();
        }
        return prop;
    }

    public static void setProperties(Properties prop) {
        FundamentalConfigProvider.prop = prop;
    }

    /**
     * 返回所有的properties
     *
     * @return
     */
    public Properties getProperties() {
        return getProp();
    }

    /**
     * 返回String类型的配置结果
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        return getProp().getProperty(key);
    }

    /**
     * 返回Integer型的配置结果。如果没有找到配置，返回null
     *
     * @param key
     * @return
     */
    public static Integer getInt(String key) {
        String value = get(key);
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 返回Boolean型的配置结果。如果没有找到配置，返回false
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        String value = get(key);
        return Boolean.valueOf(value);
    }

    /**
     * 返回Double型的配置结果。如果没有找到配置，返回null
     *
     * @param key
     * @return
     */
    public static Double getDouble(String key) {
        String value = get(key);
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回Long型的配置结果。如果没有找到配置，返回null
     *
     * @param key
     * @return
     */
    public static Long getLong(String key) {
        String value = get(key);
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static synchronized void init() {
        //已经初始化，则直接返回
        if (FundamentalConfigProvider.prop != null
                && FundamentalConfigProvider.prop.size() > 0) {
            return;
        }

        Properties properties = ConfigProperties.getProperties();
        if (properties != null && properties.size() > 0) {
            FundamentalConfigProvider.prop = properties;
        } else {
            String configPath = System.getProperty(Constants.CONFIG_PATH);

            if (configPath == null) {
                configPath = System.getenv(Constants.CONFIG_PATH);
                if (configPath == null) {
                    configPath = DEFAULT_CONFIG_PATH;
                }
            }
            Properties configs = new Properties();
            FileInputStream fileInputStream = null;
            InputStream inputStream = null;
            try {
                if (configPath.startsWith(FILE_PREFIX)) {
                    configPath = configPath.substring(FILE_PREFIX.length());
                    fileInputStream = new FileInputStream(new File(configPath));
                    configs.load(fileInputStream);
                } else if (configPath.startsWith(CLASSPATH_PREFIX)) {
                    configPath = configPath
                            .substring(CLASSPATH_PREFIX.length());
                    inputStream = FundamentalConfigProvider.class
                            .getResourceAsStream(configPath);
                    configs.load(inputStream);
                }
                FundamentalConfigProvider.prop = configs;
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                IOUtils.closeQuietly(fileInputStream);
                IOUtils.closeQuietly(inputStream);
            }
        }

    }

}
