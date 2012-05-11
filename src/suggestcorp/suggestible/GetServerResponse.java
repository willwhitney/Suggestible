package suggestcorp.suggestible;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetServerResponse {

    public class Suggestion {
        public String title;
        public double rating;
        public String imageurl;
        public String maplocation;
        public String id;
        public String description;

        Suggestion() {

        }
    }
    
    public static List<Suggestion> getMovies() {
    	String url = "http://afternoon-planet-7936.herokuapp.com/movies";
    	String json = getURLContents(url);
    	
    	Gson gson = new Gson();
    	Type collectionType = new TypeToken<List<Suggestion>>(){}.getType();
    	List<Suggestion> suggestions = gson.fromJson(json, collectionType);
    	
    	
    	
    	return suggestions;
    }
    
    
    public static List<Suggestion> getBooks() {
    	String url = "http://afternoon-planet-7936.herokuapp.com/books";
    	String json = getURLContents(url);
    	
    	Gson gson = new Gson();
    	Type collectionType = new TypeToken<List<Suggestion>>(){}.getType();
    	List<Suggestion> suggestions = gson.fromJson(json, collectionType);
    	
    	return suggestions;
    }
    
    public static List<Suggestion> getRestaurants(float lat, float lon) {
    	String url = "http://afternoon-planet-7936.herokuapp.com/restaurants?lat=" + lat + "&lon=" + lon;
    	String json = getURLContents(url);
    	
    	Gson gson = new Gson();
    	Type collectionType = new TypeToken<List<Suggestion>>(){}.getType();
    	List<Suggestion> suggestions = gson.fromJson(json, collectionType);
    	
    	return suggestions;
    }
    
 
    public static String getURLContents(String url) {
        
		try {
			
			URL target = new URL(url);
		    URLConnection conn = target.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Log.v("imdb", "reading");
		    
		    
		    String inputLine;
		    String output = "";
		    while ((inputLine = in.readLine()) != null) {
		        output += inputLine;
		        
		    }
		    in.close();
		    
		    return output;
		    
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		    Log.v("malformedEx", e.getMessage());
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    Log.v("IOEx", e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
    }


    /**
     * 
     * @param json
     *            keys: title, rating, imageurl, maplocation, id, description
     *            example: {"title":"Museum", "rating":3.5,
     *            "imageurl":"http://www.yelp.com/image",
     *            "maplocation":"40 Massachusetts Avenue, Cambridge, MA",
     *            "id":"exampleYelpID",
     *            "description":"this is a description of a Museum"}
     * 
     */
    public static Suggestion parseResponse(String json)
            throws IllegalArgumentException {
        Gson gson = new Gson();
        return gson.fromJson(json, Suggestion.class);
    }
}
