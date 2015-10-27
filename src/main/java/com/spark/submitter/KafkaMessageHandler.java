package com.spark.submitter;

public interface KafkaMessageHandler {

	void handleMessage() throws Exception;

	String getHandlerInfo();
}
