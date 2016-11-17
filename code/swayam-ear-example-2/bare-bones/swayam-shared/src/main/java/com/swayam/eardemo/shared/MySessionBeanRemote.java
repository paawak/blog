/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swayam.eardemo.shared;

import javax.ejb.Remote;

/**
 *
 * @author paawak
 */
@Remote
public interface MySessionBeanRemote {

    String sayHello();
    
}
