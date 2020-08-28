package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.common.net.HttpHeaders;
import com.google.gson.internal.LinkedTreeMap;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.callback.SearchTMDBMoviesCallback;
import com.nst.yourname.model.callback.TMDBCastsCallback;
import com.nst.yourname.model.callback.TMDBGenreCallback;
import com.nst.yourname.model.callback.TMDBTrailerCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.TMDBCastsPojo;
import com.nst.yourname.model.pojo.TMDBCrewPojo;
import com.nst.yourname.presenter.SearchMoviesPresenter;
import com.nst.yourname.presenter.VodPresenter;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.interfaces.SearchMoviesInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTimeConstants;

public class ViewDetailsTMDBActivity extends AppCompatActivity implements View.OnClickListener, SearchMoviesInterface {
    static final boolean $assertionsDisabled = false;
    private static String uk;
    private static String una;
    int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    private String categoryId = "";
    public PopupWindow changeSortPopUp;
    private TextView clientNameTv;
    Button closedBT;
    private String containerExtension = "";
    public Context context = this;
    private DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    DateFormat df;
    Date dt;
    SimpleDateFormat fr;
    String fullCast;
    String fullGenre;
    @BindView(R.id.iv_favourite)
    ImageView ivFavourite;
    @BindView(R.id.iv_movie_image)
    ImageView ivMovieImage;
    private LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.ll_cast_box)
    LinearLayout llCastBox;
    @BindView(R.id.ll_cast_box_info)
    LinearLayout llCastBoxInfo;
    @BindView(R.id.ll_detail_left_side)
    LinearLayout llDetailLeftSide;
    @BindView(R.id.ll_detail_ProgressBar)
    LinearLayout llDetailProgressBar;
    @BindView(R.id.ll_detail_right_side)
    LinearLayout llDetailRightSide;
    @BindView(R.id.ll_director_box)
    LinearLayout llDirectorBox;
    @BindView(R.id.ll_director_box_info)
    LinearLayout llDirectorBoxInfo;
    @BindView(R.id.ll_duration_box)
    LinearLayout llDurationBox;
    @BindView(R.id.ll_duration_box_info)
    LinearLayout llDurationBoxInfo;
    @BindView(R.id.ll_genre_box)
    LinearLayout llGenreBox;
    @BindView(R.id.ll_genre_box_info)
    LinearLayout llGenreBoxInfo;
    @BindView(R.id.ll_movie_info_box)
    LinearLayout llMovieInfoBox;
    @BindView(R.id.ll_released_box)
    LinearLayout llReleasedBox;
    @BindView(R.id.ll_released_box_info)
    LinearLayout llReleasedBoxInfo;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.logo)
    ImageView logo;
    MenuItem menuItemSettings;
    Menu menuSelect;
    private String movieIcon = "";
    private String movieName = "";
    private String num = "";
    private ProgressDialog progressDialog;
    @BindView(R.id.rating)
    RatingBar ratingBar;
    @BindView(R.id.rl_account_info)
    RelativeLayout rlAccountInfo;
    @BindView(R.id.rl_transparent)
    RelativeLayout rlTransparent;
    private Boolean rq = true;
    Button savePasswordBT;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private SearchMoviesPresenter searchMoviesPresenter;
    private String selectedPlayer = "";
    private int streamId = -1;
    private String streamType = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    private TypedValue f31tv;
    @BindView(R.id.tv_account_info)
    TextView tvAccountInfo;
    @BindView(R.id.tv_add_to_fav)
    TextView tvAddToFav;
    @BindView(R.id.tv_cast)
    TextView tvCast;
    @BindView(R.id.tv_cast_info)
    TextView tvCastInfo;
    TextView tvCastInfoPopUp;
    @BindView(R.id.tv_director)
    TextView tvDirector;
    @BindView(R.id.tv_director_info)
    TextView tvDirectorInfo;
    TextView tvGenreInfoPopUp;
    @BindView(R.id.tv_movie_duration)
    TextView tvMovieDuration;
    @BindView(R.id.tv_movie_duration_info)
    TextView tvMovieDurationInfo;
    @BindView(R.id.tv_movie_genere)
    TextView tvMovieGenere;
    @BindView(R.id.tv_movie_info)
    TextView tvMovieInfo;
    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.tv_readmore)
    TextView tvReadMore;
    @BindView(R.id.tv_readmore_genre)
    TextView tvReadMoreGenre;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_release_date_info)
    TextView tvReleaseDateInfo;
    @BindView(R.id.tv_watch_trailer)
    TextView tvWatchTrailer;
    @BindView(R.id.tv_genre_info)
    TextView tv_genre_info;
    @BindView(R.id.tv_detail_back_btn)
    TextView tvdetailbackbutton;
    @BindView(R.id.tv_detail_ProgressBar)
    ProgressBar tvdetailprogressbar;
    String ukd;
    String unad;
    private String userName = "";
    private String userPassword = "";
    private String videoURL = "";
    private VodPresenter vodPresenter;
    private String youTubeTrailer = "";

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_view_details_m3u);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.movie_info_bg));
        }
        changeStatusBarColor();
        this.dt = new Date();
        if (this.tvPlay != null) {
            this.tvPlay.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvPlay));
            this.tvPlay.requestFocus();
            this.tvPlay.setFocusable(true);
        }
        if (this.tvAddToFav != null) {
            this.tvAddToFav.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvAddToFav));
        }
        uk = getApplicationName(this.context);
        if (this.tvdetailbackbutton != null) {
            this.tvdetailbackbutton.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvdetailbackbutton));
        }
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        if (this.tvReadMore != null) {
            this.tvReadMore.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvReadMore));
        }
        this.unad = Utils.ukde(MeasureHelper.pnm());
        if (this.tvReadMoreGenre != null) {
            this.tvReadMoreGenre.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvReadMoreGenre));
        }
        una = getApplicationContext().getPackageName();
        if (this.tvWatchTrailer != null) {
            this.tvWatchTrailer.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.tvWatchTrailer));
        }
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        getWindow().setFlags(1024, 1024);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initialize();
    }

    private void showCastPopUp(ViewDetailsTMDBActivity viewDetailsTMDBActivity) {
        View inflate = ((LayoutInflater) viewDetailsTMDBActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_cast_details, (RelativeLayout) viewDetailsTMDBActivity.findViewById(R.id.rl_password_verification));
        this.tvCastInfoPopUp = (TextView) inflate.findViewById(R.id.tv_casts_info_popup);
        this.tvCastInfoPopUp.setText(this.fullCast);
        this.changeSortPopUp = new PopupWindow(viewDetailsTMDBActivity);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass1 */

            public void onClick(View view) {
                ViewDetailsTMDBActivity.this.changeSortPopUp.dismiss();
            }
        });
    }

    private void showGenrePopUp(ViewDetailsTMDBActivity viewDetailsTMDBActivity) {
        View inflate = ((LayoutInflater) viewDetailsTMDBActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_genre_details, (RelativeLayout) viewDetailsTMDBActivity.findViewById(R.id.rl_password_verification));
        this.tvGenreInfoPopUp = (TextView) inflate.findViewById(R.id.tv_genre_info_popup);
        this.tvGenreInfoPopUp.setText(this.fullGenre);
        this.changeSortPopUp = new PopupWindow(viewDetailsTMDBActivity);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass2 */

            public void onClick(View view) {
                ViewDetailsTMDBActivity.this.changeSortPopUp.dismiss();
            }
        });
    }

    private void initialize() {
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.database = new DatabaseHandler(this.context);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = this.loginPreferencesAfterLogin.getString("username", "");
        String string2 = this.loginPreferencesAfterLogin.getString("password", "");
        if (string.isEmpty() || string2.isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        } else {
            startViewingDetails(this.context, string, string2);
        }
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass3 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(ViewDetailsTMDBActivity.this.context);
            }
        });
    }

    private void startViewingDetails(Context context2, String str, String str2) {
        this.searchMoviesPresenter = new SearchMoviesPresenter(this, context2);
        Intent intent = getIntent();
        if (intent != null) {
            try {
                this.streamId = Integer.parseInt(intent.getStringExtra(AppConst.STREAM_ID));
            } catch (NumberFormatException unused) {
                this.streamId = -1;
            }
            this.movieName = intent.getStringExtra(AppConst.EVENT_TYPE_MOVIE);
            this.selectedPlayer = intent.getStringExtra(AppConst.LOGIN_PREF_SELECTED_PLAYER);
            this.movieIcon = intent.getStringExtra("movie_icon");
            this.streamType = intent.getStringExtra("streamType");
            this.containerExtension = intent.getStringExtra("containerExtension");
            this.categoryId = intent.getStringExtra("categoryID");
            this.num = intent.getStringExtra(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM);
            this.videoURL = intent.getStringExtra("videoURL");
            if (this.liveStreamDBHandler.checkFavourite(this.videoURL, SharepreferenceDBHandler.getUserID(context2)).size() > 0) {
                this.tvAddToFav.setText(getResources().getString(R.string.remove_from_fav));
                this.ivFavourite.setVisibility(0);
            } else {
                this.tvAddToFav.setText(getResources().getString(R.string.add_to_fav));
                this.ivFavourite.setVisibility(4);
            }
            if (context2 != null) {
                try {
                    if (this.movieIcon != null && !this.movieIcon.isEmpty()) {
                        Glide.with(getApplicationContext()).load(this.movieIcon).asBitmap().into(new SimpleTarget<Bitmap>() {
                            /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass4 */

                            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                ViewDetailsTMDBActivity.this.ivMovieImage.setBackground(new BitmapDrawable(bitmap));
                            }
                        });
                    }
                } catch (Exception unused2) {
                }
            }
            if (!(this.movieName == null || this.tvMovieName == null)) {
                this.tvMovieName.setText(this.movieName);
            }
            this.searchMoviesPresenter.getMovieInfo(this.movieName);
        }
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(67108864);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(Integer.MIN_VALUE);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 82) {
            return super.onKeyUp(i, keyEvent);
        }
        if (this.menuSelect == null) {
            return true;
        }
        this.menuSelect.performIdentifierAction(R.id.empty, 0);
        return true;
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode == 82) {
            return z ? onKeyDown(keyCode, keyEvent) : onKeyUp(keyCode, keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public String getUserName() {
        if (this.context == null) {
            return this.userName;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("username", "");
    }

    public String getUserPassword() {
        if (this.context == null) {
            return this.userPassword;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("password", "");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_text_icon);
        this.menuSelect = menu;
        this.menuItemSettings = menu.getItem(1).getSubMenu().findItem(R.id.empty);
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(16843499, typedValue, true)) {
            TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
        for (int i = 0; i < this.toolbar.getChildCount(); i++) {
            if (this.toolbar.getChildAt(i) instanceof ActionMenuView) {
                ((Toolbar.LayoutParams) this.toolbar.getChildAt(i).getLayoutParams()).gravity = 16;
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        this.menuItemSettings = menuItem;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            finish();
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        }
        if (itemId == R.id.action_logout && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(ViewDetailsTMDBActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.menu_load_channels_vod) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(ViewDetailsTMDBActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        if (itemId == R.id.menu_load_tv_guide) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder2.setIcon((int) R.drawable.questionmark);
            builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(ViewDetailsTMDBActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass10 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private boolean getEPGUpdateStatus() {
        if (!(this.liveStreamDBHandler == null || this.databaseUpdatedStatusDBModelEPG == null)) {
            this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
            if (this.databaseUpdatedStatusDBModelEPG != null) {
                if (this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH) || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED) || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals("")) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (this.loginPreferencesAfterLogin.getString("username", "").equals("") && this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (this.context != null) {
            onFinish();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
        this.llDetailProgressBar.setVisibility(8);
        this.llDetailRightSide.setVisibility(0);
    }

    @Override // com.nst.yourname.view.interfaces.SearchMoviesInterface
    public void getMovieInfo(SearchTMDBMoviesCallback searchTMDBMoviesCallback) {
        String str;
        if (searchTMDBMoviesCallback == null || searchTMDBMoviesCallback.getTotalResults() == null || !searchTMDBMoviesCallback.getTotalResults().equals(1) || searchTMDBMoviesCallback.getResults() == null || searchTMDBMoviesCallback.getResults().get(0) == null) {
            if (!(this.movieName == null || this.tvMovieName == null)) {
                this.tvMovieName.setText(this.movieName);
            }
            if (this.llDurationBox != null) {
                this.llDurationBox.setVisibility(0);
            }
            if (this.llDurationBoxInfo != null) {
                this.llDurationBoxInfo.setVisibility(0);
            }
            if (this.tvMovieDurationInfo != null) {
                this.tvMovieDurationInfo.setText("N/A");
            }
            if (this.tvCastInfo != null) {
                this.tvCastInfo.setText("N/A");
            }
            if (this.tvDirectorInfo != null) {
                this.tvDirectorInfo.setText("N/A");
            }
            if (this.tvReleaseDateInfo != null) {
                this.tvReleaseDateInfo.setText("N/A");
            }
            if (this.tvReadMoreGenre != null) {
                this.tvReadMoreGenre.setVisibility(8);
            }
            if (this.tv_genre_info != null) {
                this.tv_genre_info.setText("N/A");
            }
            if (this.tvReadMore != null) {
                this.tvReadMore.setVisibility(8);
            }
            this.llDetailProgressBar.setVisibility(8);
            this.llDetailRightSide.setVisibility(0);
            return;
        }
        int intValue = searchTMDBMoviesCallback.getResults().get(0).getId().intValue();
        this.searchMoviesPresenter.getCasts(intValue);
        this.searchMoviesPresenter.getGenre(intValue);
        this.searchMoviesPresenter.getTrailer(intValue);
        String releaseDate = searchTMDBMoviesCallback.getResults().get(0).getReleaseDate();
        Double voteAverage = searchTMDBMoviesCallback.getResults().get(0).getVoteAverage();
        String overview = searchTMDBMoviesCallback.getResults().get(0).getOverview();
        String backdropPath = searchTMDBMoviesCallback.getResults().get(0).getBackdropPath();
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            str = AppConst.TMDB_IMAGE_BASE_URL_LARGE + backdropPath;
        } else {
            str = AppConst.TMDB_IMAGE_BASE_URL_SMALL + backdropPath;
        }
        if (!(this.appbarToolbar == null || backdropPath == null)) {
            Glide.with(getApplicationContext()).load(str).asBitmap().into(new SimpleTarget<Bitmap>() {
                /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass11 */

                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                    ViewDetailsTMDBActivity.this.appbarToolbar.setBackground(new BitmapDrawable(bitmap));
                    ViewDetailsTMDBActivity.this.rlTransparent.setBackgroundColor(ViewDetailsTMDBActivity.this.getResources().getColor(R.color.trasparent_black));
                    ViewDetailsTMDBActivity.this.toolbar.setBackgroundColor(ViewDetailsTMDBActivity.this.getResources().getColor(R.color.trasparent_black));
                }
            });
        }
        if (this.llReleasedBox == null || this.llReleasedBoxInfo == null || this.tvReleaseDateInfo == null || releaseDate == null || releaseDate.isEmpty() || releaseDate.equals("n/A")) {
            if (this.llReleasedBox != null) {
                this.llReleasedBox.setVisibility(0);
            }
            if (this.llReleasedBoxInfo != null) {
                this.llReleasedBoxInfo.setVisibility(0);
            }
            if (this.tvReleaseDateInfo != null) {
                this.tvReleaseDateInfo.setText("N/A");
            }
        } else {
            this.llReleasedBox.setVisibility(0);
            this.llReleasedBoxInfo.setVisibility(0);
            this.tvReleaseDateInfo.setText(releaseDate);
        }
        if (!(this.ratingBar == null || voteAverage == null || voteAverage.equals("n/A"))) {
            this.ratingBar.setVisibility(0);
            try {
                this.ratingBar.setRating(Float.parseFloat(String.valueOf(voteAverage)) / 2.0f);
            } catch (NumberFormatException unused) {
                this.ratingBar.setRating(0.0f);
            }
        }
        if (this.tvMovieInfo != null && overview != null && !overview.isEmpty() && !overview.equals("n/A")) {
            this.tvMovieInfo.setText(overview);
        } else if (this.tvMovieInfo != null) {
            this.tvMovieInfo.setVisibility(8);
        }
    }

    @Override // com.nst.yourname.view.interfaces.SearchMoviesInterface
    public void getCasts(TMDBCastsCallback tMDBCastsCallback) {
        if (tMDBCastsCallback == null || tMDBCastsCallback.getCast() == null || tMDBCastsCallback.getCast().size() <= 0) {
            if (this.llCastBox != null) {
                this.llCastBox.setVisibility(0);
            }
            if (this.llCastBoxInfo != null) {
                this.llCastBoxInfo.setVisibility(0);
            }
            if (this.tvReadMore != null) {
                this.tvReadMore.setVisibility(8);
            }
            if (this.tvCastInfo != null) {
                this.tvCastInfo.setText("N/A");
            }
        } else {
            List<TMDBCastsPojo> cast = tMDBCastsCallback.getCast();
            if (cast != null) {
                String str = "";
                for (int i = 0; i < cast.size(); i++) {
                    str = str + cast.get(i).getName() + " / ";
                }
                String substring = str.substring(0, str.lastIndexOf(47));
                this.fullCast = substring;
                if (this.llCastBox == null || this.llCastBoxInfo == null || this.tvCastInfo == null || substring.isEmpty()) {
                    if (this.llCastBox != null) {
                        this.llCastBox.setVisibility(0);
                    }
                    if (this.llCastBoxInfo != null) {
                        this.llCastBoxInfo.setVisibility(0);
                    }
                    if (this.tvReadMore != null) {
                        this.tvReadMore.setVisibility(8);
                    }
                    if (this.tvCastInfo != null) {
                        this.tvCastInfo.setText("N/A");
                    }
                } else {
                    this.llCastBox.setVisibility(0);
                    this.llCastBoxInfo.setVisibility(0);
                    if (substring.length() > 150) {
                        this.tvCastInfo.setText(substring);
                        this.tvReadMore.setVisibility(0);
                    } else {
                        this.tvCastInfo.setText(substring);
                        this.tvReadMore.setVisibility(8);
                    }
                }
            } else {
                if (this.llCastBox != null) {
                    this.llCastBox.setVisibility(0);
                }
                if (this.llCastBoxInfo != null) {
                    this.llCastBoxInfo.setVisibility(0);
                }
                if (this.tvReadMore != null) {
                    this.tvReadMore.setVisibility(8);
                }
                if (this.tvCastInfo != null) {
                    this.tvCastInfo.setText("N/A");
                }
            }
        }
        if (tMDBCastsCallback == null || tMDBCastsCallback.getCrew() == null || tMDBCastsCallback.getCrew().size() <= 0) {
            if (this.llDirectorBox != null) {
                this.llDirectorBox.setVisibility(0);
            }
            if (this.llDirectorBoxInfo != null) {
                this.llDirectorBoxInfo.setVisibility(0);
            }
            if (this.tvDirectorInfo != null) {
                this.tvDirectorInfo.setText("N/A");
                return;
            }
            return;
        }
        List<TMDBCrewPojo> crew = tMDBCastsCallback.getCrew();
        if (crew != null) {
            String str2 = "";
            int i2 = 0;
            while (true) {
                if (i2 >= crew.size()) {
                    break;
                } else if (crew.get(i2).getJob().equals("Director")) {
                    str2 = crew.get(i2).getName();
                    break;
                } else {
                    i2++;
                }
            }
            if (this.tvDirectorInfo == null || this.llDirectorBoxInfo == null || this.llDirectorBox == null || str2 == null || str2.isEmpty() || str2.equals("n/A")) {
                if (this.llDirectorBox != null) {
                    this.llDirectorBox.setVisibility(0);
                }
                if (this.llDirectorBoxInfo != null) {
                    this.llDirectorBoxInfo.setVisibility(0);
                }
                if (this.tvDirectorInfo != null) {
                    this.tvDirectorInfo.setText("N/A");
                    return;
                }
                return;
            }
            this.llDirectorBox.setVisibility(0);
            this.llDirectorBoxInfo.setVisibility(0);
            this.tvDirectorInfo.setText(str2);
            return;
        }
        if (this.llDirectorBox != null) {
            this.llDirectorBox.setVisibility(0);
        }
        if (this.llDirectorBoxInfo != null) {
            this.llDirectorBoxInfo.setVisibility(0);
        }
        if (this.tvDirectorInfo != null) {
            this.tvDirectorInfo.setText("N/A");
        }
    }

    @Override // com.nst.yourname.view.interfaces.SearchMoviesInterface
    public void getGenre(TMDBGenreCallback tMDBGenreCallback) {
        if (tMDBGenreCallback == null || tMDBGenreCallback.getGenres() == null || tMDBGenreCallback.getGenres().size() <= 0) {
            if (this.llGenreBox != null) {
                this.llGenreBox.setVisibility(0);
            }
            if (this.llGenreBoxInfo != null) {
                this.llGenreBoxInfo.setVisibility(0);
            }
            if (this.tvReadMoreGenre != null) {
                this.tvReadMoreGenre.setVisibility(8);
            }
            if (this.tv_genre_info != null) {
                this.tv_genre_info.setText("N/A");
            }
        } else {
            String str = "";
            for (int i = 0; i < tMDBGenreCallback.getGenres().size(); i++) {
                str = str + ((LinkedTreeMap) tMDBGenreCallback.getGenres().get(i)).get("name").toString() + " / ";
            }
            String substring = str.substring(0, str.lastIndexOf(47));
            this.fullGenre = substring;
            if (this.llGenreBox == null || this.llGenreBoxInfo == null || this.tv_genre_info == null || substring.isEmpty()) {
                if (this.llGenreBox != null) {
                    this.llGenreBox.setVisibility(0);
                }
                if (this.llGenreBoxInfo != null) {
                    this.llGenreBoxInfo.setVisibility(0);
                }
                if (this.tvReadMoreGenre != null) {
                    this.tvReadMoreGenre.setVisibility(8);
                }
                if (this.tv_genre_info != null) {
                    this.tv_genre_info.setText("N/A");
                }
            } else {
                this.llGenreBox.setVisibility(0);
                this.llGenreBoxInfo.setVisibility(0);
                if (substring.length() > 40) {
                    this.tv_genre_info.setText(substring);
                    this.tvReadMoreGenre.setVisibility(0);
                } else {
                    this.tv_genre_info.setText(substring);
                    this.tvReadMoreGenre.setVisibility(8);
                }
            }
        }
        if (!(tMDBGenreCallback == null || tMDBGenreCallback.getRuntime() == null)) {
            try {
                int round = (int) Math.round(((Double) tMDBGenreCallback.getRuntime()).doubleValue() * 60.0d);
                String format = String.format("%02d:%02d:%02d", Integer.valueOf(round / DateTimeConstants.SECONDS_PER_HOUR), Integer.valueOf((round % DateTimeConstants.SECONDS_PER_HOUR) / 60), Integer.valueOf(round % 60));
                if (this.llDurationBox == null || this.llDurationBoxInfo == null || this.tvMovieDurationInfo == null || format == null || format.isEmpty()) {
                    if (this.llDurationBox != null) {
                        this.llDurationBox.setVisibility(0);
                    }
                    if (this.llDurationBoxInfo != null) {
                        this.llDurationBoxInfo.setVisibility(0);
                    }
                    if (this.tvMovieDurationInfo != null) {
                        this.tvMovieDurationInfo.setText("N/A");
                    }
                } else {
                    this.llDurationBox.setVisibility(0);
                    this.llDurationBoxInfo.setVisibility(0);
                    this.tvMovieDurationInfo.setText(format);
                }
            } catch (Exception unused) {
                if (this.llDurationBox != null) {
                    this.llDurationBox.setVisibility(0);
                }
                if (this.llDurationBoxInfo != null) {
                    this.llDurationBoxInfo.setVisibility(0);
                }
                if (this.tvMovieDurationInfo != null) {
                    this.tvMovieDurationInfo.setText("N/A");
                }
            }
        }
        this.llDetailProgressBar.setVisibility(8);
        this.llDetailRightSide.setVisibility(0);
    }

    @Override // com.nst.yourname.view.interfaces.SearchMoviesInterface
    public void getTrailer(TMDBTrailerCallback tMDBTrailerCallback) {
        if (tMDBTrailerCallback != null && tMDBTrailerCallback.getResults() != null && tMDBTrailerCallback.getResults().size() > 0) {
            for (int i = 0; i < tMDBTrailerCallback.getResults().size(); i++) {
                if (tMDBTrailerCallback.getResults().get(i).getType().equals(HttpHeaders.TRAILER)) {
                    this.youTubeTrailer = tMDBTrailerCallback.getResults().get(i).getKey();
                    if (this.tvWatchTrailer != null) {
                        this.tvWatchTrailer.setVisibility(0);
                        return;
                    }
                    return;
                }
            }
        }
    }

    @OnClick({R.id.tv_play, R.id.tv_add_to_fav, R.id.tv_detail_back_btn, R.id.tv_readmore, R.id.tv_readmore_genre, R.id.tv_watch_trailer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_to_fav:
                if (this.liveStreamDBHandler.checkFavourite(this.videoURL, SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                    removeFromFavourite();
                    return;
                } else {
                    addToFavourite();
                    return;
                }
            case R.id.tv_detail_back_btn:
                finish();
                return;
            case R.id.tv_play:
                Context context2 = this.context;
                Context context3 = this.context;
                this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
                String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
                if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                    this.rq = false;
                }
                if (this.rq.booleanValue()) {
                    Utils.playWithPlayerVOD(this.context, string, this.streamId, this.streamType, this.containerExtension, this.num, this.movieName, this.videoURL);
                    return;
                }
                return;
            case R.id.tv_readmore:
                showCastPopUp(this);
                return;
            case R.id.tv_readmore_genre:
                showGenrePopUp(this);
                return;
            case R.id.tv_watch_trailer:
                if (this.youTubeTrailer == null || this.youTubeTrailer.isEmpty()) {
                    passwordConfirmationPopUp(this);
                    return;
                } else {
                    startActivity(new Intent(this, YouTubePlayerActivity.class).putExtra(AppConst.YOUTUBE_TRAILER, this.youTubeTrailer));
                    return;
                }
            default:
                return;
        }
    }

    private void addToFavourite() {
        FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
        favouriteM3UModel.setUrl(this.videoURL);
        favouriteM3UModel.setUserID(SharepreferenceDBHandler.getUserID(this.context));
        favouriteM3UModel.setName(this.movieName);
        favouriteM3UModel.setCategoryID(this.categoryId);
        this.liveStreamDBHandler.addToFavourite(favouriteM3UModel);
        this.tvAddToFav.setText(getResources().getString(R.string.remove_from_favourite));
        this.ivFavourite.setVisibility(0);
    }

    private void removeFromFavourite() {
        this.liveStreamDBHandler.deleteFavourite(this.videoURL, SharepreferenceDBHandler.getUserID(this.context));
        this.tvAddToFav.setText(getResources().getString(R.string.add_to_favourite));
        this.ivFavourite.setVisibility(4);
    }

    private void passwordConfirmationPopUp(ViewDetailsTMDBActivity viewDetailsTMDBActivity) {
        View inflate = ((LayoutInflater) viewDetailsTMDBActivity.getSystemService("layout_inflater")).inflate((int) R.layout.layout_movie_trailer, (RelativeLayout) viewDetailsTMDBActivity.findViewById(R.id.rl_password_verification));
        this.changeSortPopUp = new PopupWindow(viewDetailsTMDBActivity);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());
        this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_save_password);
        this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
        if (this.savePasswordBT != null) {
            this.savePasswordBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.savePasswordBT));
        }
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass12 */

            public void onClick(View view) {
                ViewDetailsTMDBActivity.this.changeSortPopUp.dismiss();
            }
        });
        this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ViewDetailsTMDBActivity.AnonymousClass13 */

            public void onClick(View view) {
                ViewDetailsTMDBActivity.this.changeSortPopUp.dismiss();
            }
        });
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.05f;
                }
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals("1")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag().equals("2")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.blue_btn_effect);
                } else if (this.view.getTag().equals("3")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                } else if (this.view.getTag().equals("5")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                } else if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                    performScaleXAnimation(1.15f);
                    performScaleYAnimation(1.15f);
                } else {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    ViewDetailsTMDBActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                if (this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("3")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("5")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                    ViewDetailsTMDBActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                }
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }
}
