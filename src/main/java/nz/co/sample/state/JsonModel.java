package nz.co.sample.state;

import nz.co.sample.ResourceReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;

public interface JsonModel {

    default String getStringByJsonPath(JsonElement model, String jsonPath, Configuration configuration) {
        String result = null;

        JsonArray values = getNodesByJsonPath(model, jsonPath, configuration);

        if (values.size() == 1) {
            result = values.get(0).getAsString();
        } else if (values.size() > 1) {
            throw new IllegalStateException("Value for jsonPath [" + jsonPath + "] returned [" + values.size() + "] values, expected 1.");
        }

        return result;
    }

    default JsonArray getNodesByJsonPath(JsonElement model, String jsonPath, Configuration configuration) {
        return JsonPath.using(configuration).parse(model).read(jsonPath);
    }

    static JsonElement getJsonFromString(String jsonBody) {
        JsonParser parser = new JsonParser();
        return parser.parse(jsonBody);
    }

    static JsonElement getJsonFromRecourse(String resourceFile) throws IOException {
        String jsonBody = ResourceReader.getResourceAsString(resourceFile);
        return getJsonFromString(jsonBody);
    }

    default void setElement(JsonObject jsonObject, String key, Object value, Configuration configuration) {
        DocumentContext doc = JsonPath.using(configuration).parse(jsonObject);
        doc.set(key, value);
    }
}
