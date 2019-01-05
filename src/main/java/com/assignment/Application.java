package com.assignment;

import com.amazonaws.regions.Regions;
import com.assignment.s3.S3Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static final Regions REGION = Regions.EU_CENTRAL_1; // EU (Frankfurt)

    // Keys of an IAM with permissions only to the relevant bucket
    private static final String SECRET_KEY = "MHbc0pD1jeRTmZTDfsTZipPK9hcjFogC4AbsT35U";
    private static final String ACCESS_KEY = "AKIAIHJGXBA52FSPWPEA";


    /**
     * Main function for Springboot application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * A bean that provides a controller to AWS S3
     *
     * @return
     */
    @Bean
    public S3Controller s3Controller() {
        return new S3Controller(ACCESS_KEY, SECRET_KEY, REGION);
    }
}
