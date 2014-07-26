package films;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ImdbParser {

	public static List<Film> parseFilms(String pUrl){

		List<Film> filmList = new ArrayList<Film>();

		// Connect to the website and parse it into a document	
		try {

			Document doc = Jsoup.connect(pUrl).get();

			// Select all elements you need (see below for documentation)
			Elements filmPositions = doc.select("[class=titleColumn] span:eq(0)");
			Elements filmNames = doc.select("[class=titleColumn] a"); // Also contains Directors
			Elements filmDates = doc.select("[class=titleColumn] span:eq(2)");
			Elements filmRatings = doc.select("[class=ratingColumn] strong"); // Also contains VOTES

			for(int film = 0; film < filmNames.size(); film++){

				//Make an instance of the film class
				Film vFilm = new Film();

				// Get the film positions	
				String position = filmPositions.get(film).text();
				double tmpPos = Double.valueOf(position);
				vFilm.setPosition((int)tmpPos);

				// Get the film names		
				String name = filmNames.get(film).text();
				vFilm.setName(name);

				// Get the director names
				String director = filmNames.get(film).attr("title");
				vFilm.setDirector(director);

				// Get the film Dates	
				String date = filmDates.get(film).text().replaceAll("[()]", "");
				vFilm.setDate(Integer.parseInt(date));

				// Get the film Ratings		
				String rating = filmRatings.get(film).text();
				vFilm.setRating(rating);

				// Get the film Votes	
				String votes = filmRatings.get(film).attr("data-value");
				vFilm.setVotes(Integer.parseInt(votes));

				filmList.add(vFilm);
			}

		} catch (IOException e) {
			System.out.println("Couldn't get URL!");
		}
		return filmList;

	}

	public static Film parseFilm(String pUrl){
		
		Film film = new Film();
		
		try {

			Document doc = Jsoup.connect(pUrl).get();
			
			// Select all elements you need (se below for documentation)
			Elements filmName = doc.select("[class=header] span"); 
			Elements filmDate = doc.select("[class=header] a");
			Elements filmRating = doc.select("[class=star-box-details] strong span"); 
			Elements filmVotes= doc.select("[class=star-box-details] a span"); 
			
			String name = filmName.get(0).text();
			String rating = filmRating.get(0).text();
			String votes = filmVotes.get(0).text();
			votes = votes.replaceAll(",","");
			String date = filmDate.get(0).text();

			film.setRating(rating);
			film.setVotes(Integer.parseInt(votes));
			film.setDate(Integer.parseInt(date));
			film.setName(name);
			
		}
		
		catch (IOException e) {
			System.out.println("Couldn't get URL!");
		}
		return film;

	}
	
}
