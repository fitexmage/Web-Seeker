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
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private WebModel web;

    @ManyToOne
    private AccountModel poster;

    private String comment;
    private ZonedDateTime postTime;
    private int likeNum;
    private int incognito;

    public CommentModel(WebModel web, AccountModel poster, String comment, ZonedDateTime postTime, int likeNum, int incognito) {
        this.web = web;
        this.poster = poster;
        this.comment = comment;
        this.postTime = postTime;
        this.likeNum = likeNum;
        this.incognito = incognito;
    }
    
    public CommentModel(){
        
    }
    
    public static CommentModel newComment(WebModel web, AccountModel poster, String comment, int incognito){
        CommentModel newComment = new CommentModel(web, poster, comment, ZonedDateTime.now(), 0, incognito);
        return newComment;
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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

    /**
     * @return the likeNum
     */
    public int getLikeNum() {
        return likeNum;
    }

    /**
     * @param likeNum the likeNum to set
     */
    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    /**
     * @return the incognito
     */
    public int getIncognito() {
        return incognito;
    }

    /**
     * @param incognito the incognito to set
     */
    public void setIncognito(int incognito) {
        this.incognito = incognito;
    }
}
