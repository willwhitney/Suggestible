package suggestcorp.suggestible;

import com.google.gson.Gson;

public class GetServerResponse {

    public class Suggestion {
        public String title;
        public double rating;
        public String imageurl;
        public String location;
        public String id;
        public String description;

        Suggestion() {

        }
    }

    /**
     * 
     * @param json
     *            keys: title, rating, imageurl, location, id, description
     *            example: {"title":"Museum", "rating":3.5,
     *            "imageurl":"http://www.yelp.com/image",
     *            "location":"40 Massachusetts Avenue, Cambridge, MA",
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
