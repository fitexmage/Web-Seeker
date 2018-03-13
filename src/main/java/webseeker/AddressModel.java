package webseeker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author smb6402
 */
public class AddressModel {

    private String name;
    private String street;
    private String state;
    private String zip;

    private boolean empty;
    private boolean valid;

    public AddressModel(String name, String street, String state, String zip) {
        this.name = name;
        this.street = street;
        this.state = state;
        this.zip = zip;
    }

    public AddressModel() {

    }

    public JsonNode serializeAsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.valueToTree(this);
        return json;
    }

    public static AddressModel deserializeJSON(JsonNode json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AddressModel theAddressModel = mapper.treeToValue(json, AddressModel.class);
            return theAddressModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String serializeToString() {
        return "name=" + name + "&street=" + street + "&state=" + state + "&zip=" + zip;
    }

    public static AddressModel deSerializeFromString(String s) {
        AddressModel theAddressModel;
        boolean isEmpty = false;
        String parameter = s.substring(s.indexOf("?") + 1);
        HashMap<String, String> params = new HashMap<String, String>();
        for (String pair : parameter.split("&")) {
            String[] KeyVal = pair.split("=");
            if (KeyVal.length == 2) {
                params.put(KeyVal[0], KeyVal[1]);
            } else {
                isEmpty = true;
            }
        }
        theAddressModel = new AddressModel(addSpace(params.get("name")),
                addSpace(params.get("street")),
                addSpace(params.get("state")),
                addSpace(params.get("zip")));
        theAddressModel.empty = isEmpty;
        return theAddressModel;
    }

    public boolean isValid() {
        if (name.length() > 2 && street.length() > 1 && state.length() == 2 && zip.length() > 0 && zip.length() < 999) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    public boolean isEmpty() {
        if (name.equals("") || street.equals("") || state.equals("") || zip.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static String addSpace(String a) {
        if (a.indexOf("+") >= 0) {
            String[] words = a.split("\\+");
            String theWholeWord = "";
            for (int i = 0; i < words.length; i++) {
                if (i != words.length - 1) {
                    theWholeWord += words[i] + " ";
                } else {
                    theWholeWord += words[i];
                }
            }
            return theWholeWord;
        } else {
            return a;
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
