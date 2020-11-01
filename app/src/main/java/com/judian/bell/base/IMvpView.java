package com.judian.bell.base;

/**
 * @author: lionszhang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2017/5/12 11:09
 */
public interface IMvpView {

    void showLoading(String tips);

    void dismissLoading();

    void showToast(String info);

    void onFinish();

}
