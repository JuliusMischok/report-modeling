package de.mischok.academy.reportmodeling.report.excel;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import de.mischok.academy.reportmodeling.report.domain.ReportModel;
import de.mischok.academy.reportmodeling.report.domain.TaskModel;

/**
 * Provides the "translation" of a report model into an excel workbook.
 */
public class ExcelReportBuilder {
	
	private static final int MAX_COLUMN_INDEX = 3;
	
	private final XSSFWorkbook workbook;

	private final CellStyle normalStyle;
	private final CellStyle normalBoldStyle;
	private final CellStyle normalBoldStyleDate;
	private final CellStyle headerStyle;

	/**
	 * Constructor
	 */
	private ExcelReportBuilder() {
		workbook = new XSSFWorkbook();
		
		Font normalFont = workbook.createFont();
		normalFont.setFontHeightInPoints((short) 10);

		Font normalBoldFont = workbook.createFont();
		normalBoldFont.setFontHeightInPoints((short) 10);
		normalBoldFont.setBold(true);

		Font largerBoldFont = workbook.createFont();
		largerBoldFont.setFontHeightInPoints((short) 14);
		largerBoldFont.setBold(true);

		normalStyle = workbook.createCellStyle();
		normalStyle.setFont(normalFont);

		normalBoldStyle = workbook.createCellStyle();
		normalBoldStyle.setFont(normalBoldFont);

		headerStyle = workbook.createCellStyle();
		headerStyle.setFont(largerBoldFont);
		
		normalBoldStyleDate = workbook.createCellStyle();
		normalBoldStyleDate.setFont(normalBoldFont);
		normalBoldStyleDate.setDataFormat(workbook.createDataFormat().getFormat("mm/dd/yy"));
		normalBoldStyleDate.setAlignment(HorizontalAlignment.LEFT);
	}
	
	/**
	 * Builds the report file from a model. 
	 * @param model the model
	 * @param outStream the stream to write the report to
	 * @throws IOException thrown if writing to stream failed
	 */
	public static void writeReportToStream(ReportModel model, OutputStream outStream) throws IOException {
		requireNonNull(model);
		requireNonNull(outStream);
		
		ExcelReportBuilder exporter = new ExcelReportBuilder();
		
		exporter.createOpenIssuesWorksheet(model);
		
		exporter.getWorkbook().write(outStream);
		
		outStream.flush();
	}

	private void createOpenIssuesWorksheet(ReportModel model) {
		requireNonNull(model);
		
		XSSFSheet sheet = getWorkbook().createSheet("Open Issues");
        
		sheet = writeReportHeaderToSheet(sheet, model);
		sheet = writeReportTasksToSheet(sheet, model.getTasks());
		
		for (int i=0; i<=MAX_COLUMN_INDEX; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	private XSSFSheet writeReportHeaderToSheet(XSSFSheet sheet, ReportModel model) {
		requireNonNull(sheet);
		requireNonNull(model);
		
		Row first = sheet.createRow(0);
		Row second = sheet.createRow(1);
		
		Cell intervalHeadline = first.createCell(0);
		intervalHeadline.setCellValue("Report interval");
		intervalHeadline.setCellStyle(headerStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		
		Cell fromTitle = second.createCell(0);
		Cell fromContent = second.createCell(1);
		Cell toTitle = second.createCell(2);
		Cell toContent = second.createCell(3);
		
		fromTitle.setCellValue("From");
		fromTitle.setCellStyle(normalBoldStyle);
		
		fromContent.setCellValue(buildDate(model.getFrom()));
		fromContent.setCellStyle(normalBoldStyleDate);

		toTitle.setCellValue("To");
		toTitle.setCellStyle(normalBoldStyle);
		
		toContent.setCellValue(buildDate(model.getTo()));
		toContent.setCellStyle(normalBoldStyleDate);
		
		return sheet;
	}

	private XSSFSheet writeReportTasksToSheet(XSSFSheet sheet, List<TaskModel> tasks) {
		requireNonNull(sheet);
		requireNonNull(tasks);
		
		writeTaskHeadlinesToSheet(sheet, 3);
		writeTaskContentsToSheet(sheet, tasks, 4);
		
		return sheet;
	}

	private void writeTaskHeadlinesToSheet(XSSFSheet sheet, int rowIndex) {
		Row headlines = sheet.createRow(rowIndex);
		
		Cell headlineStatus = headlines.createCell(0);
		Cell headlineTitle = headlines.createCell(1);
		Cell headlineAssignee = headlines.createCell(2);
		Cell headlineEmail = headlines.createCell(3);

		headlineStatus.setCellValue("Status");
		headlineStatus.setCellStyle(headerStyle);
		
		headlineTitle.setCellValue("Title");
		headlineTitle.setCellStyle(headerStyle);
		
		headlineAssignee.setCellValue("Assignee");
		headlineAssignee.setCellStyle(headerStyle);
		
		headlineEmail.setCellValue("E-Mail");
		headlineEmail.setCellStyle(headerStyle);
	}

	private void writeTaskContentsToSheet(XSSFSheet sheet, List<TaskModel> tasks, int firstRowIndex) {
		requireNonNull(sheet);
		requireNonNull(tasks);
		
		int offset = 0;
		
		for (TaskModel task : tasks) {
			Row currentRow = sheet.createRow(firstRowIndex + offset);
			
			Cell status = currentRow.createCell(0);
			Cell title = currentRow.createCell(1);
			Cell assignee = currentRow.createCell(2);
			Cell email = currentRow.createCell(3);

			status.setCellValue(task.getStatus());
			status.setCellStyle(normalStyle);
			
			title.setCellValue(task.getTitle());
			title.setCellStyle(normalStyle);
			
			assignee.setCellValue(task.getAssignee());
			assignee.setCellStyle(normalStyle);
			
			email.setCellValue(task.getEmail());
			email.setCellStyle(normalStyle);
			Hyperlink link = getWorkbook().getCreationHelper().createHyperlink(HyperlinkType.EMAIL);
			link.setAddress(task.getEmail());
			email.setHyperlink(link);
			
			offset++;
		}
	}
	
	private Date buildDate(LocalDate localDate) {
		requireNonNull(localDate);
		
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	private XSSFWorkbook getWorkbook() {
		return workbook;
	}
}
