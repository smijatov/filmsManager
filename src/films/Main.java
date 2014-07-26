package films;

import java.io.IOException;
import java.util.List;

import excelTools.ReadExcel;
import excelTools.WriteExcel;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class Main {

	public static void main(String[] args) throws IOException, WriteException, BiffException {
		
		// Get top250 films from imdb
		List<Film> filmList = ImdbParser.parseFilms("http://www.imdb.com/chart/top");	
		
		// First read the sheet before updating
		ReadExcel reader = new ReadExcel();
		reader.setInputFile("Top250.xls");
		List<Film> oldFilmList = reader.read();
		
		// Look for added url films at the bottom of the list
		reader.readAdditional();
	
		// Check for updates between old excel sheet and new data
		findUpdates(filmList, oldFilmList);
		
		// Add the films to the excel sheet
		WriteExcel top250 = new WriteExcel(filmList);		
		top250.setOutputFile("Top250.xls");
		top250.write();
		
		
	}
	
	public static void findUpdates(List<Film> pListNew, List<Film> pListOld){

		int position_difference = 0;
		int vote_difference = 0;
		
		for(int film =0 ; film < pListNew.size(); film++){
			Film newFilm = pListNew.get(film);
			Film oldFilm = pListOld.get(film);
			vote_difference = newFilm.getVotes() - oldFilm.getVotes();
			//Si les films ne sont plus a la meme place
			if(!(oldFilm.getName().equalsIgnoreCase(newFilm.getName()))){
				System.out.println("Un film change de position !");
				//On cherche la position du nouveau
				for(Film tmp : pListNew){
					if(tmp.getName().equalsIgnoreCase(oldFilm.getName())){
						position_difference = tmp.getPosition() - oldFilm.getPosition();	
						vote_difference = tmp.getVotes() - oldFilm.getVotes();
						tmp.setVote_difference(vote_difference);
						break;
					}
				}		
				newFilm.setProgress(position_difference);
			}
			// Si pas de changement de position, mettre a jour les votes
			else{
				newFilm.setVote_difference(vote_difference);
			}
		}
	}

}


