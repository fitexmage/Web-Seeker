/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import org.springframework.data.repository.CrudRepository;

import webseeker.model.AccountModel;

/**
 *
 * @author fitexmage
 */
public interface AccountRepository extends CrudRepository<AccountModel, Long>{
    
    public AccountModel findByUsername(String username);
    
}
