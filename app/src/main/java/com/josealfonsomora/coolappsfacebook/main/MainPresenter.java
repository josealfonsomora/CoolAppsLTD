package com.josealfonsomora.coolappsfacebook.main;

import android.text.TextUtils;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.github.mikephil.charting.data.PieEntry;
import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.Data;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookBookProfile;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookFilmProfile;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookPictureType;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookItemPage;
import com.josealfonsomora.coolappsfacebook.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainView> {
    private final FacebookClient facebookClient;
    private final UserSession userSession;
    private List<FacebookFilmProfile> films = new ArrayList<>();
    private List<FacebookBookProfile> books = new ArrayList<>();

    public MainPresenter(FacebookClient facebookClient, UserSession userSession) {
        this.facebookClient = facebookClient;
        this.userSession = userSession;
    }

    private void getUserPictureProfile() {
        int px = 300;
        facebookClient.getUserPictureProfile(
                userSession.getFacebookUserId(),
                userSession.getFacebookAccessToken(),
                FacebookPictureType.ALBUM, px, px)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response != null) {
                                    if (response.getData() != null) {
                                        userSession.setPictureUrl(response.getData().getUrl());
                                        getView().setUserPicture(response.getData().getUrl());
                                    }
                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {

                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    private void getUserPublicProfile() {
        facebookClient.getUserPublicProfile(userSession.getFacebookUserId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response != null) {
                                    if (!TextUtils.isEmpty(response.getName())) {
                                        userSession.setUserName(response.getName());
                                        getView().setUserName(response.getName());
                                    }
                                    if (!TextUtils.isEmpty(response.getGender())) {
                                        userSession.setGender(response.getGender());
                                        getView().setUserGender(response.getGender());
                                    }
                                    if (!TextUtils.isEmpty(response.getLink())) {
                                        userSession.setLink(response.getLink());
                                        getView().setUserLink(response.getLink());
                                    }

                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {
                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    public void onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id != R.id.nav_user_profile) {
            if (isViewAttached()) {
                getView().closeNavDrawer();
            }
        }

        if (id == R.id.nav_user_profile) {
            // Handle the camera action
            if (isViewAttached()) {
                getView().openUserProfile();
            }
        } else if (id == R.id.nav_films) {
            getFilms();

        } else if (id == R.id.nav_books) {
            getBooks();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            if (isViewAttached()) {
                userSession.clear();
                LoginManager.getInstance().logOut();
                getView().moveToLogin();
            }
        }
    }

    private void getFilms() {
        facebookClient.getUserFilms(userSession.getFacebookUserId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                            if (response.getData() != null) {
                                if (!response.getData().isEmpty()) {
                                    films = new ArrayList<FacebookFilmProfile>();
                                    for (Data data : response.getData()) {
                                        getFilmProfile(response, data);
                                    }
                                } else {
                                    if (isViewAttached()) {
                                        getView().showNoDataFromFacebookErrorToast("films");
                                    }
                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {
                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    private void getBooks() {
        facebookClient.getUserBooks(userSession.getFacebookUserId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                            if (response.getData() != null) {
                                if (!response.getData().isEmpty()) {
                                    books = new ArrayList<FacebookBookProfile>();
                                    for (Data data : response.getData()) {
                                        getBookProfile(response, data);
                                    }
                                } else {
                                    if (isViewAttached()) {
                                        getView().showNoDataFromFacebookErrorToast("books");
                                    }
                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {
                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    private void getBookProfile(FacebookItemPage response, Data data) {
        facebookClient.getBookProfile(data.getId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(dataResponse -> {
                            if (dataResponse != null) {
                                books.add(dataResponse); // TODO: need to be synchronized
                                if (books.size() == response.getData().size()) {
                                    getView().initChart();
                                    processBooks(books)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(this::onBooksProcessed,
                                                    e -> {
                                                        if (isViewAttached()) {
                                                            getView().showErrorToast(e.getMessage());
                                                        }
                                                    });
                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {
                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    private Observable<ArrayList<PieEntry>> processBooks(List<FacebookBookProfile> books) {
        HashMap<String, Float> genreMap = new HashMap<>();

        for (FacebookBookProfile book : books) {
            if (!TextUtils.isEmpty(book.getWritten_by())) {
                String[] writers = book.getWritten_by().split(",");

                for (String writer : writers) {

                    if (!genreMap.containsKey(writer.trim().toUpperCase())) {
                        genreMap.put(writer.trim().toUpperCase(), 1f);
                    } else {
                        genreMap.put(writer.trim().toUpperCase(), genreMap.get(writer.trim().toUpperCase()) + 1f);
                    }
                }
            }
        }

        return Observable.just(getEntriesListFromHashMap(genreMap));
    }

    private void getFilmProfile(FacebookItemPage response, Data data) {
        facebookClient.getFilmProfile(data.getId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(dataResponse -> {
                            if (dataResponse != null) {
                                films.add(dataResponse); // TODO: need to be synchronized
                                if (films.size() == response.getData().size()) {
                                    getView().initChart();
                                    processFilms(films)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(this::onFilmsProcessed,
                                                    e -> {
                                                        if (isViewAttached()) {
                                                            getView().showErrorToast(e.getMessage());
                                                        }
                                                    });
                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {
                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    private void onFilmsProcessed(ArrayList<PieEntry> pieEntries) {
        if (isViewAttached()) {
            getView().setChartData(pieEntries, "Films");
        }
    }

    private void onBooksProcessed(ArrayList<PieEntry> pieEntries) {
        if (isViewAttached()) {
            getView().setChartData(pieEntries, "Books");
        }
    }

    private Observable<ArrayList<PieEntry>> processFilms(List<FacebookFilmProfile> films) {
        HashMap<String, Float> genreMap = new HashMap<>();

        for (FacebookFilmProfile film : films) {
            if (!TextUtils.isEmpty(film.getGenre())) {
                String[] genres = film.getGenre().split(",");

                for (String genre : genres) {

                    if (!genreMap.containsKey(genre.trim().toUpperCase())) {
                        genreMap.put(genre.trim().toUpperCase(), 1f);
                    } else {
                        genreMap.put(genre.trim().toUpperCase(), genreMap.get(genre.trim().toUpperCase()) + 1f);
                    }
                }
            }
        }

        return Observable.just(getEntriesListFromHashMap(genreMap));
    }

    private ArrayList<PieEntry> getEntriesListFromHashMap(HashMap<String, Float> genreMap) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Iterator it = genreMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            PieEntry pieEntry = new PieEntry((Float) pair.getValue(), (String) pair.getKey());
            entries.add(pieEntry);
            it.remove(); // avoids a ConcurrentModificationException
        }
        return entries;
    }

    public void init() {
        getUserPublicProfile();
        getUserPictureProfile();
    }

}
