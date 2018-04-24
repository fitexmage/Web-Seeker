/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.model;

import static java.lang.Math.sqrt;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author fitexmage
 */
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private AccountModel user;

    private String name;
    private String email;
    private int exp;

    private int recordAction;
    private int shareEmail;
    private int shareAction;

    public UserModel(AccountModel user, String name, String email, int exp, int recordAction, int shareEmail, int shareAction) {
        this.user = user;
        this.name = name;
        this.email = email;
        this.exp = exp;
        this.recordAction = recordAction;
        this.shareEmail = shareEmail;
        this.shareAction = shareAction;
    }

    public UserModel() {

    }
    
    public static UserModel newUser(AccountModel theAccountModel){
        UserModel newUserModel = new UserModel(theAccountModel, "New User", "", 0, 1, 1, 1);
        return newUserModel;
    }

    public int level() {
        if (exp == 0) {
            return 1;
        } else {
            return (int) sqrt((double) exp);
        }
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public AccountModel getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(AccountModel user) {
        this.user = user;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the exp
     */
    public int getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

    /**
     * @return the recordAction
     */
    public int getRecordAction() {
        return recordAction;
    }

    /**
     * @param recordAction the recordAction to set
     */
    public void setRecordAction(int recordAction) {
        this.recordAction = recordAction;
    }

    /**
     * @return the shareEmail
     */
    public int getShareEmail() {
        return shareEmail;
    }

    /**
     * @param shareEmail the shareEmail to set
     */
    public void setShareEmail(int shareEmail) {
        this.shareEmail = shareEmail;
    }

    /**
     * @return the shareAction
     */
    public int getShareAction() {
        return shareAction;
    }

    /**
     * @param shareAction the shareAction to set
     */
    public void setShareAction(int shareAction) {
        this.shareAction = shareAction;
    }
}
