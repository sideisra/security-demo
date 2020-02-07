# security-demo
A demo app for security techniques based on Spring Boot and React

#prepare
Create Certificates for HTTPS:

run in bash:
```
cd certs
new.sh
```

#run
## run backend
```
cd backend
gradlew bootRun
```
## run client
```
cd client
gradlew run
```
## renew backend certificate
Generates a new backend certificate. Client can still connect to the backend after that because it trusts the root ca.
```
cd certs
renew_backend_cert.sh
```

#Java Security Manager
## sources
* https://docs.oracle.com/javase/8/docs/technotes/guides/security/troubleshooting-security.html
## Start app with SecurityManager Policy
```
-Djava.security.manager
-Djava.security.policy==BackendPermissions.policy
-Djava.security.debug=access
-Djdk.io.permissionsUseCanonicalPath=true
```

`-Djdk.io.permissionsUseCanonicalPath=true` is necessary otherwise the backend is not able to load the HTTPS Certificate:
```
Caused by: java.io.IOException: Failed to load keystore type [PKCS12] with path [file:/<path to keystore>/certAndKey.p12] due to [access denied ("java.io.FilePermission" "<java.io.tmpdir>\tomcat.3606642031625470347.8443\file:\<path to keystore>\certAndKey.p12" "read")]
	at org.apache.tomcat.util.net.SSLUtilBase.getStore(SSLUtilBase.java:221) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.net.SSLHostConfigCertificate.getCertificateKeystore(SSLHostConfigCertificate.java:206) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.net.SSLUtilBase.getKeyManagers(SSLUtilBase.java:272) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.net.SSLUtilBase.createSSLContext(SSLUtilBase.java:239) ~[tomcat-embed-core-9.0.19.jar:9.0.19]
	at org.apache.tomcat.util.net.AbstractJsseEndpoint.createSSLContext(AbstractJsseEndpoint.java:97) ~[tomcat-embed-core-9.0.19.jar:9.0.19]

```
