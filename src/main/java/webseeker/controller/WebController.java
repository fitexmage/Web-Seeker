/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webseeker.model.*;
import webseeker.repository.*;

/**
 *
 * @author fitexmage
 */
@Controller
public class WebController {

    @Autowired
    private AccountRepository theAccountRepository;

    @Autowired
    private UserRepository theUserRepository;

    @Autowired
    private WebRepository theWebRepository;

    @Autowired
    private RateRepository theRateRepository;

    @Autowired
    private CommentRepository theCommentRepository;

    @Autowired
    private ActionRepository theActionRepository;

    @Autowired
    private ReportRepository theReportRepository;

    public WebController() {

    }

    @RequestMapping("/webinfo")
    public String webInfo(@RequestParam(value = "web", defaultValue = "") Long webId,
            Model model) {
        WebModel theWebModel = theWebRepository.findById(webId);

        boolean canRate = false;
        boolean canReport = false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());

            AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
            UserModel theUserModel = theUserRepository.findByUser(theAccountModel);
            RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
            ReportModel theReportModel = theReportRepository.findByWebAndReporterAndValid(theWebModel, theAccountModel, 1);

            canRate = true;
            canReport = true;
            if (theRateModel != null) {
                canRate = false;
            }
            if (theReportModel != null) {
                canReport = false;
            }

