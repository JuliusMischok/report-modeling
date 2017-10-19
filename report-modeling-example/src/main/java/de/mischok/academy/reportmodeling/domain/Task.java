package de.mischok.academy.reportmodeling.domain;

import static java.util.Objects.requireNonNull;

/**
 * Represents a task.
 */
public class Task {
	private final String title;
	private final Person assignee;
	private final Status status;
	
	private Task(String title, Person assignee, Status status) {
		requireNonNull(title);
		requireNonNull(assignee);
		requireNonNull(status);
		
		this.title = title;
		this.assignee = assignee;
		this.status = status;
	}
	
	/**
	 * Generator for a task.
	 * @param title the title/description
	 * @param assignee the assignee of the task
	 * @param status the status of the task
	 * @return generated task
	 */
	public static Task of(String title, Person assignee, Status status) {
		return new Task(title, assignee, status);
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
	public Person getAssignee() {
		return assignee;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
}
