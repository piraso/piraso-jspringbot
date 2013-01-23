package org.piraso.jspringbot;

import org.piraso.server.CustomEntryPoint;

public class JSpringBotEntryPoint extends CustomEntryPoint {

    public JSpringBotEntryPoint(String path, String key) {
        super(path, "jspringbot." + key);
    }
}
