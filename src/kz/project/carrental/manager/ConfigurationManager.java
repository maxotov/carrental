package kz.project.carrental.manager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager extends Properties {

    private static ConfigurationManager ourInstance = new ConfigurationManager();

    private static final String INITIALIZING_CONFIGURATOR_MANAGER_WITH = "Initializing ConfiguratorManager with ";
    private static final String NO_CONFIG_FILE_FOR_CONFIGURATION_MANAGER = "No config file for ConfigurationManager";
    private static final String CONFIG_FILE_FOR_CONFIGURATION_MANAGER_NOT_EXISTS = "Config file for ConfigurationManager not exists";
    private static final String CONFIG_PROPERTIES_LOCATION = "config-properties-location";

    public static ConfigurationManager getInstance() {
        return ourInstance;
    }

    private ConfigurationManager() {
    }


    public void init(ServletConfig config) {
        String configFile = config.getInitParameter(CONFIG_PROPERTIES_LOCATION);
        if (configFile != null) {
            ServletContext sc = config.getServletContext();
            String webAppPath = sc.getRealPath("/");
            String configFileLocation = webAppPath + configFile;
            init(configFileLocation);
        } else {
            System.out.println(NO_CONFIG_FILE_FOR_CONFIGURATION_MANAGER);
        }
    }

    public void init(Properties properties) {
        this.clear();
        this.putAll(properties);
    }

    public void init(String configFile) {
        File file = new File(configFile);
        if (file.exists()) {
            System.out.println(INITIALIZING_CONFIGURATOR_MANAGER_WITH + configFile);
            Properties properties = new Properties();
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                properties.load(inputStream);
                init(properties);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println(CONFIG_FILE_FOR_CONFIGURATION_MANAGER_NOT_EXISTS);
        }
    }

}
