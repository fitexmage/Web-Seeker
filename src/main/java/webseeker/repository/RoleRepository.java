/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import webseeker.model.RoleModel;

/**
 *
 * @author fitexmage
 */
public interface RoleRepository extends CrudRepository<RoleModel, Long>{
    
}
