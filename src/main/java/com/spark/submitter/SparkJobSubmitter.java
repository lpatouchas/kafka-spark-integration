package com.spark.submitter;

public interface SparkJobSubmitter {

	void submitJob() throws Exception;

	String getMainClass();
}
