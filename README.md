# Build java services

## Consumer service

    cd java-consumer-service
    docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project gradle:7-jdk17 gradle build

Optionally, create a volume with gradle cache in order to speed up repeated builds

    cd java-consumer-service
    docker volume create --name gradle-cache
    docker run --rm -u gradle -v gradle-cache:/home/gradle/.gradle -v "$PWD/java-consumer-service":/home/gradle/project -w /home/gradle/project gradle:7-jdk17 gradle build

## Producer service

    cd java-producer-service
    docker run --rm -u gradle -v "$PWD":/home/gradle/project -w /home/gradle/project gradle:7-jdk17 gradle build

or with the gradle cache

    cd java-producer-service
    docker run --rm -u gradle -v gradle-cache:/home/gradle/.gradle -v "$PWD":/home/gradle/project -w /home/gradle/project gradle:7-jdk17 gradle build

# Run the example

Use the provided [docker-compose.yml](docker-compose.yml) in order to start Kafka together with producer and consumer services.

    docker-compose up

Send some requests to producer.

    curl --header "Content-Type: application/json" --request POST --data '{"id": 123,"data":"some user data"}' http://localhost:8080/kafka/publish

# Connecting to Kafka

Connect to the running container

    docker exec -it kafka_kafka_1 /bin/bash

Create a new topic (with name user_topic)

    /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic test_topic

List topics

    /opt/bitnami/kafka/bin/kafka-topics.sh --list --bootstrap-server kafka:9092

Start a new producer, insert some messages separated by `Enter` and quit by pressing `Ctrl+D`

    /opt/bitnami/kafka/bin/kafka-console-producer.sh --broker-list kafka:9092 --producer.config /opt/bitnami/kafka/config/producer.properties --topic test_topic

Collect those messages by a consumer (press `Ctrl+C` to stop consuming messages)
    
    /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic test_topic --consumer.config /opt/bitnami/kafka/config/consumer.properties --from-beginning

Delete the topic

    /opt/bitnami/kafka/bin/kafka-topics.sh --delete --bootstrap-server kafka:9092 --topic test_topic
