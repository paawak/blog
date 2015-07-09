/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swayam.demo.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author paawak
 */
@WebService()
public class UserService {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addUser")
    public Boolean addUser(@WebParam(name = "userName")
    String userName) {
        
        System.out.println("add user");

        return Boolean.TRUE;

    }

}
