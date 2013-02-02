package org.piraso.api.jspringbot;

import org.piraso.api.entry.ResponseEntry;

public class JSpringBotResponseEntry extends ResponseEntry {

    private JSpringBotStatus testStatus;

    public JSpringBotResponseEntry() {
    }

    public JSpringBotResponseEntry(JSpringBotStatusAware entry) {
        this.testStatus = entry.getStatus();

        if(entry.getElapseTime() != null) {
            setElapseTime(entry.getElapseTime());
        }
    }

    public JSpringBotStatus getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(JSpringBotStatus testStatus) {
        this.testStatus = testStatus;
    }

    @Override
    public String toString() {
        if(testStatus != null) {
            if(getElapseTime() == null) {
                return String.format("%sED", testStatus.name());
            } else {
                return String.format("%sED (%s)", testStatus.name(), getElapseTime().prettyPrint());
            }
        } else {
            return "UNKNOWN";
        }
    }
}
