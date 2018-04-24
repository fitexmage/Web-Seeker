/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import webseeker.model.AccountModel;
import webseeker.model.UserModel;

/**
 *
 * @author fitexmage
 */
public interface UserRepository extends CrudRepository<UserModel, Long>{
    
    public UserModel findById(Long id);
    
    public UserModel findByUser(AccountModel theAccountModel);
    
    public List<UserModel> findByNameContainingIgnoreCase(String name);
}
