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
    final Path log = Paths.get("backend/access.txt");
    final Path policyFile = Paths.get("backend/BackendPermissions.policy");
    final BufferedWriter writer = Files.newBufferedWriter(policyFile, StandardCharsets.UTF_8);
    writer.write("grant {" + System.lineSeparator());
    Files.readAllLines(log, StandardCharsets.UTF_8).stream()
        .filter(line -> line.startsWith("access: access allowed "))
        .map(line -> {
          System.out.println(line);
          final Pattern pattern = Pattern.compile("(\".*?\")");
          final Matcher matcher = pattern.matcher(line);
          var groups = matcher.results().map(MatchResult::group).collect(toList());
          final String permission = groups.get(0).substring(1, groups.get(0).length() - 1);
          final var params = groups.subList(1, groups.size()).stream()
              .map(param -> param.replace("\\", "\\\\"))
              .collect(joining(", "));
          final String policyEntry = "\tpermission " + permission + " " + params + ";";
          System.out.println(policyEntry);
          return policyEntry;
        })
        .distinct()
        .forEach(policyEntry -> {
          try {
            writer.write(policyEntry + System.lineSeparator());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    writer.write("};" + System.lineSeparator());
    writer.flush();
    writer.close();
  }
}
