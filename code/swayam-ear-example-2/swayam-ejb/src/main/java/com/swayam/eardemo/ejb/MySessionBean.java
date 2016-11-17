/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swayam.eardemo.ejb;

import com.swayam.eardemo.shared.MySessionBeanRemote;
import java.util.Random;
import javax.ejb.Stateless;

/**
 *
 * @author paawak
 */
@Stateless
public class MySessionBean implements MySessionBeanRemote {

    private Random rand;

    public MySessionBean() {        
        rand = new Random(7676326986854665363l);
    }

    public String sayHello() {
        return "Hi " + rand.nextInt();
    }
        
 
}
