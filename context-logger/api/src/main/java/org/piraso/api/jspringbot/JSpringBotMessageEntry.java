package org.piraso.api.jspringbot;

import java.util.Map;

public class JSpringBotMessageEntry extends JSpringBotBaseEntry {

    public JSpringBotMessageEntry() {
    }

    public JSpringBotMessageEntry(JSpringBotType type, Map attributes) {
        super(type, null, attributes);
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getHtml() {
        return html;
    }

    public void setHtml(Boolean html) {
        this.html = html;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String attrMessage) {
        this.logMessage = attrMessage;
    }
}
