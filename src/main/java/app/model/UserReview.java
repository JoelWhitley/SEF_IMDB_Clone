package app.model;

import java.util.Date;





public class UserReview {
    private int reviewID;
    private String username;
    private String review;
    private int rating;
    private Date date;






    public UserReview(String r, int v) {
        review = r;
        rating = v;
        date = new Date();
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

    public Date getDate() {
        return date;
    }
}
