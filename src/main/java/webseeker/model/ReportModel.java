/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.model;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author fitexmage
 */
@Entity
public class ReportModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private WebModel web;

    @ManyToOne
    private AccountModel reporter;
    
    private ZonedDateTime rateTime;
    
    public ReportModel(WebModel web, AccountModel reporter, ZonedDateTime rateTime){
        this.web = web;
        this.reporter = reporter;
        this.rateTime = rateTime;
    }
    
    public ReportModel(){
        
    }
    
    public static ReportModel newReport(WebModel web, AccountModel reporter){
        ReportModel newReport = new ReportModel(web, reporter, ZonedDateTime.now());
        return newReport;
    }
}
