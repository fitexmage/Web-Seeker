/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webest.model;

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
    private int valid;
    
    public ReportModel(WebModel web, AccountModel reporter, ZonedDateTime rateTime, int valid){
        this.web = web;
        this.reporter = reporter;
        this.rateTime = rateTime;
        this.valid = valid;
    }
    
    public ReportModel(){
        
    }
    
    public static ReportModel newReport(WebModel web, AccountModel reporter){
        ReportModel newReport = new ReportModel(web, reporter, ZonedDateTime.now(), 1);
        return newReport;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the web
     */
    public WebModel getWeb() {
        return web;
    }

    /**
     * @param web the web to set
     */
    public void setWeb(WebModel web) {
        this.web = web;
    }

    /**
     * @return the reporter
     */
    public AccountModel getReporter() {
        return reporter;
    }

    /**
     * @param reporter the reporter to set
     */
    public void setReporter(AccountModel reporter) {
        this.reporter = reporter;
    }

    /**
     * @return the rateTime
     */
    public ZonedDateTime getRateTime() {
        return rateTime;
    }

    /**
     * @param rateTime the rateTime to set
     */
    public void setRateTime(ZonedDateTime rateTime) {
        this.rateTime = rateTime;
    }

    /**
     * @return the valid
     */
    public int getValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(int valid) {
        this.valid = valid;
    }
}
