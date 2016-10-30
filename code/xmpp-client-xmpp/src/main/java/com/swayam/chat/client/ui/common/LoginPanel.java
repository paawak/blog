/*
 * LoginPanel.java
 *
 * Created on Dec 7, 2009 11:40:43 PM
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

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.swayam.chat.client.core.model.Credentials;
import com.swayam.chat.client.core.util.AccountManager;

/**
 * 
 * @author paawak
 */
public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 4603399021683353717L;

    private final LoginListener credListener;
    private JTextField txtUserName;
    private JPasswordField txtPassword;

    public LoginPanel(LoginListener credListener) {
        this.credListener = credListener;
        initComponents();
    }

    private void initComponents() {

        GridBagConstraints gridBagConstraints;

        JLabel lbTitle = new JLabel();
        JLabel lbUserName = new JLabel();
        txtUserName = new JTextField();
        JLabel lbPassword = new JLabel();
        txtPassword = new JPasswordField();

        ActionListener loginActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                doLogin();

            }

        };

        txtPassword.addActionListener(loginActionListener);

        JButton btLogin = new JButton();
        btLogin.addActionListener(loginActionListener);

        JLabel lbFooter = new JLabel();

        setLayout(new GridBagLayout());

        lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitle
                .setText("<html><h1 align=\"center\">Welcome to <i><b><font color=\"green\">theND</font></b></i> an IM based on "
                        + "<i><font color=\"teal\">XMPP</font></i><br></h1>"
                        + "<h2 align=\"center\">You can connect to your <b><font color=\"blue\">GTalk</font></b> or any other <b><font color=\"blue\">Jabber Sever</font></b>"
                        + " account using this</h2></html>");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.7;
        add(lbTitle, gridBagConstraints);

        lbUserName.setText("<html>Username:<font color=\"red\">*</font></html>");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new Insets(30, 30, 0, 0);
        add(lbUserName, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new Insets(30, 10, 0, 30);
        add(txtUserName, gridBagConstraints);

        lbPassword.setText("Password:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 30, 0, 0);
        add(lbPassword, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new Insets(0, 10, 0, 30);
        add(txtPassword, gridBagConstraints);

        btLogin.setText("Login");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new Insets(30, 0, 30, 0);
        add(btLogin, gridBagConstraints);

        lbFooter
                .setText("<html><font color=\"red\">*</font> For example: <i>john.smith@gmail.com</i></html>");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new Insets(0, 10, 15, 10);
        add(lbFooter, gridBagConstraints);
    }

    private void doLogin() {

        String userName = txtUserName.getText().trim();
        char[] password = txtPassword.getPassword();

        int indexOfAt = userName.indexOf('@');

        if (!"".equals(userName) && (indexOfAt != -1)
                && (userName.indexOf('@') + 1 < userName.length()) && (password != null)) {

            String[] array = userName.split("@");
            String user = array[0];
            String host = array[1];
            Credentials creds = new Credentials(host, user, new String(password));

            // try to login
            SwingUtilities.invokeLater(new LoginUtil(creds));

        } else {
            JOptionPane.showMessageDialog(LoginPanel.this, "Please enter username/password",
                    "Oops!", JOptionPane.ERROR_MESSAGE);
        }

    }

    private class LoginUtil implements Runnable {

        private final Credentials creds;

        private AccountManager acManager = null;
        private Exception loginException = null;

        LoginUtil(Credentials creds) {
            this.creds = creds;
        }

        @Override
        public void run() {

            try {

                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                acManager = AccountManager.getInstance(creds);
                acManager.login();

            } catch (Exception e) {

                loginException = e;
                acManager = null;

            } finally {

                txtUserName.setText(null);
                txtPassword.setText(null);

                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }

            if (loginException != null) {

                Utils.displayError(LoginPanel.this, "I am sorry!", "Could not log you on.",
                        loginException);

            } else if (acManager != null) {

                credListener.loginSuccess(acManager);

            }

        }

    }

}
