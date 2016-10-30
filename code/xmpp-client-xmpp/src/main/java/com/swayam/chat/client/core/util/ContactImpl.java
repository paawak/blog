/*
 * ContactImpl.java
 *
 * Created on Dec 6, 2009 9:56:09 PM
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

import com.swayam.chat.client.core.model.Contact;

/**
 * 
 * @author paawak
 */
class ContactImpl implements Contact {

    private String aliasName;

    private String userName;

    private Status status;

    private String statusText;

    public String getDisplayName() {

        String displayName = aliasName;

        if (displayName == null) {

            displayName = userName.substring(0, userName.indexOf('@'));

        }

        return displayName;
    }

    void setAliasName(String displayName) {
        this.aliasName = displayName;
    }

    public Status getStatus() {
        return status;
    }

    void setStatus(Status status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatusText() {
        return statusText;
    }

    void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public int compareTo(Contact contact) {

        // compare status
        int compareResult = status.getWeight() - contact.getStatus().getWeight();

        // if same status, compare the user name alphabetically
        if (compareResult == 0) {
            compareResult = userName.compareTo(contact.getUserName());
        }

        return compareResult;
    }

    @Override
    public String toString() {
        return "ContactImpl [userName=" + userName + ", aliasName=" + aliasName + ", status="
                + status + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContactImpl other = (ContactImpl) obj;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

}
