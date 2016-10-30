/*
 * AccountManager.java
 *
 * Created on Dec 6, 2009 9:49:57 PM
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;

import com.swayam.chat.client.core.model.Contact;
import com.swayam.chat.client.core.model.Credentials;
import com.swayam.chat.client.core.model.Group;
import com.swayam.chat.client.core.model.Contact.Status;
import com.swayam.chat.client.ui.common.ChatWindowFactory;

/**
 * 
 * @author paawak
 */
public class AccountManager {

    private static AccountManager instance;

    private final Credentials creds;

    private XMPPConnection con;

    private AccountManager(Credentials creds) {
        this.creds = creds;
    }

    public static AccountManager getInstance(Credentials creds) {

        if (instance == null || !instance.creds.equals(creds)) {
            instance = new AccountManager(creds);
        }

        return instance;

    }

    public void login() throws XMPPException {

        if (con == null || !con.isConnected()) {
            con = new ConnectionManager().getConnection(creds);
        }

    }

    public void logout() {

        con.disconnect();
        con = null;

    }

    public Chat createChat(String user, MessageListener messageListener) {

        return con.getChatManager().createChat(user, messageListener);

    }

    public List<Group> getContactGroups(RosterListener rosterListener) {

        List<Group> groups = new ArrayList<Group>();

        if (con != null) {

            Roster roster = con.getRoster();

            roster.addRosterListener(rosterListener);

            Collection<RosterGroup> rosterGroups = roster.getGroups();

            if (rosterGroups.size() > 0) {

                for (RosterGroup rosterGroup : rosterGroups) {

                    String groupName = rosterGroup.getName();

                    GroupImpl group = new GroupImpl(groupName);

                    for (RosterEntry entry : rosterGroup.getEntries()) {

                        group.addContact(getContact(roster, entry));

                    }

                    groups.add(group);

                }

            } else {

                GroupImpl group = GroupImpl.getFalseGroup();

                for (RosterEntry entry : roster.getEntries()) {

                    group.addContact(getContact(roster, entry));

                }

                groups.add(group);

            }

        }

        return Collections.unmodifiableList(groups);

    }

    private Contact getContact(Roster roster, RosterEntry entry) {

        String name = entry.getName();
        String user = entry.getUser();

        // listen for incoming messages
        PacketFilter filter = IncomingMessageFilterFactory.INSTANCE.getFilter(user);

        // null if its been added earlier
        if (filter != null) {

            con.addPacketListener(new PacketListener() {

                @Override
                public void processPacket(Packet packet) {

                    if (packet instanceof Message) {

                        Message msg = (Message) packet;

                        String message = msg.getBody();

                        if (message != null && !"".equals(message)) {
                            ChatWindowFactory.INSTANCE.displayChatWindow(AccountManager.this, msg
                                    .getFrom(), msg.getBody(), null);
                        }

                    }

                }

            }, filter);

        }

        ContactImpl contact = new ContactImpl();
        contact.setUserName(user);
        contact.setAliasName(name);

        Presence presence = roster.getPresence(user);

        contact.setStatusText(presence.getStatus());

        Status status = Status.OFFLINE;

        Type type = presence.getType();

        Mode mode = presence.getMode();

        if (Type.available.equals(type)) {

            if (mode != null) {

                switch (mode) {
                default:
                case available:
                    status = Status.AVAILABLE;
                    break;
                case dnd:
                    status = Status.BUSY;
                    break;
                case away:
                    status = Status.AWAY;
                    break;
                case xa:
                    status = Status.EXTENDED_AWAY;
                    break;

                }

            } else {

                status = Status.AVAILABLE;

            }
        }

        contact.setStatus(status);

        return contact;

    }

}
