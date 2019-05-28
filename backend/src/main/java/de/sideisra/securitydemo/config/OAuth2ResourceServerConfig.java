package de.sideisra.securitydemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sideisra.securitydemo.security.SecurityDemoUserAuthenticationConverter;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

/**
 * Configuration for http security and OAuth2.
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private final ResourceServerProperties resourceServerProperties;
  private final ResourceServerProperties resource;
  private final ObjectMapper objectMapper;

  public OAuth2ResourceServerConfig(
      final ResourceServerProperties resourceServerProperties,
      final ResourceServerProperties resource, final ObjectMapper objectMapper) {
    this.resourceServerProperties = resourceServerProperties;
    this.resource = resource;
    this.objectMapper = objectMapper;
  }

  @Override
  public void configure(final ResourceServerSecurityConfigurer resources) {
    resources.resourceId(this.resource.getResourceId());
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest()
        .fullyAuthenticated()
        .and().csrf().disable()
        .headers().frameOptions().sameOrigin();
  }

  @Bean
  public TokenStore jwkTokenStore() {
    final var converter = new DefaultAccessTokenConverter();
    converter.setUserTokenConverter(new SecurityDemoUserAuthenticationConverter(objectMapper));
    return new JwkTokenStore(resourceServerProperties.getJwk().getKeySetUri(), converter);
  }
}
