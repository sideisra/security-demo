package de.sideisra.securitydemo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SecurityLogTransformTest {

  @Test
  public void shouldTransformSingleLinesToPermissionsCorrectly() {
    assertThat(SecurityLogTransform
        .lineToPolicyEntry("access: access allowed (\"java.lang.RuntimePermission\" \"getProtectionDomain\")"))
        .isEqualTo("permission java.lang.RuntimePermission \"getProtectionDomain\";");
    assertThat(SecurityLogTransform.lineToPolicyEntry(
        "access: access allowed (\"javax.management.MBeanPermission\" \"org.apache.tomcat.util.modeler.BaseModelMBean#-[Tomcat:port=8443,type=ProtocolHandler]\" \"registerMBean\")"))
        .isEqualTo(
            "permission javax.management.MBeanPermission \"org.apache.tomcat.util.modeler.BaseModelMBean#-[Tomcat:port=8443,type=ProtocolHandler]\", \"registerMBean\";");
    assertThat(SecurityLogTransform.lineToPolicyEntry(
        "access: access allowed (\"javax.management.MBeanPermission\" \"org.apache.tomcat.util.modeler.BaseModelMBean#-[Tomcat:name=\"https-jsse-nio-8443\",type=GlobalRequestProcessor]\" \"registerMBean\")"))
        .isEqualTo(
            "permission javax.management.MBeanPermission \"org.apache.tomcat.util.modeler.BaseModelMBean#-[Tomcat:name=\\\"https-jsse-nio-8443\\\",type=GlobalRequestProcessor]\", \"registerMBean\";");
  }

  @Test
  public void shouldSkipTempDirAccessPermissions() {
    assertThat(SecurityLogTransform.skipTempDirAccess("access: access allowed (\"java.io.FilePermission\" \"C:\\Users\\disrael\\AppData\\Local\\Temp\\tomcat.16626359725385266760.8443\" \"write\")")).isFalse();
  }
}
