/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webseeker.model.AccountModel;
import webseeker.model.RoleModel;
import webseeker.repository.AccountRepository;

/**
 *
 * @author fitexmage
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository theAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountModel theAccountModel = theAccountRepository.findByUsername(username);

        if (theAccountModel != null) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (RoleModel role : theAccountModel.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            return new org.springframework.security.core.userdetails.User(theAccountModel.getUsername(), theAccountModel.getPassword(), grantedAuthorities);
        }
        return null;
    }
}
