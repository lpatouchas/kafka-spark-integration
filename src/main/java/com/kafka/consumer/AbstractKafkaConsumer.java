package com.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spark.submitter.KafkaMessageHandler;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

public abstract class AbstractKafkaConsumer implements KafkaConsumer {

	private final Logger logger = LoggerFactory.getLogger(AbstractKafkaConsumer.class);

	protected String topic;

	protected ConsumerConfig consumerConfig;

	protected ConsumerConnector consumerConnector;

	protected KafkaMessageHandler kafkaMessageHandler;

	@Override
	public void consume() {
		new Thread(() -> {
			while (AbstractKafkaConsumer.this.consumerConnector == null) {
				try {
					AbstractKafkaConsumer.this.consumerConnector = kafka.consumer.Consumer
									.createJavaConsumerConnector(AbstractKafkaConsumer.this.consumerConfig);
					if (AbstractKafkaConsumer.this.consumerConnector != null) {
						break;
					} else {
						Thread.sleep(18000l);
					}
				} catch (final Exception e) {
					AbstractKafkaConsumer.this.logger.error("Could not connect to consumer for topic id :: {}",
									new Object[] { AbstractKafkaConsumer.this.topic });
				}
			}
			AbstractKafkaConsumer.this.execute();

		}).start();
	}

	public abstract void execute();

	@Override
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(final String topic) {
		this.topic = topic;
	}

	public ConsumerConfig getConsumerConfig() {
		return this.consumerConfig;
	}

	public void setConsumerConfig(final ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}

	public ConsumerConnector getConsumerConnector() {
		return this.consumerConnector;
	}

	public void setConsumerConnector(final ConsumerConnector consumerConnector) {
		this.consumerConnector = consumerConnector;
	}

	public KafkaMessageHandler getKafkaMessageHandler() {
		return this.kafkaMessageHandler;
	}

	public void setKafkaMessageHandler(final KafkaMessageHandler sparkJobSubmitter) {
		this.kafkaMessageHandler = sparkJobSubmitter;
	}
}
