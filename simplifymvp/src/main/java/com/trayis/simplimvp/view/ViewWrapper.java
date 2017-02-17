package com.trayis.simplimvp.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by Mukund Desai on 2/17/17.
 */
public class ViewWrapper<V extends SimpliView> implements InvocationHandler {

    private V mView;

    private static final HashMap<Class<?>, Object> DEFAULTS;

    static {
        DEFAULTS = new HashMap<>();
        DEFAULTS.put(Boolean.TYPE, false);
        DEFAULTS.put(Byte.TYPE, (byte) 0);
        DEFAULTS.put(Character.TYPE, '\000');
        DEFAULTS.put(Double.TYPE, 0.0d);
        DEFAULTS.put(Float.TYPE, 0.0f);
        DEFAULTS.put(Integer.TYPE, 0);
        DEFAULTS.put(Long.TYPE, 0L);
        DEFAULTS.put(Short.TYPE, (short) 0);
    }

    public ViewWrapper(V view) {
        this.mView = view;
    }

    public static <V extends SimpliView> V prepareViewDelegator(ViewWrapper wrapper) {
        Class<? extends SimpliView> viewClass = wrapper.mView.getClass();
        return (V) Proxy.newProxyInstance(viewClass.getClassLoader(), new Class<?>[]{viewClass}, wrapper);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (mView == null) {
            Class<?> returnType = method.getReturnType();
            return DEFAULTS.get(returnType);
        }

        return method.invoke(mView, args);
    }

    public void dropView() {
        mView = null;
    }
}
