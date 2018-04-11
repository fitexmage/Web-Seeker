package webseeker.controller;

import webseeker.repository.WebRepository;
import webseeker.repository.RateRepository;
import webseeker.repository.ActionRepository;
import webseeker.repository.CommentRepository;
import webseeker.model.AccountModel;
import webseeker.model.WebModel;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import webseeker.SecurityService;
import webseeker.UserService;

@Controller
public class LoginController {

    public static String error = "";

    @Autowired
    private UserService theUserService;

    @Autowired
    private SecurityService theSecurityService;

    @Autowired
    private WebRepository theWebRepository;

    @Autowired
    private RateRepository theRateRepository;

    @Autowired
    private CommentRepository theCommentRepository;

    @Autowired
    private ActionRepository theActionRepository;

    public LoginController() {

    }

    @RequestMapping(value = "/login")
    public String loginPage(Model model, HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }

    @RequestMapping("/login/login")
    public String login(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            Model model, HttpSession session) {
        try {
            theSecurityService.autologin(username, password);
        } catch (BadCredentialsException e) {
            model.addAttribute("alert", "Username and password do not match!");
            return "login";
        } catch (NullPointerException e) {
            model.addAttribute("alert", "Please input valid username and password!");
            return "login";
        }

        AccountModel theAccountModel = theUserService.findByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("welcome", "Welcome " + username + "!");
        session.setAttribute("user", theAccountModel);
        List<WebModel> newList = theWebRepository.findTop5ByOrderByAddTimeDesc();
        model.addAttribute("newList", newList);

        return "homepage";

    }

    @RequestMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }

    @RequestMapping("/register/register")
    public String register(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            Model model
    ) {
        AccountModel theAccountModel = new AccountModel(username, password);
        if (theAccountModel.isValid()) {
            if (theUserService.findByUsername(username) == null) {
                theUserService.save(theAccountModel);
                theSecurityService.autologin(username, password);
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

    @RequestMapping("/logout")
    public String logOff(Model model, HttpSession session) {
        session.removeAttribute("user");

        List<WebModel> newList = theWebRepository.findTop5ByOrderByAddTimeDesc();
        model.addAttribute("newList", newList);
        return "homepage";
    }
}
