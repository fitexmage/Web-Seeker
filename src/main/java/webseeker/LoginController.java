package webseeker;

import java.io.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    AccountListModel theAccountListModel;

    public LoginController() {
        theAccountListModel = new AccountListModel();
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            Model model) {
        AccountModel theAccountModel = new AccountModel(username, password);
        if (theAccountModel.isValid()) {
            model.addAttribute("username", username);
            return "thankyou";
        } else {
            model.addAttribute("alert", "Please input valid username and password!");
            return "login";
        }
    }

    @RequestMapping("/register")
    public String register(@RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            Model model) {
        AccountModel theAccountModel = new AccountModel(username, password);
        if (theAccountModel.isValid()) {
            if (theAccountListModel.addAccount(theAccountModel)) {
                model.addAttribute("alert", "Register Successfully!");
                return "register";
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
