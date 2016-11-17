
package com.swayam.eardemo.ejb.facade;

import java.util.Random;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.swayam.eardemo.ejb.service.PersonService;
import com.swayam.eardemo.shared.api.MySessionBeanRemote;
import com.swayam.eardemo.shared.model.Person;

/**
 *
 * @author paawak
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class MySessionBean implements MySessionBeanRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySessionBean.class);

    @Autowired
    private PersonService personService;

    public MySessionBean() {
    }

    @Override
    public String sayHello() {
        return "Hi " + new Random().nextInt();
    }

    @Override
    public int savePerson(Person person) {
        LOGGER.info("person: {}", person);
        return personService.save(person);
    }

}
