package de.sideisra.securitydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Security demo backend application
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityDemoApp {
  public static void main(final String[] args) {
    new SpringApplication(SecurityDemoApp.class).run(args);
  }
}
