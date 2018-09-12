/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webest.repository;

import webest.model.CommentModel;
import webest.model.WebModel;
import webest.model.AccountModel;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fitexmage
 */
public interface CommentRepository extends CrudRepository<CommentModel, Long>{
    
    public CommentModel findById(Long id);
    
    public List<CommentModel> findByWebOrderByPostTimeDesc(WebModel theWebModel);
    
    public List<CommentModel> findByPoster(AccountModel theAccountModel);
}
