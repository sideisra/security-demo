package de.sideisra.securitydemo.config;

import net.minidev.json.JSONArray;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

@Configuration
public class JwtConversionConfig {
  private static final String REALM_ACCESS_ROOT = "realm_access";
  private static final String ROLES = "roles";

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    Converter<Jwt, Collection<GrantedAuthority>> grantedAuthoritiesConverter = new Converter<>() {

      @Override
      public Collection<GrantedAuthority> convert(Jwt source) {
        final net.minidev.json.JSONObject realmAccessRoot = Optional.ofNullable(source.<net.minidev.json.JSONObject>getClaim(REALM_ACCESS_ROOT))
            .orElseThrow(() -> new RuntimeException("Missing realm_access values in JWT."));

        return Optional.ofNullable((JSONArray) realmAccessRoot.get(ROLES))
            .map(roles -> roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .collect(toSet())
            ).orElse(Collections.emptySet());
      }

      @Override
      public <U> Converter<Jwt, U> andThen(Converter<? super Collection<GrantedAuthority>, ? extends U> after) {
        return Converter.super.andThen(after);
      }
    };

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }
}
