package com.nst.yourname.miscelleneious;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.nst.yourname.R;
import com.nst.yourname.view.adapter.DirectoriesAdapter;
import com.nst.yourname.view.utility.ListViewMaxHeight;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class DownloadDirectoryChooserDialog {
    static final boolean $assertionsDisabled = false;
    private static PopupWindow changeSortPopUp;
    private DirectoriesAdapter adapter;
    private AlertDialog.Builder dialogBuilder;
    public AlertDialog dirsDialog;
    private ImageView imageView;
    private ListViewMaxHeight listChannels;
    public ChosenDirectoryListener m_chosenDirectoryListener = null;
    public Context m_context;
    public String m_dir = "";
    private boolean m_isNewFolderEnabled = true;
    public String m_sdcardDirectory = "";
    public List<String> m_subdirs = null;
    private List<String> m_subdirsOld = null;
    private TextView m_titleView;
    public Button negativeButton;
    public Button negativeButtonFolderCreate;
    public Button neutralButton;
    public Button positiveButton;
    public Button positiveButtonFolderCreate;
    public HashMap<String, String> savedDirectories = new HashMap<>();

    public interface ChosenDirectoryListener {
        void onChosenDir(String str);
    }

    public DownloadDirectoryChooserDialog(Context context, ChosenDirectoryListener chosenDirectoryListener) {
        this.m_context = context;
        if (Build.VERSION.SDK_INT >= 19) {
            int length = context.getExternalFilesDirs("external").length;
        }
        this.m_sdcardDirectory = "/storage";
        this.m_chosenDirectoryListener = chosenDirectoryListener;
        try {
            this.m_sdcardDirectory = new File(this.m_sdcardDirectory).getCanonicalPath();
        } catch (IOException unused) {
        }
    }

    public void setNewFolderEnabled(boolean z) {
        this.m_isNewFolderEnabled = z;
    }

    public boolean getNewFolderEnabled() {
        return this.m_isNewFolderEnabled;
    }

    public void chooseDirectory() {
        chooseDirectory(this.m_sdcardDirectory);
    }

    public void chooseDirectory(String str) {
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            str = this.m_sdcardDirectory;
        }
        try {
            String canonicalPath = new File(str).getCanonicalPath();
            this.m_dir = canonicalPath;
            this.m_subdirsOld = getDirectories(canonicalPath);
            this.m_subdirs = getDirectories("");
            getSubDirectories(this.m_subdirsOld);
            this.dialogBuilder = createDirectoryChooserDialog(canonicalPath, this.m_subdirs);
            this.dialogBuilder.setNegativeButton(this.m_context.getResources().getString(R.string.set_cancel), (DialogInterface.OnClickListener) null);
            this.dialogBuilder.setNeutralButton(this.m_context.getResources().getString(R.string.set_back), (DialogInterface.OnClickListener) null);
            this.dialogBuilder.setPositiveButton(this.m_context.getResources().getString(R.string.set_ok), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass1 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    if (DownloadDirectoryChooserDialog.this.m_chosenDirectoryListener != null) {
                        DownloadDirectoryChooserDialog.this.m_chosenDirectoryListener.onChosenDir(DownloadDirectoryChooserDialog.this.m_dir);
                    }
                }
            });
            this.adapter = new DirectoriesAdapter(this.m_context, this.m_subdirs);
            if (this.listChannels != null) {
                this.listChannels.setAdapter((ListAdapter) this.adapter);
                this.listChannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass2 */

                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (((String) DownloadDirectoryChooserDialog.this.m_subdirs.get(i)).equals("Internal Storage")) {
                            DownloadDirectoryChooserDialog downloadDirectoryChooserDialog = DownloadDirectoryChooserDialog.this;
                            downloadDirectoryChooserDialog.m_dir = DownloadDirectoryChooserDialog.this.m_dir + "/emulated/0";
                        } else if (DownloadDirectoryChooserDialog.this.m_subdirs.get(i) != null && i == 0 && ((String) DownloadDirectoryChooserDialog.this.m_subdirs.get(i)).equals("..")) {
                            DownloadDirectoryChooserDialog.this.neutralButton.performClick();
                        } else if (DownloadDirectoryChooserDialog.this.savedDirectories == null) {
                            DownloadDirectoryChooserDialog downloadDirectoryChooserDialog2 = DownloadDirectoryChooserDialog.this;
                            String unused2 = downloadDirectoryChooserDialog2.m_dir = DownloadDirectoryChooserDialog.this.m_dir + "/" + ((String) DownloadDirectoryChooserDialog.this.m_subdirs.get(i));
                        } else if (DownloadDirectoryChooserDialog.this.savedDirectories.get(DownloadDirectoryChooserDialog.this.m_subdirs.get(i)) == null || ((String) DownloadDirectoryChooserDialog.this.savedDirectories.get(DownloadDirectoryChooserDialog.this.m_subdirs.get(i))).equals("")) {
                            DownloadDirectoryChooserDialog downloadDirectoryChooserDialog3 = DownloadDirectoryChooserDialog.this;
                            String unused3 = downloadDirectoryChooserDialog3.m_dir = DownloadDirectoryChooserDialog.this.m_dir + "/" + ((String) DownloadDirectoryChooserDialog.this.m_subdirs.get(i));
                        } else {
                            DownloadDirectoryChooserDialog downloadDirectoryChooserDialog4 = DownloadDirectoryChooserDialog.this;
                            String unused4 = downloadDirectoryChooserDialog4.m_dir = DownloadDirectoryChooserDialog.this.m_dir + "/" + ((String) DownloadDirectoryChooserDialog.this.savedDirectories.get(DownloadDirectoryChooserDialog.this.m_subdirs.get(i))) + "/Android/data/" + "com.nst.yourname";
                        }
                        DownloadDirectoryChooserDialog.this.updateDirectory();
                    }
                });
            }
            this.dirsDialog = this.dialogBuilder.create();
            this.dirsDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass3 */

                public void onShow(DialogInterface dialogInterface) {
                    Button unused = DownloadDirectoryChooserDialog.this.negativeButton = DownloadDirectoryChooserDialog.this.dirsDialog.getButton(-2);
                    Button unused2 = DownloadDirectoryChooserDialog.this.positiveButton = DownloadDirectoryChooserDialog.this.dirsDialog.getButton(-1);
                    Button unused3 = DownloadDirectoryChooserDialog.this.neutralButton = DownloadDirectoryChooserDialog.this.dirsDialog.getButton(-3);
                    if (DownloadDirectoryChooserDialog.this.m_dir.equals(DownloadDirectoryChooserDialog.this.m_sdcardDirectory)) {
                        DownloadDirectoryChooserDialog.this.neutralButton.setVisibility(4);
                        DownloadDirectoryChooserDialog.this.positiveButton.setVisibility(8);
                    } else {
                        DownloadDirectoryChooserDialog.this.neutralButton.setVisibility(0);
                        DownloadDirectoryChooserDialog.this.positiveButton.setVisibility(0);
                    }
                    DownloadDirectoryChooserDialog.this.negativeButton.setTag("1");
                    DownloadDirectoryChooserDialog.this.positiveButton.setTag("2");
                    DownloadDirectoryChooserDialog.this.neutralButton.setTag("3");
                    DownloadDirectoryChooserDialog.this.negativeButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(DownloadDirectoryChooserDialog.this.negativeButton));
                    DownloadDirectoryChooserDialog.this.negativeButton.setTextColor(DownloadDirectoryChooserDialog.this.m_context.getResources().getColor(R.color.white));
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) DownloadDirectoryChooserDialog.this.negativeButton.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) DownloadDirectoryChooserDialog.this.positiveButton.getLayoutParams();
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) DownloadDirectoryChooserDialog.this.neutralButton.getLayoutParams();
                    marginLayoutParams.setMargins(0, 0, 50, 0);
                    if ((DownloadDirectoryChooserDialog.this.m_context.getResources().getConfiguration().screenLayout & 15) == 3) {
                        DownloadDirectoryChooserDialog.this.negativeButton.setTextSize(16.0f);
                        DownloadDirectoryChooserDialog.this.negativeButton.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.black_button_dark));
                        marginLayoutParams.width = 230;
                        marginLayoutParams.height = 90;
                    } else {
                        DownloadDirectoryChooserDialog.this.negativeButton.setTextSize(14.0f);
                        DownloadDirectoryChooserDialog.this.negativeButton.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.back_btn_effect));
                        marginLayoutParams.width = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
                        marginLayoutParams.height = 110;
                    }
                    DownloadDirectoryChooserDialog.this.positiveButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(DownloadDirectoryChooserDialog.this.positiveButton));
                    marginLayoutParams2.setMargins(0, 0, 70, 0);
                    if ((DownloadDirectoryChooserDialog.this.m_context.getResources().getConfiguration().screenLayout & 15) == 3) {
                        DownloadDirectoryChooserDialog.this.positiveButton.setTextColor(DownloadDirectoryChooserDialog.this.m_context.getResources().getColor(R.color.white));
                        DownloadDirectoryChooserDialog.this.positiveButton.setTextSize(16.0f);
                        DownloadDirectoryChooserDialog.this.positiveButton.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.black_button_dark));
                        marginLayoutParams2.width = 230;
                        marginLayoutParams2.height = 90;
                    } else {
                        DownloadDirectoryChooserDialog.this.positiveButton.setTextColor(DownloadDirectoryChooserDialog.this.m_context.getResources().getColor(R.color.white));
                        DownloadDirectoryChooserDialog.this.positiveButton.setTextSize(14.0f);
                        DownloadDirectoryChooserDialog.this.positiveButton.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.logout_btn_effect));
                        marginLayoutParams2.width = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
                        marginLayoutParams2.height = 110;
                    }
                    DownloadDirectoryChooserDialog.this.neutralButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(DownloadDirectoryChooserDialog.this.neutralButton));
                    DownloadDirectoryChooserDialog.this.neutralButton.setTextColor(DownloadDirectoryChooserDialog.this.m_context.getResources().getColor(R.color.white));
                    if ((DownloadDirectoryChooserDialog.this.m_context.getResources().getConfiguration().screenLayout & 15) == 3) {
                        DownloadDirectoryChooserDialog.this.neutralButton.setTextSize(16.0f);
                        DownloadDirectoryChooserDialog.this.neutralButton.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.black_button_dark));
                        marginLayoutParams3.width = 230;
                        marginLayoutParams3.height = 90;
                    } else {
                        DownloadDirectoryChooserDialog.this.neutralButton.setTextSize(14.0f);
                        DownloadDirectoryChooserDialog.this.neutralButton.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.logout_btn_effect));
                        marginLayoutParams3.width = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
                        marginLayoutParams3.height = 110;
                    }
                    DownloadDirectoryChooserDialog.this.neutralButton.setOnClickListener(new View.OnClickListener() {
                        /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass3.AnonymousClass1 */

                        public void onClick(View view) {
                            if (!DownloadDirectoryChooserDialog.this.m_dir.equals(DownloadDirectoryChooserDialog.this.m_sdcardDirectory)) {
                                if (DownloadDirectoryChooserDialog.this.m_dir.equals("/storage/emulated/0")) {
                                    DownloadDirectoryChooserDialog.this.m_dir = "/storage/emulated";
                                    String unused2 = DownloadDirectoryChooserDialog.this.m_dir = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                                    DownloadDirectoryChooserDialog.this.updateDirectory();
                                    return;
                                }
                                String unused3 = DownloadDirectoryChooserDialog.this.m_dir = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                                String[] split = DownloadDirectoryChooserDialog.this.m_dir.split("/");
                                if (split.length >= 3) {
                                    if (Pattern.compile("^\\(?(.{4})\\)?[- ]?(.{4})$").matcher(split[2]).matches()) {
                                        String access$100 = DownloadDirectoryChooserDialog.this.m_dir;
                                        if (access$100.equals("/storage/" + split[2] + "/Android/data")) {
                                            DownloadDirectoryChooserDialog downloadDirectoryChooserDialog = DownloadDirectoryChooserDialog.this;
                                            String unused4 = downloadDirectoryChooserDialog.m_dir = "/storage/" + split[2];
                                            String unused5 = DownloadDirectoryChooserDialog.this.m_dir = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                                        }
                                    }
                                    String parent = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                                    if (parent.equals("/storage/" + split[2] + "/Android/data")) {
                                        DownloadDirectoryChooserDialog downloadDirectoryChooserDialog2 = DownloadDirectoryChooserDialog.this;
                                        String unused6 = downloadDirectoryChooserDialog2.m_dir = "/storage/" + split[2] + "/Android/data/" + "com.nst.yourname";
                                    }
                                }
                                DownloadDirectoryChooserDialog.this.updateDirectory();
                            }
                        }
                    });
                }
            });
            this.dirsDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass4 */

                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i != 4 || keyEvent.getAction() != 0) {
                        return false;
                    }
                    if (DownloadDirectoryChooserDialog.this.m_dir.equals(DownloadDirectoryChooserDialog.this.m_sdcardDirectory)) {
                        DownloadDirectoryChooserDialog.this.dirsDialog.dismiss();
                        return true;
                    }
                    if (DownloadDirectoryChooserDialog.this.m_dir.equals("/storage/emulated/0")) {
                        DownloadDirectoryChooserDialog.this.m_dir = "/storage/emulated";
                        String unused2 = DownloadDirectoryChooserDialog.this.m_dir = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                        DownloadDirectoryChooserDialog.this.updateDirectory();
                    } else {
                        String unused3 = DownloadDirectoryChooserDialog.this.m_dir = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                        String[] split = DownloadDirectoryChooserDialog.this.m_dir.split("/");
                        if (split.length >= 3) {
                            if (Pattern.compile("^\\(?(.{4})\\)?[- ]?(.{4})$").matcher(split[2]).matches()) {
                                String access$100 = DownloadDirectoryChooserDialog.this.m_dir;
                                if (access$100.equals("/storage/" + split[2] + "/Android/data")) {
                                    DownloadDirectoryChooserDialog downloadDirectoryChooserDialog = DownloadDirectoryChooserDialog.this;
                                    String unused4 = downloadDirectoryChooserDialog.m_dir = "/storage/" + split[2];
                                    String unused5 = DownloadDirectoryChooserDialog.this.m_dir = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                                }
                            }
                            String parent = new File(DownloadDirectoryChooserDialog.this.m_dir).getParent();
                            if (parent.equals("/storage/" + split[2] + "/Android/data")) {
                                DownloadDirectoryChooserDialog downloadDirectoryChooserDialog2 = DownloadDirectoryChooserDialog.this;
                                String unused6 = downloadDirectoryChooserDialog2.m_dir = "/storage/" + split[2] + "/Android/data/" + "com.nst.yourname";
                            }
                        }
                        DownloadDirectoryChooserDialog.this.updateDirectory();
                    }
                    return true;
                }
            });
            this.dirsDialog.show();
            if (this.dirsDialog.getWindow() != null) {
                this.dirsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(-1));
                this.dirsDialog.getWindow().setLayout(convertDipToPixels(650.0f, this.m_context), -2);
            }
        } catch (IOException unused) {
        }
    }

    private void getSubDirectories(List<String> list) {
        this.m_subdirs.clear();
        boolean z = false;
        for (String str : list) {
            if (!str.equals("self")) {
                if (str.equals("emulated")) {
                    z = true;
                } else if (Pattern.compile("^\\(?(.{4})\\)?[- ]?(.{4})$").matcher(str).matches()) {
                    this.savedDirectories.put("SD Card", str);
                    this.m_subdirs.add("SD Card");
                } else {
                    this.m_subdirs.add(str);
                }
            }
        }
        if (z) {
            this.m_subdirs.add(0, "Internal Storage");
        }
    }

    public static int convertDipToPixels(float f, Context context) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
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
                if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("6")) {
                    if (z) {
                        f = 1.12f;
                    }
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || DownloadDirectoryChooserDialog.this.negativeButton == null)) {
                        DownloadDirectoryChooserDialog.this.negativeButton.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2") || DownloadDirectoryChooserDialog.this.positiveButton == null)) {
                        DownloadDirectoryChooserDialog.this.positiveButton.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3") || DownloadDirectoryChooserDialog.this.neutralButton == null)) {
                        DownloadDirectoryChooserDialog.this.neutralButton.setBackgroundResource(R.drawable.blue_btn_effect);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("4") || DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate == null)) {
                        DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("5") && DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate != null) {
                        DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setBackgroundResource(R.drawable.logout_btn_effect);
                        return;
                    }
                    return;
                }
                if (z) {
                    f = 1.18f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
            } else if (!z) {
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || DownloadDirectoryChooserDialog.this.negativeButton == null)) {
                    DownloadDirectoryChooserDialog.this.negativeButton.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2") || DownloadDirectoryChooserDialog.this.positiveButton == null)) {
                    DownloadDirectoryChooserDialog.this.positiveButton.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3") || DownloadDirectoryChooserDialog.this.neutralButton == null)) {
                    DownloadDirectoryChooserDialog.this.neutralButton.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("4") || DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate == null)) {
                    DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("5") || DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate == null)) {
                    DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setBackgroundResource(R.drawable.black_button_dark);
                }
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
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

    public boolean createSubDir(String str) {
        File file = new File(str);
        Toast.makeText(this.m_context, "No can't Write", 0).show();
        if (!file.exists()) {
            return file.mkdir();
        }
        return false;
    }

    private List<String> getDirectories(String str) {
        ArrayList arrayList = new ArrayList();
        //try {
            File file = new File(str);
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    for (File file2 : listFiles) {
                        if (file2.isDirectory()) {
                            arrayList.add(file2.getName());
                        }
                    }
                    Collections.sort(arrayList, new Comparator<String>() {
                        /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass5 */

                        public int compare(String str, String str2) {
                            return str.compareTo(str2);
                        }
                    });
                    return arrayList;
                }
            }
            return arrayList;
        /*} catch (Exception unused) {
        }*/
    }

    private AlertDialog.Builder createDirectoryChooserDialog(String str, List<String> list) {
        this.dialogBuilder = new AlertDialog.Builder(this.m_context, R.style.AlertDialogCustom);
        LinearLayout linearLayout = new LinearLayout(this.m_context);
        linearLayout.setOrientation(0);
        TextView textView = new TextView(this.m_context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        textView.setText(this.m_context.getResources().getString(R.string.choose_recording_directory));
        Drawable drawable = this.m_context.getResources().getDrawable(R.drawable.cast_genre);
        drawable.setBounds(0, 0, 80, 80);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setTextSize(22.0f);
        textView.setTextColor(this.m_context.getResources().getColor(17170444));
        textView.setPadding(50, 50, 0, 0);
        textView.setTypeface(null, 1);
        this.imageView = new ImageView(this.m_context);
        this.imageView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.imageView.setPadding(20, 20, 0, 0);
        this.imageView.setTag("6");
        this.imageView.setId(R.id.testing1);
        this.imageView.setFocusable(true);
        this.imageView.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.imageView));
        if (Build.VERSION.SDK_INT <= 21) {
            this.imageView.setImageResource(R.drawable.addfolder_icon);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.imageView.setImageDrawable(this.m_context.getResources().getDrawable(R.drawable.addfolder_icon, null));
        }
        ((LinearLayout.LayoutParams) this.imageView.getLayoutParams()).gravity = GravityCompat.END;
        this.imageView.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass6 */

            public void onClick(View view) {
                final EditText editText = new EditText(DownloadDirectoryChooserDialog.this.m_context);
                AlertDialog.Builder builder = new AlertDialog.Builder(DownloadDirectoryChooserDialog.this.m_context, R.style.AlertDialogCustom);
                builder.setTitle(DownloadDirectoryChooserDialog.this.m_context.getResources().getString(R.string.new_folder_name)).setView(editText).setPositiveButton(DownloadDirectoryChooserDialog.this.m_context.getResources().getString(R.string.set_ok), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass6.AnonymousClass1 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        String obj = editText.getText().toString();
                        DownloadDirectoryChooserDialog downloadDirectoryChooserDialog = DownloadDirectoryChooserDialog.this;
                        if (downloadDirectoryChooserDialog.createSubDir(DownloadDirectoryChooserDialog.this.m_dir + "/" + obj)) {
                            DownloadDirectoryChooserDialog downloadDirectoryChooserDialog2 = DownloadDirectoryChooserDialog.this;
                            downloadDirectoryChooserDialog2.m_dir = DownloadDirectoryChooserDialog.this.m_dir + "/" + obj;
                            DownloadDirectoryChooserDialog.this.updateDirectory();
                            return;
                        }
                        Context access$1000 = DownloadDirectoryChooserDialog.this.m_context;
                        Toast.makeText(access$1000, DownloadDirectoryChooserDialog.this.m_context.getResources().getString(R.string.failed_to_create) + "'" + obj + "' " + DownloadDirectoryChooserDialog.this.m_context.getResources().getString(R.string.folder), 0).show();
                    }
                }).setNeutralButton(DownloadDirectoryChooserDialog.this.m_context.getResources().getString(R.string.set_cancel), (DialogInterface.OnClickListener) null);
                final AlertDialog create = builder.create();
                create.setOnShowListener(new DialogInterface.OnShowListener() {
                    /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass6.AnonymousClass2 */

                    public void onShow(DialogInterface dialogInterface) {
                        Button unused = DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate = create.getButton(-3);
                        Button unused2 = DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate = create.getButton(-1);
                        DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setTag("4");
                        DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setTag("5");
                        DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setOnFocusChangeListener(new OnFocusChangeAccountListener(DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate));
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.getLayoutParams();
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.getLayoutParams();
                        marginLayoutParams.setMargins(70, 0, 0, 0);
                        marginLayoutParams2.setMargins(0, 0, 70, 0);
                        if ((DownloadDirectoryChooserDialog.this.m_context.getResources().getConfiguration().screenLayout & 15) == 3) {
                            DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setTextColor(DownloadDirectoryChooserDialog.this.m_context.getResources().getColor(R.color.white));
                            DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setTextSize(16.0f);
                            DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.black_button_dark));
                            marginLayoutParams.width = PsExtractor.VIDEO_STREAM_MASK;
                            marginLayoutParams.height = TsExtractor.TS_STREAM_TYPE_E_AC3;
                        } else {
                            DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setTextColor(DownloadDirectoryChooserDialog.this.m_context.getResources().getColor(R.color.white));
                            DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setTextSize(14.0f);
                            DownloadDirectoryChooserDialog.this.negativeButtonFolderCreate.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.back_btn_effect));
                            marginLayoutParams.width = PsExtractor.VIDEO_STREAM_MASK;
                            marginLayoutParams.height = TsExtractor.TS_STREAM_TYPE_E_AC3;
                        }
                        DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setOnFocusChangeListener(new OnFocusChangeAccountListener(DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate));
                        DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setTextColor(DownloadDirectoryChooserDialog.this.m_context.getResources().getColor(R.color.white));
                        if ((DownloadDirectoryChooserDialog.this.m_context.getResources().getConfiguration().screenLayout & 15) == 3) {
                            DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setTextSize(16.0f);
                            DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.black_button_dark));
                            marginLayoutParams2.width = 230;
                            marginLayoutParams2.height = 90;
                            return;
                        }
                        DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setTextSize(14.0f);
                        DownloadDirectoryChooserDialog.this.positiveButtonFolderCreate.setBackground(DownloadDirectoryChooserDialog.this.m_context.getResources().getDrawable(R.drawable.logout_btn_effect));
                        marginLayoutParams2.width = ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
                        marginLayoutParams2.height = 110;
                    }
                });
                create.show();
                if (create.getWindow() != null) {
                    create.getWindow().setLayout(750, 350);
                    create.getWindow().setBackgroundDrawable(new ColorDrawable(-1));
                }
            }
        });
        linearLayout.addView(textView);
        linearLayout.addView(this.imageView);
        this.dialogBuilder.setCustomTitle(linearLayout);
        this.dialogBuilder.setMessage(this.m_dir);
        View inflate = ((LayoutInflater) this.m_context.getSystemService("layout_inflater")).inflate((int) R.layout.directory_listview, (ViewGroup) null);
        this.dialogBuilder.setView(inflate);
        this.listChannels = (ListViewMaxHeight) inflate.findViewById(R.id.lv_ch);
        this.dialogBuilder.setCancelable(false);
        return this.dialogBuilder;
    }

    public void updateDirectory() {
        if (this.m_dir.equals(this.m_sdcardDirectory)) {
            getSubDirectories(getDirectories(this.m_dir));
        } else {
            this.m_subdirs.clear();
            this.m_subdirs.add("..");
            this.m_subdirs.addAll(getDirectories(this.m_dir));
        }
        if (this.dirsDialog != null) {
            ((TextView) this.dirsDialog.findViewById(16908299)).setText(this.m_dir);
        }
        if (this.m_dir.equals(this.m_sdcardDirectory)) {
            this.neutralButton.setVisibility(4);
            this.positiveButton.setVisibility(8);
        } else {
            this.neutralButton.setVisibility(0);
            this.positiveButton.setVisibility(0);
        }
        this.adapter.notifyDataSetChanged();
        this.listChannels.setSelection(0);
        if (this.listChannels != null) {
            this.listChannels.requestFocus();
        }
    }

    private ArrayAdapter<String> createListAdapter(List<String> list) {
        return new ArrayAdapter<String>(this.m_context, R.layout.select_dialog_item_folder, 16908308, list) {
            /* class com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.AnonymousClass7 */

            public View getView(int i, View view, ViewGroup viewGroup) {
                View view2 = super.getView(i, view, viewGroup);
                if (view2 instanceof TextView) {
                    TextView textView = (TextView) view2;
                    textView.getLayoutParams().height = -2;
                    textView.setEllipsize(null);
                }
                return view2;
            }
        };
    }
}
