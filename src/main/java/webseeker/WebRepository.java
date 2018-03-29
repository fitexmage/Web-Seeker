/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author fitexmage
 */
public interface WebRepository extends CrudRepository<WebModel, Long>{
    
    public WebModel findById(Long id);
}
