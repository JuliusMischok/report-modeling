package de.mischok.academy.reportmodeling.report.domain;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents the modeled data for a report.
 */
public class ReportModel {
	private final LocalDate from;
	private final LocalDate to;
	private final List<TaskModel> tasks;
	
	private ReportModel(LocalDate from, LocalDate to, List<TaskModel> tasks) {
		requireNonNull(from);
		requireNonNull(to);
		requireNonNull(tasks);

		this.from = from;
		this.to = to;
		this.tasks = tasks;
	}
	
	/**
	 * Generator method for a report model.
	 * @param from the start of the report period
	 * @param to the end of the report period
	 * @param tasks the tasks to be reported
	 * @return instance of report model
	 */
	public static ReportModel of(LocalDate from, LocalDate to, List<TaskModel> tasks) {
		return new ReportModel(from, to, tasks);
	}

	/**
	 * @return the from
	 */
	public LocalDate getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public LocalDate getTo() {
		return to;
	}

	/**
	 * @return the tasks
	 */
	public List<TaskModel> getTasks() {
		return tasks;
	}
}
