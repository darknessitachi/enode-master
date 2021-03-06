package com.qianzhui.enode.infrastructure.impl;

import com.qianzhui.enode.eventing.IDomainEvent;
import com.qianzhui.enode.infrastructure.IMessageHandler;
import com.qianzhui.enode.infrastructure.IMessageHandlerProxy2;
import com.qianzhui.enode.infrastructure.ITwoMessageHandlerProvider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by junbo_xu on 2016/4/3.
 */
public class DefaultTwoMessageHandlerProvider extends AbstractHandlerProvider<ManyType, IMessageHandlerProxy2, List<Class>> implements ITwoMessageHandlerProvider {
    @Override
    protected Class getHandlerType() {
        return IMessageHandler.class;
    }

    @Override
    protected ManyType getKey(Method method) {
        return new ManyType(Arrays.asList(method.getParameterTypes()));
    }

    @Override
    protected Class<? extends IMessageHandlerProxy2> getHandlerProxyImplementationType() {
        return MessageHandlerProxy2.class;
    }

    @Override
    protected boolean isHandlerSourceMatchKey(List<Class> handlerSource, ManyType key) {
        return handlerSource.size() == 2
                && handlerSource.size() == key.getTypes().size()
                && handlerSource.get(0) == key.getTypes().get(0)
                && handlerSource.get(1) == key.getTypes().get(1);
    }

    @Override
    protected boolean isHandleMethodMatchKey(Class[] argumentTypes, ManyType key) {
        return argumentTypes.length == 2
                && argumentTypes.length == key.getTypes().size()
                && argumentTypes[0] == key.getTypes().get(0)
                && argumentTypes[1] == key.getTypes().get(1);
    }

    @Override
    protected boolean isHandleMethodMatch(Method method) {
        return method.getName().equals("handleAsync")
                && method.getParameterTypes().length == 2
                && IDomainEvent.class.isAssignableFrom(method.getParameterTypes()[0])
                && IDomainEvent.class.isAssignableFrom(method.getParameterTypes()[1]);
    }
}
