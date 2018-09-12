/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webest.repository;

import org.springframework.data.repository.CrudRepository;

import webest.model.AccountModel;

/**
 *
 * @author fitexmage
 */
public interface AccountRepository extends CrudRepository<AccountModel, Long>{
    
    public AccountModel findById(Long id);
    
    public AccountModel findByUsername(String username);
    
}
