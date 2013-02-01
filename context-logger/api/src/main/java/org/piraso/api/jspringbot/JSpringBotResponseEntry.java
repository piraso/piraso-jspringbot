package org.piraso.api.jspringbot;

import org.piraso.api.entry.ResponseEntry;

public class JSpringBotResponseEntry extends ResponseEntry {

    private JSpringBotStatus testStatus;

    public JSpringBotResponseEntry() {
    }

    public JSpringBotResponseEntry(JSpringBotStatus testStatus) {
        this.testStatus = testStatus;
    }

    public JSpringBotStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(JSpringBotStatus testStatus) {
        this.testStatus = testStatus;
    }

    @Override
    public String toString() {
        return String.format("%sED", testStatus.name());
    }
}
