package de.sideisra.securitydemo.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Authentication principal for Spring Security.
 */
@JsonDeserialize(using = SecurityDemoUserDetailsDeserializer.class)
public class SecurityDemoUserDetails implements UserDetails {

  private String username;
  private String surname;
  private String forename;
  private String email;
  private Set<GrantedAuthority> authorities = Set.of();

  public SecurityDemoUserDetails() {
  }

  public SecurityDemoUserDetails(final String username, final String surname, final String forename, final String email,
      final Set<GrantedAuthority> authorities) {
    this.username = username;
    this.surname = surname;
    this.forename = forename;
    this.email = email;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(final Set<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(final String forename) {
    this.forename = forename;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  // CHECKSTYLE:OFF generated
  @Override
  public String toString() {
    return "SecurityDemoUserDetails{"
        + "username='" + username + '\''
        + ", surname='" + surname + '\''
        + ", forename='" + forename + '\''
        + ", email='" + email + '\''
        + ", authorities=" + authorities
        + '}';
  }
  // CHECKSTYLE:on
}
