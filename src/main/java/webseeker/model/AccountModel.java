/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author fitexmage
 */
@Entity
public class AccountModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String username;
    private String password;
    private int enabled;
    
    @ManyToMany
    @JoinTable(name = "account_model_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles;
    
    public AccountModel(String username, String password, int enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
    
    public AccountModel(){
        
    }
    
    public static AccountModel newAccount(String username, String password){
        AccountModel newAccountModel = new AccountModel(username, password, 1);
        return newAccountModel;
    }

    public boolean isValid() {
        boolean valid = false;
        if (!username.equals("") && !password.equals("")) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
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
     * @return the enabled
     */
    public int getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the roles
     */
    public Set<RoleModel> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }
}
