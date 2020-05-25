package app.model;


import java.util.List;

import app.model.enumeration.ShowStatus;


public class Show {
    private int showid;
    private int year;
    private double length;
    private boolean isMovie;
    private boolean isSeries;
    private String genre;
    private String productionCompany;
    private String showTitle;
    private ShowStatus showStatus;
    private String status;
    //TODO
    private List<UserReview> userReviewList;
    private List<CreditsRoll> creditsRolls;
    
    

    public Show(int showid,String showTitle,double length,boolean isMovie,
    		boolean isSeries,String genre,int year,ShowStatus status,
    		String proCo) {
    	this.showid = showid;
    	this.showTitle = showTitle;
    	this.length = length;
    	this.isMovie = isMovie;
    	this.isSeries = isSeries;
    	this.genre = genre;
    	this.year = year;
    	this.showStatus = status;
    	this.status = status.getString();
    	this.productionCompany = proCo;
    }
    public int getShowid() {
    	return showid;
    }
    public String getShowTitle() {
    	return showTitle;
    }
    
    public double getLength() {
    	return length;
    }
    
    public int getIsMovie() {
    	if (isMovie) return 1;
    	else return 0;
    }
    
    public int getIsSeries() {
    	if (isSeries) return 1;
    	else return 0;
    	
    }
    
    public String getGenre() {
    	return genre;
    }
    
    public int getYear() {
    	return year;
    }
    
    public String getProCo() {
    	return productionCompany;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public ShowStatus getShowStatus() {
    	return showStatus;
    }

}
