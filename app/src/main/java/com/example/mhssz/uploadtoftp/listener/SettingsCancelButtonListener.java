package com.example.mhssz.uploadtoftp.listener;

import android.app.Dialog;
import android.view.View;

public class SettingsCancelButtonListener implements View.OnClickListener {
    private Dialog settingsDialog;

    public SettingsCancelButtonListener(Dialog settingsDialog) {
        this.settingsDialog = settingsDialog;
    }

    @Override
    public void onClick(View v) {
        this.settingsDialog.dismiss();
    }
}
