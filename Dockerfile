FROM java:8

ADD src /data/src
ADD gradle /data/gradle
COPY build.gradle /data/build.gradle
COPY settings.gradle /data/settings.gradle
COPY gradlew /data/gradlew

WORKDIR /data
RUN ./gradlew build
RUN ./gradlew installDist
RUN cp -R build/install/tiagodeoliveira /release
RUN rm -rf /data

WORKDIR /release

EXPOSE 8080
CMD bin/tiagodeoliveira