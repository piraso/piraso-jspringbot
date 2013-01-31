package org.piraso.api.jspringbot;

import java.util.List;
import java.util.Map;

public class JSpringBotKeywordEntry extends JSpringBotBaseEntry {

    public JSpringBotKeywordEntry() {
    }

    public JSpringBotKeywordEntry(String name, Map attributes) {
        super(JSpringBotType.KEYWORD, name, attributes);
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public JSpringBotStatus getStatus() {
        return status;
    }

    public void setStatus(JSpringBotStatus status) {
        this.status = status;
    }

    public int getElapseTime() {
        return elapseTime;
    }

    public void setElapseTime(int elapseTime) {
        this.elapseTime = elapseTime;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String attrMessage) {
        this.logMessage = attrMessage;
    }
}
