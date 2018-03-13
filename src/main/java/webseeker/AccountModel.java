/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker;

/**
 *
 * @author fitexmage
 */
public class AccountModel {

    private String username;
    private String password;
    private boolean valid;

    public AccountModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String serializeToString() {
        return "username=" + username + "&password=" + password;
    }

    public boolean isValid() {
        if (!username.equals("") && !password.equals("")) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
