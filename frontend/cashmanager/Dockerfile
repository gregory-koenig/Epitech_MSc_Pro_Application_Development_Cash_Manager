FROM openjdk:11-slim

ENV ANDROID_HOME=/opt/android-sdk

WORKDIR /usr/src/cashmanager

COPY . .

RUN apt-get update && \
    apt-get install -yq wget unzip && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-6858069_latest.zip && \
    unzip commandlinetools-linux-6858069_latest.zip -d $ANDROID_HOME && \
    rm commandlinetools-linux-6858069_latest.zip && \
    mv $ANDROID_HOME/cmdline-tools $ANDROID_HOME/latest/ && \
    mkdir $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/latest/ $ANDROID_HOME/cmdline-tools && \
    yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "build-tools;30.0.2" && \
    yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses && \
    touch /root/.android/repositories.cfg && \
    ./gradlew build && \
    mkdir /usr/src/data && \
    cp app/build/outputs/apk/release/app-release.apk /usr/src/data/client.apk
