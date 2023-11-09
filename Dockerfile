FROM openjdk:17

ENV MINECRAFT_VERSION=1.20.2
ENV PAPER_BUILD=280
ENV CHUNKY_VERSION=1.3.92
ENV JAVA_ARGS="-Xmx2G"

ENV SEED=""

WORKDIR /srv/
COPY ./docker-data .
COPY ./chunky-dedicated/target/chunky-dedicated-*.jar ./plugins
ENTRYPOINT ./entrypoint.sh
