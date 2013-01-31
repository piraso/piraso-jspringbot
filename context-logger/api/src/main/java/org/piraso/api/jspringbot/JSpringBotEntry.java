package org.piraso.api.jspringbot;

import org.piraso.api.entry.*;

public class JSpringBotEntry extends Entry implements MessageTypeAwareEntry, ThrowableAwareEntry {

    protected JSpringBotType type;

    protected String message;

    protected MessageType messageType;

    protected ThrowableEntry throwableEntry;

    public JSpringBotEntry() {
    }

    public JSpringBotEntry(JSpringBotType type, String message) {
        this(type, message, MessageType.TEXT_PLAIN);
    }

    public JSpringBotEntry(JSpringBotType type, String message, MessageType messageType) {
        this.type = type;
        this.message = message;
        this.messageType = messageType;
    }

    public JSpringBotType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public ThrowableEntry getThrown() {
        return throwableEntry;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setThrowableEntry(ThrowableEntry throwableEntry) {
        this.throwableEntry = throwableEntry;
    }

    public void setType(JSpringBotType type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
