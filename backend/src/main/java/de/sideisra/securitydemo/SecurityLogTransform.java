package de.sideisra.securitydemo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class SecurityLogTransform {
  public static void main(String[] args) throws IOException {
    final Path log = Paths.get("backend/out.txt");
    final Path policyFile = Paths.get("backend/BackendPermissions.policy");
    final BufferedWriter writer = Files.newBufferedWriter(policyFile, StandardCharsets.UTF_8);
    writer.write("grant {" + System.lineSeparator());
    Files.readAllLines(log, StandardCharsets.UTF_8).stream()
        .filter(line -> line.startsWith("access: access allowed "))
        .filter(SecurityLogTransform::skipTempDirAccess)
        .filter(SecurityLogTransform::skipSocketPermissions)
        .map(SecurityLogTransform::lineToPolicyEntry)
        .distinct()
        .map(entry -> "\t" + entry)
        .forEach(policyEntry -> {
          try {
            writer.write(policyEntry + System.lineSeparator());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    addTempDirAccess(writer);
    addSocketPermissions(writer);

    writer.write("};" + System.lineSeparator());
    writer.flush();
    writer.close();
  }

  public static boolean skipTempDirAccess(final String line) {
    return !line.contains(System.getProperty("java.io.tmpdir"));
  }

  private static void addTempDirAccess(final BufferedWriter writer) throws IOException {
    writer.write("\tpermission java.io.FilePermission \"${java.io.tmpdir}\\\\-\", \"read\";" + System.lineSeparator());
    writer.write("\tpermission java.io.FilePermission \"${java.io.tmpdir}\\\\-\", \"write\";" + System.lineSeparator());
    writer
        .write("\tpermission java.io.FilePermission \"${java.io.tmpdir}\\\\-\", \"delete\";" + System.lineSeparator());
  }

  public static boolean skipSocketPermissions(final String line) {
    return !line.contains("java.net.SocketPermission");
  }

  private static void addSocketPermissions(final BufferedWriter writer) throws IOException {
    writer.write("\tpermission java.net.SocketPermission \"*\", \"connect,resolve\";" + System.lineSeparator());
    writer.write("\tpermission java.net.SocketPermission \"*\", \"listen,resolve\";" + System.lineSeparator());
    writer.write("\tpermission java.net.SocketPermission \"*\", \"accept,resolve\";" + System.lineSeparator());
  }

  public static String lineToPolicyEntry(String line) {
    System.out.println(line);
    final Pattern pattern = Pattern.compile("(\".*?\"[ )])");
    final Matcher matcher = pattern.matcher(line);
    var groups = matcher.results().map(MatchResult::group).collect(toList());

    // '"javax.management.MBeanPermission" ' -> 'javax.management.MBeanPermission'
    final String permission = groups.get(0).substring(1, groups.get(0).length() - 2);

    final var params = groups.subList(1, groups.size()).stream()
        .map(param -> param
            .substring(1, param.length() - 2)) // strip quotes surrounding to prevent them from bing masked
        .map(param -> param.replace("\\", "\\\\"))
        .map(param -> param.replace("\"", "\\\"")) // mask quotes contained in text
        .map(param -> "\"" + param + "\"") // add unmasked quotes again
        .collect(joining(", "));
    final String policyEntry = "permission " + permission + " " + params + ";";
    System.out.println(policyEntry);
    return policyEntry;
  }
}
