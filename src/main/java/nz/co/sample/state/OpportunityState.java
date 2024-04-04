package nz.co.sample.state;

import nz.co.sample.ResourceReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;

import java.io.IOException;
import java.util.Map;

public class OpportunityState implements JsonModel {

    private static JsonParser parser = new JsonParser();
    private static final Configuration jsonPathConfiguration = Configuration.builder().jsonProvider(new GsonJsonProvider()).options(Option.SUPPRESS_EXCEPTIONS, Option.ALWAYS_RETURN_LIST).build();
    private final JsonObject model;

    private OpportunityState(JsonObject json){
        model = json;
    }

    public static OpportunityState createFromResource(String resourcePath) throws IOException {
        String jsonBody = ResourceReader.getResourceAsString(resourcePath);
        return createFromString(jsonBody);
    }

    public static OpportunityState createFromString(String jsonBody){
        if (!parser.parse(jsonBody).isJsonNull()) {
            JsonObject jsonObject = parser.parse(jsonBody).getAsJsonObject();
            return new OpportunityState(jsonObject);
        }
        else {
            return new OpportunityState(null);
        }
    }

    public JsonObject getModel() {
        return model;
    }

    public void modifyJsonFields(Map<String, String> data) {
        for(Map.Entry<String,String> e : data.entrySet()){
            setElement(getModel(), e.getKey(), e.getValue(),jsonPathConfiguration);
        }
    }
}
