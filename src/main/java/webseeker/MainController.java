package webseeker;

import java.time.ZonedDateTime;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private AccountRepository theAccountRepository;

    @Autowired
    private WebRepository theWebRepository;

    public MainController() {

    }

    @RequestMapping("/")
    public String homepage(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
            model.addAttribute("username", theAccountModel.getUsername());
        }
        List webList = (List<WebModel>) theWebRepository.findAll();
        model.addAttribute("webList", webList);
        return "homepage";
    }

    @RequestMapping("/loginpage")
    public String loginPage(Model model, HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }

    @RequestMapping("/login")
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

                    List webList = (List<WebModel>) theWebRepository.findAll();
                    model.addAttribute("webList", webList);
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

    @RequestMapping("/registerpage")
    public String registerPage(Model model) {
        return "register";
    }

    @RequestMapping("/register")
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

    @RequestMapping("/getwebinfo")
    public String getWebInfo(@RequestParam(value = "webId", defaultValue = "") Long webId,
            Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
            model.addAttribute("username", theAccountModel.getUsername());
        }
        WebModel theWebModel = theWebRepository.findById(webId);
        model.addAttribute("web", theWebModel);
        return "webinfo";
    }

    @RequestMapping("/userpage")
    public String userPage(Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        model.addAttribute("username", theAccountModel.getUsername());
        return "accountinfo";
    }

    @RequestMapping("/userpage/accountinfo")
    public String accountInfo(Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        model.addAttribute("username", theAccountModel.getUsername());
        return "accountinfo";
    }

    @RequestMapping("/modifyinfo")
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

    @RequestMapping("/userpage/addweb")
    public String addWeb(Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        model.addAttribute("username", theAccountModel.getUsername());
        return "addweb";
    }

    @RequestMapping("/addnewweb")
    public String addNewWeb(@RequestParam(value = "webName", defaultValue = "") String webName,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "category", defaultValue = "") String category,
            @RequestParam(value = "description", defaultValue = "") String description,
            Model model, HttpSession session) {
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
        model.addAttribute("username", theAccountModel.getUsername());

        WebModel newWebModel = new WebModel(theAccountModel, webName, url, WebModel.categoryToInt(category), description, 0, 0, ZonedDateTime.now());
        if (newWebModel.isValid()) {
            theWebRepository.save(newWebModel);
            model.addAttribute("alert", "Add successfully!");
        } else {
            model.addAttribute("alert", "Something was wrong!");
        }
        return "addweb";
    }

    @RequestMapping("/logoff")
    public String logOff(Model model, HttpSession session) {
        session.removeAttribute("user");
        return "homepage";
    }
}
