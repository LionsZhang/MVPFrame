package com.judian.bell.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.judian.bell.rxbus.RxBus;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * <br> ClassName:   AbstractMvpActivity
 * <br> Description: MVP架构 Activity基类
 * <br>
 * <br> Author:      lionszhang
 * <br> Date:        2017/8/1 15:55
 */
public abstract class AbstractMvpActivity<T extends BasePresenter>
        extends AppCompatActivity implements IMvpView {
    protected T mCurrentPresenter;
    private Observable<Object> mObervable;
    private String mCurrentComponentName;

    /**
     * <br> Description: 创建Presenters
     * <br> Author:      lionszhang
     * <br> Date:        2017/5/24 17:10
     *
     * @return List presenterList
     */
    protected abstract T createPresenter();

    public T getCurrentPresenter() {
        return mCurrentPresenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentComponentName = getClass().getName();
        mObervable = RxBus.instance().register(mCurrentComponentName, Object.class);
        onSubscribe();
        mCurrentPresenter = createPresenter();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.attachView(this);
        }

    }

    protected void onSubscribe() {
        try {
            Disposable disposable =  mObervable.subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) {
                    onEvent(o);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void onEvent(Object o) {

    }

    @Override
    public void onFinish() {
        if (!isFinishing()) {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.detachView();
        }
        if (mObervable != null) {
            RxBus.instance().unregister(mCurrentComponentName, mObervable);
        }
    }
}
