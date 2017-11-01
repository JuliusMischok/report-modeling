package de.mischok.academy.reportmodeling.data.repository;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.mischok.academy.reportmodeling.data.domain.Person;
import de.mischok.academy.reportmodeling.data.domain.Status;
import de.mischok.academy.reportmodeling.data.domain.Task;

/**
 * Supplies a set of tasks for demonstration purposes.
 */
public class TaskRepository {
	
	private static List<Task> tasks;
	
	{{
		Person alfredColeman = Person.of("Alfred", "Coleman", "edi@ulvajja.er");
		Person marieWalker = Person.of("Marie", "Walker", "cogoz@irzemse.su");
		Person anneKelly = Person.of("Anne", "Kelly", "tasom@fov.lk");
		Person floydWade = Person.of("Floyd", "Wade", "awuv@mi.ms");
		Person normanRodgers = Person.of("Norman", "Rodgers", "te@matfezlaz.kw");
		
		Task demo0101 = Task.of("DEMO-0101: Repository setup", alfredColeman, Status.DONE);
		Task demo0102 = Task.of("DEMO-0102: Install IDE", marieWalker, Status.DONE);
		Task demo0103 = Task.of("DEMO-0103: Create workspace", anneKelly, Status.DONE);
		Task demo0104 = Task.of("DEMO-0104: Initial commit", floydWade, Status.DONE);
		Task demo0105 = Task.of("DEMO-0105: Add README", alfredColeman, Status.DONE);
		Task demo0201 = Task.of("DEMO-0201: Create domain POJOs", marieWalker, Status.DONE);
		Task demo0202 = Task.of("DEMO-0202: Create repository", anneKelly, Status.DONE);
		Task demo0203 = Task.of("DEMO-0203: Add filter ability to repository", floydWade, Status.IN_PROGRESS);
		Task demo0204 = Task.of("DEMO-0204: Create test data", normanRodgers, Status.IN_PROGRESS);
		Task demo0205 = Task.of("DEMO-0205: ", alfredColeman, Status.TODO);
		Task demo0301 = Task.of("DEMO-0301: Create model structure", marieWalker, Status.IN_PROGRESS);
		Task demo0302 = Task.of("DEMO-0302: Create model test (structure)", anneKelly, Status.IN_PROGRESS);
		Task demo0303 = Task.of("DEMO-0303: Create model test (contents)", floydWade, Status.TODO);
		Task demo0304 = Task.of("DEMO-0304: ", normanRodgers, Status.IN_PROGRESS);
		Task demo0305 = Task.of("DEMO-0305: Implement model generation", alfredColeman, Status.IN_PROGRESS);
		Task demo0401 = Task.of("DEMO-0401: Create reporting test",marieWalker, Status.TODO);
		Task demo0402 = Task.of("DEMO-0402: Explore PDF library", anneKelly, Status.TODO);
		Task demo0403 = Task.of("DEMO-0403: Create PDF report", floydWade, Status.TODO);
		Task demo0404 = Task.of("DEMO-0404: Explore Excel library", normanRodgers, Status.TODO);
		Task demo0405 = Task.of("DEMO-0405: Create Excel report engine", alfredColeman, Status.TODO);
				
		tasks = Arrays.asList(
				demo0101, demo0102, demo0103, demo0104, demo0105,
				demo0201, demo0202, demo0203, demo0204, demo0205,
				demo0301, demo0302, demo0303, demo0304, demo0305,
				demo0401, demo0402, demo0403, demo0404, demo0405
				);
	}}

	/**
	 * Returns all tasks.
	 * @return list of all tasks
	 */
	public List<Task> findAll() {
		return Collections.unmodifiableList(tasks);
	}
	
	/**
	 * Returns a filtered list of tasks. 
	 * @param filters the filters to be applied
	 * @return filtered list of tasks
	 */
	@SuppressWarnings("unchecked")
	public List<Task> findAll(Predicate<Task>...filters) {
		requireNonNull(filters);
		
		Predicate<Task> predicate = Arrays.stream(filters)
				.reduce(task -> true, (a,b) -> a.and(b));
		
		return tasks.stream().filter(predicate).collect(Collectors.toList());
	}
}
