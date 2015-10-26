package com.spark.submitter.rest.objects;

public class SubmissionStatusResponse {

	private String action;

	private SparkDriverState driverState;

	private String serverSparkVersion;

	private String submissionId;

	private Boolean success;

	private String workerHostPort;

	private String workerId;

	public String getAction() {
		return this.action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public SparkDriverState getDriverState() {
		return this.driverState;
	}

	public void setDriverState(final SparkDriverState driverState) {
		this.driverState = driverState;
	}

	public String getServerSparkVersion() {
		return this.serverSparkVersion;
	}

	public void setServerSparkVersion(final String serverSparkVersion) {
		this.serverSparkVersion = serverSparkVersion;
	}

	public String getSubmissionId() {
		return this.submissionId;
	}

	public void setSubmissionId(final String submissionId) {
		this.submissionId = submissionId;
	}

	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(final Boolean success) {
		this.success = success;
	}

	public String getWorkerHostPort() {
		return this.workerHostPort;
	}

	public void setWorkerHostPort(final String workerHostPort) {
		this.workerHostPort = workerHostPort;
	}

	public String getWorkerId() {
		return this.workerId;
	}

	public void setWorkerId(final String workerId) {
		this.workerId = workerId;
	}

	@Override
	public String toString() {
		return "RestResponse [action=" + this.action + ", driverState=" + this.driverState + ", serverSparkVersion=" + this.serverSparkVersion
						+ ", submissionId=" + this.submissionId + ", success=" + this.success + ", workerHostPort=" + this.workerHostPort
						+ ", workerId=" + this.workerId + "]";
	}

}
