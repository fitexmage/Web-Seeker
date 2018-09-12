package webest.controller;

import webest.repository.ActionRepository;
import webest.repository.WebRepository;
import webest.repository.UserRepository;
import webest.repository.AccountRepository;
import webest.repository.RateRepository;
import webest.repository.ReportRepository;
import webest.repository.CommentRepository;
import webest.model.CommentModel;
import webest.model.WebModel;
import webest.model.AccountModel;
import webest.model.ActionModel;
import webest.model.UserModel;
import webest.model.RateModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

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

    public MainController() {

    }

    @RequestMapping("/")
    public String homepage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());

            AccountModel theAccountModel = theAccountRepository.findByUsername(theUser.getUsername());
            List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);
            HashMap<Integer, Integer> webVisitMap = new HashMap<Integer, Integer>();
            for (ActionModel theActionModel : actionList) {
                int category = theActionModel.getWeb().getCategory();
                if (webVisitMap.containsKey(category)) {
                    webVisitMap.put(category, webVisitMap.get(category) + 1);
                } else {
                    webVisitMap.put(category, 1);
                }
            }
            List<Map.Entry<Integer, Integer>> webVisitList = new ArrayList<Map.Entry<Integer, Integer>>(webVisitMap.entrySet());
            Collections.sort(webVisitList, new Comparator<Map.Entry<Integer, Integer>>() {
                public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            if (webVisitList.size() >= 2) {
                int category1st = webVisitList.get(0).getKey();
                int category2nd = webVisitList.get(1).getKey();
                List<WebModel> webList = theWebRepository.findByCategoryOrCategory(category1st, category2nd);

                ArrayList<Integer> randomArray = WebModel.randomCommon(0, webList.size() - 1, 10);
                ArrayList<WebModel> recommendList = new ArrayList<WebModel>();
                for (int i = 0; i < randomArray.size(); i++) {
                    recommendList.add(webList.get(randomArray.get(i)));
                }
                model.addAttribute("recommendList", recommendList);
            }
        }

        List<WebModel> newList = theWebRepository.findTop10ByOrderByAddTimeDesc();
        model.addAttribute("newList", newList);
        return "homepage";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(value = "input", defaultValue = "") String input,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());
        }

        if (!input.equals("")) {
            List<WebModel> webSearchList = theWebRepository.findByWebNameContainingIgnoreCaseOrUrlContainingIgnoreCaseOrDescriptionContainingIgnoreCase(input, input, input);
            List<UserModel> userSearchList = theUserRepository.findByNameContainingIgnoreCase(input);
            model.addAttribute("input", input);
            model.addAttribute("webList", webSearchList);
            model.addAttribute("userList", userSearchList);
        }else{
            List<WebModel> webSearchList = new ArrayList<WebModel>();
            List<UserModel> userSearchList = new ArrayList<UserModel>();
            model.addAttribute("input", input);
            model.addAttribute("webList", webSearchList);
            model.addAttribute("userList", userSearchList);
        }
        return "search";
    }

    @RequestMapping("/popular")
    public String popular(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());
        }

        List<WebModel> webList = theWebRepository.findTop20ByOrderByActionDesc();

        model.addAttribute("webList", webList);

        return "popular";
    }

    @RequestMapping("/webest")
    public String webest(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());
        }

        List<WebModel> highestRateList = theWebRepository.findTop10ByOrderByRateDesc();
        List<WebModel> mostRaterList = theWebRepository.findTop10ByOrderByRaterDesc();
        List<WebModel> mostCommentList = theWebRepository.findTop10ByOrderByCommentDesc();

        model.addAttribute("highestRateList", highestRateList);
        model.addAttribute("mostRaterList", mostRaterList);
        model.addAttribute("mostCommentList", mostCommentList);

        return "webest";
    }

    @RequestMapping("/category")
    public String category(@RequestParam(value = "category", defaultValue = "1") int category,
            Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());
        }

        List<WebModel> webList = theWebRepository.findTop20ByCategoryOrderByRateDesc(category);

        model.addAttribute("webList", webList);
        model.addAttribute("category", WebModel.categoryToString(category));
        return "category";
    }

    @RequestMapping("/explore")
    public String explore(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());
        }

        List<WebModel> allWebList = (List) theWebRepository.findAll();
        ArrayList<Integer> randomArray = WebModel.randomCommon(0, allWebList.size() - 1, 20);
        ArrayList<WebModel> webList = new ArrayList<WebModel>();
        for (int i = 0; i < randomArray.size(); i++) {
            webList.add(allWebList.get(randomArray.get(i)));
        }

        model.addAttribute("webList", webList);

        return "explore";
    }

    @RequestMapping("/userinfo")
    public String userInfo(@RequestParam(value = "user", defaultValue = "") Long userId,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User theUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("username", theUser.getUsername());
        }
        AccountModel theAccountModel = theAccountRepository.findById(userId);
        UserModel theUserModel = theUserRepository.findByUser(theAccountModel);

        List<RateModel> rateList = theRateRepository.findByRater(theAccountModel);
        List<CommentModel> commentList = theCommentRepository.findByPoster(theAccountModel);
        List<ActionModel> actionList = theActionRepository.findByVisiter(theAccountModel);

        model.addAttribute("user", theUserModel);
        model.addAttribute("rateNum", rateList.size());
        model.addAttribute("commentNum", commentList.size());
        model.addAttribute("actionNum", actionList.size());
        return "userinfo";
    }
}
