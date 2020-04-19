package app.model;

import java.text.SimpleDateFormat;
import java.util.Date;




/**
 * Stores a User Review, containing UNIQUE immutable reviewID, an immutable username, an immutable date, and a rating.
 * NB. There are unique constructors for creating a review for the first time and for reconstructing one based on database content.
 * ALso note: Both starred ratings and written reviews use this class. Thus not all ratings will have a review.
 * However, every written review must be accompanied by a rating in the current implementation.
 * The review constructors call the related rating constructors to minimize code repetition.
 * @author Cameron
 *
 */
public class UserReview {
    private int reviewID;
    private String username;
    private String review;
    private int showID;
    

	private int rating;
    private Date date;



    //Used when creating a star rating for the first time
    public UserReview(int reviewID, String username, int showID, int rating) {
    	this.reviewID = reviewID;
    	this.username = username;
    	this.showID = showID;
    	this.rating = rating;
    	this.date = new Date();
    }
    
    //Used when getting a star rating from the database.
    public UserReview(int reviewID, String username, int showID, int rating,  Date date) {
    	this.reviewID = reviewID;
    	this.username = username;
    	this.showID = showID;
    	this.rating = rating;
    	this.date = date;
    }

    //Used when creating a written review for the first time.
    public UserReview(int reviewID, String username, int showID, int rating, String review) {
    	this(reviewID, username, showID, rating);
        this.review = review;
    }
    
    //Used when getting a written review from the database.
    public UserReview(int reviewID, String username, int showID, int rating, String review, Date date) {
    	this(reviewID, username, showID, rating, date);
        this.review = review;
    }
    

    public int getReviewID() {
		return reviewID;
	}


	public String getUsername() {
		return username;
	}
	
	public int getShowID() {
		return showID;
	}

	public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

	public String getDate() {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }
}
