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
public class WebModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private AccountModel creator;

    private String webName;
    private String url;
    private int category; // 1: Information 2: Tool
    private String description;
    private int rate;
    private int rater;
    private ZonedDateTime addTime;

    public WebModel(AccountModel creator, String webName, String url, int category, String description, int rate, int rater, ZonedDateTime addTime) {
        this.creator = creator;
        this.webName = webName;
        this.url = url;
        this.category = category;
        this.rate = rate;
        this.rater = rater;
        this.addTime = addTime;
    }

    public WebModel() {

    }

    public boolean isValid() {
        if (!webName.equals("") & !url.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static int categoryToInt(String category) {
        if (category.equals("information")) {
            return 1;
        } else if (category.equals("tool")) {
            return 2;
        } else if (category.equals("entertainment")) {
            return 3;
        }
        return 0;
    }
    
    public String categoryToString(){
        if(category == 1){
            return "Information";
        }
        else if(category == 2){
            return "Tool";
        }
        else if(category == 3){
            return "Entertainment";
        }
        return "";
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
     * @return the creator
     */
    public AccountModel getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(AccountModel creator) {
        this.creator = creator;
    }

    /**
     * @return the webName
     */
    public String getWebName() {
        return webName;
    }

    /**
     * @param webName the webName to set
     */
    public void setWebName(String webName) {
        this.webName = webName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the category
     */
    public int getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the rate
     */
    public int getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    /**
     * @return the rater
     */
    public int getRater() {
        return rater;
    }

    /**
     * @param rater the rater to set
     */
    public void setRater(int rater) {
        this.rater = rater;
    }

    /**
     * @return the addTime
     */
    public ZonedDateTime getAddTime() {
        return addTime;
    }

    /**
     * @param addTime the addTime to set
     */
    public void setAddTime(ZonedDateTime addTime) {
        this.addTime = addTime;
    }
}
