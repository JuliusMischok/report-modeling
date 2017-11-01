package de.mischok.academy.reportmodeling.data.domain;

/**
 * Represents the status of a task. 
 */
public enum Status {
	/**
	 * The task is ready to be worked on.
	 */
	TODO, 
	
	/**
	 * Work on the task is in progress.
	 */
	IN_PROGRESS, 
	
	/**
	 * The task is completed.
	 */
	DONE
}
