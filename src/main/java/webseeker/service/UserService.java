/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.service;

import webseeker.model.AccountModel;

/**
 *
 * @author fitexmage
 */
public interface UserService {
    
    public void save(AccountModel theAccountModel);
    
    public AccountModel findByUsername(String username);
}
