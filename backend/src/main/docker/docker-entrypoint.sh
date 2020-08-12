#!/bin/bash

java \
    -Djava.security.egd=file:/dev/./urandom \
    -Djava.security.manager \
    -Djava.security.policy==/app/AllPermissions.policy \
    -Djava.security.debug=access \
    -Djdk.io.permissionsUseCanonicalPath=true \
    -Dsun.misc.URLClassPath.disableJarChecking=true \
    -jar /app/backend.jar \
    --spring.profiles.active=docker
