package nz.co.sample;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class HttpClient {
    private String url;
    private String username;
    private String password;

    private String mediaType = MediaType.APPLICATION_JSON;
    private Response response;
    private String correlationId;


    /***
     * Create the client
     * @param url       The url to be called
     * @param username  The user name, if required
     * @param password  The password, if required
     */
    public HttpClient(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /***
     * The content type, defaults to application/json
     * @return current content type
     */
    public String getMediaType() {
        return mediaType;
    }

    /***
     * Set the content type
     * @param mediaType The new content type
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    /****
     * Get the current correlation id, defaults to a UUID
     * @return cuorrelation id
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /***
     * Set the correlation id
     * @param correlationId The current correlation id
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    /***
     * Get the current URL
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /***
     * Set the url
     * @param url The url to be set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /***
     * Get the current user name
     * @return the user name
     */
    public String getUsername() {
        return username;
    }

    /***
     * Set the user name, NOTE: this is not encrypted and so should not be used with real production credentials
     * @param username  The user name to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /***
     * Get the current password in plan text
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /***
     * Set the password
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /***
     * Get the HTTP status of the last request. Throws null pointer exception if a request has not been made
     * @return The http status code
     */
    public Integer getHttpStatus() {
        if (response == null) {
            throw new NullPointerException("Attempt to get HTTP status before a request has been made.");
        }
        return response.getStatus();
    }

    /***
     * Get the HTTP status of the last request as a string. Throws null pointer exception if a request has not been made
     * @return The http status code
     */
    public String getHttpStatusString() {
        if (response == null) {
            throw new NullPointerException("Attempt to get HTTP status before a request has been made.");
        }
        return String.valueOf(response.getStatus());

    }

    /***
     * Perform a GET request
     * @return The response body
     */
    public String getRestResponse() {
        Invocation.Builder request = getHttpBuilder();
        response = request.get();
        return response.readEntity(String.class);
    }

    /***
     * Perform a POST request
     * @param body The body of the post request
     * @return The response body
     */
    public String postBody(String body) {
        Invocation.Builder request = getHttpBuilder();
        response = request.post(Entity.json(body));
        return response.readEntity(String.class);
    }

    /***
     * Perform a DELETE request
     * @return The response body
     */
    public String delete() {
        Invocation.Builder request = getHttpBuilder();
        response = request.delete();
        return response.readEntity(String.class);
    }

    /***
     * Perform a PUT request
     * @param body The body of the PUT request
     * @return The response body
     */
    public String putRequest(String body) {
        Invocation.Builder request = getHttpBuilder();
        response = request.put(Entity.json(body));
        return response.readEntity(String.class);
    }

    private Invocation.Builder getHttpBuilder() {
        Client client = ClientBuilder.newClient();

        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(username, password).build();
            client.register(feature);
        }

        WebTarget webTarget = client.target(url);

        return webTarget.request().header("MULE_CORRELATION_ID", correlationId).header("Content-Type", mediaType);
    }
}
