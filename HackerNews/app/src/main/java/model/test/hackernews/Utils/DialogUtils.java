package model.test.hackernews.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.WindowManager;

import model.test.hackernews.R;


/**
 * Utility class to manage dialogs
 */
public class DialogUtils {


    private static ProgressDialog mProgressDialog;
    private static AlertDialog mAlertDialog;


    /**
     * To show progress messages.
     *
     * @param context Calling context.
     * @param message Progress message.
     */
    public static void showProgressDialog(Context context, String message) {
        try {
            if (mProgressDialog == null || !mProgressDialog.isShowing()) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage(message);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.show();
            } else {
                mProgressDialog.setMessage(message);
            }
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (WindowManager.BadTokenException bte) {
            bte.printStackTrace();
        }
    }

    /**
     * To dismiss progress dialog associated with mProgressDialog instance.
     */
    public static void dismissProgress() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (WindowManager.BadTokenException bte) {
            bte.printStackTrace();
        }
    }


    /**
     * Method which shows popup when there is no network
     *
     * @param context               Calling context.
     * @param finishCurrentActivity boolean variables set to true if we want to finish the current activity
     */

    public static void showNoNetworkDialog(final Context context, final boolean finishCurrentActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.no_network))
                .setMessage(context.getString(R.string.please_check_internet_connection))
                .setPositiveButton(context.getString(R.string.ok).toUpperCase(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (finishCurrentActivity && context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                });
            builder.create().show();
    }
}
