package de.mischok.academy.reportmodeling.report.business;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import de.mischok.academy.reportmodeling.data.domain.Task;
import de.mischok.academy.reportmodeling.data.repository.TaskRepository;
import de.mischok.academy.reportmodeling.report.domain.ReportModel;
import de.mischok.academy.reportmodeling.report.domain.TaskModel;

/**
 * Test to ensure, that the report model is built correctly.
 */
public class ReportModelGenerationTest {
	
	private ModelBuilder modelBuilder;
	private TaskRepository taskRepository;

	/**
	 * Does the test setup.
	 */
	@Before
	public void setUp() {
		this.modelBuilder = new ModelBuilder();
		this.taskRepository = new TaskRepository();
	}
	
	/**
	 * Ensures, that for each (domain) task provided to the model builder, one task is generated in the model. 
	 */
	@Test
	public void testTaskCount() {
		List<Task> allTasks = taskRepository.findAll();
		
		ReportModel model = modelBuilder.buildFrom(LocalDate.now(), LocalDate.now(), allTasks);
		
		assertThat(model.getTasks().size(), is(allTasks.size()));
	}
	
	/**
	 * Ensures, that the period is reflected correctly in the model. 
	 */
	@Test
	public void testPeriodContents() {
		List<Task> allTasks = taskRepository.findAll();
		
		LocalDate from = LocalDate.of(2016, 5, 1);
		LocalDate to = LocalDate.of(2018, 5, 30);
		
		ReportModel model = modelBuilder.buildFrom(from, to, allTasks);
		
		assertThat(model.getFrom(), is(from));
		assertThat(model.getTo(), is(to));
	}
	
	/**
	 * Ensures, that the details of two tasks are reflected correctly in the model.
	 */
	@Test
	public void testTaskContents() {
		List<Task> twoTasks = taskRepository.findAll()
				.stream()
				.filter(task -> task.getTitle().startsWith("DEMO-0201") || task.getTitle().startsWith("DEMO-0203"))
				.collect(Collectors.toList());
		
		LocalDate from = LocalDate.of(2016, 5, 1);
		LocalDate to = LocalDate.of(2018, 5, 30);
		
		ReportModel model = modelBuilder.buildFrom(from, to, twoTasks);
		
		TaskModel demo0201 = model.getTasks().stream().filter(task -> task.getTitle().startsWith("DEMO-0201")).findFirst().orElseThrow(RuntimeException::new);
		TaskModel demo0203 = model.getTasks().stream().filter(task -> task.getTitle().startsWith("DEMO-0203")).findFirst().orElseThrow(RuntimeException::new);
				
		assertThat(demo0201.getAssignee(), is("Marie Walker"));
		assertThat(demo0201.getStatus(), is("Done"));
		assertThat(demo0201.getTitle(), is("DEMO-0201: Create domain POJOs"));
		
		assertThat(demo0203.getAssignee(), is("Floyd Wade"));
		assertThat(demo0203.getStatus(), is("In progress"));
		assertThat(demo0203.getTitle(), is("DEMO-0203: Add filter ability to repository"));
	}
}
