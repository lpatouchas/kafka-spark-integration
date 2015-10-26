package com.kafka.consumer;

public interface KafkaConsumer {

	void consume();

	String getTopic();

}
