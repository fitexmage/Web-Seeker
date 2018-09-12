/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webest.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import webest.model.AccountModel;
import webest.model.UserModel;

/**
 *
 * @author fitexmage
 */
public interface UserRepository extends CrudRepository<UserModel, Long>{
    
    public UserModel findById(Long id);
    
    public UserModel findByUser(AccountModel theAccountModel);
    
    public List<UserModel> findByNameContainingIgnoreCase(String name);
}
