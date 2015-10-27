package com.spark.submitter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.kafka.producer.KafkaProducer;
import com.spark.submitter.KafkaMessageHandler;
import com.spark.submitter.rest.objects.SparkDriverState;
import com.spark.submitter.rest.objects.SparkJobStatusResponse;
import com.spark.submitter.rest.objects.SparkRestJobConfiguration;
import com.spark.submitter.rest.objects.SparkRestJobResponse;

public class SparkRestJobSubmmiter implements KafkaMessageHandler {

	private final Logger logger = LoggerFactory.getLogger(SparkRestJobSubmmiter.class);

	private static final String JOB_SUBMISSION_URL = "http://170.118.146.163:6066/v1/submissions/create";

	private static final String JOB_STATUS_URL = "http://170.118.146.163:6066/v1/submissions/status/";

	@Autowired
	private KafkaProducer kafkaProducer;

	private SparkRestJobConfiguration createSubmissionRequest;

	private String outputTopic;

	private Long checkJobStatusInterval;

	private RestTemplate restTemplate;

	@Override
	public void handleMessage() throws Exception {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<SparkRestJobConfiguration> request = new HttpEntity<>(this.createSubmissionRequest, headers);

		final SparkRestJobResponse createSubmissionResponse = this.restTemplate.postForObject(SparkRestJobSubmmiter.JOB_SUBMISSION_URL, request,
						SparkRestJobResponse.class);

		final SparkJobStatusResponse ssr = this.monitorSubmittedJob(this.restTemplate, createSubmissionResponse);

		System.out.println(ssr);
		this.informKafka(ssr);

	}

	private SparkJobStatusResponse monitorSubmittedJob(final RestTemplate restTemplate, final SparkRestJobResponse createSubmissionResponse)
					throws InterruptedException {
		SparkJobStatusResponse ssr = null;
		do {
			ssr = restTemplate.getForObject(SparkRestJobSubmmiter.JOB_STATUS_URL + createSubmissionResponse.getSubmissionId(),
							SparkJobStatusResponse.class);
			if (SparkDriverState.RUNNING.equals(ssr.getDriverState())) {
				System.out.println("Spark Job Still Running");
				this.logger.info("Spark Job Still Running");
			}
			Thread.sleep(this.checkJobStatusInterval);
		} while (SparkDriverState.RUNNING.equals(ssr.getDriverState()));

		return ssr;
	}

	private void informKafka(final SparkJobStatusResponse ssr) {
		if (SparkDriverState.FINISHED.equals(ssr.getDriverState())) {
			this.logger.info("Job finished succesfully, informing kafka");
			this.kafkaProducer.send(this.outputTopic, "key", "job1 finished succesffuly");
		} else if (SparkDriverState.ERROR.equals(ssr.getDriverState())) {
			// TODO do something in case of erroneous job
		}
	}

	public void setCreateSubmissionRequest(final SparkRestJobConfiguration createSubmissionRequest) {
		this.createSubmissionRequest = createSubmissionRequest;
	}

	@Override
	public String getHandlerInfo() {
		return this.createSubmissionRequest.getMainClass();
	}

	public void setOutputTopic(final String outputTopic) {
		this.outputTopic = outputTopic;
	}

	public Long getCheckJobStatusInterval() {
		return this.checkJobStatusInterval;
	}

	public void setCheckJobStatusInterval(final Long checkJobStatusInterval) {
		this.checkJobStatusInterval = checkJobStatusInterval;
	}

	public void setRestTemplate(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
