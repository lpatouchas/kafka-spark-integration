package com.spark.submitter.rest;

public class CreateSubmissionRequest {

	private String action;

	private String appResource;

	private String clientSparkVersion;

	private SparkEvniromentalVariables environmentVariables;

	private String mainClass;

	private SparkProperties sparkProperties;

	public String getAction() {
		return this.action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public String[] getAppArgs() {
		return new String[0];
	}

	public String getAppResource() {
		return this.appResource;
	}

	public void setAppResource(final String appResource) {
		this.appResource = appResource;
	}

	public String getClientSparkVersion() {
		return this.clientSparkVersion;
	}

	public void setClientSparkVersion(final String clientSparkVersion) {
		this.clientSparkVersion = clientSparkVersion;
	}

	public SparkEvniromentalVariables getEnvironmentVariables() {
		return this.environmentVariables;
	}

	public void setEnvironmentVariables(final SparkEvniromentalVariables environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public String getMainClass() {
		return this.mainClass;
	}

	public void setMainClass(final String mainClass) {
		this.mainClass = mainClass;
	}

	public SparkProperties getSparkProperties() {
		return this.sparkProperties;
	}

	public void setSparkProperties(final SparkProperties sparkProperties) {
		this.sparkProperties = sparkProperties;
	}

	// public Map<String, String> getSparkProperties() {
	// return this.sparkProperties;
	// }
	//
	// public void setSparkProperties(final Map<String, String> sparkProperties) {
	// this.sparkProperties = sparkProperties;
	// }

}
