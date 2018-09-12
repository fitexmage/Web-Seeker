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
public class ActionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private WebModel web;

    @ManyToOne
    private AccountModel visiter;

    private ZonedDateTime visitTime;
    
    public ActionModel(WebModel web, AccountModel visiter, ZonedDateTime visitTime){
        this.web = web;
        this.visiter = visiter;
        this.visitTime = visitTime;
    }
    
    public ActionModel(){
        
    }
    
    public static ActionModel newAction(WebModel web, AccountModel poster){
        ActionModel newAction = new ActionModel(web, poster, ZonedDateTime.now());
        return newAction;
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
     * @return the visiter
     */
    public AccountModel getVisiter() {
        return visiter;
    }

    /**
     * @param visiter the visiter to set
     */
    public void setVisiter(AccountModel visiter) {
        this.visiter = visiter;
    }

    /**
     * @return the visitTime
     */
    public ZonedDateTime getVisitTime() {
        return visitTime;
    }

    /**
     * @param visitTime the visitTime to set
     */
    public void setVisitTime(ZonedDateTime visitTime) {
        this.visitTime = visitTime;
    }
}
