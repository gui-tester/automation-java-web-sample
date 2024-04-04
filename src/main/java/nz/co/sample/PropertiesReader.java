package nz.co.sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger LOGGER = LogManager.getLogger(PropertiesReader.class.getName());

    private PropertiesReader(){}

    public static Properties getProperties(String propertyFileName) throws IOException {
        Properties properties = new Properties();
        InputStream input;
        input = PropertiesReader.class.getClassLoader().getResourceAsStream(propertyFileName);
        if(input==null){
            LOGGER.error("Sorry, unable to find " + propertyFileName);
            return null;
        }

        //load a properties file from class path, inside static method
        properties.load(input);
        return properties;
    }

}
