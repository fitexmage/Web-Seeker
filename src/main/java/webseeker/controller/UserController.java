package webseeker.controller;

import webseeker.repository.*;
import webseeker.model.*;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

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

    public UserController() {

    }

    @RequestMapping(value = {"/userpage", "/userpage/accountinfo"})
    public String userPage(Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);
        List<RateModel> rateList = theRateRepository.findByRater(theAccountModel);
        List<CommentModel> commentList = theCommentRepository.findByPoster(theAccountModel);
        List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("user", theUserModel);

        model.addAttribute("rateNum", rateList.size());
        model.addAttribute("commentNum", commentList.size());
        model.addAttribute("actionNum", actionList.size());
        return "accountinfo";
    }

    @RequestMapping("/userpage/modifyinfo")
    public String modifyInfo(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "email", defaultValue = "") String email,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        if (!username.equals("")) {
            theAccountModel.setUsername(username);
            theAccountRepository.save(theAccountModel);
            theUserModel.setName(name);
            theUserModel.setEmail(email);
            theUserRepository.save(theUserModel);
            model.addAttribute("alert", "Modify successfully!");
        } else {
            model.addAttribute("alert", "Something was wrong!");
        }

        List<RateModel> rateList = theRateRepository.findByRater(theAccountModel);
        List<CommentModel> commentList = theCommentRepository.findByPoster(theAccountModel);
        List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("user", theUserModel);
        model.addAttribute("rateNum", rateList.size());
        model.addAttribute("commentNum", commentList.size());
        model.addAttribute("actionNum", actionList.size());
        return "accountinfo";
    }

    @RequestMapping("/userpage/addweb")
    public String addWeb(Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        model.addAttribute("username", theAccountModel.getUsername());
        return "addweb";
    }

    @RequestMapping("/userpage/addnewweb")
    public String addNewWeb(@RequestParam(value = "webName", defaultValue = "") String webName,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "description", defaultValue = "") String description,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
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
    public String history(Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);
        Collections.reverse(actionList);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("actionList", actionList);

        return "history";
    }

    @RequestMapping("/userpage/deleteaction")
    public String delectAction(@RequestParam(value = "action", defaultValue = "") Long action,
            Model model) {

        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        theActionRepository.delete(action);

        theUserModel.setExp(theUserModel.getExp() - 1);
        theUserRepository.save(theUserModel);

        List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);
        Collections.reverse(actionList);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("actionList", actionList);

        return "history";
    }

    @RequestMapping("/userpage/setting")
    public String setting(Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("user", theUserModel);
        return "setting";
    }

    @RequestMapping("/userpage/modifysetting")
    public String modifySetting(@RequestParam(value = "recordAction", defaultValue = "") String recordAction,
            @RequestParam(value = "shareEmail", defaultValue = "") String shareEmail,
            @RequestParam(value = "shareAction", defaultValue = "") String shareAction,
            Model model) {
        User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        theUserModel.setRecordAction(Integer.parseInt(recordAction));
        theUserModel.setShareEmail(Integer.parseInt(shareEmail));
        theUserModel.setShareAction(Integer.parseInt(shareAction));
        theUserRepository.save(theUserModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("user", theUserModel);
        model.addAttribute("alert", "Modify successfully!");
        return "setting";
    }
}
