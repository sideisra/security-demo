package de.sideisra.securitydemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts the Keycloak OAuth2 token to a Spring Security {@link Authentication}.
 */
public class SecurityDemoUserAuthenticationConverter implements UserAuthenticationConverter {

  private static final Logger LOG = LoggerFactory.getLogger(SecurityDemoUserAuthenticationConverter.class);
  private final ObjectMapper objectMapper;

  public SecurityDemoUserAuthenticationConverter(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Map<String, ?> convertUserAuthentication(final Authentication userAuthentication) {
    // because we never need a conversion into jwt this implementation is empty
    return new HashMap<>();
  }

  @Override
  public Authentication extractAuthentication(final Map<String, ?> map) {
    final var userDetails = objectMapper.convertValue(map, SecurityDemoUserDetails.class);
    if (userDetails.getUsername() == null || userDetails.getUsername().isEmpty()) {
      LOG.warn("because no username is given, no authentication is created");
      return null;
    }
    return new UsernamePasswordAuthenticationToken(userDetails, "N/A", userDetails.getAuthorities());
  }
}
