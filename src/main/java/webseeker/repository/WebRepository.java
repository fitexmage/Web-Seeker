/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import webseeker.model.WebModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import webseeker.model.AccountModel;

/**
 *
 * @author fitexmage
 */
public interface WebRepository extends CrudRepository<WebModel, Long> {

    public WebModel findById(Long id);

    public WebModel findByUrl(String url);
    
    public List<WebModel> findByEditor(AccountModel theAccountModel);

    //Search List
    public List<WebModel> findByWebNameContainingIgnoreCaseOrUrlContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String webName, String url, String description);

    //Recommend List
    public List<WebModel> findByCategoryOrCategory(int category1st, int category2nd);

    //New List
    public List<WebModel> findTop10ByOrderByAddTimeDesc();

    //New List
    public List<WebModel> findTop1000ByOrderByAddTimeDesc();

    //Popular
    public List<WebModel> findTop20ByOrderByActionDesc();

    //Webest
    public List<WebModel> findTop10ByOrderByRateDesc();

    //Webest
    public List<WebModel> findTop10ByOrderByRaterDesc();
    
    //Webest
    public List<WebModel> findTop10ByOrderByCommentDesc();
    
    //Category
    public List<WebModel> findTop20ByCategoryOrderByRateDesc(int category);

}
