/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webest.repository;

import webest.model.AccountModel;
import webest.model.RateModel;
import webest.model.WebModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fitexmage
 */
public interface RateRepository extends CrudRepository<RateModel, Long>{
    
    public List<RateModel> findByRater(AccountModel theAccountModel);
    
    public RateModel findByWebAndRater(WebModel theWebModel, AccountModel theAccountModel);
}
