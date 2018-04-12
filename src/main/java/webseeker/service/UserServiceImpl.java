/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker.service;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webseeker.model.AccountModel;
import webseeker.repository.AccountRepository;
import webseeker.repository.RoleRepository;

/**
 *
 * @author fitexmage
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountRepository theAccountRepository;
    @Autowired
    private RoleRepository theRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(AccountModel theAccountModel) {
        theAccountModel.setPassword(bCryptPasswordEncoder.encode(theAccountModel.getPassword()));
        theAccountModel.setRoles(new HashSet<>((List) theRoleRepository.findAll()));
        theAccountRepository.save(theAccountModel);
    }

    @Override
    public AccountModel findByUsername(String username) {
        return theAccountRepository.findByUsername(username);
    }
}
