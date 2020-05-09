package app.model;


public class CreditsRoll {
    private int personID;
    private String role;
    private String character;
    private String realName;
    private int startYear;
    private int endYear;




    public CreditsRoll(int personID, String role, String realName, String character) {
        this.personID = personID;
        this.role = role;
        this.character = character;
        this.realName = realName;
    }


    public int getPersonID() {
        return personID;
    }

    public String getRole() {
        return role;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public String getCharacter() {
        return character;
    }
    
    public String getRealName() {
    	return realName;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
