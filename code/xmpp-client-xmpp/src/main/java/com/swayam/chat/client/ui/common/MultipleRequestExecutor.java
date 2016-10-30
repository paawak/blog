/*
 * MultipleRequestExecutor.java
 *
 * Created on Dec 22, 2009 7:56:44 PM
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * Collapses multiple requests within a given time-frame into a single request, essentially ignoring all others.
 * 
 * @author paawak
 */
public class MultipleRequestExecutor implements RequestExecutor {

    private final List<Command> commandStack;

    private final long timeFrameMilliSecs;

    public MultipleRequestExecutor(long timeFrameMilliSecs) {
        this.timeFrameMilliSecs = timeFrameMilliSecs;
        commandStack = new ArrayList<Command>();
    }

    @Override
    public void executeCommand(final Command command) {

        if (!commandStack.contains(command)) {

            commandStack.add(command);

            Timer timer = new Timer(command.getId());
            timer.schedule(new TimerTask() {

                @Override
                public void run() {

                    command.execute();

                    commandStack.remove(command);

                }

            }, timeFrameMilliSecs);

        }

    }

}
