/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.model;

import java.text.DecimalFormat;
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

    @ManyToOne
    private AccountModel editor;

    private String webName;
    private String url;
    private int category;
    private String description;
    private int totalScore;
    private int rater; // Num of raters
    private double rate;
    private int comment;
    private int action;
    private int report;
    private ZonedDateTime addTime;

    public WebModel(AccountModel creator, AccountModel editor, String webName, String url, int category, String description, int totalScore, int rater, double rate, int comment, int action, int report, ZonedDateTime addTime) {
        this.creator = creator;
        this.editor = editor;
        this.webName = webName;
        this.url = url;
        this.category = category;
        this.description = description;
        this.totalScore = totalScore;
        this.rater = rater;
        this.rate = rate;
        this.comment = comment;
        this.action = action;
        this.report = report;
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

    public static WebModel newWeb(AccountModel theAccountModel, String webName, String url, String category, String description) {
        WebModel newWebModel = new WebModel(theAccountModel, theAccountModel, webName, url, WebModel.categoryToInt(category), description, 0, 0, 0, 0, 0, 0, ZonedDateTime.now());
        return newWebModel;
    }

    public static int categoryToInt(String category) {
        if (category.equals("portal")) {
            return 1;
        } else if (category.equals("ecommerce")) {
            return 2;
        } else if (category.equals("service")) {
            return 3;
        } else if (category.equals("media")) {
            return 4;
        } else if (category.equals("corporate")) {
            return 5;
        } else if (category.equals("education")) {
            return 6;
        } else if (category.equals("personal")) {
            return 7;
        } else if (category.equals("other")) {
            return 8;
        }
        return 0;
    }

    public static String categoryToString(int category) {
        if (category == 1) {
            return "Portal";
        } else if (category == 2) {
            return "E-commerce";
        } else if (category == 3) {
            return "Service";
        } else if (category == 4) {
            return "Media";
        } else if (category == 5) {
            return "Corporate";
        } else if (category == 6) {
            return "Education";
        } else if (category == 7) {
            return "Personal";
        } else if (category == 8) {
            return "Other";
        }
        return "";
    }

    public String rateInString() {
        DecimalFormat df = new DecimalFormat("#.0");
        if (totalScore != 0) {
            return df.format((double) totalScore / (double) rater);
        } else {
            return "0";
        }
    }

    public double rate() {
        if (totalScore != 0) {
            return (double) totalScore / (double) rater;
        } else {
            return 0;
        }
    }

    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
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
     * @return the editor
     */
    public AccountModel getEditor() {
        return editor;
    }

    /**
     * @param editor the editor to set
     */
    public void setEditor(AccountModel editor) {
        this.editor = editor;
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
     * @return the totalScore
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * @param totalScore the totalScore to set
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
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
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * @return the comment
     */
    public int getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(int comment) {
        this.comment = comment;
    }

    /**
     * @return the action
     */
    public int getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(int action) {
        this.action = action;
    }

    /**
     * @return the report
     */
    public int getReport() {
        return report;
    }

    /**
     * @param report the report to set
     */
    public void setReport(int report) {
        this.report = report;
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
