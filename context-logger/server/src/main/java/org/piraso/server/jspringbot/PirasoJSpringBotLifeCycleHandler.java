package org.piraso.server.jspringbot;

import org.apache.log4j.Logger;
import org.jspringbot.lifecycle.RobotListenerHandler;
import org.piraso.api.Level;
import org.piraso.api.jspringbot.*;
import org.piraso.server.GroupChainId;
import org.piraso.server.dispatcher.ContextLogDispatcher;

import java.util.Map;

import static org.piraso.server.jspringbot.JSpringBotPirasoContextHolder.*;

public class PirasoJSpringBotLifeCycleHandler implements RobotListenerHandler {

    private static final Logger LOG = Logger.getLogger(PirasoPreferenceWrapperFactory.class);

    private static final Level SUITE = Level.get(JSpringBotPreferenceEnum.SUITE.getPropertyName());

    private static final Level TEST_CASE = Level.get(JSpringBotPreferenceEnum.TEST_CASE.getPropertyName());

    private static final JSpringBotPreferenceEvaluator EVALUATOR = new JSpringBotPreferenceEvaluator();

    public void startSuite(String name, Map attributes) {
        push(new JSpringBotSuiteEntry(name, attributes));
        ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("START"), new JSpringBotRequestEntry(peekEntryPoint().getPath()));
        ContextLogDispatcher.forward(SUITE, create(), peekEntry());
        peekEntry().setStartTimer();
    }

    public void endSuite(String name, Map attributes) {
        try {
            peekSuiteEntry().end(attributes);
            peekSuiteEntry().getElapseTime().stop();
            ContextLogDispatcher.forward(SUITE, create(), peekEntry());
            ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("END"), new JSpringBotResponseEntry(peekSuiteEntry()));
        } finally {
            pop();
        }
    }

    public void startTest(String name, Map attributes) {
        push(new JSpringBotTestCaseEntry(name, attributes));
        ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("START"), new JSpringBotRequestEntry(peekEntryPoint().getPath()));

        peekEntry().setStartTimer();
    }

    public void endTest(String name, Map attributes) {
        try {
            setContext(JSpringBotType.SUITE);
            try {
                JSpringBotTestCaseEntry entry = peekTestCaseEntry();
                entry.end(attributes);
                entry.getElapseTime().stop();

                ContextLogDispatcher.forward(TEST_CASE, create(), entry);
            } finally {
                removeContext();
            }

            ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("END"), new JSpringBotResponseEntry(peekTestCaseEntry()));
        } finally {
            pop();
        }
    }

    public void startKeyword(String name, Map attributes) {
        if(isPeekKeyword()) {
            JSpringBotKeywordEntry entry = peekKeywordEntry();

            if(EVALUATOR.isTestCaseEnabled() && !entry.isParent()) {
                entry.setParent(true);
                entry.setElapseTime(null);

                setContext(JSpringBotType.TEST_CASE, JSpringBotType.SUITE);

                try {
                    Level keywordLevel = Level.get(entry.getType().getPreference().getPropertyName());
                    ContextLogDispatcher.forward(keywordLevel, create(), entry);
                    entry.setStartTimer();
                } finally {
                    removeContext();
                }
            }
        }

        push(new JSpringBotKeywordEntry(name, attributes));
        peekEntry().setStartTimer();
        setContextIndent();

        if(EVALUATOR.isKeywordContextEnabled()) {
            ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("START"), new JSpringBotRequestEntry(peekEntryPoint().getPath()));
        }
    }

    public void endKeyword(String name, Map attributes) {
        try {
            JSpringBotKeywordEntry entry = peekKeywordEntry();
            entry.end(attributes);

            if(EVALUATOR.isTestCaseEnabled()) {
                entry.getElapseTime().stop();

                if(entry.isParent()) {
                    setContextIndent(1);
                }

                setContext(JSpringBotType.TEST_CASE, JSpringBotType.SUITE);
                try {
                    Level keywordLevel = Level.get(entry.getType().getPreference().getPropertyName());
                    ContextLogDispatcher.forward(keywordLevel, create(), entry);
                } finally {
                    removeContext();
                }
            }

            if(EVALUATOR.isKeywordContextEnabled()) {
                ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("END"), new JSpringBotResponseEntry(peekKeywordEntry()));
            }
        } finally {
            pop();
            setContextIndent();
        }
    }

    public void startJSpringBotKeyword(String name, Map attributes) {
    }

    public void endJSpringBotKeyword(String name, Map attributes) {
    }

    public void logMessage(Map message) {
        
    }

    public void message(Map message) {
        
    }

    public void outputFile(String path) {
        
    }

    public void logFile(String path) {
        
    }

    public void reportFile(String path) {
        
    }

    public void debugFile(String path) {
        
    }

    public void close() {
        
    }
}
