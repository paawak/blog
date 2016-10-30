/*
 * IncomingMessageFilterFactory.java
 *
 * Created on Dec 13, 2009 8:15:27 PM
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

import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;

/**
 * 
 * @author paawak
 */
public class IncomingMessageFilterFactory {

    public static final IncomingMessageFilterFactory INSTANCE = new IncomingMessageFilterFactory();

    private final List<String> filterList;

    private IncomingMessageFilterFactory() {

        filterList = Collections.synchronizedList(new ArrayList<String>());

    }

    public PacketFilter getFilter(String user) {

        PacketFilter filter = null;

        if (!filterList.contains(user)) {

            filter = new AndFilter(new PacketTypeFilter(Message.class),
                    new FromContainsFilter(user));
            filterList.add(user);

        }

        return filter;

    }

}
