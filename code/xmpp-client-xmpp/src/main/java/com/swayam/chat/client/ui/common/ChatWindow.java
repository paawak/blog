/*
 * ChatWindow.java
 *
 * Created on Dec 7, 2009 3:49:38 PM
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * 
 * @author paawak
 */
class ChatWindow extends JDialog implements MessageListener {

    private static final long serialVersionUID = -1624324592341163069L;

    private static final int WIDTH = 500;

    private static final int HEIGHT = 500;

    private Chat chat;

    private JEditorPane textEntered;
    private JEditorPane chatText;

    ChatWindow(String userName, String initailMessage) {
        initComponents(userName, initailMessage);
    }

    ChatWindow(String userName) {
        this(userName, "");
    }

    private void initComponents(String userName, String initialMessage) {

        JMenuBar menuBar = new JMenuBar();
        JMenu chatMenu = new JMenu();
        chatMenu.setText("Chat");
        menuBar.add(chatMenu);
        setJMenuBar(menuBar);

        // getContentPane().setLayout(new BorderLayout());

        JLabel title = new JLabel(getUserName(userName));
        // title.setHorizontalAlignment(JLabel.CENTER);
        title.setPreferredSize(new Dimension(WIDTH, 30));
        getContentPane().add(title, BorderLayout.NORTH);

        JScrollPane centreScrPane = new JScrollPane();
        chatText = new JEditorPane();

        appendMessageToMainWindow(userName, initialMessage);

        chatText.setEditable(false);
        centreScrPane.setViewportView(chatText);
        getContentPane().add(centreScrPane, BorderLayout.CENTER);

        JScrollPane southScrPane = new JScrollPane();
        southScrPane.setPreferredSize(new Dimension(WIDTH, 60));
        textEntered = new JEditorPane();
        southScrPane.setViewportView(textEntered);
        getContentPane().add(southScrPane, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - WIDTH) / 2, (screenSize.height - HEIGHT) / 2, WIDTH, HEIGHT);

        textEntered.requestFocusInWindow();

        textEntered.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {

                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                    // send chat
                    String text = textEntered.getText();
                    Message message = new Message();
                    message.setBody(text);

                    try {
                        chat.sendMessage(message);
                        appendMessageToMainWindow("me", text);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void keyReleased(KeyEvent evt) {

                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    textEntered.setText(null);
                }

            }

        });

    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void processMessage(Chat chat, Message message) {
        appendMessageToMainWindow(message.getFrom(), message.getBody());
    }

    private void appendMessageToMainWindow(String user, String message) {

        if (message != null && !message.equals("")) {

            String oldText = chatText.getText();

            if (!oldText.equals("")) {
                oldText += "\n";
            }

            chatText.setText(oldText + getUserName(user) + ":" + message);

        }

    }

    private String getUserName(String user) {

        int indexOfAt = user.indexOf('@');

        if (indexOfAt != -1) {

            user = user.substring(0, indexOfAt);

        }

        return user;

    }

}
