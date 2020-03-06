package de.sideisra.securitydemo.model;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;
import java.util.Objects;

public class ListOwner {
  private final String eMail;
  private final String firstName;
  private final String lastName;

  public ListOwner(final String eMail, final String firstName, final String lastName) {
    this.eMail = eMail;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static ListOwner fromUserDetails(final JwtAuthenticationToken authentication) {
    final Map<String, Object> attributes = authentication.getTokenAttributes();
    return new ListOwner(
        (String) attributes.get("email"),
        (String) attributes.get("given_name"),
        (String) attributes.get("family_name")
    );
  }

  public String geteMail() {
    return eMail;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final ListOwner listOwner = (ListOwner) o;
    return Objects.equals(eMail, listOwner.eMail) &&
        Objects.equals(firstName, listOwner.firstName) &&
        Objects.equals(lastName, listOwner.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eMail, firstName, lastName);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ListOwner{");
    sb.append("eMail='").append(eMail).append('\'');
    sb.append(", firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
