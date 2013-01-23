package org.piraso.jspringbot;

import org.jspringbot.lifecycle.LifeCycleHandler;
import org.piraso.api.Level;
import org.piraso.api.entry.ElapseTimeEntry;
import org.piraso.api.entry.MessageEntry;
import org.piraso.api.entry.RequestEntry;
import org.piraso.api.entry.ResponseEntry;
import org.piraso.server.GroupChainId;
import org.piraso.server.PirasoContext;
import org.piraso.server.PirasoContextHolder;
import org.piraso.server.bridge.BridgeConfig;
import org.piraso.server.dispatcher.ContextLogDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PirasoJSpringBotLifeCycleHandler implements LifeCycleHandler {

    private PirasoContext suiteContext;

    private PirasoContext testContext;

    private Stack<String> suites = new Stack<String>();

    private MessageEntry testEntry;

    private MessageEntry keywordEntry;

    private List<String> keywordId;

    public void startSuite(String name, Map attributes) {
        if (!BridgeConfig.INSTANCE.getLoggingEnabled()) {
            return;
        }

        suites.push(name);
    }

    public void endSuite(String name, Map attributes) {
        if (!BridgeConfig.INSTANCE.getLoggingEnabled()) {
            return;
        }

        if (suiteContext != null) {
            PirasoContextHolder.setContext(suiteContext);
            try {
                ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("response"), new ResponseEntry());
            } finally {
                PirasoContextHolder.removeContext();
            }
        }

        suiteContext = null;
        suites.pop();
    }

    public void startTest(String name, Map attributes) {
        if (!BridgeConfig.INSTANCE.getLoggingEnabled()) {
            return;
        }

        if (suiteContext == null) {
            JSpringBotEntryPoint entryPoint = new JSpringBotEntryPoint(suites.peek(), suites.peek());
            suiteContext = new PirasoContext(entryPoint);

            PirasoContextHolder.setContext(suiteContext);
            try {
                ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("request"), new RequestEntry(entryPoint.getPath()));
            } finally {
                PirasoContextHolder.removeContext();
            }
        }

        testEntry = new MessageEntry(name, new ElapseTimeEntry());
        testEntry.getElapseTime().start();

        JSpringBotEntryPoint entryPoint = new JSpringBotEntryPoint(name, suites.peek());
        testContext = new PirasoContext(entryPoint);

        keywordId = new ArrayList<String>(suites);
    }

    public void endTest(String name, Map attributes) {
        if (!BridgeConfig.INSTANCE.getLoggingEnabled()) {
            return;
        }

        if (testContext != null) {
            PirasoContextHolder.setContext(testContext);
            try {
                ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("response"), new ResponseEntry());
            } finally {
                PirasoContextHolder.removeContext();
            }
        }

        PirasoContextHolder.setContext(suiteContext);
        try {
            testEntry.getElapseTime().stop();
            ContextLogDispatcher.forward(Level.ALL, new GroupChainId(suites.toString()), testEntry);
        } finally {
            PirasoContextHolder.removeContext();
        }
    }

    public void startKeyword(String name, Map attributes) {
        if (!BridgeConfig.INSTANCE.getLoggingEnabled()) {
            return;
        }

        keywordEntry = new MessageEntry(name, new ElapseTimeEntry());
        keywordEntry.getElapseTime().start();
    }

    public void endKeyword(String name, Map attributes) {
        if (!BridgeConfig.INSTANCE.getLoggingEnabled()) {
            return;
        }

        PirasoContextHolder.setContext(testContext);
        try {
            keywordEntry.getElapseTime().stop();
            ContextLogDispatcher.forward(Level.ALL, new GroupChainId(keywordId.toString()), keywordEntry);
        } finally {
            PirasoContextHolder.removeContext();
        }
    }

    public void startJSpringBotKeyword(String name, Map attributes) {
    }

    public void endJSpringBotKeyword(String name, Map attributes) {
    }
}
