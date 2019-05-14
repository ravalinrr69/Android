package androidservices.ravalinrr.com.myapplication11;

/**
 * Created by ravalin on 4/12/18.
 */

public class Review {

    private String review;
    private String location;
    private String restaurantName;
    private String rating;
    private int image;

    public Review(String restaurantName, String location, String review, String rating) {
        this.review = review;
        this.location = location;
        this.restaurantName = restaurantName;
        this.rating = rating;
    }

    public String getRating() {

        return rating;
    }

    public String getReview() {

        return review;
    }

    public String getLocation() {

        return location;
    }

    public String getRestaurantName() {

        return restaurantName;
    }

    public int getImage() {

        return image;
    }
}
