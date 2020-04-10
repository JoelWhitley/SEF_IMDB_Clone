package app.model;



import java.util.Date;



public class Person {
    private int personId;
    private String fullName;
    private String role;
    private String bio;
    private Date birthdate;




    public Person(int id, String fn, String r, Date bd, String b) {

    	this.personId = id;
        this.fullName = fn;
        this.role = r;
        this.birthdate = bd;
        this.bio = b;

    }



    public String getRole() {
        return role;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getFullName() {
        return fullName;
    }

    public int getPersonId() {
        return personId;
    }

    public String getBio() {
        return bio;
    }
}
