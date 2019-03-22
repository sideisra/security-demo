package de.sideisra.securitydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Security demo backend application
 */
@SpringBootApplication
public class SecurityDemoApp {
  public static void main(final String[] args) {
    new SpringApplication(SecurityDemoApp.class).run(args);
  }
}
