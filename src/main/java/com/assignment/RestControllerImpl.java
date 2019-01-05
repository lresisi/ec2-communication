package com.assignment;

import com.assignment.s3.S3Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class RestControllerImpl {
    private static final String BUCKET_NAME = "argus-assignment";
    public static final String OBJ_KEY_NAME = "argus-assignment-key-name";

    @Autowired
    private S3Controller s3Controller;

    public RestControllerImpl() {
    }

    // TODO - remove, only for unit testing
    public RestControllerImpl(S3Controller s3Controller) {
        this.s3Controller = s3Controller;
    }

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
     * Handles GET requests for /api/resource by trying to read a json string from
     * an S3 bucket
     *
     * @return
     */
    @GetMapping("/api/resource")
    public String handleGet() {
        String s = null;
        try {
            s = s3Controller.readObject(BUCKET_NAME, OBJ_KEY_NAME);
        } catch (Exception e) {
            s = "Exception was caught: " + e.getMessage();
        }

        return s;
    }

    /**
     * Handles POST requests for /api/resource by trying to upload a json string into
     * an S3 bucket
     *
     * @return true iff the json was uploaded to S3 successfully
     */
    @PostMapping(path = "/api/resource", consumes = "application/json")
    public boolean handlePost(@RequestBody String s) {
        try {
            s3Controller.putJsonString(BUCKET_NAME, OBJ_KEY_NAME, s);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}