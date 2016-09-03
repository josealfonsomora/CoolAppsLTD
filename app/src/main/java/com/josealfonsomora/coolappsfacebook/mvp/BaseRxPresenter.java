package com.josealfonsomora.coolappsfacebook.mvp;


import rx.Observable;
import rx.subjects.BehaviorSubject;

public class BaseRxPresenter<V extends MvpView> extends BasePresenter<V> {

    private final BehaviorSubject<Boolean> lifecycleSubject = BehaviorSubject.create();

    private static <T> Observable<T> takeUntilEvent(Observable<T> lifecycle, T event) {
        return lifecycle.takeFirst(lifecycleEvent -> lifecycleEvent.equals(event));
    }

    protected <T> Observable.Transformer<T, T> cancelOperationTransformer() {
        return observable -> observable.takeUntil(takeUntilEvent(lifecycleSubject.asObservable(), true));
    }

    protected void cancelRxOperation() {
        lifecycleSubject.onNext(true);
    }

    protected void resetRxOperation() {
        lifecycleSubject.onNext(false);
    }

    @Override
    public void detachView() {
        cancelRxOperation();
        super.detachView();
    }
}
