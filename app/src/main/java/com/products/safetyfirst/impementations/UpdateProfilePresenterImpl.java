package com.products.safetyfirst.impementations;

import com.products.safetyfirst.interfaces.UpdateProfileInteractor;
import com.products.safetyfirst.interfaces.UpdateProfilePresenter;
import com.products.safetyfirst.interfaces.UpdateProfileView;

/**
 * Created by vikas on 04/10/17.
 */

public class UpdateProfilePresenterImpl implements UpdateProfilePresenter, UpdateProfileInteractor.OnUpdateFinishedListener {

    private UpdateProfileView updateProfileView;
    private UpdateProfileInteractor updateProfileInteractor;

    public UpdateProfilePresenterImpl(UpdateProfileView updateProfileView) {
        this.updateProfileView = updateProfileView;
        this.updateProfileInteractor = new UpdateProfileInteractorImpl();
    }

    @Override
    public void validateCredentials(String name, String phone, String company, String designation, String certificate, String city) {
        if (updateProfileView != null) {
            updateProfileView.showProgress();
        }
        updateProfileInteractor.updateProfile(name, phone, company, designation, certificate, city, this);
    }

    @Override
    public void onDestroy() {
        updateProfileView = null;
    }

    @Override
    public void onUsernameError() {
        if (updateProfileView != null) {
            updateProfileView.setUsernameError();
            updateProfileView.hideProgress();
        }
    }

    @Override
    public void onPhoneError() {
        if (updateProfileView != null) {
            updateProfileView.setPhoneError();
            updateProfileView.hideProgress();
        }
    }

    @Override
    public void onCompanyError() {
        if (updateProfileView != null) {
            updateProfileView.setCompanyError();
            updateProfileView.hideProgress();
        }
    }

    @Override
    public void onDesignationdError() {
        if (updateProfileView != null) {
            updateProfileView.setDesignationdError();
            updateProfileView.hideProgress();
        }
    }

    @Override
    public void onCertificateError() {
        if (updateProfileView != null) {
            updateProfileView.setCertificateError();
            updateProfileView.hideProgress();
        }
    }

    @Override
    public void onCityError() {
        if (updateProfileView != null) {
            updateProfileView.setCityError();
            updateProfileView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (updateProfileView != null) {
            updateProfileView.hideProgress();
            updateProfileView.navigateToHome();
        }
    }
}