/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webseeker;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fitexmage
 */
public class AccountListModel {

    private List<AccountModel> accountList;

    public AccountListModel() {
        accountList = new ArrayList<AccountModel>();
    }

    public ArrayList<String> serializedList() {
        ArrayList serializedList = new ArrayList<String>();
        for (AccountModel theAccountModel : accountList) {
            serializedList.add(theAccountModel.serializeToString());
        }
        return serializedList;
    }
    
    public boolean addAccount(AccountModel newAccountModel){
        
        boolean isValid = true;
        
        if (accountList.isEmpty()) {
            accountList.add(newAccountModel);
        } else {
            for (AccountModel theAccountModel : accountList) {
                if ((theAccountModel.getUsername().equals(newAccountModel.getUsername()))) {
                    isValid = false;
                }
            }
            if (isValid == true) {
                accountList.add(newAccountModel);
            }
        }
        return isValid;
    }
    
    public boolean authenticated(String username, String password){
        for(AccountModel theAccountModel: accountList){
            if(theAccountModel.getUsername().equals(username) && theAccountModel.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
