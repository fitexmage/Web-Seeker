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
public class RateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private WebModel web;

    @ManyToOne
    private AccountModel rater;
    
    private int score;
    private ZonedDateTime rateTime;
    
    public RateModel(WebModel web, AccountModel rater, int score, ZonedDateTime rateTime){
        this.web = web;
        this.rater = rater;
        this.score = score;
        this.rateTime = rateTime;
    }
    
    public RateModel(){
        
    }
    
    public static RateModel newRate(WebModel web, AccountModel rater, int score){
        RateModel newRate = new RateModel(web, rater, score, ZonedDateTime.now());
        return newRate;
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
     * @return the rater
     */
    public AccountModel getRater() {
        return rater;
    }

    /**
     * @param rater the rater to set
     */
    public void setRater(AccountModel rater) {
        this.rater = rater;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
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
}
