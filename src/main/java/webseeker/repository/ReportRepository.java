/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import webseeker.model.AccountModel;
import webseeker.model.ReportModel;
import webseeker.model.WebModel;

/**
 *
 * @author fitexmage
 */
public interface ReportRepository extends CrudRepository<ReportModel, Long>{
    
    public ReportModel findByWebAndReporterAndValid(WebModel theWebModel, AccountModel theAccountModel, int valid);
    
    public List<ReportModel> findByWebAndValid(WebModel theWebModel, int valid);
}
