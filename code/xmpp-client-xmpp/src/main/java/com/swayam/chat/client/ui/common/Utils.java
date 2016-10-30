/*
 * Utils.java
 *
 * Created on Dec 22, 2009 1:02:22 PM
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

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JOptionPane;

/**
 * 
 * @author paawak
 */
public class Utils {

    private Utils() {

    }

    public static void displayError(Component parent, final String title, final String message,
            final Throwable t) {

        if (t != null) {

            EventQueue.invokeLater(new Runnable() {

                public void run() {

                    ErrorDialog dialog = new ErrorDialog(null, title, message
                            + "\nThe error details are:", t);

                    dialog.setVisible(true);

                }

            });

        } else {

            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);

        }

    }

}
