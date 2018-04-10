/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker;

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
    private AccountModel poster;

    private ZonedDateTime postTime;
    
    public ActionModel(WebModel web, AccountModel poster, ZonedDateTime postTime){
        this.web = web;
        this.poster = poster;
        this.postTime = postTime;
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
     * @return the poster
     */
    public AccountModel getPoster() {
        return poster;
    }

    /**
     * @param poster the poster to set
     */
    public void setPoster(AccountModel poster) {
        this.poster = poster;
    }

    /**
     * @return the postTime
     */
    public ZonedDateTime getPostTime() {
        return postTime;
    }

    /**
     * @param postTime the postTime to set
     */
    public void setPostTime(ZonedDateTime postTime) {
        this.postTime = postTime;
    }
}
