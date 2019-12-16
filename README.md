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
```
