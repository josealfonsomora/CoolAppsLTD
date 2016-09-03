package com.josealfonsomora.coolappsfacebook.main;

import android.text.TextUtils;
import android.view.MenuItem;

import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookData;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookPicture;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookPublicProfile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class MainPresenterTest {
    @Mock
    MainView mView;
    @Mock
    FacebookClient facebookClientMock;
    @Mock
    UserSession userSessionMock;
    @Mock
    FacebookPicture facebookPictureMock;
    @Mock
    FacebookData dataMock;
    @Mock
    FacebookPublicProfile facebookPublicProfileMock;
    @Mock
    Throwable throwableMock;
    @Mock
    MenuItem menuItemMock;
    private MainPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mockStatic(TextUtils.class);
        MockitoAnnotations.initMocks(this);
        when(TextUtils.isEmpty("")).thenReturn(true);

        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });


        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        mPresenter = new MainPresenter(facebookClientMock, userSessionMock);

        mPresenter.attachView(mView);

    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().reset();
    }

    @Test
    public void user_image_is_updated_if_is_fetched_from_facebook_without_error() {
        String pictureUrl = "any_valid_url";

        when(TextUtils.isEmpty(pictureUrl)).thenReturn(false);
        when(dataMock.getUrl()).thenReturn(pictureUrl);
        when(facebookPictureMock.getData()).thenReturn(dataMock);
        when(userSessionMock.getFacebookAccessToken()).thenReturn("FacebookAccessToken");
        when(userSessionMock.getFacebookUserId()).thenReturn("FacebookUserId");

        final Observable<FacebookPicture> observableFacebookPicture = Observable.just(facebookPictureMock);
        final Observable<FacebookPublicProfile> observableFacebookPublicProfile = Observable.just(facebookPublicProfileMock);

        when(facebookClientMock.getUserPublicProfile(anyString(), anyString())).thenReturn(observableFacebookPublicProfile);
        when(facebookClientMock.getUserPictureProfile(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(observableFacebookPicture);

        mPresenter.init();

        verify(mView, times(1)).setUserPicture(pictureUrl);
    }

    @Test
    public void error_toast_is_shown_if_user_image_is_fetched_with_any_error() {
        String errorMessage = "any_error_message";

        when(TextUtils.isEmpty(errorMessage)).thenReturn(false);
        when(throwableMock.getMessage()).thenReturn(errorMessage);

        when(userSessionMock.getFacebookAccessToken()).thenReturn("FacebookAccessToken");
        when(userSessionMock.getFacebookUserId()).thenReturn("FacebookUserId");

        final Observable<FacebookPicture> observableFacebookPicture = Observable.error(throwableMock);
        final Observable<FacebookPublicProfile> observableFacebookPublicProfile = Observable.just(facebookPublicProfileMock);

        when(facebookClientMock.getUserPublicProfile(anyString(), anyString())).thenReturn(observableFacebookPublicProfile);
        when(facebookClientMock.getUserPictureProfile(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(observableFacebookPicture);

        mPresenter.init();

        verify(mView, never()).setUserPicture(anyString());
        verify(mView, times(1)).showErrorToast(errorMessage);
    }

    @Test
    public void user_details_update_from_facebook_graph() {

        String userName = "any_userName";
        String userGender = "any_gender";
        String userLink = "any_link";

        when(TextUtils.isEmpty(userName)).thenReturn(false);
        when(TextUtils.isEmpty(userGender)).thenReturn(false);
        when(TextUtils.isEmpty(userLink)).thenReturn(false);

        when(userSessionMock.getFacebookAccessToken()).thenReturn("FacebookAccessToken");
        when(userSessionMock.getFacebookUserId()).thenReturn("FacebookUserId");

        when(facebookPublicProfileMock.getName()).thenReturn(userName);
        when(facebookPublicProfileMock.getGender()).thenReturn(userGender);
        when(facebookPublicProfileMock.getLink()).thenReturn(userLink);
        final Observable<FacebookPicture> observableFacebookPicture = Observable.error(throwableMock);
        final Observable<FacebookPublicProfile> observableFacebookPublicProfile = Observable.just(facebookPublicProfileMock);

        when(facebookClientMock.getUserPublicProfile(anyString(), anyString())).thenReturn(observableFacebookPublicProfile);
        when(facebookClientMock.getUserPictureProfile(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(observableFacebookPicture);

        mPresenter.init();

        verify(mView, times(1)).setUserName(userName);
        verify(mView, times(1)).setUserGender(userGender);
        verify(mView, times(1)).setUserLink(userLink);
    }

    @Test
    public void user_name_not_update_from_facebook_graph_if_is_empty() {

        String userName = "";

        when(userSessionMock.getFacebookAccessToken()).thenReturn("FacebookAccessToken");
        when(userSessionMock.getFacebookUserId()).thenReturn("FacebookUserId");

        when(facebookPublicProfileMock.getName()).thenReturn(userName);
        final Observable<FacebookPicture> observableFacebookPicture = Observable.error(throwableMock);
        final Observable<FacebookPublicProfile> observableFacebookPublicProfile = Observable.error(throwableMock);

        when(facebookClientMock.getUserPublicProfile(anyString(), anyString())).thenReturn(observableFacebookPublicProfile);
        when(facebookClientMock.getUserPictureProfile(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(observableFacebookPicture);

        mPresenter.init();

        verify(mView, never()).setUserName(anyString());
        verify(mView, never()).setUserLink(anyString());
        verify(mView, never()).setUserGender(anyString());
    }

    @Test
    public void user_is_moved_to_login_after_menu_logout_clicked() {
        when(menuItemMock.getItemId()).thenReturn(R.id.nav_logout);
        mPresenter.onNavigationItemSelected(menuItemMock);
        verify(mView, times(1)).moveToLogin();
        verify(userSessionMock, times(1)).clear();

    }
}

