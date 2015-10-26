package com.spark.submitter;

import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkJavaJobSubmitter implements SparkJobSubmitter {

	private final Logger logger = LoggerFactory.getLogger(SparkJobSubmitter.class);

	private String appResource; // D:\\source\\spark-playroom\\target\\spark-playroom-1.0-jar-with-dependencies.jar

	private String deployMode; // cluster

	private String sparkMaster; // spark://170.118.146.163:7077

	private String mainClass; // gr.patouchas.spark.SimpleExample3

	private String sparkHome; // D:\\spark-1.5.1

	@Override
	public void submitJob() throws Exception {
		final Process spark = new SparkLauncher()//
						.setAppResource(this.appResource)//
						.setDeployMode(this.deployMode)//
						.setMaster(this.sparkMaster)//
						.setMainClass(this.mainClass)//
						.setSparkHome(this.sparkHome)//
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

	public String getSparkHome() {
		return this.sparkHome;
	}

	public void setSparkHome(final String sparkHome) {
		this.sparkHome = sparkHome;
	}

	@Override
	public String getMainClass() {
		return this.mainClass;
	}

	public void setMainClass(final String mainClass) {
		this.mainClass = mainClass;
	}

}
