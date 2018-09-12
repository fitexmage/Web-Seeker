/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webest.repository;

import java.util.List;
import webest.model.ActionModel;
import org.springframework.data.repository.CrudRepository;
import webest.model.AccountModel;

/**
 *
 * @author fitexmage
 */
public interface ActionRepository extends CrudRepository<ActionModel, Long>{
    
    public List<ActionModel> findByVisiter(AccountModel theAccountModel);

}
