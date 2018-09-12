/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webest.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import webest.model.AccountModel;
import webest.model.ReportModel;
import webest.model.WebModel;

/**
 *
 * @author fitexmage
 */
public interface ReportRepository extends CrudRepository<ReportModel, Long>{
    
    public ReportModel findByWebAndReporterAndValid(WebModel theWebModel, AccountModel theAccountModel, int valid);
    
    public List<ReportModel> findByWebAndValid(WebModel theWebModel, int valid);
}
