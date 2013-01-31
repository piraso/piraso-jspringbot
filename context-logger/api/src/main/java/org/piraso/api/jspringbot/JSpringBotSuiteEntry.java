package org.piraso.api.jspringbot;

import java.util.Map;

public class JSpringBotSuiteEntry extends JSpringBotBaseEntry {

    public JSpringBotSuiteEntry() {
    }

    public JSpringBotSuiteEntry(String name, Map attributes) {
        super(JSpringBotType.SUITE, name, attributes);
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

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
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

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String attrMessage) {
        this.logMessage = attrMessage;
    }
}
