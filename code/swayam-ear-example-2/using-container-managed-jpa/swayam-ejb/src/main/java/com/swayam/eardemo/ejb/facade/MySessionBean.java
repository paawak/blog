
package com.swayam.eardemo.ejb.facade;

import java.time.LocalDate;
import java.util.Random;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.eardemo.ejb.service.PetStoreService;
import com.swayam.eardemo.shared.api.MySessionBeanRemote;
import com.swayam.eardemo.shared.model.AnimalType;
import com.swayam.eardemo.shared.model.Person;
import com.swayam.eardemo.shared.model.Pet;

/**
 *
 * @author paawak
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MySessionBean implements MySessionBeanRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySessionBean.class);

    @Inject
    private PetStoreService petStoreService;

    public MySessionBean() {
    }

    @Override
    public String sayHello() {
        return "Hi " + new Random().nextInt();
    }

    @Transactional(TxType.REQUIRED)
    @Override
    public int savePerson(Person person) {
        LOGGER.info("person: {}", person);
        // FIXME: get this from the client
        Pet pet = new Pet();
        // pet.setName("Benjamin");
        pet.setAnimalType(AnimalType.BUNNY);
        pet.setDateOfBirth(LocalDate.of(2008, 9, 9));
        return petStoreService.save(person, pet);
    }

}
