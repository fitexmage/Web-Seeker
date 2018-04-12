package webseeker.controller;

import webseeker.repository.*;
import webseeker.model.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private AccountRepository theAccountRepository;

    @Autowired
    private WebRepository theWebRepository;

    @Autowired
    private RateRepository theRateRepository;

    @Autowired
    private CommentRepository theCommentRepository;

    @Autowired
    private ActionRepository theActionRepository;

    public UserController() {

    }

    @RequestMapping("/userpage")
    public String userPage(Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        List<RateModel> rateList = theRateRepository.findByRater(theAccountModel);
        List<CommentModel> commentList = theCommentRepository.findByPoster(theAccountModel);
        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("rateNum", rateList.size());
        model.addAttribute("commentNum", commentList.size());
        return "accountinfo";
    }

    @RequestMapping("/userpage/accountinfo")
    public String accountInfo(Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        List<RateModel> rateList = theRateRepository.findByRater(theAccountModel);
        List<CommentModel> commentList = theCommentRepository.findByPoster(theAccountModel);
        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("rateNum", rateList.size());
        model.addAttribute("commentNum", commentList.size());
        return "accountinfo";
    }

    @RequestMapping("/userpage/modifyinfo")
    public String modifyInfo(@RequestParam(value = "username", defaultValue = "") String username,
            Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");

        if (!username.equals("")) {
            theAccountModel.setUsername(username);
            theAccountRepository.save(theAccountModel);
            model.addAttribute("alert", "Modify successfully!");
        } else {
            model.addAttribute("alert", "Something was wrong!");
        }

        model.addAttribute("username", theAccountModel.getUsername());
        return "accountinfo";
    }

    @RequestMapping("/userpage/favorites")
    public String favorites(Model model, HttpSession session) {
        return "homepage";
    }

    @RequestMapping("/userpage/addweb")
    public String addWeb(Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        model.addAttribute("username", theAccountModel.getUsername());
        return "addweb";
    }

    @RequestMapping("/userpage/addnewweb")
    public String addNewWeb(@RequestParam(value = "webName", defaultValue = "") String webName,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "description", defaultValue = "") String description,
            Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        model.addAttribute("username", theAccountModel.getUsername());
        WebModel theWebModel = theWebRepository.findByUrl(url);
        if (theWebModel == null) {
            WebModel newWebModel = WebModel.newWeb(theAccountModel, webName, url, category, description);
            if (newWebModel.isValid()) {
                theWebRepository.save(newWebModel);
                model.addAttribute("alert", "Add successfully!");
            } else {
                model.addAttribute("alert", "Web name and URL should not be empty!");
            }
        } else {
            model.addAttribute("alert", "URL already exists!");
        }
        return "addweb";
    }

    @RequestMapping("/userpage/history")
    public String history(Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);
        Collections.reverse(actionList);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("actionList", actionList);

        return "history";
    }

    @RequestMapping("/userpage/deleteaction")
    public String delectAction(@RequestParam(value = "action", defaultValue = "") Long action,
            Model model, HttpSession session) {

        theActionRepository.delete(action);

        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);
        Collections.reverse(actionList);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("actionList", actionList);

        return "history";
    }

    @RequestMapping("/userpage/setting")
    public String setting(Model model, HttpSession session) {
        return "homepage";
    }
}
