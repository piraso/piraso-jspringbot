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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PirasoJSpringBotLifeCycleHandler implements LifeCycleHandler {

    private static final PrintStream OUT = System.out;

    private PirasoContext suiteContext;

    private PirasoContext testContext;

    private Stack<String> suites = new Stack<String>();

    private Stack<MessageEntry> keywordEntryStack = new Stack<MessageEntry>();

    private MessageEntry suiteStarted;

    private MessageEntry testEntry;

    private List<String> keywordId = new ArrayList<String>();

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
                if (suiteStarted != null) {
                    suiteStarted.setMessage("Suite '" + suites.peek() + "' completed.");
                    if (suiteStarted.getElapseTime() != null) {
                        suiteStarted.getElapseTime().stop();
                    }

                    ContextLogDispatcher.forward(Level.ALL, new GroupChainId(suites.toString()), suiteStarted);
                }

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
            JSpringBotEntryPoint entryPoint = new JSpringBotEntryPoint("/SUITE/" + suites.peek(), suites.peek());
            suiteContext = new PirasoContext(entryPoint);

            PirasoContextHolder.setContext(suiteContext);
            try {
                ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("request"), new RequestEntry(entryPoint.getPath()));

                suiteStarted = new MessageEntry("Suite '" + suites.peek() + "' started.");
                ContextLogDispatcher.forward(Level.ALL, new GroupChainId(suites.toString()), suiteStarted);

                suiteStarted.setElapseTime(new ElapseTimeEntry());
                suiteStarted.getElapseTime().start();
            } finally {
                PirasoContextHolder.removeContext();
            }
        }

        testEntry = new MessageEntry(name, new ElapseTimeEntry());
        testEntry.setLevel("TEST CASE");
        testEntry.getElapseTime().start();

        JSpringBotEntryPoint entryPoint = new JSpringBotEntryPoint("/TEST_CASE/" + suites.peek() + "/" + name, suites.peek());
        testContext = new PirasoContext(entryPoint);
        PirasoContextHolder.setContext(testContext);

        ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("request"), new RequestEntry(entryPoint.getPath()));

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

        MessageEntry keywordEntry = new MessageEntry(name, new ElapseTimeEntry());
        keywordEntry.setLevel("KEYWORD");
        keywordEntry.getElapseTime().start();

        keywordEntryStack.add(keywordEntry);
    }

    public void endKeyword(String name, Map attributes) {
        if (!BridgeConfig.INSTANCE.getLoggingEnabled()) {
            return;
        }

        MessageEntry keywordEntry = keywordEntryStack.pop();
        keywordEntry.getElapseTime().stop();
        ContextLogDispatcher.forward(Level.ALL, new GroupChainId(keywordId.toString()), keywordEntry);
    }

    public void startJSpringBotKeyword(String name, Map attributes) {
    }

    public void endJSpringBotKeyword(String name, Map attributes) {
    }
}
