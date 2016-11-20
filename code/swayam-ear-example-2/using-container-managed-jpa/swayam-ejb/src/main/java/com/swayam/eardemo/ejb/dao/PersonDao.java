/*
 * PersonService.java
 *
 * Created on 18-Nov-2016 1:04:21 AM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.eardemo.ejb.dao;

import com.swayam.eardemo.shared.model.Person;

/**
 * 
 * @author paawak
 */
public interface PersonDao {

    int saveWithEntityManager(Person person);

    int saveWithConnection(Person person);

}
