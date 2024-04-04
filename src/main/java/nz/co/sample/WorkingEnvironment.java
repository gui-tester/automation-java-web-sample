package nz.co.sample;

import java.io.IOException;
import java.util.Properties;

public class WorkingEnvironment {

    private WorkingEnvironment() {}

    public static Properties getProperties() throws IOException {
        return getProperties(".test.properties");
    }

    public static Properties getProperties(String suffix) throws IOException {
        String systemEnvironment = System.getenv().get("env");

        if(systemEnvironment == null || "".equals(systemEnvironment)) {
            systemEnvironment = System.getenv().get("ENV");
            if(systemEnvironment == null || "".equals(systemEnvironment)) {
                systemEnvironment = "EVOTST";
            }
        }

        String resource = systemEnvironment + suffix;

        return PropertiesReader.getProperties(resource);
    }

}
