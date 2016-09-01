package com.josealfonsomora.coolappsfacebook.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends MvpView> implements Presenter<V> {
    private WeakReference<V> viewRef;
    private String TAG = getClass().getName();

    @Override
    @CallSuper
    public void attachView(@NonNull V view) {
        viewRef = new WeakReference<>(view);
    }

    @Override
    @CallSuper
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }


    @NonNull
    @CallSuper
    protected V getView() {
        if (viewRef == null || viewRef.get() == null) {
            throw new IllegalStateException("view not attached or trying to access a view after it has been destroyed. you need to fix this!");
        } else {
            return viewRef.get();
        }
    }

    protected boolean isViewAttached() {
        boolean attached = viewRef != null && viewRef.get() != null;
        if (!attached) {
            Log.d(TAG, String.format("Presenter %s view not attached", getClass().getSimpleName()));
        }
        return attached;
    }


}
