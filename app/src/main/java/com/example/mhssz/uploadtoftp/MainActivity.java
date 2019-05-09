package com.example.mhssz.uploadtoftp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.VolumeShaper;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.mhssz.uploadtoftp.adapter.TorrentFilesAdapter;
import com.example.mhssz.uploadtoftp.listener.UploadButtonListener;
import com.example.mhssz.uploadtoftp.tool.LocationHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TORRENT_FILE_EXTEND = "torrent";

    private static String PREF_NAME = "prefLanguage";
    private static String LANGUAGE_HU = "hu";
    private static String LANGUAGE_EN = "en";
    private static String LANGUAGE = "language";

    private List<File> torrentFiles = new ArrayList<>();

    private TorrentFilesAdapter torrentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String language = getLanguage();
        LocationHelper.setLocation(this, language, language.toUpperCase());

        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.upload_button))
                .setOnClickListener(new UploadButtonListener(this, torrentFiles));

        ((Button) findViewById(R.id.refresh_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findTorrentFiles(Environment.getExternalStorageDirectory());
                    }
                });

        ((Button) findViewById(R.id.language_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setLanguage();
                        recreate();
                    }
                });

        ((Button) findViewById(R.id.language_button)).setText(getLanguaButtonText(language));

        findTorrentFiles(Environment.getExternalStorageDirectory());

        this.torrentListAdapter = new TorrentFilesAdapter(torrentFiles);

    }

    private void findTorrentFiles(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    findTorrentFiles(f);
                }
                else {
                    if (isTorrentFile(f)) {
                        torrentFiles.add(f);
                    }
                }
            }
        }
    }

    private boolean isTorrentFile(File f) {
        String fileName = f.getName();
        String[] parts = fileName.split(".");
        if (parts.length >= 2) {
            return parts[1].equals(TORRENT_FILE_EXTEND);
        }

        return false;
    }

    private void setLanguage() {
        String language = getLanguage().equals(LANGUAGE_HU) ? LANGUAGE_EN : LANGUAGE_HU;

        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LANGUAGE, language);

        editor.apply();
    }

    private String getLanguage() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREF_NAME, 0);
        String language = settings.getString(LANGUAGE, null);
        return language == null ? LANGUAGE_HU : language;
    }

    private String getLanguaButtonText(String language) {
        return language.equals(LANGUAGE_HU)
                ? getResources().getString(R.string.language_english) :
                getResources().getString(R.string.language_hungarian);
    }
}
