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
import com.spark.submitter.rest.objects.CreateSubmissionRequest;
import com.spark.submitter.rest.objects.CreateSubmissionResponse;
import com.spark.submitter.rest.objects.SparkDriverState;
import com.spark.submitter.rest.objects.SubmissionStatusResponse;

public class SparkRestJobSubmmiter implements SparkJobSubmitter {

	private final Logger logger = LoggerFactory.getLogger(SparkRestJobSubmmiter.class);

	private static final String JOB_SUBMISSION_URL = "http://170.118.146.163:6066/v1/submissions/create";

	private static final String JOB_STATUS_URL = "http://170.118.146.163:6066/v1/submissions/status/";

	private CreateSubmissionRequest createSubmissionRequest;

	@Autowired
	private KafkaProducer kafkaProducer;

	@Override
	public void submitJob() throws Exception {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<CreateSubmissionRequest> request = new HttpEntity<>(this.createSubmissionRequest, headers);

		final RestTemplate restTemplate = new RestTemplate();
		final CreateSubmissionResponse createSubmissionResponse = restTemplate.postForObject(SparkRestJobSubmmiter.JOB_SUBMISSION_URL, request,
						CreateSubmissionResponse.class);

		final SubmissionStatusResponse ssr = this.monitorSubmittedJob(restTemplate, createSubmissionResponse);

		System.out.println(ssr);
		this.informKafka(ssr);

	}

	private SubmissionStatusResponse monitorSubmittedJob(final RestTemplate restTemplate, final CreateSubmissionResponse createSubmissionResponse)
					throws InterruptedException {
		SubmissionStatusResponse ssr = null;
		do {
			ssr = restTemplate.getForObject(SparkRestJobSubmmiter.JOB_STATUS_URL + createSubmissionResponse.getSubmissionId(),
							SubmissionStatusResponse.class);
			if (SparkDriverState.RUNNING.equals(ssr.getDriverState())) {
				System.out.println("Job Still Running");
				this.logger.info("Job Still Running");
			}
			Thread.sleep(1000);
		} while (SparkDriverState.RUNNING.equals(ssr.getDriverState()));

		return ssr;
	}

	private void informKafka(final SubmissionStatusResponse ssr) {
		if (SparkDriverState.FINISHED.equals(ssr.getDriverState())) {
			this.logger.info("Job finished succesfully, informing kafka");
			this.kafkaProducer.send("test-rep-topic", "key", "job1 finished succesffuly");
		} else if (SparkDriverState.ERROR.equals(ssr.getDriverState())) {
			// TODO do something in case of erroneous job
		}
	}

	public void setCreateSubmissionRequest(final CreateSubmissionRequest createSubmissionRequest) {
		this.createSubmissionRequest = createSubmissionRequest;
	}

	@Override
	public String getMainClass() {
		return this.createSubmissionRequest.getMainClass();
	}

}
