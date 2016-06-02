package kz.project.carrental.manager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.File;

public class Log4jManager {
    private static final String NO_CONFIG_FILE =
            "*** No log4j-xml-location init param, so initializing log4j with BasicConfigurator";
    private static final String INIT_WITH = "Initializing log4j with: ";
    private static final String CONFIG_FILE_NOT_FOUND = "file not found, so initializing log4j with BasicConfigurator";
    private static final String LOG4J_XML_LOCATION = "log4j-xml-location";


    /**
     * Configures the logger log4j by ServletConfig. ServletConfig contains path to log4j.xml config file.
     * Path to log4j.xml specified in the variable "log4j-xml-location".
     *
     * @param config ServletConfig
     */
    public static void init(ServletConfig config){
        String log4jLocation = config.getInitParameter(LOG4J_XML_LOCATION);

        ServletContext sc = config.getServletContext();
        if (log4jLocation == null) {
            System.err.println(NO_CONFIG_FILE);
            BasicConfigurator.configure();
        } else {
            String webAppPath = sc.getRealPath("/");
            String log4jXml = webAppPath + log4jLocation;
            File file = new File(log4jXml);
            if (file.exists()) {
                System.out.println(INIT_WITH + log4jXml);
                DOMConfigurator.configure(log4jXml);
            } else {
                System.err.println("*** " + log4jXml + CONFIG_FILE_NOT_FOUND);
                BasicConfigurator.configure();
            }
        }
    }

}