            if (theUserModel.getRecordAction() == 1) {
                ActionModel newAction = ActionModel.newAction(theWebModel, theAccountModel);
                theActionRepository.save(newAction);

                theWebModel.setAction(theWebModel.getAction() + 1);
                theWebRepository.save(theWebModel);

                theUserModel.setExp(theUserModel.getExp() + 1);
                theUserRepository.save(theUserModel);
            }
        }

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canReport", canReport);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/webinfo/rate")
    public String rate(@RequestParam(value = "score", defaultValue = "") String score,
            @RequestParam(value = "web", defaultValue = "") Long webId,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        WebModel theWebModel = theWebRepository.findById(webId);

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        ReportModel theReportModel = theReportRepository.findByWebAndReporterAndValid(theWebModel, theAccountModel, 1);

        if (theRateModel == null) {
            RateModel newRate = RateModel.newRate(theWebModel, theAccountModel, Integer.parseInt(score));
            theRateRepository.save(newRate);

            theWebModel.setRater(theWebModel.getRater() + 1);
            theWebModel.setTotalScore(theWebModel.getTotalScore() + Integer.parseInt(score));
            theWebModel.setRate(theWebModel.rate());
            theWebRepository.save(theWebModel);

            theUserModel.setExp(theUserModel.getExp() + 2);
            theUserRepository.save(theUserModel);
        }

        boolean canRate = false;
        boolean canReport = true;
        if (theReportModel != null) {
            canReport = false;
        }

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canReport", canReport);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/webinfo/report")
    public String report(@RequestParam(value = "web", defaultValue = "") Long webId,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());

        WebModel theWebModel = theWebRepository.findById(webId);

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        ReportModel theReportModel = theReportRepository.findByWebAndReporterAndValid(theWebModel, theAccountModel, 1);

        if (theReportModel == null) {
            ReportModel newReport = ReportModel.newReport(theWebModel, theAccountModel);
            theReportRepository.save(newReport);

            theWebModel.setReport(theWebModel.getReport() + 1);
            theWebRepository.save(theWebModel);
        }

        boolean canRate = true;
        boolean canReport = false;
        if (theRateModel != null) {
            canRate = false;
        }

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canReport", canReport);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/webinfo/requesteditweb")
    public String requestEditWeb(@RequestParam(value = "web", defaultValue = "") Long webId,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        WebModel theWebModel = theWebRepository.findById(webId);

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        ReportModel theReportModel = theReportRepository.findByWebAndReporterAndValid(theWebModel, theAccountModel, 1);

        boolean canRate = true;
        boolean canReport = true;
        if (theRateModel != null) {
            canRate = false;
        }
        if (theReportModel != null) {
            canReport = false;
        }

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        if (theWebModel.getReport() >= 1) {
            int reportNum = 0;
            List<WebModel> addWebList = theWebRepository.findByEditor(theAccountModel);
            for (WebModel theWeb : addWebList) {
                reportNum += theWeb.getReport();
            }

            if (theUserModel.level() >= 4 && reportNum < 5) {
                model.addAttribute("web", theWebModel);
                return "editweb";
            } else {
                model.addAttribute("username", theAccountModel.getUsername());
                model.addAttribute("web", theWebModel);
                model.addAttribute("canRate", canRate);
                model.addAttribute("canReport", canReport);
                model.addAttribute("alert", "You do not have permission!");
                model.addAttribute("commentList", commentList);

                return "webinfo";
            }
        } else {
            model.addAttribute("username", theAccountModel.getUsername());
            model.addAttribute("web", theWebModel);
            model.addAttribute("canRate", canRate);
            model.addAttribute("canReport", canReport);
            model.addAttribute("alert", "Not enough reports, do not need to edit!");
            model.addAttribute("commentList", commentList);

            return "webinfo";
        }
    }

    @RequestMapping("/webinfo/editweb")
    public String editWeb(@RequestParam(value = "web", defaultValue = "") Long webId,
            @RequestParam(value = "webName", defaultValue = "") String webName,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "description", defaultValue = "") String description,
            Model model) {

        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        model.addAttribute("username", theAccountModel.getUsername());

        WebModel theWebModel = theWebRepository.findByUrl(url);
        WebModel updatedWebModel = theWebRepository.findById(webId);
        if (theWebModel == null || updatedWebModel.getUrl().equals(url)) {
            updatedWebModel.setWebName(webName);
            updatedWebModel.setUrl(url);
            updatedWebModel.setCategory(WebModel.categoryToInt(category));
            updatedWebModel.setDescription(description);
            if (updatedWebModel.error().equals("")) {

                updatedWebModel.setEditor(theAccountModel);
                updatedWebModel.setReport(0);
                theWebRepository.save(updatedWebModel);

                RateModel theRateModel = theRateRepository.findByWebAndRater(updatedWebModel, theAccountModel);
                List<ReportModel> reportList = theReportRepository.findByWebAndValid(updatedWebModel, 1);
                for (ReportModel theReportModel : reportList) {
                    theReportModel.setValid(0);
                    theReportRepository.save(theReportModel);
                }

                boolean canRate = true;
                boolean canReport = true;
                if (theRateModel != null) {
                    canRate = false;
                }

                List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

                model.addAttribute("web", updatedWebModel);
                model.addAttribute("canRate", canRate);
                model.addAttribute("canReport", canReport);
                model.addAttribute("commentList", commentList);

                return "webinfo";
            } else {
                model.addAttribute("web", theWebModel);
                model.addAttribute("alert", updatedWebModel.error());
                return "editweb";
            }
        } else {
            model.addAttribute("web", theWebModel);
            model.addAttribute("alert", "URL already exists!");
            return "editweb";
        }
    }

    @RequestMapping("/webinfo/comment")
    public String comment(@RequestParam(value = "comment", defaultValue = "") String comment,
            @RequestParam(value = "incognito", defaultValue = "0") String incognito,
            @RequestParam(value = "web", defaultValue = "") Long webId,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        WebModel theWebModel = theWebRepository.findById(webId);

        CommentModel newComment = CommentModel.newComment(theWebModel, theAccountModel, comment, Integer.parseInt(incognito));
        theCommentRepository.save(newComment);

        theWebModel.setComment(theWebModel.getComment() + 1);
        theWebRepository.save(theWebModel);

        theUserModel.setExp(theUserModel.getExp() + 2);
        theUserRepository.save(theUserModel);

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        ReportModel theReportModel = theReportRepository.findByWebAndReporterAndValid(theWebModel, theAccountModel, 1);

        boolean canRate = true;
        boolean canReport = true;
        if (theRateModel != null) {
            canRate = false;
        }
        if (theReportModel != null) {
            canReport = false;
        }

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canReport", canReport);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/webinfo/deletecomment")
    public String deleteComment(@RequestParam(value = "web", defaultValue = "") Long webId,
            @RequestParam(value = "comment", defaultValue = "") Long commentId,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        WebModel theWebModel = theWebRepository.findById(webId);

        theCommentRepository.delete(commentId);

        theWebModel.setComment(theWebModel.getComment() - 1);
        theWebRepository.save(theWebModel);

        theUserModel.setExp(theUserModel.getExp() - 2);
        theUserRepository.save(theUserModel);

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        ReportModel theReportModel = theReportRepository.findByWebAndReporterAndValid(theWebModel, theAccountModel, 1);

        boolean canRate = true;
        boolean canReport = true;
        if (theRateModel != null) {
            canRate = false;
        }
        if (theReportModel != null) {
            canReport = false;
        }

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canReport", canReport);
        model.addAttribute("commentList", commentList);

        return "webinfo";
    }

    @RequestMapping("/webinfo/like")
    public String like(@RequestParam(value = "comment", defaultValue = "") Long commentId,
            @RequestParam(value = "web", defaultValue = "") Long webId,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());

        WebModel theWebModel = theWebRepository.findById(webId);

        CommentModel theCommentModel = theCommentRepository.findById(commentId);
        theCommentModel.setLikeNum(theCommentModel.getLikeNum() + 1);
        theCommentRepository.save(theCommentModel);

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        ReportModel theReportModel = theReportRepository.findByWebAndReporterAndValid(theWebModel, theAccountModel, 1);

        boolean canRate = true;
        boolean canReport = true;
        if (theRateModel != null) {
            canRate = false;
        }
        if (theReportModel != null) {
            canReport = false;
        }

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canReport", canReport);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }
}
