package com.spark.submitter.rest.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SparkRestJobConfigurationProperties {

	@JsonProperty(value = "spark.jars")
	private String jars;

	@JsonProperty(value = "spark.driver.supervise")
	private Boolean driverSupervise;

	@JsonProperty(value = "spark.app.name")
	private String appName;

	@JsonProperty(value = "spark.eventLog.enabled")
	private Boolean eventLogEnabled;

	@JsonProperty(value = "spark.submit.deployMode")
	private String deployMode;

	@JsonProperty(value = "spark.master")
	private String master;

	public String getJars() {
		return this.jars;
	}

	public void setJars(final String jars) {
		this.jars = jars;
	}

	public Boolean getDriverSupervise() {
		return this.driverSupervise;
	}

	public void setDriverSupervise(final Boolean driverSupervise) {
		this.driverSupervise = driverSupervise;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(final String appName) {
		this.appName = appName;
	}

	public Boolean getEventLogEnabled() {
		return this.eventLogEnabled;
	}

	public void setEventLogEnabled(final Boolean eventLogEnabled) {
		this.eventLogEnabled = eventLogEnabled;
	}

	public String getDeployMode() {
		return this.deployMode;
	}

	public void setDeployMode(final String deployMode) {
		this.deployMode = deployMode;
	}

	public String getMaster() {
		return this.master;
	}

	public void setMaster(final String master) {
		this.master = master;
	}

}
