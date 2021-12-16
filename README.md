# Running Kafka

Use the provided [docker-compose.yml](docker-compose.yml) in order to start Kafka as a docker container.

    docker-compose up

Connect to the running container

    docker exec -it kafka_kafka_1 /bin/bash

Create a new topic (with name test)

    /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic test

List topics

    /opt/bitnami/kafka/bin/kafka-topics.sh --list --bootstrap-server kafka:9092

Start a new producer, insert some messages separated by `Enter` and quit by pressing `Ctrl+D`

    /opt/bitnami/kafka/bin/kafka-console-producer.sh --broker-list kafka:9092 --producer.config /opt/bitnami/kafka/config/producer.properties --topic test

Collect those messages by a consumer (press `Ctrl+C` to stop consuming messages)
    
    /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic test --consumer.config /opt/bitnami/kafka/config/consumer.properties --from-beginning

Delete the topic

    /opt/bitnami/kafka/bin/kafka-topics.sh --delete --bootstrap-server kafka:9092 --topic test

# Java consumer service

Run the spring boot app and send some requests.

    curl --header "Content-Type: application/json" --request POST --data '{"id": 123,"data":"some user data"}' http://localhost:8080/kafka/publish
