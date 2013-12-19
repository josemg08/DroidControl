package com.josetomas.client.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.josetomas.client.R;import java.lang.Override;import java.lang.String;

/**
 * Created with IntelliJ IDEA.
 * User: Tomas Najun
 * Date: 13/12/13
 * Time: 05:03
 * To change this template use File | Settings | File Templates.
 */
public class ErrorDialog {

    public AlertDialog.Builder createDialog(final Activity activity, String msg) {
    AlertDialog.Builder exitAlert = new AlertDialog.Builder(activity);
    exitAlert.setIcon(android.R.drawable.ic_dialog_alert);
    exitAlert.setTitle(R.string.titleExitDialog);
    exitAlert.setMessage(msg);
    exitAlert.setPositiveButton(R.string.yesButton, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            activity.finish();
        }

    }).setNegativeButton(R.string.noButton, null);

        return exitAlert;
    }
}

