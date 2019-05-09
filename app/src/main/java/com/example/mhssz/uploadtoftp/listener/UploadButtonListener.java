package com.example.mhssz.uploadtoftp.listener;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mhssz.uploadtoftp.R;

import java.io.File;
import java.util.List;

public class UploadButtonListener implements View.OnClickListener {
    private List<File> torrentFiles;
    private Context context;

    public  UploadButtonListener(Context context, List<File> torrentFiles) {
        this.torrentFiles = torrentFiles;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        final Dialog settingsDialog = new Dialog(context);
        settingsDialog.setContentView(R.layout.settings_dialog);

        settingsDialog.show();

        Button okButton = (Button) settingsDialog.findViewById(R.id.settings_ok_button);
        okButton.setOnClickListener(new SettingsOKButtonListener(settingsDialog, torrentFiles));

        Button cancelButton = (Button) settingsDialog.findViewById(R.id.settings_cancel_button);
        cancelButton.setOnClickListener(new SettingsCancelButtonListener(settingsDialog));

        ((TextView)settingsDialog.findViewById(R.id.settings_info)).setVisibility(View.INVISIBLE);
    }
}
