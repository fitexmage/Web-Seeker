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
            ReportModel theReportModel = theReportRepository.findByWebAndReporter(theWebModel, theAccountModel);

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
        ReportModel theReportModel = theReportRepository.findByWebAndReporter(theWebModel, theAccountModel);

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

        boolean canRate = true;
        boolean canReport = true;

        canRate = false;

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
        ReportModel theReportModel = theReportRepository.findByWebAndReporter(theWebModel, theAccountModel);

        if (theReportModel == null) {
            ReportModel newReport = ReportModel.newReport(theWebModel, theAccountModel);
            theReportRepository.save(newReport);

            theWebModel.setReport(theWebModel.getReport() + 1);
            theWebRepository.save(theWebModel);
        }

        boolean canRate = true;
        boolean canReport = true;

        if (theRateModel != null) {
            canRate = false;
        }

        canReport = false;

        List<CommentModel> commentList = theCommentRepository.findByWebOrderByPostTimeDesc(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canReport", canReport);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/webinfo/requestmodifyweb")
    public String requestModifyWeb(@RequestParam(value = "web", defaultValue = "") Long webId,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        WebModel theWebModel = theWebRepository.findById(webId);

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        ReportModel theReportModel = theReportRepository.findByWebAndReporter(theWebModel, theAccountModel);

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
            List<WebModel> addWebList = theWebRepository.findByCreator(theAccountModel);
            for (WebModel theWeb : addWebList) {
                reportNum += theWeb.getReport();
            }

            if (theUserModel.level() >= 4 && reportNum < 1) {
                model.addAttribute("web", theWebModel);
                return "modifyweb";
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
            model.addAttribute("alert", "Not enough reports, do not need to modify!");
            model.addAttribute("commentList", commentList);

            return "webinfo";
        }
    }

    @RequestMapping("/webinfo/modifyweb")
    public String modifyWeb(Model model) {
        
        return "webinfo";
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
        ReportModel theReportModel = theReportRepository.findByWebAndReporter(theWebModel, theAccountModel);

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
        ReportModel theReportModel = theReportRepository.findByWebAndReporter(theWebModel, theAccountModel);

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
        ReportModel theReportModel = theReportRepository.findByWebAndReporter(theWebModel, theAccountModel);

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
