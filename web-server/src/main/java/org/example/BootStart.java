package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"${migoo.framework.package-name}", "${migoo.framework.biz-package-name}"})
public class BootStart {

    public static void main(String[] args) {
        SpringApplication.run(BootStart.class, args);
    }
}
