package app.model;



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



    public Account(String un, String p) {
        username = un;
        password = p;
    }


    public Account(String fn, String ln, String a, String c, String g, String email) {
        // TODO fill in here
        /* You should use this constructor when you are showing the account page,
        hence, the user is already logged in. Therefore, the username Should be used
        to fetch this information from the database. You may have to tweek some stuff
        here and there.
        You should NEVER show the current password in the form. NEVER!
        And if you want to change the password, you need to ask for current password as well.
         */
    }




    public void updateDetails() {
        // TODO
    }



    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
}
