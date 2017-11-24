package de.mischok.academy.reportmodeling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import de.mischok.academy.reportmodeling.data.repository.TaskRepository;
import de.mischok.academy.reportmodeling.report.business.ModelBuilder;
import de.mischok.academy.reportmodeling.report.domain.ReportModel;
import de.mischok.academy.reportmodeling.report.excel.ExcelReportBuilder;

/**
 * Main class for demonstration.
 */
public class Main {
	
	/**
	 * Main method of the demo.
	 * @param args the command line args
	 */
	public static void main(String[] args) {
		String filename = "demo.xlsx";
		
		if (args != null && args.length > 0) {
			filename = args[0];
		}
		
		ModelBuilder modelBuilder = new ModelBuilder();
		TaskRepository taskRepo = new TaskRepository();
		ReportModel model = modelBuilder.buildFrom(LocalDate.now().minusMonths(1), LocalDate.now(), taskRepo.findAll());
		
		try {
			File file = new File("./", filename);
			if (file.exists() == false) {
				file.createNewFile();
			}
			
			OutputStream outStream = new FileOutputStream(file);
			
			ExcelReportBuilder.writeReportToStream(model, outStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
