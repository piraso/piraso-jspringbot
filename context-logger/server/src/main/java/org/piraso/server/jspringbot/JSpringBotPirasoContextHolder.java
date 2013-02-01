package org.piraso.server.jspringbot;

import org.piraso.api.jspringbot.*;
import org.piraso.server.CustomEntryPoint;
import org.piraso.server.GroupChainId;
import org.piraso.server.PirasoContextHolder;
import org.piraso.server.PirasoEntryPoint;

import java.util.Collections;
import java.util.Stack;

public class JSpringBotPirasoContextHolder {

    private static final Stack<JSpringBotPirasoContext> CONTEXT_STACK = new Stack<JSpringBotPirasoContext>();

    private static final JSpringBotPreferenceEvaluator EVALUATOR = new JSpringBotPreferenceEvaluator();

    static {
        push(new JSpringBotSuiteEntry("global", Collections.emptyMap()));
    }

    private static PirasoEntryPoint createEntryPoint(String name, JSpringBotType type) {
        String path = String.format("/%s/%s", type.name(), name);
        String remoteAddr;

        if(JSpringBotType.SUITE == type) {
            remoteAddr = "jspringbot." + name;
        } else {
            remoteAddr = "jspringbot." + peekName(JSpringBotType.SUITE);
        }

        if(JSpringBotType.KEYWORD == type ||
                JSpringBotType.SUITE_SETUP == type ||
                JSpringBotType.SUITE_TEAR_DOWN == type ||
                JSpringBotType.TEST_SETUP == type ||
                JSpringBotType.TEST_TEAR_DOWN == type) {
            String test = peekName(JSpringBotType.TEST_CASE);

            path = String.format("/%s/%s/%s", type.name(), test, name);
        }

        return new CustomEntryPoint(path, remoteAddr);
    }

    public static JSpringBotPirasoContext push(JSpringBotBaseEntry entry) {
        PirasoEntryPoint entryPoint = createEntryPoint(entry.getName(), entry.getType());
        JSpringBotPirasoContext context = new JSpringBotPirasoContext(entryPoint, entry.getName(), entry.getType());
        context.setEntry(entry);

        CONTEXT_STACK.push(context);
        PirasoContextHolder.setContext(context);

        if(!EVALUATOR.isKeywordContextEnabled() && JSpringBotKeywordEntry.class.isInstance(peek().getEntry())) {
            setContext(JSpringBotType.TEST_CASE, JSpringBotType.SUITE);
        }

        return context;
    }

    public static GroupChainId create() {
        return new GroupChainId(peekSuiteEntry().getName());
    }

    public static boolean isPeekKeyword() {
        return isPeek(JSpringBotType.KEYWORD) ||
                isPeek(JSpringBotType.TEST_SETUP) ||
                isPeek(JSpringBotType.TEST_TEAR_DOWN) ||
                isPeek(JSpringBotType.SUITE_SETUP) ||
                isPeek(JSpringBotType.SUITE_TEAR_DOWN);
    }

    public static boolean isPeekTestCase() {
        return isPeek(JSpringBotType.TEST_CASE);
    }

    public static boolean isPeekSuite() {
        return isPeek(JSpringBotType.SUITE);
    }

    public static boolean isPeek(JSpringBotType type) {
        return type == peek().getType();
    }

    public static PirasoEntryPoint peekEntryPoint() {
        return peek().getEntryPoint();
    }

    public static JSpringBotPirasoContext pop() {
        JSpringBotPirasoContext context = CONTEXT_STACK.pop();

        PirasoContextHolder.setContext(peek());

        if(!EVALUATOR.isKeywordContextEnabled() && JSpringBotKeywordEntry.class.isInstance(peek().getEntry())) {
            setContext(JSpringBotType.TEST_CASE, JSpringBotType.SUITE);
        }

        return context;
    }

    public static JSpringBotPirasoContext peek() {
        return CONTEXT_STACK.peek();
    }

    public static JSpringBotBaseEntry peekEntry() {
        return CONTEXT_STACK.peek().getEntry();
    }

    public static JSpringBotBaseEntry peekEntry(JSpringBotType... types) {
        return peekContext(types).getEntry();
    }

    public static JSpringBotKeywordEntry peekKeywordEntry() {
        return (JSpringBotKeywordEntry) peekEntry(JSpringBotType.KEYWORD, JSpringBotType.SUITE_SETUP, JSpringBotType.SUITE_TEAR_DOWN, JSpringBotType.TEST_SETUP, JSpringBotType.TEST_TEAR_DOWN);
    }

    public static JSpringBotSuiteEntry peekSuiteEntry() {
        return (JSpringBotSuiteEntry) peekEntry(JSpringBotType.SUITE);
    }

    public static JSpringBotTestCaseEntry peekTestCaseEntry() {
        return (JSpringBotTestCaseEntry) peekEntry(JSpringBotType.TEST_CASE);
    }

    public static void setContext() {
        PirasoContextHolder.setContext(CONTEXT_STACK.peek());
    }

    public static void setContext(JSpringBotType... types) {
        JSpringBotPirasoContext context = peekContext(types);

        if(context != null) {
            PirasoContextHolder.setContext(context);
        }
    }

    public static void setContextIndent() {
        setContextIndent(0);
    }

    public static void setContextIndent(int add) {
        int count  = 0;

        for(JSpringBotPirasoContext context : CONTEXT_STACK) {
            if(JSpringBotKeywordEntry.class.isInstance(context.getEntry())) {
                count ++;
            }
        }

        JSpringBotPirasoContext context = peekContext(JSpringBotType.TEST_CASE, JSpringBotType.SUITE);
        if(context != null && count > 0) {
            context.setIndent(((count-1) + add) *2);
        } else if(context != null) {
            context.setIndent(add);
        }
    }

    private static JSpringBotPirasoContext peekContext(JSpringBotType... types) {
        Stack<JSpringBotPirasoContext> temp = new Stack<JSpringBotPirasoContext>();
        temp.addAll(CONTEXT_STACK);

        while(!temp.isEmpty()) {
            JSpringBotPirasoContext context = temp.pop();

            for(JSpringBotType type : types) {
                if(context.getType() == type) {
                    return context;
                }
            }
        }

        return null;
    }

    private static String peekName(JSpringBotType type) {
        JSpringBotPirasoContext context = peekContext(type);

        if(context != null) {
            return context.getName();
        }

        return "";
    }

    public static void removeContext() {
        PirasoContextHolder.removeContext();

        // ensure to use the last context
        setContext();

        if(!EVALUATOR.isKeywordContextEnabled() && JSpringBotKeywordEntry.class.isInstance(peek().getEntry())) {
            setContext(JSpringBotType.TEST_CASE, JSpringBotType.SUITE);
        }
    }
}
