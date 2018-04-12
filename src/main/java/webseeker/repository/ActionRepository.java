/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import java.util.List;
import webseeker.model.ActionModel;
import org.springframework.data.repository.CrudRepository;
import webseeker.model.AccountModel;

/**
 *
 * @author fitexmage
 */
public interface ActionRepository extends CrudRepository<ActionModel, Long>{
    
    public List<ActionModel> findByVisiter(AccountModel theAccountModel);

}
