package org.piraso.server.jspringbot;

import org.jspringbot.lifecycle.RobotListenerHandler;
import org.piraso.api.Level;
import org.piraso.api.entry.RequestEntry;
import org.piraso.api.jspringbot.*;
import org.piraso.server.GroupChainId;
import org.piraso.server.dispatcher.ContextLogDispatcher;

import java.util.Map;

import static org.piraso.server.jspringbot.JSpringBotPirasoContextHolder.*;

public class PirasoJSpringBotLifeCycleHandler implements RobotListenerHandler {

    private static final Level SUITE = Level.get(JSpringBotPreferenceEnum.SUITE.getPropertyName());

    private static final Level TEST_CASE = Level.get(JSpringBotPreferenceEnum.TEST_CASE.getPropertyName());

    private static final JSpringBotPreferenceEvaluator EVALUATOR = new JSpringBotPreferenceEvaluator();

    public void startSuite(String name, Map attributes) {
        push(new JSpringBotSuiteEntry(name, attributes));
    }

    public void endSuite(String name, Map attributes) {
        try {
            if (EVALUATOR.isSuiteEnabled()) {
                setContext(JSpringBotType.SUITE);
                try {
                    ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("response"), new JSpringBotResponseEntry(peekSuiteEntry().getStatus()));
                } finally {
                    removeContext();
                }
            }
        } finally {
            pop();
        }
    }

    public void startTest(String name, Map attributes) {
        try {
            if (isPeekSuite() && EVALUATOR.isSuiteEnabled()) {
                ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("request"), new RequestEntry(peekEntryPoint().getPath()));
                ContextLogDispatcher.forward(SUITE, create(), peekEntry());
                peekEntry().setStartTimer();
            }
        } finally {
            push(new JSpringBotTestCaseEntry(name, attributes));
        }
    }

    public void endTest(String name, Map attributes) {
        try {
            if(EVALUATOR.isSuiteEnabled() && isPeekTestCase()) {
                setContext(JSpringBotType.SUITE);
                try {
                    JSpringBotTestCaseEntry entry = peekTestCaseEntry();
                    entry.end(attributes);
                    entry.getElapseTime().stop();

                    ContextLogDispatcher.forward(SUITE, create(), entry);
                } finally {
                    removeContext();
                }
            }

            if (isPeekTestCase() && EVALUATOR.isTestCaseEnabled()) {
                setContext(JSpringBotType.TEST_CASE);
                try {
                    ContextLogDispatcher.forward(Level.SCOPED, new GroupChainId("response"), new JSpringBotResponseEntry(peekTestCaseEntry().getStatus()));
                } finally {
                    removeContext();
                }
            }
        } finally {
            pop();
        }
    }

    public void startKeyword(String name, Map attributes) {
        if(isPeekKeyword()) {
            JSpringBotKeywordEntry entry = peekKeywordEntry();
            entry.setParent(true);

            if(EVALUATOR.isTestCaseEnabled()) {
                setContext(JSpringBotType.TEST_CASE);
                try {
                    ContextLogDispatcher.forward(TEST_CASE, create(), entry);
                } finally {
                    removeContext();
                }
            }

        }

        push(new JSpringBotKeywordEntry(name, attributes));
        peekEntry().setStartTimer();
        setContextIndent();
    }

    public void endKeyword(String name, Map attributes) {
        try {
            if(isPeekKeyword()) {
                JSpringBotKeywordEntry entry = peekKeywordEntry();

                if(EVALUATOR.isTestCaseEnabled()) {
                    setContext(JSpringBotType.TEST_CASE);
                    try {
                        entry.getElapseTime().stop();
                        ContextLogDispatcher.forward(TEST_CASE, create(), entry);
                    } finally {
                        removeContext();
                    }
                }
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
