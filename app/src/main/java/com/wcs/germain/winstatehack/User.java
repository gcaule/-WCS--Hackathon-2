package com.wcs.germain.winstatehack;

/**
 * Created by apprenti on 21/12/17.
 */

public class User {

    String firstName;
    String mail;
    String password;
    int nbWin;
    String id;

    public User() {
    }

    public User(String name, String mail, String password, String id) {
        this.firstName = name;
        this.mail = mail;
        this.password = password;
        this.nbWin = 0;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNbWin() {
        return nbWin;
    }

    public void setNbWin(int nbWin) {
        this.nbWin = nbWin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
