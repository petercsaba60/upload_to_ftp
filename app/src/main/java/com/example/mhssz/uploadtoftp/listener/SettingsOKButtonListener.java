package com.example.mhssz.uploadtoftp.listener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mhssz.uploadtoftp.R;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class SettingsOKButtonListener implements View.OnClickListener{
    private Dialog settingsDialog;
    private List<File> torrentFiles;

    public SettingsOKButtonListener(Dialog settingsDialog, List<File> torrentFiles) {
        this.torrentFiles = torrentFiles;
        this.settingsDialog = settingsDialog;
    }

    @Override
    public void onClick(View v) {
        FTPClient con = null;

        String ftpAddress =
                ((EditText)settingsDialog.findViewById(R.id.settings_ftp_address)).getText().toString();

        String userName =
                ((EditText)settingsDialog.findViewById(R.id.settings_username)).getText().toString();

        String password =
                ((EditText)settingsDialog.findViewById(R.id.settings_password)).getText().toString();

        TextView info = (TextView)settingsDialog.findViewById(R.id.settings_info);

        StringBuilder sb = new StringBuilder();

        if (ftpAddress.length() == 0) {
            sb.append(" - ");
            sb.append(getStringFromResource(R.string.error_empty_ftp_address));
        }

        if (userName.length() == 0) {
            sb.append("\n - ");
            sb.append(getStringFromResource(R.string.error_empty_username));
        }

        if (password.length() == 0) {
            sb.append("\n - ");
            sb.append(getStringFromResource(R.string.error_empty_password));
        }

        if (sb.toString().length() == 0) {
            try
            {
                con = new FTPClient();
                con.connect(ftpAddress);

                if (con.login(userName, password)) {

                    for(File f : torrentFiles) {
                        con.enterLocalPassiveMode();
                        con.setFileType(FTP.BINARY_FILE_TYPE);

                        FileInputStream in = new FileInputStream(f);

                        info.setVisibility(View.VISIBLE);
                        info.setText(getStringFromResource(R.string.file_upload)
                                + f.getName() + " ...");
                        boolean result = con.storeFile("/" + f.getName(), in);

                        if (!result) {
                            alertDialog(getStringFromResource(R.string.error),
                                    getStringFromResource(R.string.error_upload_file)
                                            + f.getName(),
                                    false);
                        }
                        in.close();
                    }
                    con.logout();
                    con.disconnect();

                    alertDialog(getStringFromResource(R.string.info),
                            getStringFromResource(R.string.success_upload), true);
                }
                else {
                    alertDialog(getStringFromResource(R.string.error),
                            getStringFromResource(R.string.error_upload), true);
                }
            }
            catch (Exception e) {
                alertDialog(getStringFromResource(R.string.error),
                        getStringFromResource(R.string.error_connect_failed), true);
                e.printStackTrace();
            }
        }
        else {
            alertDialog(getStringFromResource(R.string.error), sb.toString(), false);
        }
    }

    private String getStringFromResource(int id) {
        return settingsDialog.getContext().getResources().getString(id);
    }

    private void alertDialog(String title, String message, final boolean closeDialog) {
        ((TextView)settingsDialog.findViewById(R.id.settings_info)).setVisibility(View.INVISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(settingsDialog.getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getStringFromResource(R.string.settings_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (closeDialog) {
                    settingsDialog.dismiss();
                }
            }
        });
        builder.create();
        builder.show();
    }
}
