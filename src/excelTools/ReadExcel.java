package excelTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import films.Film;
import films.ImdbParser;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

	private final int RANKING_COLUMN = 0;
	private final int NAME_COLUMN = 1;
	private final int DIRECTOR_COLUMN = 2;
	private final int DATE_COLUMN = 3;
	private final int VOTES_COLUMN = 4;
	private final int RATING_COLUMN = 5;
	
	private String inputFile;
	
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public List<Film> read() throws IOException  {
		
		List<Film> filmList = new ArrayList<Film>();
		
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			for(int j = 0; j < 250 ; j++){

				Film vFilm = new Film();
				
				for (int i = 1; i < j + 2; i++) {
					
					Cell positionCell = sheet.getCell(RANKING_COLUMN, i);
					Cell nameCell = sheet.getCell(NAME_COLUMN, i);
					Cell directorCell = sheet.getCell(DIRECTOR_COLUMN, i);
					Cell dateCell = sheet.getCell(DATE_COLUMN, i);
					Cell voteCell = sheet.getCell(VOTES_COLUMN, i);
					Cell ratingCell = sheet.getCell(RATING_COLUMN, i);

					vFilm.setPosition(Integer.parseInt(positionCell.getContents()));
					vFilm.setName(nameCell.getContents());
					vFilm.setDirector(directorCell.getContents());
					vFilm.setDate(Integer.parseInt(dateCell.getContents()));
					vFilm.setVotes(Integer.parseInt(voteCell.getContents()));
					vFilm.setRating(ratingCell.getContents());
				}	
				filmList.add(vFilm);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}
		return filmList;
	}
	
	public List<Film> readAdditional() throws IOException  {

		List<Film> additionnalFilms = new ArrayList<Film>();

		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			//Read the rest after the top250
			for(int j = 251; j < sheet.getColumn(1).length ; j++){	
					Cell url = sheet.getCell(NAME_COLUMN, j);
					//Si une url est détéctée on la traite
					if(url.getContents().contains("www") || url.getContents().contains("http") ){
						Film vFilm = new Film();
						vFilm = ImdbParser.parseFilm(url.getContents());
						additionnalFilms.add(vFilm);
					}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return additionnalFilms;
	}
	
}
