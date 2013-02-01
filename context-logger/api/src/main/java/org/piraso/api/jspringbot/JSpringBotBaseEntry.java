package org.piraso.api.jspringbot;

import org.apache.commons.lang.StringUtils;
import org.piraso.api.entry.ElapseTimeAware;
import org.piraso.api.entry.ElapseTimeEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class JSpringBotBaseEntry extends JSpringBotEntry implements ElapseTimeAware {

    protected String name;

    protected String longName;

    protected String documentation;

    protected String source;

    protected String startTime;

    protected String endTime;

    protected Integer totalTests;

    protected JSpringBotStatus status;

    protected Integer scopeElapseTime;

    protected String statistics;

    protected String logMessage;

    protected String logLevel;

    protected String timestamp;

    protected Boolean html;

    protected String template;

    protected Boolean critical;

    protected List<String> tags;

    protected List<String> arguments;

    protected ElapseTimeEntry elapseTime;

    protected ElapseTimeEntry startTimeEntry;

    public JSpringBotBaseEntry() {
    }

    public JSpringBotBaseEntry(JSpringBotType type, String name) {
        this(type, name, null);
    }

    public JSpringBotBaseEntry(JSpringBotType type, String name, Map attributes) {
        this.type = type;
        this.name = name;

        startTimeEntry = new ElapseTimeEntry();
        startTimeEntry.start();
        parseAttributes(attributes);
    }

    public void start(Map attributes) {
        parseAttributes(attributes);
    }

    public void end(Map attributes) {
        parseAttributes(attributes);
    }

    @SuppressWarnings("unchecked")
    protected void parseAttributes(Map attributes) {
        if(attributes == null) {
            return;
        }

        if(attributes.containsKey("longname")) {
            longName = String.valueOf(attributes.get("longname"));
        }
        if(attributes.containsKey("doc")) {
            documentation = String.valueOf(attributes.get("doc"));
        }
        if(attributes.containsKey("source")) {
            source = String.valueOf(attributes.get("source"));
        }
        if(attributes.containsKey("template")) {
            template = String.valueOf(attributes.get("template"));
        }
        if(attributes.containsKey("critical")) {
            critical = StringUtils.equalsIgnoreCase("yes", String.valueOf(attributes.get("critical")));
        }
        if(attributes.containsKey("totaltests")) {
            totalTests = Integer.valueOf(String.valueOf(attributes.get("totaltests")));
        }
        if(attributes.containsKey("starttime")) {
            startTime = String.valueOf(attributes.get("starttime"));
        }
        if(attributes.containsKey("endtime")) {
            endTime = String.valueOf(attributes.get("endtime"));
        }
        if(attributes.containsKey("elapsetime")) {
            scopeElapseTime = Integer.valueOf(String.valueOf(attributes.get("elapsetime")));
        }
        if(attributes.containsKey("status")) {
            status = JSpringBotStatus.valueOf(String.valueOf(attributes.get("status")));
        }
        if(attributes.containsKey("statistics")) {
            statistics = String.valueOf(attributes.get("statistics"));
        }
        if(attributes.containsKey("message")) {
            logMessage = String.valueOf(attributes.get("message"));
        }
        if(attributes.containsKey("level")) {
            logLevel = String.valueOf(attributes.get("level"));
        }
        if(attributes.containsKey("timestamp")) {
            timestamp = String.valueOf(attributes.get("timestamp"));
        }
        if(attributes.containsKey("html")) {
            html = StringUtils.equalsIgnoreCase("yes", String.valueOf(attributes.get("html")));
        }
        if(attributes.containsKey("tags")) {
            List items = (List) attributes.get("tags");
            tags = new ArrayList<String>(items.size());

            for(Object item : items) {
                tags.add(String.valueOf(item));
            }
        }
        if(attributes.containsKey("args")) {
            List items = (List) attributes.get("args");
            arguments = new ArrayList<String>(items.size());
            for(Object item : items) {
                arguments.add(String.valueOf(item));
            }
        }
        if(attributes.containsKey("type")) {
            type = JSpringBotType.valueOfByType(String.valueOf(attributes.get("type")));
        }
    }

    public void setStartTimer() {
        setElapseTime(startTimeEntry);
    }

    public ElapseTimeEntry getElapseTime() {
        return elapseTime;
    }

    public void setElapseTime(ElapseTimeEntry elapseTime) {
        this.elapseTime = elapseTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
