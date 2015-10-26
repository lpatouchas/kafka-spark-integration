package com.spark.submitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.spark.submitter.rest.CreateSubmissionRequest;
import com.spark.submitter.rest.CreateSubmissionResponse;
import com.spark.submitter.rest.SparkDriverState;
import com.spark.submitter.rest.SubmissionStatusResponse;

public class SparkRestJobSubmmiter implements SparkJobSubmitter {

	private final Logger logger = LoggerFactory.getLogger(SparkRestJobSubmmiter.class);

	private final String requestUrl = "http://170.118.146.163:6066/v1/submissions/create";

	private CreateSubmissionRequest createSubmissionRequest;

	@Override
	public void submitJob() throws Exception {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity request = new HttpEntity(this.createSubmissionRequest, headers);

		final RestTemplate restTemplate = new RestTemplate();
		try {
			final CreateSubmissionResponse createSubmissionResponse = restTemplate.postForObject(this.requestUrl, request,
							CreateSubmissionResponse.class);

			SubmissionStatusResponse ssr = null;
			do {
				ssr = restTemplate.getForObject("http://170.118.146.163:6066/v1/submissions/status/" + createSubmissionResponse.getSubmissionId(),
								SubmissionStatusResponse.class);
				if (SparkDriverState.RUNNING.equals(ssr.getDriverState())) {
					System.out.println("Job Still Running");
				}
				Thread.sleep(1000);
			} while (SparkDriverState.RUNNING.equals(ssr.getDriverState()));

			System.out.println(ssr);
		} catch (final HttpClientErrorException e) {
			System.out.println(e.getResponseBodyAsString());
			System.out.println(e);
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
