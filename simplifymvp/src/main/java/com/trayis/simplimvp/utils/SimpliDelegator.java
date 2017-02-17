package com.trayis.simplimvp.utils;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.trayis.simplimvp.presenter.SimpliPresenter;
import com.trayis.simplimvp.view.SimpliView;
import com.trayis.simplimvp.view.ViewWrapper;

/**
 * Created by Mukund Desai on 2/17/17.
 */
public class SimpliDelegator<P extends SimpliPresenter<V>, V extends SimpliView> {

    private final P presenter;

    private final ViewWrapper<V> viewWrapper;

    public SimpliDelegator(P presenter, V view) {
        this.presenter = presenter;
        viewWrapper = new ViewWrapper<>(view);
    }

    public void onConfigurationChangedAfterSuper(Configuration newConfig) {
        presenter.invalidateView();
    }

    public void onCreateAfterSuper(Bundle savedInstanceState) {
        presenter.onCreate();
    }

    public void onDestroyAfterSuper() {
        viewWrapper.dropView();
        presenter.onDestroy();
    }

    public void onSaveInstanceStateAfterSuper(Bundle outState) {
        presenter.onSaveinstanceState(outState);
    }

    public void onStartAfterSuper() {
        presenter.bindView((V) ViewWrapper.prepareViewDelegator(viewWrapper));
    }

    public void onStopBeforeSuper() {
        presenter.onStopBefore();
    }

    public void onStopAfterSuper() {
        presenter.onStopAfter();
    }

}
