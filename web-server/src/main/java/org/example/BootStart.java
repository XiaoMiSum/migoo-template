package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"${migoo.info.base-package}"})
public class BootStart {

    public static void main(String[] args) {
        SpringApplication.run(BootStart.class, args);
    }
}
