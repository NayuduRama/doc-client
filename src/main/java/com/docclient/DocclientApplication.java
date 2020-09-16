package com.docclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocClientApplication {
    /**
     * Implement CRUDS on files C => Create R => Retrieve U => Update D => Delte S => Search
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DocClientApplication.class, args);

    }

}
