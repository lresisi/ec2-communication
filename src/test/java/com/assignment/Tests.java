package com.assignment;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class Tests {

    private static RestControllerImpl restControllerImpl = new RestControllerImpl();

    /**
     * Tests {@link RestControllerImpl#handlePost(String)}
     */
    @Test
    public void testPost() throws IOException {
        boolean isSuccessful = restControllerImpl.handlePost("{\"hello\": \"world\"}");
        Assert.assertTrue(isSuccessful);
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