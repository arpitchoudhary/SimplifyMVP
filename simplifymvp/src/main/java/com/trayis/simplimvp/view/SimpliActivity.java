package com.trayis.simplimvp.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.trayis.simplimvp.presenter.SimpliPresenter;
import com.trayis.simplimvp.utils.Logging;
import com.trayis.simplimvp.utils.SimpliDelegator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Mukund Desai on 2/17/17.
 */

public abstract class SimpliActivity<P extends SimpliPresenter<V>, V extends SimpliView> extends AppCompatActivity implements SimpliView {

    private static String TAG;

    private final SimpliDelegator mDelegate;

    private P mPresenter;

    public SimpliActivity() {
        TAG = getClass().getSimpleName();
        mDelegate = new SimpliDelegator(getPresenter(), this);
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDelegate.onConfigurationChangedAfterSuper(newConfig);
    }

    @Override
    public boolean postToMessageQueue(final Runnable runnable) {
        return getWindow().getDecorView().post(runnable);
    }

    public P getPresenter() {
        if (mPresenter == null) {
            Type type = getClass().getGenericSuperclass();
            ParameterizedType paramType = (ParameterizedType) type;
            Class<P> pClass = (Class<P>) paramType.getActualTypeArguments()[0];
            try {
                mPresenter = pClass.newInstance();
            } catch (InstantiationException e) {
                Logging.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Logging.e(TAG, e.getMessage(), e);
            }
        }
        return mPresenter;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreateAfterSuper(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroyAfterSuper();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceStateAfterSuper(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDelegate.onStartAfterSuper();
    }

    @Override
    protected void onStop() {
        mDelegate.onStopBeforeSuper();
        super.onStop();
        mDelegate.onStopAfterSuper();
    }

    @Override
    public String toString() {
        String presenter = mPresenter == null ? "null" : mPresenter.getClass().getSimpleName() + "@" + Integer.toHexString(mPresenter.hashCode());
        return getClass().getSimpleName() + ":" + SimpliActivity.class.getSimpleName() + "@" + Integer.toHexString(hashCode()) + "{presenter = " + presenter + "}";
    }
}
