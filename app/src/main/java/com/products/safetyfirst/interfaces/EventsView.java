package com.products.safetyfirst.interfaces;

/**
 * Created by vikas on 11/10/17.
 */

public interface EventsView {

    void onError(String message);

    void onSuccess(String message);

    void showProgress();

    void hideProgress();

    void navigateToHome();

}
