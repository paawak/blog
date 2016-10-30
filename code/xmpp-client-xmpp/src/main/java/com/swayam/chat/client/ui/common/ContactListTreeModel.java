/*
 * ContactListTreeModel.java
 *
 * Created on Dec 7, 2009 1:16:26 AM
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

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.swayam.chat.client.core.model.Group;

/**
 * 
 * @author paawak
 */
public class ContactListTreeModel implements TreeModel {

    private static final String FALSE_ROOT = "Groups";

    private final List<TreeModelListener> listeners;

    private final List<Group> groupList;

    public ContactListTreeModel(List<Group> groupList) {

        this.groupList = groupList;

        listeners = new ArrayList<TreeModelListener>();

    }

    public Object getRoot() {

        Object root = FALSE_ROOT;

        // hide a false group, root will be always hidden, this is for servers which doesnt support groups
        if (groupList.size() == 1) {

            Group group = groupList.get(0);

            if (Group.FALSE_GROUP_NAME.equals(group.getName())) {

                root = group;

            }

        }

        return root;
    }

    public int getChildCount(Object parent) {

        int childCount = 0;

        if (FALSE_ROOT.equals(parent)) {

            childCount = groupList.size();

        } else if (parent instanceof Group) {

            childCount = ((Group) parent).getContactCount();

        }

        return childCount;
    }

    public Object getChild(Object parent, int index) {

        Object child = null;

        if (FALSE_ROOT.equals(parent)) {

            child = groupList.get(index);

        } else if (parent instanceof Group) {

            child = ((Group) parent).getContacts().get(index);

        }

        return child;
    }

    public int getIndexOfChild(Object parent, Object child) {

        int index = 0;

        if (FALSE_ROOT.equals(parent)) {

            index = groupList.indexOf(child);

        } else if (parent instanceof Group) {

            index = ((Group) parent).getContacts().indexOf(child);

        }

        return index;

    }

    public boolean isLeaf(Object node) {

        boolean isLeaf = true;

        if (!groupList.isEmpty()) {

            if (FALSE_ROOT.equals(node)) {

                isLeaf = false;

            } else if (node instanceof Group) {

                isLeaf = false;

            }

        }

        return isLeaf;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

}
