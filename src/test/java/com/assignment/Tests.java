package com.assignment;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.assignment.s3.S3Controller;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

public class Tests {

    private static final Regions REGION = Regions.EU_CENTRAL_1; // EU (Frankfurt)

    // TODO - remove from here and use beans for unit tests
    private static S3Controller s3Controller;
    private static RestControllerImpl restControllerImpl;
    private static final String SECRET_KEY = "MHbc0pD1jeRTmZTDfsTZipPK9hcjFogC4AbsT35U";
    private static final String ACCESS_KEY = "AKIAIHJGXBA52FSPWPEA";

    /**
     * Initiates an S3Controller and RestController
     */
    @BeforeClass
    public static void init() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        s3Controller = new S3Controller(s3Client);
        restControllerImpl = new RestControllerImpl(s3Controller);
    }

    /**
     * Tests {@link RestControllerImpl#handlePost(String)}
     */
    @Test
    public void testPost() {
        boolean isSuccesful = restControllerImpl.handlePost("{\"hello\": \"world\"}");
        Assert.assertTrue(isSuccesful);
    }

    /**
     * Tests {@link RestControllerImpl#handleGet()}}
     */
    @Test
    public void testFullFlow() {
        String json = "{\"hello\": \"" + UUID.randomUUID().toString() + "\"}";
        boolean isSuccesful = restControllerImpl.handlePost(json);
        Assert.assertTrue(isSuccesful);

        String s = restControllerImpl.handleGet();
        Assert.assertEquals(json, s);
    }
}