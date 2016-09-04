package com.josealfonsomora.coolappsfacebook.profile;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.widget.ImageView;

import com.josealfonsomora.coolappsfacebook.App;
import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.mvp.BaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileView {
    public static final String USER_IMAGE_EXTRA = "USER_IMAGE_EXTRA";
    @Inject

    public ProfilePresenter presenter;
    @BindView(R.id.smaller_image_view)
    ImageView smallImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.getApp().component().inject(this);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        if (getIntent() != null) {
            if (getIntent().getStringExtra(USER_IMAGE_EXTRA) != null) {
                String userImageUrl = getIntent().getStringExtra(USER_IMAGE_EXTRA);
                ActivityCompat.postponeEnterTransition(this);
                Picasso.with(this.getApplicationContext())
                        .load(userImageUrl)
                        .noFade()
                        .fit()
                        .centerInside()
                        .error(R.drawable.com_facebook_profile_picture_blank_portrait)
                        .into(smallImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                ActivityCompat.startPostponedEnterTransition(ProfileActivity.this);
                            }

                            @Override
                            public void onError() {
                                ActivityCompat.startPostponedEnterTransition(ProfileActivity.this);
                            }
                        });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.init();
    }


    @Override
    protected void onPause() {
        presenter.detachView();
        super.onPause();
    }
}
