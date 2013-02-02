package org.piraso.api.jspringbot;

import org.piraso.api.entry.ElapseTimeAware;

public interface JSpringBotStatusAware extends ElapseTimeAware {
    JSpringBotStatus getStatus();
}
