/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swayam.eardemo.shared.api;

import javax.ejb.Remote;

import com.swayam.eardemo.shared.model.Person;

/**
 *
 * @author paawak
 */
@Remote
public interface MySessionBeanRemote {

    String sayHello();

    int saveTransactional(Person person);

    int saveNonTransactional(Person person);

}
