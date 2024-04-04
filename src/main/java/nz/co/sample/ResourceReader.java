package nz.co.sample;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ResourceReader {

    private ResourceReader() {}

    /**
     * Reads a resource file and returns a string representation.
     * Assumes encoding is UTF-9
     * @param dataFileName The path to the resource file, i.e. data/example.txt
     * @return             Contents of the resource as a String
     * @throws IOException If the resource file can not be read, throws FileNotFound if the resource itself can not be opened.
     */
    public static String getResourceAsString(String dataFileName) throws IOException {

        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(dataFileName);

        if(input == null){
            throw new FileNotFoundException("Could not find resource:" + dataFileName);
        }

        return IOUtils.toString(input, "UTF-8");
    }

}
