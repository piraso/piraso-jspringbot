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

import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.piraso.api.entry.Entry;
import org.piraso.api.jspringbot.JSpringBotPreferenceEnum;
import org.piraso.ui.api.NCPreferenceProperty;
import org.piraso.ui.api.PreferenceProperty;
import org.piraso.ui.api.PreferenceProvider;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author adleon
 */
@ServiceProvider(service=PreferenceProvider.class)
public class JSpringBotPreferenceProviderImpl implements PreferenceProvider {

    @Override
    public List<? extends PreferenceProperty> getPreferences() {
        NCPreferenceProperty enabled = new NCPreferenceProperty(JSpringBotPreferenceEnum.ENABLED.getPropertyName(), Boolean.class);
        NCPreferenceProperty keywordContext = new NCPreferenceProperty(JSpringBotPreferenceEnum.KEYWORD_CONTEXT.getPropertyName(), Boolean.class);
        NCPreferenceProperty suite = new NCPreferenceProperty(JSpringBotPreferenceEnum.SUITE.getPropertyName(), Boolean.class);
        NCPreferenceProperty testCase = new NCPreferenceProperty(JSpringBotPreferenceEnum.TEST_CASE.getPropertyName(), Boolean.class);
        NCPreferenceProperty keyword = new NCPreferenceProperty(JSpringBotPreferenceEnum.KEYWORD.getPropertyName(), Boolean.class);

        return Arrays.asList(enabled, keywordContext, suite, testCase, keyword);
    }

    @Override
    public String getMessage(String name, Object[] args) {
        return NbBundle.getMessage(JSpringBotPreferenceProviderImpl.class, name, args);
    }

    @Override
    public String getShortName(Entry entry, PreferenceProperty property) {
        return getMessage(entry.getLevel() + ".short");
    }

    @Override
    public Integer getOrder() {
        return 5;
    }

    @Override
    public String getName() {
        return getMessage("jspringbot.name");
    }

    @Override
    public String getShortName() {
        return getMessage("jspringbot.name.short");
    }

    @Override
    public boolean isHorizontalChildLayout() {
        return false;
    }

    @Override
    public boolean isPreviewLastChildOnly() {
        return false;
    }

    @Override
    public String getMessage(String name) {
        return getMessage(name, null);
    }
}
