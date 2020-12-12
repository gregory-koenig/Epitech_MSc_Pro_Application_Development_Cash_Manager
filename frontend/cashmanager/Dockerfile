FROM openjdk:11-slim

ENV ANDROID_HOME=/opt/android-sdk

ARG ANDROID_TOOLS_VERSION=6858069
ARG ANDROID_BUILD_VERSION=30.0.2
ARG APP_ROOT=/usr/src
ARG APP_NAME=cashmanager
ARG APP_PATH=$APP_ROOT/$APP_NAME
ARG VOLUME=$APP_ROOT/data/

WORKDIR ${ANDROID_HOME}

RUN apt-get update && \
    apt-get install -yq wget unzip && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_TOOLS_VERSION}_latest.zip && \
    unzip commandlinetools-linux-${ANDROID_TOOLS_VERSION}_latest.zip && \
    rm commandlinetools-linux-${ANDROID_TOOLS_VERSION}_latest.zip && \
    mv cmdline-tools/ latest/ && \
    mkdir cmdline-tools/ && \
    mv latest/ cmdline-tools/

WORKDIR ${ANDROID_HOME}/cmdline-tools/latest/bin

RUN yes | ./sdkmanager "build-tools;${ANDROID_BUILD_VERSION}" && \
    yes | ./sdkmanager --licenses && \
    touch /root/.android/repositories.cfg

WORKDIR ${APP_PATH}

COPY . .

RUN ./gradlew build && \
    mkdir ${VOLUME} && \
    cp app/build/outputs/apk/release/app-release.apk ${VOLUME}/client.apk
