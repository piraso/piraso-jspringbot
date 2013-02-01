package org.piraso.server.jspringbot;

import org.piraso.api.jspringbot.JSpringBotBaseEntry;
import org.piraso.api.jspringbot.JSpringBotType;
import org.piraso.server.PirasoContext;
import org.piraso.server.PirasoEntryPoint;

public class JSpringBotPirasoContext extends PirasoContext {

    private JSpringBotType type;

    private String name;

    private JSpringBotBaseEntry entry;

    public JSpringBotPirasoContext(PirasoEntryPoint entryPoint, String name, JSpringBotType type) {
        super(entryPoint);

        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public JSpringBotType getType() {
        return type;
    }

    public JSpringBotBaseEntry getEntry() {
        return entry;
    }

    public void setEntry(JSpringBotBaseEntry entry) {
        this.entry = entry;
    }
}
