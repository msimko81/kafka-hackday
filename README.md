# Running Kafka

Use the provided [docker-compose.yml](docker-compose.yml) in order to start Kafka as a docker container.

    docker-compose up

Connect to the running container

    docker exec -it kafka_kafka_1 /bin/bash

Create a new topic (with name test)

    /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic test

Start a new producer, insert some messages separated by `Enter` and quit by pressing `Ctrl+D`

    /opt/bitnami/kafka/bin/kafka-console-producer.sh --broker-list kafka:9092 --producer.config /opt/bitnami/kafka/config/producer.properties --topic test

Collect those messages by a consumer
    
    /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic test --consumer.config /opt/bitnami/kafka/config/consumer.properties --from-beginning

