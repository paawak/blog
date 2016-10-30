/*
 * ChatWindowFactory.java
 *
 * Created on Dec 13, 2009 9:01:09 PM
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

package com.swayam.chat.client.ui.common;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTree;

import org.jivesoftware.smack.Chat;

import com.swayam.chat.client.core.util.AccountManager;

/**
 * 
 * @author paawak
 */
public class ChatWindowFactory {

    public static final ChatWindowFactory INSTANCE = new ChatWindowFactory();

    /**
     * Keeps track of the currently opened chat windows for users
     */
    private final Set<String> openChatWindowStore;

    private ChatWindowFactory() {

        openChatWindowStore = Collections.synchronizedSet(new HashSet<String>(1));

    }

    public void displayChatWindow(final AccountManager acManager, final String fullUserName,
            final String initialMessage, final JTree tree) {

        final String user;

        int slashIndex = fullUserName.indexOf('/');

        if (slashIndex == -1) {
            user = fullUserName;
        } else {
            user = fullUserName.substring(0, slashIndex);
        }

        if (!openChatWindowStore.contains(user)) {

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    ChatWindow dialog = new ChatWindow(user, initialMessage);

                    Chat chat = acManager.createChat(user, dialog);

                    dialog.setChat(chat);

                    dialog.addWindowListener(new WindowAdapter() {

                        public void windowClosing(WindowEvent e) {

                            if (tree != null) {
                                tree.clearSelection();
                            }

                            // remove from the store
                            openChatWindowStore.remove(user);

                        }

                    });

                    openChatWindowStore.add(user);

                    dialog.setVisible(true);
                }

            });

        }

    }
}
