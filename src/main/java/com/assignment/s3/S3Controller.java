package com.assignment.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Controller for basic S3 operations, such as uploading a json string
 * and retrieving its value
 */
public class S3Controller {
    private AmazonS3 s3Client;

    /**
     * Constructor
     *
     * @param accessKey accessKey for S3
     * @param secretKey secretKey for S3
     * @param region    the region of S3 in AWS
     */
    public S3Controller(String accessKey, String secretKey, Regions region) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    /**
     * Constructor for unit testing (will be removed in the future)
     *
     * @param s3Client
     */
    public S3Controller(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Upload a json string to S3
     *
     * @param bucketName     the name of the bucket in S3 in which the object will be saved
     * @param fileObjKeyName the name of the object in S3
     * @param content        a valid json string
     */
    public void putJsonString(String bucketName, String fileObjKeyName, String content) throws IOException, AmazonServiceException, SdkClientException {
        String fileName = UUID.randomUUID().toString();
        Path path = Files.write(Paths.get(fileName), content.getBytes());
        PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, path.toFile());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
        request.setMetadata(metadata);
        s3Client.putObject(request);
    }

    /**
     * Retrieves the value of a json from an S3 object
     *
     * @param bucketName     the name of the bucket in S3 from which the object should be retrieved
     * @param fileObjKeyName the name of the object in S3
     * @return
     * @throws AmazonServiceException
     * @throws SdkClientException
     */
    public String readObject(String bucketName, String fileObjKeyName) throws AmazonServiceException, SdkClientException {
        return s3Client.getObjectAsString(bucketName, fileObjKeyName);
    }
}
