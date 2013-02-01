package org.piraso.api.jspringbot;

import java.util.List;
import java.util.Map;

public class JSpringBotTestCaseEntry extends JSpringBotBaseEntry implements JSpringBotStatusAware {

    public JSpringBotTestCaseEntry() {
    }

    public JSpringBotTestCaseEntry(String name, Map attributes) {
        super(JSpringBotType.TEST_CASE, name, attributes);
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public Integer getScopeElapseTime() {
        return scopeElapseTime;
    }

    public void setScopeElapseTime(Integer scopeElapseTime) {
        this.scopeElapseTime = scopeElapseTime;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String attrMessage) {
        this.logMessage = attrMessage;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getCritical() {
        return critical;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
