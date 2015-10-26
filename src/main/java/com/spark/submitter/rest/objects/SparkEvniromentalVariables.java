package com.spark.submitter.rest.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SparkEvniromentalVariables {

	@JsonProperty(value = "SPARK_HOME")
	private String sparkHome;

	public String getSparkHome() {
		return this.sparkHome;
	}

	public void setSparkHome(final String sparkHome) {
		this.sparkHome = sparkHome;
	}

}
