/*
 * ContactListTreeCellRenderer.java
 *
 * Created on Dec 7, 2009 1:55:12 AM
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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.swayam.chat.client.core.model.Contact;
import com.swayam.chat.client.core.model.Group;
import com.swayam.chat.client.core.model.Contact.Status;

/**
 * 
 * @author paawak
 */
public class ContactListTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = -6844158441533404757L;

    /** Max characters to be displayed for the status text */
    private static final int MAX_STATUS_CHARS = 50;

    private Contact contact;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
            boolean expanded, boolean leaf, int row, boolean hasFocus) {

        if (value instanceof Group) {

            value = ((Group) value).getName();

        } else if (value instanceof Contact) {

            contact = (Contact) value;
            value = contact.getDisplayName();

            String statusText = contact.getStatusText();

            if (statusText != null) {

                if (statusText.length() > MAX_STATUS_CHARS) {

                    statusText = statusText.substring(0, MAX_STATUS_CHARS) + "...";

                }

                value = "<html><strong>" + value + "</strong><br><i>" + statusText + "</i></html>";
            }

        }

        return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row,
                hasFocus);

    }

    @Override
    public Icon getLeafIcon() {

        Icon icon = null;

        if (contact != null) {

            Status status = contact.getStatus();

            if (status == null) {
                status = Status.OFFLINE;
            }

            String iconPath = "/com/swayam/chat/client/assets/images/chat/status/" + status.name()
                    + ".png";

            icon = getIcon(iconPath);

        }

        return icon;
    }

    @Override
    public Icon getOpenIcon() {

        String iconPath = "/com/swayam/chat/client/assets/images/tree/node-open.png";

        return getIcon(iconPath);

    }

    @Override
    public Icon getClosedIcon() {

        String iconPath = "/com/swayam/chat/client/assets/images/tree/node-close.png";

        return getIcon(iconPath);

    }

    private Icon getIcon(String iconPath) {

        return new ImageIcon(ContactListTreeCellRenderer.class.getResource(iconPath));

    }

}
