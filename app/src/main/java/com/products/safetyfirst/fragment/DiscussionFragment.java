package com.products.safetyfirst.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.DocumentSnapshot;
import com.products.safetyfirst.BuildConfig;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.BaseActivity;
import com.products.safetyfirst.activity.NewPostActivity;
import com.products.safetyfirst.impementations.presenter.PostPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.interfaces.view.PostsView;
import com.products.safetyfirst.modelhelper.UserHelper;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class DiscussionFragment extends Fragment implements PostsView{
    public static final String ARG_TITLE = "arg_title";

    private PostPresenter presenter;
    private ProgressBar mProgressbar;
    private RecyclerView recycler;
    private com.products.safetyfirst.adaptersnew.PostAdapter adapter;
    private FloatingActionButton mFab;
    private static final int RC_SIGN_IN = 123;

    public DiscussionFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if(bundle!=null && bundle.getString("action").equals("bookmark")){
            //TODO:
        }
        return inflater.inflate(R.layout.discussion_fragment, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createUI(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillUI();
    }

    private void fillUI() {

        adapter=new com.products.safetyfirst.adaptersnew.PostAdapter( new com.products.safetyfirst.adaptersnew.PostAdapter.OnPostSelectedListener() {
            @Override
            public void onPostSelected(DocumentSnapshot post) {
                //TODO: do something here, till then this temporary snackbar
                Snackbar.make(getView(),"Selected", BaseTransientBottomBar.LENGTH_LONG);
            }
        });
        recycler.setAdapter(adapter);
    }

    private void createUI(View view) {
        recycler = view.findViewById(R.id.discussion_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());

        mFab = view.findViewById(R.id.new_post);

        final UserHelper user  = UserHelper.getInstance();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.isSignedIn()) {
                    Intent intent = new Intent(getContext(), NewPostActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar mySnackbar = Snackbar.make(getView(),
                            R.string.not_signed_in, Snackbar.LENGTH_SHORT);
                    mySnackbar.setAction(R.string.signIn, new MySignInListener());
                    mySnackbar.show();
                }
            }
        });

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState > 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }

        });
       // mProgressbar = view.findViewById(R.id.newspaginateprogbar);

        presenter = new PostPresenterImpl(this);

    }

    public class MySignInListener implements View.OnClickListener{

        public MySignInListener(){
        }

        @Override
        public void onClick(View v) {

            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                    // new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                    // new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTosUrl("https://superapp.example.com/terms-of-service.html")
                            .setPrivacyPolicyUrl("https://superapp.example.com/privacy-policy.html")
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                            .build(),
                    RC_SIGN_IN);


        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void navigateToHome() {

    }
}
