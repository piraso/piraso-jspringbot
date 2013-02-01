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

import org.openide.util.lookup.ServiceProvider;
import org.piraso.api.entry.Entry;
import org.piraso.api.jspringbot.JSpringBotEntry;
import org.piraso.api.jspringbot.JSpringBotKeywordEntry;
import org.piraso.api.jspringbot.JSpringBotStatus;
import org.piraso.api.jspringbot.JSpringBotStatusAware;
import org.piraso.ui.api.EntryRowColumn;
import org.piraso.ui.api.EntryRowRenderingProvider;

import javax.swing.*;
import java.awt.*;

/**
 * Provides rendering to general entry types.
 */
@ServiceProvider(service=EntryRowRenderingProvider.class)
public class JSpringBotEntryRowRenderingProviderImpl implements EntryRowRenderingProvider {
    @Override
    public boolean isSupported(Entry entry) {
        return JSpringBotEntry.class.isInstance(entry);
    }

    @Override
    public void render(JLabel cell, Entry entry, EntryRowColumn column) {
        if(JSpringBotKeywordEntry.class.isInstance(entry)) {
            JSpringBotKeywordEntry keywordEntry = (JSpringBotKeywordEntry) entry;

            if(keywordEntry.isParent() && keywordEntry.getElapseTime() == null) {
                if(column == EntryRowColumn.NUMBER) {
                    cell.setIcon(new ImageIcon(getClass().getResource("/org/piraso/ui/jspringbot/icons/bullet_toggle_plus.png")));
                } else {
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                }
            }
        }

        if(JSpringBotStatusAware.class.isInstance(entry)) {
            JSpringBotStatusAware statusAware = (JSpringBotStatusAware) entry;

            if(statusAware.getStatus() != null) {
                if(statusAware.getStatus() == JSpringBotStatus.PASS) {
                    cell.setForeground(new Color(0x008000));
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                } else {
                    cell.setForeground(Color.RED);
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD));
                }
            }
        }
    }
}
