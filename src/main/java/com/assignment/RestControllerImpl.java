package com.assignment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class RestControllerImpl {
    // TODO - move to Dockerfile
    private static final String FILE_NAME = "argus-assignment.json";

    @Autowired
    private List<String> otherHosts;

    /**
     * A default handler for REST requests
     *
     * @return
     */
    @RequestMapping("*")
    public String generalResource() {
        return "Welcome!\n" +
                "Usage for first run:\n" +
                "curl -X POST -H \"Content-Type: application/json\" -d '{\"something\": \"blabla\"}' http://&lt;some_instance&gt;/api/resource\n" +
                "\n" +
                "Usage for second run:\n" +
                "curl http://&lt;another_instance&gt;/api/resource";
    }

    /**
     * Handles GET requests for /api/resource by reading a Json file
     * and returning its content
     *
     * @return
     */
    @GetMapping("/api/resource")
    public String handleGet() {
        String retVal = "";
        try {
            retVal = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    /**
     * Handles POST requests for /api/resource by writing the given Json
     * into a file and updating other servers as well
     *
     * @param jsonAsString
     * @return
     */
    @PostMapping(path = "/api/resource", consumes = "application/json")
    public boolean handlePost(@RequestBody String jsonAsString) {
        try {
            writeToFile(FILE_NAME, jsonAsString);
            updateOtherHosts(jsonAsString);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Handles internal POST requests for /api/resource/internal by writing
     * the given Json into a file.
     * This resource is used internally in order to update other servers that
     * a Json string was received
     *
     * @param jsonAsString
     * @return
     */
    @PostMapping(path = "/api/resource/internal", consumes = "application/json")
    public void handlePostInternal(@RequestBody String jsonAsString) {
        try {
            writeToFile(FILE_NAME, jsonAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Iterates over all of the other known hosts and sends an internal
     * POST request in order to update them
     *
     * @param jsonAsString
     * @throws IOException
     */
    private void updateOtherHosts(String jsonAsString) throws IOException {
        if (otherHosts != null) {
            for (String host : otherHosts) {
                sendInternalPostRequest(host, jsonAsString);
            }
        }
    }

    /**
     * Sends an internal POST request to a given host in order to
     * update it with a new Json field
     *
     * @param host
     * @param jsonAsString
     * @return
     * @throws IOException
     */
    private HttpResponse sendInternalPostRequest(String host, String jsonAsString) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://" + host + "/api/resource/internal");
        StringEntity params = new StringEntity(jsonAsString);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        return httpClient.execute(request);
    }


    /**
     * Writes the given string into a the given file
     *
     * @param filename
     * @param s
     * @throws IOException
     */
    private void writeToFile(String filename, String s) throws IOException {
        Files.write(Paths.get(filename), s.getBytes());
    }


    /**
     * Handle errors
     *
     * @return
     */
    @ExceptionHandler
    public String handleErrors() {
        return "Oops! An error was caught";
    }
}