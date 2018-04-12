package webseeker.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import webseeker.repository.*;
import webseeker.model.*;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private RateRepository theRateRepository;

    @Autowired
    private CommentRepository theCommentRepository;

    @Autowired
    private ActionRepository theActionRepository;

    public MainController() {

    }

    @RequestMapping("/")
    public String homepage(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
            model.addAttribute("username", theAccountModel.getUsername());
        }
        List<WebModel> newList = theWebRepository.findTop5ByOrderByAddTimeDesc();
        model.addAttribute("newList", newList);
        return "homepage";
    }
    
    @RequestMapping("/popular")
    public String popular(Model model, HttpSession session){
        
        List<ActionModel> actionList = (List)theActionRepository.findAll();
        HashMap<Long, Integer> webVisitMap = new HashMap<Long, Integer>();
        for(ActionModel theActionModel: actionList){
            Long webId = theActionModel.getWeb().getId();
            if(webVisitMap.containsKey(webId)){
                webVisitMap.put(webId, webVisitMap.get(webId) + 1);
            }else{
                webVisitMap.put(theActionModel.getWeb().getId(), 1);
            }
        }
        
        ArrayList<WebModel> decsendingWebVisitList = new ArrayList<WebModel>();
        int webNum = 0;
        List<Map.Entry<Long, Integer>> webVisitList = new ArrayList<Map.Entry<Long, Integer>>(webVisitMap.entrySet());
        Collections.sort(webVisitList, new Comparator<Map.Entry<Long, Integer>>() {
            public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (Map.Entry<Long, Integer> mapping : webVisitList) {
            if (webNum < 10) {
                decsendingWebVisitList.add(theWebRepository.findById(mapping.getKey()));
            }
            webNum++;
        }
        
        model.addAttribute("webList", decsendingWebVisitList);
        
        return "popular";
    }

    @RequestMapping("/category")
    public String category(@RequestParam(value = "category", defaultValue = "1") int category,
            Model model, HttpSession session) {

        if (session.getAttribute("user") != null) {
            AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
            model.addAttribute("username", theAccountModel.getUsername());
        }

        List<WebModel> webList = theWebRepository.findByCategory(category);
        if (!webList.isEmpty()) {
            Collections.sort(webList, new Comparator<WebModel>() {

                @Override
                public int compare(WebModel o1, WebModel o2) {
                    double i = o1.rate() - o2.rate();
                    if (i > 0) {
                        return -1;
                    } else if (i == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });

            ArrayList<WebModel> highList = new ArrayList<WebModel>();
            int webNum;
            if (webList.size() > 10) {
                webNum = 10;
            } else {
                webNum = webList.size();
            }

            for (int i = 0; i < webNum; i++) {
                highList.add(webList.get(i));
            }
            if (!highList.isEmpty()) {
                model.addAttribute("webList", highList);
            }
        }
        model.addAttribute("category", WebModel.categoryToString(category) + " Website Rank");
        return "category";
    }

    @RequestMapping("/getwebinfo")
    public String getWebInfo(@RequestParam(value = "webId", defaultValue = "") Long webId,
            Model model, HttpSession session) {
        WebModel theWebModel = theWebRepository.findById(webId);

        RateModel theRateModel = null;
        boolean canRate = false;
        boolean canComment = false;

        if (session.getAttribute("user") != null) {
            AccountModel theAccountModel = (AccountModel) session.getAttribute("user");
            model.addAttribute("username", theAccountModel.getUsername());

            theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
            canRate = true;
            canComment = true;

            ActionModel newAction = ActionModel.newAction(theWebModel, theAccountModel);
            theActionRepository.save(newAction);
        }

        if (theRateModel != null) {
            canRate = false;
        }

        List<CommentModel> commentList = theCommentRepository.findByWeb(theWebModel);

        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canComment", canComment);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/rate")
    public String rate(@RequestParam(value = "score", defaultValue = "") String score,
            @RequestParam(value = "web", defaultValue = "") Long webId,
            Model model, HttpSession session) {
        WebModel theWebModel = theWebRepository.findById(webId);
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");

        RateModel theRateModel = theRateRepository.findByWebAndRater(theWebModel, theAccountModel);
        if (theRateModel == null) {
            RateModel newRate = RateModel.newRate(theWebModel, theAccountModel, Integer.parseInt(score));
            theRateRepository.save(newRate);

            theWebModel.setRater(theWebModel.getRater() + 1);
            theWebModel.setTotalScore(theWebModel.getTotalScore() + Integer.parseInt(score));
            theWebRepository.save(theWebModel);
        }

        List<CommentModel> commentList = theCommentRepository.findByWeb(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", false);
        model.addAttribute("canComment", true);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/comment")
    public String comment(@RequestParam(value = "comment", defaultValue = "") String comment,
            @RequestParam(value = "web", defaultValue = "") Long webId,
            @RequestParam(value = "canRate", defaultValue = "") boolean canRate,
            Model model, HttpSession session) {
        WebModel theWebModel = theWebRepository.findById(webId);
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");

        CommentModel newComment = CommentModel.newComment(theWebModel, theAccountModel, comment);
        theCommentRepository.save(newComment);

        List<CommentModel> commentList = theCommentRepository.findByWeb(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canComment", true);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }

    @RequestMapping("/like")
    public String like(@RequestParam(value = "comment", defaultValue = "") Long commentId,
            @RequestParam(value = "web", defaultValue = "") Long webId,
            @RequestParam(value = "canRate", defaultValue = "") boolean canRate,
            Model model, HttpSession session) {
        WebModel theWebModel = theWebRepository.findById(webId);
        AccountModel theAccountModel = (AccountModel) session.getAttribute("user");

        CommentModel theCommentModel = theCommentRepository.findById(commentId);
        theCommentModel.setLikeNum(theCommentModel.getLikeNum() + 1);
        theCommentRepository.save(theCommentModel);

        List<CommentModel> commentList = theCommentRepository.findByWeb(theWebModel);

        model.addAttribute("username", theAccountModel.getUsername());
        model.addAttribute("web", theWebModel);
        model.addAttribute("canRate", canRate);
        model.addAttribute("canComment", true);
        model.addAttribute("commentList", commentList);
        return "webinfo";
    }
}
