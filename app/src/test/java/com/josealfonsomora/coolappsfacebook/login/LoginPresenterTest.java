package com.josealfonsomora.coolappsfacebook.login;

import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.josealfonsomora.coolappsfacebook.UserSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, AccessToken.class})
public class LoginPresenterTest {
    @Mock
    LoginView mView;

    @Mock
    UserSession userSessionMock;
    @Mock
    FacebookException mockFacebookException;
    @Mock
    LoginResult mockLoginResult;
    AccessToken mockAccessToken;
    private LoginPresenter mPresenter;

    @Before
    public void setup() {
        mockStatic(TextUtils.class);
        MockitoAnnotations.initMocks(this);

        when(TextUtils.isEmpty("")).thenReturn(true);

        mPresenter = new LoginPresenter(userSessionMock);
        mockAccessToken = PowerMockito.mock(AccessToken.class);
    }

    @Test
    public void user_is_moved_to_main_if_is_logged_in_when_the_view_is_attached() {
        final String facebookUserID = "facebookUserID";
        when(userSessionMock.getFacebookUserId()).thenReturn(facebookUserID);
        when(TextUtils.isEmpty(facebookUserID)).thenReturn(false);

        mPresenter.attachView(mView);

        verify(mView, times(1)).moveToMain();

    }

    @Test
    public void user_is_NOT_moved_to_main_if_is_NOT_logged_in_when_the_view_is_attached() {
        final String facebookUserID = "";
        when(userSessionMock.getFacebookUserId()).thenReturn(facebookUserID);
        when(TextUtils.isEmpty("")).thenReturn(true);

        mPresenter.attachView(mView);

        verify(mView, never()).moveToMain();

    }

    @Test
    public void message_is_shown_if_a_user_cancels_login_with_Facebook() {
        mPresenter.attachView(mView);
        when(userSessionMock.getFacebookUserId()).thenReturn("");
        mPresenter.onFacebookLoginCallbackCancel();

        verify(mView, times(1)).showMushLoginWithFacebookToContinueError();
    }

    @Test
    public void error_message_is_displayed_if_Facebook_throws_any_error_on_login() {
        String errorMessage = "error message";
        when(userSessionMock.getFacebookUserId()).thenReturn("");
        mPresenter.attachView(mView);
        when(mockFacebookException.getMessage()).thenReturn(errorMessage);
        mPresenter.onFacebookLoginCallbackError(mockFacebookException);

        verify(mView, times(1)).showFacebookError(errorMessage);
    }

    @Test
    public void user_is_moved_to_main_after_successful_login_with_facebook() {
        when(userSessionMock.getFacebookUserId()).thenReturn("");
        mPresenter.attachView(mView);
        String facebookUserID = "facebookUserID";

        when(mockAccessToken.getUserId()).thenReturn(facebookUserID);
        when(mockLoginResult.getAccessToken()).thenReturn(mockAccessToken);
        when(TextUtils.isEmpty(facebookUserID)).thenReturn(false);

        mPresenter.onFacebookCallbackSuccess(mockLoginResult);

        verify(userSessionMock,times(1)).setFacebookUserId(facebookUserID);
        verify(mView, times(1)).moveToMain();
    }

    @Test
    public void error_message_is_shown_if_facebook_credentials_are_empty_after_successful_login() {
        when(userSessionMock.getFacebookUserId()).thenReturn("");
        mPresenter.attachView(mView);
        String facebookUserID = "facebookUserID";

        when(mockAccessToken.getUserId()).thenReturn(facebookUserID);
        when(mockLoginResult.getAccessToken()).thenReturn(null);
        when(TextUtils.isEmpty(facebookUserID)).thenReturn(true);

        mPresenter.onFacebookCallbackSuccess(mockLoginResult);

        verify(mView, never()).moveToMain();

    }
}