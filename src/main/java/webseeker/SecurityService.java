/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker;

/**
 *
 * @author fitexmage
 */
public interface SecurityService {
    
    public String findLoggedInUsername();
    
    public void autologin(String username, String password);
}
