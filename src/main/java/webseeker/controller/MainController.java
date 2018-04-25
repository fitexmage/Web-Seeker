package webseeker.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import webseeker.repository.*;
import webseeker.model.*;
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
                List<WebModel> recommendList = theWebRepository.findTop10ByCategoryOrCategory(category1st, category2nd);
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

        List<WebModel> webSearchList = theWebRepository.findByWebNameContainingIgnoreCaseOrUrlContainingIgnoreCaseOrDescriptionContainingIgnoreCase(input, input, input);
        List<UserModel> userSearchList = theUserRepository.findByNameContainingIgnoreCase(input);
        model.addAttribute("input", input);
        model.addAttribute("webList", webSearchList);
        model.addAttribute("userList", userSearchList);

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
        model.addAttribute("category", WebModel.categoryToString(category) + " Website Rank");
        return "category";
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
