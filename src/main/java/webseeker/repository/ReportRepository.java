/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import org.springframework.data.repository.CrudRepository;
import webseeker.model.AccountModel;
import webseeker.model.ReportModel;
import webseeker.model.WebModel;

/**
 *
 * @author fitexmage
 */
public interface ReportRepository extends CrudRepository<ReportModel, Long>{
    
    public ReportModel findByWebAndReporter(WebModel theWebModel, AccountModel theAccountModel);
}
