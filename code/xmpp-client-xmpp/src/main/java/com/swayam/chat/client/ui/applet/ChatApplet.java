/*
 * ChatApplet.java
 *
 * Created on Dec 6, 2009 9:19:01 PM
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

package com.swayam.chat.client.ui.applet;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.swayam.chat.client.core.util.AccountManager;
import com.swayam.chat.client.ui.common.ContactListPane;
import com.swayam.chat.client.ui.common.LoginListener;
import com.swayam.chat.client.ui.common.LoginPanel;

/**
 * 
 * @author paawak
 */
public class ChatApplet extends JApplet implements LoginListener {

    private static final long serialVersionUID = -2126075630790344759L;

    private static final String LOGIN_PANEL = "LOGIN_PANEL";
    private static final String CHAT_PANEL = "CHAT_PANEL";

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JMenuBar mainMenuBar;
    private ContactListPane contactListPanel;

    private AccountManager acManager;

    public void init() {
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {

        mainMenuBar = new JMenuBar();
        JMenu accountMenu = new JMenu();
        accountMenu.setText("Account");
        JMenuItem logout = new JMenuItem();
        logout.setText("Logout");

        logout.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                acManager.logout();

                mainMenuBar.setVisible(false);
                cardLayout.show(mainPanel, LOGIN_PANEL);

            }

        });

        accountMenu.add(logout);
        mainMenuBar.add(accountMenu);

        setJMenuBar(mainMenuBar);

        mainMenuBar.setVisible(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), LOGIN_PANEL);

        cardLayout.show(mainPanel, LOGIN_PANEL);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

    }

    @Override
    public void loginSuccess(AccountManager acManager) {

        this.acManager = acManager;

        mainMenuBar.setVisible(true);

        if (contactListPanel == null) {

            contactListPanel = new ContactListPane(acManager);
            mainPanel.add(contactListPanel, CHAT_PANEL);

        } else {

            contactListPanel.setAccountManager(acManager);

        }

        cardLayout.show(mainPanel, CHAT_PANEL);

    }

}
