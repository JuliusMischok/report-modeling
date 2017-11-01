package de.mischok.academy.reportmodeling.report.domain;

import static java.util.Objects.requireNonNull;

/**
 * Represents the data of a task for reporting.
 */
public class TaskModel {
	private final String status;
	private final String title;
	private final String assignee;
	private final String email;
	
	private TaskModel(final String status, final String title, final String assignee, final String email) {
		requireNonNull(status);
		requireNonNull(title);
		requireNonNull(assignee);
		requireNonNull(email);

		this.status = status;
		this.title = title;
		this.assignee = assignee;
		this.email = email;
	}
	
	/**
	 * Generator method for a task model.
	 * @param status the status
	 * @param title the title
	 * @param assignee the assignee (full name)
	 * @param email the email address
	 * @return the generated task model
	 */
	public static TaskModel of(final String status, final String title, final String assignee, final String email) {
		return new TaskModel(status, title, assignee, email);
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
}
