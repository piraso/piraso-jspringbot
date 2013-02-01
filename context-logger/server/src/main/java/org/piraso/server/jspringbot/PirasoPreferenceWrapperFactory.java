package org.piraso.server.jspringbot;

import org.jspringbot.lifecycle.RobotListenerHandler;
import org.piraso.proxy.RegexMethodInterceptorAdapter;
import org.piraso.proxy.RegexMethodInterceptorEvent;
import org.piraso.proxy.RegexProxyFactory;

public class PirasoPreferenceWrapperFactory {

    private static final JSpringBotPreferenceEvaluator EVALUATOR = new JSpringBotPreferenceEvaluator();

    public static RobotListenerHandler create(RobotListenerHandler wrap) {
        RegexProxyFactory<RobotListenerHandler> factory = new RegexProxyFactory<RobotListenerHandler>(RobotListenerHandler.class);

        factory.addMethodListener(".*", new RegexMethodInterceptorAdapter<RobotListenerHandler>() {
            @Override
            public void beforeCall(RegexMethodInterceptorEvent<RobotListenerHandler> evt) {
                if(!EVALUATOR.isJSpringBotEnabled()) {
                    evt.skip();
                }
            }
        });

        return factory.getProxy(wrap);
    }
}
