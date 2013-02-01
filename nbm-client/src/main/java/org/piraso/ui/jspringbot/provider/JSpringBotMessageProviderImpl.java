/*
 * Copyright (c) 2012 Alvin R. de Leon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package org.piraso.ui.jspringbot.provider;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openide.util.lookup.ServiceProvider;
import org.piraso.api.entry.Entry;
import org.piraso.api.jspringbot.JSpringBotBaseEntry;
import org.piraso.api.jspringbot.JSpringBotEntry;
import org.piraso.ui.api.MessageProvider;

/**
 * Provide log4J message provider.
 */
@ServiceProvider(service=MessageProvider.class)
public class JSpringBotMessageProviderImpl implements MessageProvider {
    public static final int MAX_GROUP_SIZE = 2;

    @Override
    public boolean isSupported(Entry entry) {
        return JSpringBotEntry.class.isInstance(entry);
    }

    @Override
    public String toMessage(Entry entry) {
        JSpringBotEntry jspringbot = (JSpringBotEntry) entry;

        StringBuilder buf = new StringBuilder();

        if(JSpringBotBaseEntry.class.isInstance(jspringbot)) {
            buf.append(((JSpringBotBaseEntry) jspringbot).getName());
        } else {
            buf.append(jspringbot.getMessage());
        }

        return buf.toString();
    }

    @Override
    public String toGroupMessage(Entry entry) {
        if(entry.getGroup() == null) {
            return "";
        }

        if(CollectionUtils.isNotEmpty(entry.getGroup().getGroups())) {
            String group = entry.getGroup().getGroups().iterator().next();
            String[] split = StringUtils.split(group, ".");

            if(split != null && split.length > MAX_GROUP_SIZE) {
                StringBuilder buf = new StringBuilder();

                for(int i = split.length - MAX_GROUP_SIZE; i < split.length; i++) {
                    if(buf.length() > 0) {
                        buf.append(".");
                    }

                    buf.append(split[i]);
                }

                group = buf.toString();
            }

            return String.format("[%s] ", group);
        }

        return "";
    }
}
