package org.piraso.api.jspringbot;

import org.apache.commons.lang.StringUtils;

public enum JSpringBotType {
    SUITE("Suite", JSpringBotPreferenceEnum.SUITE),
    TEST_CASE("Test Case", JSpringBotPreferenceEnum.TEST_CASE),
    KEYWORD("Keyword", JSpringBotPreferenceEnum.KEYWORD),
    TEST_SETUP("Test Setup", JSpringBotPreferenceEnum.TEST_SETUP),
    TEST_TEAR_DOWN("Test Teardown", JSpringBotPreferenceEnum.TEST_TEAR_DOWN),
    SUITE_SETUP("Suite Setup", JSpringBotPreferenceEnum.SUITE_SETUP),
    SUITE_TEAR_DOWN("Suite Teardown", JSpringBotPreferenceEnum.SUITE_TEAR_DOWN),

    LOG_MESSAGES("Log Message", JSpringBotPreferenceEnum.LOG_MESSAGES),
    SYSTEM_MESSAGES("System Messages", JSpringBotPreferenceEnum.SYSTEM_MESSAGES),
    FILE_CREATION("File Creation", JSpringBotPreferenceEnum.FILE_CREATION);

    private String type;

    private JSpringBotPreferenceEnum preference;

    private JSpringBotType(String type, JSpringBotPreferenceEnum preference) {
        this.type = type;
        this.preference = preference;
    }

    public String getType() {
        return type;
    }

    public JSpringBotPreferenceEnum getPreference() {
        return preference;
    }

    public static JSpringBotType valueOfByPreference(JSpringBotPreferenceEnum preference) {
        for(JSpringBotType type : values()) {
            if(preference == type.getPreference()) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown preference '" + preference.name() + "'");
    }

    public static JSpringBotType valueOfByType(String str) {
        for(JSpringBotType type : values()) {
            if(StringUtils.equalsIgnoreCase(str, type.getType())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown type '" + str + "'");
    }
}
