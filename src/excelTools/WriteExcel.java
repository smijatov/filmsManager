package excelTools;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import films.Film;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.Number;


public class WriteExcel {

	private List<Film> mFilms;
	private String inputFile;
	private WritableCellFormat times;
	
	public WriteExcel(List<Film> pFilms){
		mFilms = pFilms;
	}

	
	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write() throws IOException, WriteException, BiffException {
		
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("fr", "FR"));
		
		Workbook readWorkbook = Workbook.getWorkbook(file, wbSettings);
		
		WritableWorkbook workbook = Workbook.createWorkbook(file,readWorkbook,wbSettings);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy (hh'h'mm'm'ss's')");
		Calendar cal = Calendar.getInstance();
		String currentDate = dateFormat.format(cal.getTime());
		
		workbook.createSheet(currentDate, 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		
		createLabel(excelSheet);
		addFilms(excelSheet);

		workbook.write();
		workbook.close();
	}

	private void addFilms(WritableSheet sheet) throws RowsExceededException, WriteException {
		
		for(Film film : mFilms){
			
			String position = String.valueOf(film.getPosition());
			String name = film.getName();
			String director = film.getDirector();
			String date = String.valueOf(film.getDate()); 
			String rating = String.valueOf(film.getRating()); 
			String votes = String.valueOf(film.getVotes()); 
			String votes_difference = String.valueOf(film.getVote_difference());
			String progress = String.valueOf(film.getProgress()); 
			
			addNumber(sheet, 0, mFilms.indexOf(film) + 1, position);
			addCaption(sheet, 1, mFilms.indexOf(film) + 1, name);
			addCaption(sheet, 2, mFilms.indexOf(film) + 1, director);
			addNumber(sheet, 3, mFilms.indexOf(film) + 1, date);
			addNumber(sheet, 4, mFilms.indexOf(film) + 1, votes);	
			addCaption(sheet, 5, mFilms.indexOf(film) + 1, rating);	
			if(film.getProgress() > 0){
				addColorCaption(sheet, 6, mFilms.indexOf(film) + 1, "+" + progress,Colour.BRIGHT_GREEN);
			}
			else if(film.getProgress() < 0){
				addColorCaption(sheet, 6, mFilms.indexOf(film) + 1, progress,Colour.RED);
			}		
			addNumber(sheet, 7, mFilms.indexOf(film) + 1, votes_difference);	
			
		}
		
	}

	private void createLabel(WritableSheet sheet) throws RowsExceededException, WriteException {
		
		// Lets create a times font
	    WritableFont times12pt = new WritableFont(WritableFont.TIMES, 12);
	    // Define the cell format
	    times = new WritableCellFormat(times12pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);
		
		addTitleCaption(sheet, 0, 0, "Ranking");
		addTitleCaption(sheet, 1, 0, "Title");	
		addTitleCaption(sheet, 2, 0, "Casting");
		addTitleCaption(sheet, 3, 0, "Date");
		addTitleCaption(sheet, 4, 0, "Votes");
		addTitleCaption(sheet, 5, 0, "Grade");
		addTitleCaption(sheet, 6, 0, "Progress");
		addTitleCaption(sheet, 7, 0, "New votes");
		
		resizeCells(sheet);
	}

	private void addNumber(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		sheet.addCell(new Number(column,row,Double.valueOf(s)));
	}
	
	
	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	private void addTitleCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		WritableFont titleFont = new WritableFont(WritableFont.TIMES, 12);
		titleFont.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(titleFont);
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setBackground(Colour.AQUA);
		Label label;
		label = new Label(column, row, s, cellFormat);
		sheet.addCell(label);
	}
	
	private void addColorCaption(WritableSheet sheet, int column, int row, String s, Colour red) throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, getCellFormat(red));
		sheet.addCell(label);

	}
	
	private void resizeCells(WritableSheet sheet){
		// Changer la taille des 3 premieres colonnes
		for(int column = 0; column < 3; column++){
			CellView cell = sheet.getColumnView(column);
			cell.setAutosize(true);
			sheet.setColumnView(column, cell);
		}
		
	}
	
	// Create cell font and format
	private static WritableCellFormat getCellFormat(Colour c) throws WriteException {
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		cellFont.setBoldStyle(WritableFont.BOLD);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setBackground(c);
		return cellFormat;
	}
}
