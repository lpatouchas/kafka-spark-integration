package com.spark.submitter.java.objects;

public class SparkJavaJobConfiguration {

	private String appResource;

	private String deployMode;

	private String sparkMaster;

	private String mainClass;

	private String sparkHome;

	public String getAppResource() {
		return this.appResource;
	}

	public void setAppResource(final String appResource) {
		this.appResource = appResource;
	}

	public String getDeployMode() {
		return this.deployMode;
	}

	public void setDeployMode(final String deployMode) {
		this.deployMode = deployMode;
	}

	public String getSparkMaster() {
		return this.sparkMaster;
	}

	public void setSparkMaster(final String sparkMaster) {
		this.sparkMaster = sparkMaster;
	}

	public String getMainClass() {
		return this.mainClass;
	}

	public void setMainClass(final String mainClass) {
		this.mainClass = mainClass;
	}

	public String getSparkHome() {
		return this.sparkHome;
	}

	public void setSparkHome(final String sparkHome) {
		this.sparkHome = sparkHome;
	}

}
