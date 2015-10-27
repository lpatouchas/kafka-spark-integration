package com.kafka.producer;

import java.util.Properties;

import org.springframework.stereotype.Service;

import com.spark.submitter.KafkaMessageHandler;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

@Service
public class KafkaProducer implements KafkaMessageHandler {
	private kafka.javaapi.producer.Producer<String, String> producer;
	private final Properties props = new Properties();

	private String topic;
	private String key;
	private String msg;

	public void send(final String topic, final String key, final String msg) {
		this.props.put("metadata.broker.list", "170.118.146.163:9092");
		this.props.put("serializer.class", "kafka.serializer.StringEncoder");
		this.props.put("request.required.acks", "1");
		this.producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(this.props));

		final String messageStr = new String(msg);
		this.producer.send(new KeyedMessage<String, String>(topic, key, messageStr));
		this.producer.close();
	}

	@Override
	public void handleMessage() throws Exception {
		this.send(this.topic, this.key, this.msg);
	}

	@Override
	public String getHandlerInfo() {
		return null;
	}

	public void setTopic(final String topic) {
		this.topic = topic;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}

}
