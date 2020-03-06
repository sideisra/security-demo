FROM openjdk:11-jre-slim
RUN apt-get update \
    && apt-get install -y \
        curl
COPY backend.jar /app/backend.jar
COPY AllPermissions.policy /app/AllPermissions.policy

COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]
