package com.spark.test;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.submitter.rest.objects.SparkDriverState;
import com.spark.submitter.rest.objects.SparkJobStatusResponse;
import com.spark.submitter.rest.objects.SparkRestJobConfiguration;
import com.spark.submitter.rest.objects.SparkRestJobConfigurationEnvVars;
import com.spark.submitter.rest.objects.SparkRestJobConfigurationProperties;

public class RestPostTest {

	private final String requestUrl = "http://170.118.146.163:6066/v1/submissions/create";

	@Test
	public void test() throws InterruptedException, JsonProcessingException {
		final ObjectMapper m = new ObjectMapper();

		final SparkRestJobConfiguration createSubmissionRequest = new SparkRestJobConfiguration();
		createSubmissionRequest.setAction("CreateSubmissionRequest");
		createSubmissionRequest.setAppResource("file:/source/spark-playroom/target/spark-playroom-1.0-jar-with-dependencies.jar");
		createSubmissionRequest.setClientSparkVersion("1.5.1");
		createSubmissionRequest.setMainClass("gr.patouchas.spark.SimpleExample2");
		final SparkRestJobConfigurationEnvVars environmentVariables = new SparkRestJobConfigurationEnvVars();
		environmentVariables.setSparkHome("D:\\\\spark-1.5.1\\");
		createSubmissionRequest.setEnvironmentVariables(environmentVariables);

		final SparkRestJobConfigurationProperties sparkProperties = new SparkRestJobConfigurationProperties();
		sparkProperties.setAppName("Play Room");
		sparkProperties.setDeployMode("cluster");
		sparkProperties.setDriverSupervise(Boolean.TRUE);
		sparkProperties.setEventLogEnabled(Boolean.TRUE);
		sparkProperties.setJars("file:/source/spark-playroom/target/spark-playroom-1.0-jar-with-dependencies.jar");
		sparkProperties.setMaster("spark://170.118.146.163:7077");

		createSubmissionRequest.setSparkProperties(sparkProperties);
		System.out.println(m.writeValueAsString(createSubmissionRequest));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		final HttpEntity request = new HttpEntity(createSubmissionRequest, headers);

		final RestTemplate restTemplate = new RestTemplate();
		try {
			final SparkJobStatusResponse createSubmissionResponse = restTemplate.postForObject(this.requestUrl, request,
							SparkJobStatusResponse.class);

			SparkJobStatusResponse ssr = null;
			do {
				ssr = restTemplate.getForObject("http://170.118.146.163:6066/v1/submissions/status/" + createSubmissionResponse.getSubmissionId(),
								SparkJobStatusResponse.class);
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

}
