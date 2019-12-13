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
