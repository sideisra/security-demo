package de.sideisra.securitydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.security.Permission;
import java.security.Policy;
import java.security.ProtectionDomain;

/**
 * Security demo backend application
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityDemoApp {
  public static void main(final String[] args) {
////    Policy.setPolicy(new Policy() {
////
////      @Override
////      public boolean implies(ProtectionDomain domain, Permission permission) {
////        return true;
////      }
////    });
//    Policy.setPolicy(new PolicyFile());
//    System.setSecurityManager(new SecurityManager());
    new SpringApplication(SecurityDemoApp.class).run(args);
  }
}
