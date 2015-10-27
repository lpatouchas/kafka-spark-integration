package com.kafka.consumer;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kafka.consumer.KafkaStream;

public class KafkaConsumerImpl extends AbstractKafkaConsumer {

	private final Logger logger = LoggerFactory.getLogger(KafkaConsumerImpl.class);

	@Override
	public void execute() {
		final Map<String, Integer> topicCountMap = new java.util.HashMap<String, Integer>();
		topicCountMap.put(this.topic, 1);
		final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = this.consumerConnector.createMessageStreams(topicCountMap);
		if (consumerMap != null && consumerMap.containsKey(this.topic)) {
			final KafkaStream<byte[], byte[]> stream = consumerMap.get(this.topic).get(0);

			stream.forEach(messageAndMetadata -> {//
				System.out.println(new String(messageAndMetadata.message()));//
				try {
					this.kafkaMessageHandler.handleMessage();
					this.consumerConnector.commitOffsets();//
					this.logger.info("Succesfully Submited Spark Job: {}", this.kafkaMessageHandler.getHandlerInfo());//
					this.logger.info("Kafka msg offset: {} ", messageAndMetadata.offset());
				} catch (final Exception e) {
					this.logger.error("Could not submit Spark Job, error: {}", e);
				}
			});

		}

	}

}
