
package com.swayam.eardemo.ejb.facade;

import java.util.Random;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.eardemo.shared.api.MySessionBeanRemote;
import com.swayam.eardemo.shared.model.Person;

/**
 *
 * @author paawak
 */
@Stateless
public class MySessionBean implements MySessionBeanRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySessionBean.class);

    public MySessionBean() {
    }

    @Override
    public String sayHello() {
        return "Hi " + new Random().nextInt();
    }

    @Override
    public int savePerson(Person person) {
        LOGGER.info("person: {}", person);
        return new Random().nextInt();
    }

}
