#!/bin/bash

java \
    -Djava.security.egd=file:/dev/./urandom \
    -Djava.security.manager \
    -Djava.security.policy==/app/AllPermissions.policy \
    -Djava.security.debug=access \
    -Djdk.io.permissionsUseCanonicalPath=true \
    -jar /app/backend.jar \
    --spring.profiles.active=docker \
    2> out.txt
