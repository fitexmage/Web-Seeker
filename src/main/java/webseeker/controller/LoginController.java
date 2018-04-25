package webseeker.controller;

import webseeker.repository.*;
import webseeker.model.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webseeker.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService theUserService;
    
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

    public LoginController() {

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error) {
        if (error != null) {
            model.addAttribute("alert", "Your username and password is invalid.");
        }
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());
            List<WebModel> newList = theWebRepository.findTop1000ByOrderByAddTimeDesc();
            model.addAttribute("newList", newList);
            return "homepage";
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            Model model) {
        AccountModel newAccountModel = AccountModel.newAccount(username, password);
        if (newAccountModel.isValid()) {
            if (theUserService.findByUsername(username) == null) {
                theUserService.save(newAccountModel);
                UserModel newUserModel = UserModel.newUser(newAccountModel);
                theUserRepository.save(newUserModel);
                model.addAttribute("alert", "Register Successfully!");
                return "login";
            } else {
                model.addAttribute("alert", "Username already exists!");
                return "register";
            }
        } else {
            model.addAttribute("alert", "Please input valid username and password!");
            return "register";
        }
    }
}
