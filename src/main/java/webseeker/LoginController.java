package webseeker;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

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

    public LoginController() {

    }

    @RequestMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }

    @RequestMapping("/login/login")
    public String login(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            Model model, HttpSession session) {
        AccountModel newAccountModel = new AccountModel(username, password);
        if (newAccountModel.isValid()) {
            AccountModel theAccountModel = theAccountRepository.findByUsername(username);
            if (theAccountModel != null) {
                if (theAccountModel.getPassword().equals(password)) {
                    model.addAttribute("username", username);
                    model.addAttribute("welcome", "Welcome " + username + "!");
                    session.setAttribute("user", theAccountModel);

                    List<WebModel> newList = theWebRepository.findTop5ByOrderByAddTimeDesc();
                    model.addAttribute("newList", newList);
                    return "homepage";
                }
            }
            model.addAttribute("alert", "Username and password do not match!");
            return "login";
        } else {
            model.addAttribute("alert", "Please input username and password!");
            return "login";
        }
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
            if (theAccountRepository.findByUsername(username) == null) {
                theAccountRepository.save(theAccountModel);
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

    @RequestMapping("/logoff")
    public String logOff(Model model, HttpSession session) {
        session.removeAttribute("user");

        List<WebModel> newList = theWebRepository.findTop5ByOrderByAddTimeDesc();
        model.addAttribute("newList", newList);
        return "homepage";
    }
}
