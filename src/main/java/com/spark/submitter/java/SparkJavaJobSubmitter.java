package com.spark.submitter.java;

import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spark.submitter.SparkJobSubmitter;
import com.spark.submitter.java.objects.SparkJavaJobConfiguration;

public class SparkJavaJobSubmitter implements SparkJobSubmitter {

	private final Logger logger = LoggerFactory.getLogger(SparkJobSubmitter.class);

	private SparkJavaJobConfiguration jobConfig;

	@Override
	public void submitJob() throws Exception {
		final Process spark = new SparkLauncher()//
						.setAppResource(this.jobConfig.getAppResource())//
						.setDeployMode(this.jobConfig.getDeployMode())//
						.setMaster(this.jobConfig.getSparkMaster())//
						.setMainClass(this.jobConfig.getMainClass())//
						.setSparkHome(this.jobConfig.getSparkHome())//
						.launch();

		final InputStreamReaderRunnable inputStreamReaderRunnable = new InputStreamReaderRunnable(spark.getInputStream(), "input");
		final Thread inputThread = new Thread(inputStreamReaderRunnable, "LogStreamReader input");
		inputThread.start();

		final InputStreamReaderRunnable errorStreamReaderRunnable = new InputStreamReaderRunnable(spark.getErrorStream(), "error");
		final Thread errorThread = new Thread(errorStreamReaderRunnable, "LogStreamReader error");
		errorThread.start();

		this.logger.info("Waiting for finish...");
		final int exitCode = spark.waitFor();
		this.logger.info("Finished! Exit code:" + exitCode);
	}

	public void setJobConfig(final SparkJavaJobConfiguration jobConfig) {
		this.jobConfig = jobConfig;
	}

	@Override
	public String getMainClass() {
		return this.jobConfig.getMainClass();
	}
}
