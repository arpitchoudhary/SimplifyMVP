/*
 * Copyright (C) 2017 grandcentrix GmbH
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trayis.simplimvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trayis.simplimvp.presenter.SimpliPresenter;
import com.trayis.simplimvp.utils.Logging;
import com.trayis.simplimvp.utils.SimpliDelegator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Mukund Desai on 2/17/17.
 */

public abstract class SimpliFragment<P extends SimpliPresenter<V>, V extends SimpliView> extends Fragment implements SimpliView {

    private static String TAG;

    private final SimpliDelegator mDelegate;

    private P mPresenter;

    public SimpliFragment() {
        TAG = getClass().getSimpleName();
        mDelegate = new SimpliDelegator(getPresenter(), this);
    }

    public P getPresenter() {
        if (mPresenter == null) {
            Type type = getClass().getGenericSuperclass();
            ParameterizedType paramType = (ParameterizedType) type;
            Class<P> pClass = (Class<P>) paramType.getActualTypeArguments()[0];
            try {
                mPresenter = pClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                Logging.e(TAG, e.getMessage(), e);
            } catch (IllegalAccessException e) {
                Logging.e(TAG, e.getMessage(), e);
            }
        }
        return mPresenter;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreateAfterSuper(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mDelegate.onCreateAfterSuper(savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroyAfterSuper();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDelegate.onDestroyAfterSuper();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceStateAfterSuper(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mDelegate.onStartAfterSuper();
    }

    @Override
    public void onStop() {
        mDelegate.onStopBeforeSuper();
        super.onStop();
    }

    @Override
    public boolean postToMessageQueue(final Runnable runnable) {
        return getActivity().getWindow().getDecorView().post(runnable);
    }

    @Override
    public String toString() {
        String presenter = getPresenter() == null ? "null" : getPresenter().getClass().getSimpleName() + "@" + Integer.toHexString(getPresenter().hashCode());
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + "{presenter=" + presenter + "}";
    }
}