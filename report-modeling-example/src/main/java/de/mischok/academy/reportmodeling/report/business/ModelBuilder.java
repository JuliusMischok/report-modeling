package de.mischok.academy.reportmodeling.report.business;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.mischok.academy.reportmodeling.data.domain.Status;
import de.mischok.academy.reportmodeling.data.domain.Task;
import de.mischok.academy.reportmodeling.report.domain.ReportModel;
import de.mischok.academy.reportmodeling.report.domain.TaskModel;

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
		
		List<TaskModel> taskModels = buildTaskModels(tasks);
		
		return ReportModel.of(from, to, taskModels);
	}

	private List<TaskModel> buildTaskModels(List<Task> tasks) {
		requireNonNull(tasks);
		
		Function<Task, TaskModel> mapper = task -> {
			String status = buildTaskStatusString(task.getStatus());
			String title = task.getTitle();
			String assignee = task.getAssignee().getFirstname() + " " + task.getAssignee().getLastname();
			String email = task.getAssignee().getEmail();
			
			return TaskModel.of(status, title, assignee, email);
		}; 

		return tasks.stream()
				.map(mapper)
				.collect(Collectors.toList());
	}

	private String buildTaskStatusString(Status status) {
		requireNonNull(status);

		switch (status) {
		case DONE:
			return "Done";
		case IN_PROGRESS:
			return "In progress";
		case TODO:
			return "Not started";
		default:
			throw new IllegalArgumentException("invalid enum value");
		}
	}
}
