package com.kafka.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.consumer.KafkaConsumer;

@Service
public class KafkaConsumerService {

	@Autowired
	private List<KafkaConsumer> registeredConsumers;

	private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

	@PostConstruct
	public void startKafkaConsumers() {
		if (this.registeredConsumers != null) {

			this.registeredConsumers.stream().forEach(entry -> { //
				entry.consume(); //
				this.logger.info("Starting consumer for topic id :: {}", new Object[] { entry.getTopic() });//
			});

		} else {
			this.logger.warn("No kafka consumer defined!");
		}
	}
}
