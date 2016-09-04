package com.josealfonsomora.coolappsfacebook.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.josealfonsomora.coolappsfacebook.App;
import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebooFilmProfile;
import com.josealfonsomora.coolappsfacebook.login.LoginActivity;
import com.josealfonsomora.coolappsfacebook.mvp.BaseActivity;
import com.josealfonsomora.coolappsfacebook.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    UserSession userSession;

    @Inject
    MainPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.films_text)
    TextView textViewFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp().component().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.init();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        presenter.onNavigationItemSelected(item);

        return true;
    }

    @Override
    public void setUserPicture(String response) {
        ImageView userPicture = ButterKnife.findById(navigationView.getHeaderView(0), R.id.imageView);
        Picasso.with(this)
                .load(response)
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .error(R.drawable.com_facebook_profile_picture_blank_portrait)
                .into(userPicture);
    }

    @Override
    public void setUserName(String name) {
        TextView userName = ButterKnife.findById(navigationView.getHeaderView(0), R.id.user_name);
        userName.setText(name);
    }

    @Override
    public void setUserGender(String gender) {

    }

    @Override
    public void setUserLink(String link) {
        TextView profileLink = ButterKnife.findById(navigationView.getHeaderView(0), R.id.profile_link);
        profileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void openUserProfile() {
        ImageView imageView = ButterKnife.findById(navigationView.getHeaderView(0), R.id.imageView);
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        if (!TextUtils.isEmpty(userSession.getPictureUrl())) {
            intent.putExtra(ProfileActivity.USER_IMAGE_EXTRA, userSession.getPictureUrl());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, (ImageView) imageView, "profile");
            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void closeNavDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void addNewFilm(FacebooFilmProfile dataResponse) {
        textViewFilms.setText(textViewFilms.getText() + "\n" + dataResponse.getName());
    }
}
