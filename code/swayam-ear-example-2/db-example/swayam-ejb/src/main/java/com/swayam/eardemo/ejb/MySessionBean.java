/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swayam.eardemo.ejb;

import java.util.Random;

import javax.ejb.Stateless;

import com.swayam.eardemo.shared.api.MySessionBeanRemote;
import com.swayam.eardemo.shared.model.Person;

/**
 *
 * @author paawak
 */
@Stateless
public class MySessionBean implements MySessionBeanRemote {

    public MySessionBean() {
    }

    @Override
    public String sayHello() {
        return "Hi " + new Random().nextInt();
    }

    @Override
    public int savePerson(Person person) {
        System.out.println("***************** person: " + person);
        return new Random().nextInt();
    }

}
