package de.sideisra.securitydemo.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.SerializationException;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

/**
 * Deserializer for Keycloak OAuth2 token.
 */
public class SecurityDemoUserDetailsDeserializer extends StdDeserializer<SecurityDemoUserDetails> {

  private static final String USERNAME = "preferred_username";
  private static final String SURNAME = "family_name";
  private static final String FORNAME = "given_name";
  private static final String EMAIL = "email";
  private static final String REALM_ACCESS_ROOT = "realm_access";
  private static final String ROLES = "roles";

  protected SecurityDemoUserDetailsDeserializer() {
    super(SecurityDemoUserDetails.class);
  }

  @Override
  public SecurityDemoUserDetails deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
    final var userDetails = new SecurityDemoUserDetails();

    final JsonNode root = jp.getCodec().readTree(jp);

    userDetails.setUsername(Optional.ofNullable(root.get(USERNAME))
        .map(JsonNode::asText)
        .orElseThrow(() -> new SerializationException("Missing username in JWT.")));

    Optional.ofNullable(root.get(EMAIL))
        .map(JsonNode::asText)
        .ifPresent(userDetails::setEmail);
    Optional.ofNullable(root.get(FORNAME))
        .map(JsonNode::asText)
        .ifPresent(userDetails::setForename);
    Optional.ofNullable(root.get(SURNAME))
        .map(JsonNode::asText)
        .ifPresent(userDetails::setSurname);

    final var realmAccessRoot = Optional.ofNullable(root.get(REALM_ACCESS_ROOT))
        .orElseThrow(() -> new SerializationException("Missing realm_access values in JWT."));

    Optional.ofNullable(realmAccessRoot.get(ROLES))
        .map(roles -> StreamSupport.stream(roles.spliterator(), false)
            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.asText()))
            .collect(toSet())
        ).ifPresent(userDetails::setAuthorities);

    return userDetails;
  }
}
