package com.kafka.producer;

import java.util.Properties;

import org.springframework.stereotype.Service;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

@Service
public class KafkaProducer {
	private kafka.javaapi.producer.Producer<String, String> producer;
	private final Properties props = new Properties();

	public void send(final String topic, final String key, final String msg) {
		this.props.put("metadata.broker.list", "170.118.146.163:9092");
		this.props.put("serializer.class", "kafka.serializer.StringEncoder");
		this.props.put("request.required.acks", "1");
		this.producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(this.props));

		final String messageStr = new String(msg);
		this.producer.send(new KeyedMessage<String, String>(topic, key, messageStr));
		this.producer.close();
	}
}
