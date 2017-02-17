package com.trayis.simplimvp.view;

/**
 * Created by Mukund Desai on 2/17/17.
 */
public interface SimpliView {

    boolean postToMessageQueue(Runnable runnable);

}
