package de.sideisra.securitydemo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Security demo backend application
 */
public class Client {
  public static void main(final String[] args) throws IOException {
    URL url = new URL("https://localhost:8443/actuator/health");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    System.out.println(con.getResponseCode() + " - " + con.getResponseMessage());
  }
}
