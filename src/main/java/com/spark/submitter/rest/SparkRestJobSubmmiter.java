package com.spark.submitter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.kafka.producer.KafkaProducer;
import com.spark.submitter.SparkJobSubmitter;
import com.spark.submitter.rest.objects.SparkRestJobConfiguration;
import com.spark.submitter.rest.objects.SparkRestJobResponse;
import com.spark.submitter.rest.objects.SparkDriverState;
import com.spark.submitter.rest.objects.SparkJobStatusResponse;

public class SparkRestJobSubmmiter implements SparkJobSubmitter {

	private final Logger logger = LoggerFactory.getLogger(SparkRestJobSubmmiter.class);

	private static final String JOB_SUBMISSION_URL = "http://170.118.146.163:6066/v1/submissions/create";

	private static final String JOB_STATUS_URL = "http://170.118.146.163:6066/v1/submissions/status/";

	private SparkRestJobConfiguration createSubmissionRequest;

	private String outputTopic;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Override
	public void submitJob() throws Exception {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<SparkRestJobConfiguration> request = new HttpEntity<>(this.createSubmissionRequest, headers);

		final RestTemplate restTemplate = new RestTemplate();
		final SparkRestJobResponse createSubmissionResponse = restTemplate.postForObject(SparkRestJobSubmmiter.JOB_SUBMISSION_URL, request,
						SparkRestJobResponse.class);

		final SparkJobStatusResponse ssr = this.monitorSubmittedJob(restTemplate, createSubmissionResponse);

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
				System.out.println("Job Still Running");
				this.logger.info("Job Still Running");
			}
			Thread.sleep(1000);
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
	public String getMainClass() {
		return this.createSubmissionRequest.getMainClass();
	}

	public void setOutputTopic(final String outputTopic) {
		this.outputTopic = outputTopic;
	}

}
