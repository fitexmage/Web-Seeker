package webseeker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author smb6402
 */
public class AddressListModel {

    private List<AddressModel> addressList;

    public AddressListModel() {
        addressList = new ArrayList<AddressModel>();
        addressList = Collections.synchronizedList(addressList);
    }

    public JsonNode serializeAsJSON() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.valueToTree(this);
        return json;
    }

    public static AddressListModel deserializeJSON(JsonNode json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            AddressListModel theAddressListModel = mapper.treeToValue(json, AddressListModel.class);
            return theAddressListModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveJSONToFile(File theFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(theFile, this.serializeAsJSON());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AddressListModel readJSONFromFile(File theFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!theFile.isFile()) {
                System.out.println("Not a file!");
                return null;
            }
            JsonNode json = mapper.readTree(theFile);
            return deserializeJSON(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> serializedList() {
        ArrayList serializedList = new ArrayList<String>();
        for (AddressModel theAddressModel : addressList) {
            serializedList.add(theAddressModel.serializeToString());
        }
        return serializedList;
    }

    public void addAddress(AddressModel newAddressModel) {

        boolean isValid = true;

        if (addressList.isEmpty()) {
            addressList.add(newAddressModel);
        } else {
            for (AddressModel theAddressModel : addressList) {
                if ((theAddressModel.getName().equals(newAddressModel.getName())) && (theAddressModel.getStreet().equals(newAddressModel.getStreet())) && (theAddressModel.getState().equals(newAddressModel.getState())) && (theAddressModel.getZip().equals(newAddressModel.getZip()))) {
                    isValid = false;
                }
            }
            if (isValid == true) {
                addressList.add(newAddressModel);
            }
        }
    }

    /**
     * @return the addressList
     */
    public List<AddressModel> getAddressList() {
        return addressList;
    }

    /**
     * @param addressList the addressList to set
     */
    public void setAddressList(List<AddressModel> addressList) {
        this.addressList = addressList;
    }
}
