package org.piraso.server.jspringbot;

import org.piraso.api.entry.ResponseEntry;
import org.piraso.api.jspringbot.JSpringBotStatus;

public class JSpringBotResponseEntry extends ResponseEntry {

    private JSpringBotStatus status;

    public JSpringBotResponseEntry(JSpringBotStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status.name();
    }
}
