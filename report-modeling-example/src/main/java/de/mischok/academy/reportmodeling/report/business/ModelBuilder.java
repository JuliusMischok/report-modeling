package de.mischok.academy.reportmodeling.report.business;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import de.mischok.academy.reportmodeling.data.domain.Task;
import de.mischok.academy.reportmodeling.report.domain.ReportModel;

/**
 * Class to build a report model from the domain data.
 */
public class ModelBuilder {
	
	/**
	 * Builds a model from the period and the tasks to be reported.
	 * @param from the start of report period
	 * @param to the end of report period
	 * @param tasks the tasks to be reported
	 * @return model instance
	 */
	public ReportModel buildFrom(final LocalDate from, final LocalDate to, final List<Task> tasks) {
		requireNonNull(from);
		requireNonNull(to);
		requireNonNull(tasks);
		
		// TODO: Implementation
		
		return null;
	}
}
