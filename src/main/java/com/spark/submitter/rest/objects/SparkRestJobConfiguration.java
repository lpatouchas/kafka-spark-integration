package com.spark.submitter.rest.objects;

public class SparkRestJobConfiguration {

	private String action;

	private String appResource;

	private String clientSparkVersion;

	private SparkRestJobConfigurationEnvVars environmentVariables;

	private String mainClass;

	private SparkRestJobConfigurationProperties sparkProperties;

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

	public SparkRestJobConfigurationEnvVars getEnvironmentVariables() {
		return this.environmentVariables;
	}

	public void setEnvironmentVariables(final SparkRestJobConfigurationEnvVars environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public String getMainClass() {
		return this.mainClass;
	}

	public void setMainClass(final String mainClass) {
		this.mainClass = mainClass;
	}

	public SparkRestJobConfigurationProperties getSparkProperties() {
		return this.sparkProperties;
	}

	public void setSparkProperties(final SparkRestJobConfigurationProperties sparkProperties) {
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
