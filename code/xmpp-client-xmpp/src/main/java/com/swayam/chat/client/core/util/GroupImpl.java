/*
 * GroupImpl.java
 *
 * Created on Dec 6, 2009 10:02:27 PM
 *
 * Copyright (c) 2002 - 2009 : Swayam Inc.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swayam.chat.client.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.swayam.chat.client.core.model.Contact;
import com.swayam.chat.client.core.model.Group;

/**
 * 
 * @author paawak
 */
class GroupImpl implements Group {

    private final String name;

    private String description;

    private final List<Contact> contactList;

    GroupImpl(String name) {
        this.name = name;
        contactList = new ArrayList<Contact>(1);
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    void addContact(Contact contact) {
        contactList.add(contact);
    }

    public int getContactCount() {
        return contactList.size();
    }

    public List<Contact> getContacts() {
        Collections.sort(contactList);
        return Collections.unmodifiableList(contactList);
    }

    /**
     * Use this for jabber servers which do not support groups
     */
    static GroupImpl getFalseGroup() {
        return new GroupImpl(FALSE_GROUP_NAME);
    }

}
