package com.trayis.simplimvp.presenter;

import android.os.Bundle;

import com.trayis.simplimvp.view.SimpliView;

/**
 * Created by Mukund Desai on 2/17/17.
 */
public class SimpliPresenter<V extends SimpliView> {

    /**
     * The LifecycleState of a {@link SimpliPresenter}
     */
    public enum State {
        /**
         * Initial state of the presenter before {@link #onCreate()} got called
         */
        INITIALIZED,
        /**
         * presenter is running fine but has no attached view. Either it gets a view  and
         * transitions to {@link #VIEW_ATTACHED} or the presenter gets destroyed ->
         * {@link
         * #DESTROYED}
         */
        VIEW_DETACHED,
        /**
         * the view is attached. In any case, the next step will be {@link
         * #VIEW_DETACHED}
         */
        VIEW_ATTACHED,
        /**
         * termination state. It will never change again.
         */
        DESTROYED
    }

    private V view;

    private State mState = State.INITIALIZED;

    public void invalidateView() {
        view = null;
    }

    public void bindView(V view) {
        this.view = view;
        moveToState(State.VIEW_ATTACHED);
    }

    public void onCreate() {
        moveToState(State.VIEW_DETACHED);
    }

    public void onSaveinstanceState(Bundle outState) {
    }

    public void onStopBefore() {
        moveToState(State.VIEW_DETACHED);
    }

    public void onStopAfter() {
    }

    public void onDestroy() {
        moveToState(State.DESTROYED);
    }

    private void moveToState(State newState) {
        final State oldState = mState;
        if (newState != oldState) {
            switch (oldState) {
                case INITIALIZED:
                    if (newState == State.VIEW_DETACHED) {
                        // move allowed
                        break;
                    } else {
                        throw new IllegalStateException("Can't move to state " + newState
                                + ", the next state after INITIALIZED has to be VIEW_DETACHED");
                    }
                case VIEW_DETACHED:
                    if (newState == State.VIEW_ATTACHED) {
                        // move allowed
                        break;
                    } else if (newState == State.DESTROYED) {
                        // move allowed
                        break;
                    } else {
                        throw new IllegalStateException("Can't move to state " + newState
                                + ", the allowed states after VIEW_DETACHED are VIEW_ATTACHED or DESTROYED");
                    }
                case VIEW_ATTACHED:
                    // directly moving to DESTROYED is not possible, first detach the view
                    if (newState == State.VIEW_DETACHED) {
                        // move allowed
                        break;
                    } else {
                        throw new IllegalStateException("Can't move to state " + newState
                                + ", the next state after VIEW_ATTACHED has to be VIEW_DETACHED");
                    }
                case DESTROYED:
                    throw new IllegalStateException(
                            "once destroyed the presenter can't be moved to a different state");
            }

            mState = newState;
        }
    }
}
