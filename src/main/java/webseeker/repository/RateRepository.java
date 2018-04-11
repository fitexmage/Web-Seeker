/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import webseeker.model.AccountModel;
import webseeker.model.RateModel;
import webseeker.model.WebModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fitexmage
 */
public interface RateRepository extends CrudRepository<RateModel, Long>{
    
    public RateModel findByWebAndRater(WebModel theWebModel, AccountModel theAccountModel);
}
