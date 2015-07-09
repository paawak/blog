/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swayam.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author paawak
 */
@WebService()
public class UserManager {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addUser")
    public boolean addUser(@WebParam(name = "userName")
    String userName) {
        System.out.println(userName);
        return true;
    }

}
