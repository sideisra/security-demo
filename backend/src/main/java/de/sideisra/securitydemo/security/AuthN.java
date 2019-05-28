package de.sideisra.securitydemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Helper class to extract authentication information form {@link SecurityContextHolder}.
 */
public class AuthN {

  private AuthN() {

  }

  public static SecurityDemoUserDetails getCurrentLoggedInUser() {
    return getPossiblyLoggedInUser().orElseThrow();
  }

  public static Optional<SecurityDemoUserDetails> getPossiblyLoggedInUser() {
    return getAuthentication()
        .map(authN -> {
          if (authN.getPrincipal() instanceof SecurityDemoUserDetails) {
            return (SecurityDemoUserDetails) authN.getPrincipal();
          } else {
            // e.g.during health check the principal is just the string "anonymousUser"
            final SecurityDemoUserDetails otherUserDetails = new SecurityDemoUserDetails();
            otherUserDetails.setUsername(authN.getPrincipal().toString());
            return otherUserDetails;
          }
        });
  }

  public static Optional<Authentication> getAuthentication() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
  }
}
