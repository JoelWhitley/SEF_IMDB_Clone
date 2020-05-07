package app.model;

import app.model.enumeration.AccountRole;

public class Account {
    private String firstName;
    private String lastName;
    private String address;
    private String username;

    /**
     * Only stores hashed passwords.
     */
    private String password;

    private String country;
    private String gender;
    private String email;
    private AccountRole type;



	public Account(String un, String p) {
        username = un;
        password = p;
    }


    public Account(String username, String password, String fn, String ln, String a, String c, String g, String email,AccountRole type) {
        // TODO fill in here
        /* You should use this constructor when you are showing the account page,
        hence, the user is already logged in. Therefore, the username Should be used
        to fetch this information from the database. You may have to tweek some stuff
        here and there.
        You should NEVER show the current password in the form. NEVER!
        And if you want to change the password, you need to ask for current password as well.
         */
    	this(username, password);
    	this.firstName = fn;
    	this.lastName = ln;
    	this.address = a;
    	this.country = c;
    	this.gender = g;
    	this.email= email;
    	this.type = type;
    }




    public void updateDetails() {
        // TODO
    }
    
    public String getFirstName() {
    	return firstName;
    }

    public String getLastName() {
    	return lastName;
    }
    
    public String getAddress() {
    	return address;
    }
    
    public String getCountry() {
    	return country;
    }

    public String getGender() {
    	return gender;
    }
    
    public String getEmail() {
    	return email;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
    
    public AccountRole getType() {
    	return type;
    }
    public void setRole(AccountRole ar) {
    	this.type = ar;
    }
}
