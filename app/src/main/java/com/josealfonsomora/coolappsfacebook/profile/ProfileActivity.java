package com.josealfonsomora.coolappsfacebook.profile;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.josealfonsomora.coolappsfacebook.App;
import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.mvp.BaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileView, NavigationView.OnNavigationItemSelectedListener  {
    public static final String USER_IMAGE_EXTRA = "USER_IMAGE_EXTRA";
    @Inject

    public ProfilePresenter presenter;
    @BindView(R.id.smaller_image_view)
    ImageView smallImageView;
    @BindView(R.id.profile_quote)
    TextView quoteTextView;
    @BindView(R.id.gender)
    TextView genderTextView;
    @BindView(R.id.email)
    TextView emailTextView;
    @BindView(R.id.birthay)
    TextView birthayTextView;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.name)
    TextView nameTextView;

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

    @Override
    public void updateCover(String source) {
       Picasso.with(this)
                .load(source)
                .fit()
                .placeholder(R.drawable.com_facebook_button_background)
                .error(R.drawable.com_facebook_button_background)
                .into(cover);
    }

    @Override
    public void updateGender(String gender) {
        genderTextView.setText(gender);
    }

    @Override
    public void updateEmail(String email) {
        emailTextView.setText(email);
    }

    @Override
    public void updateBirthday(String birthday) {
        birthayTextView.setText(birthday);
    }

    @Override
    public void updateQuote(String quotes) {
        quoteTextView.setText(quotes);
    }

    @Override
    public void updateName(String name) {
        nameTextView.setText(name);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
