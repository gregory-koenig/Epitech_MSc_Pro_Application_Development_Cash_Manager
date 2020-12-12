FROM maven:3-openjdk-11-slim

RUN apt-get update && \
    apt-get install -yq wget unzip && \
    wget https://github.com/vishnubob/wait-for-it/archive/master.zip && \
    unzip master.zip && \
    mv wait-for-it-master/wait-for-it.sh /usr/bin/wait-for-it && \
    chmod +x /usr/bin/wait-for-it && \
    rm master.zip && \
    rm -r wait-for-it-master

WORKDIR /usr/app/cashmanager

COPY . .

CMD wait-for-it --timeout=0 ${DB_HOST}:${DB_PORT} && mvn spring-boot:run
