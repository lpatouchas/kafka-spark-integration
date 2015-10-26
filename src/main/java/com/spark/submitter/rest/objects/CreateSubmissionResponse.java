package com.spark.submitter.rest.objects;

public class CreateSubmissionResponse {

	private String action;

	private String message;

	private String serverSparkVersion;

	private String submissionId;

	private Boolean success;

	public String getAction() {
		return this.action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
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

	@Override
	public String toString() {
		return "CreateSubmissionResponse [action=" + this.action + ", message=" + this.message + ", serverSparkVersion=" + this.serverSparkVersion
						+ ", submissionId=" + this.submissionId + ", success=" + this.success + "]";
	}
}
