package com.ruetgmail.taufiqur.tutorialstemplate.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.ruetgmail.taufiqur.tutorialstemplate.R;

public class AppUtilities {

    private static long backPressed = 0;

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void tapPromptToExit(Activity activity) {
        if (backPressed + 2500 > System.currentTimeMillis()) {
            activity.finish();
        } else {
            showToast(activity.getApplicationContext(), activity.getResources().getString(R.string.tapAgain));
        }
        backPressed = System.currentTimeMillis();
    }

    public static void youtubeLink(Activity activity) {
        updateLink(activity, activity.getString(R.string.youtube_url));
    }

    public static void faceBookLink(Activity activity) {
        try {
            ApplicationInfo applicationInfo = activity.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                updateLink(activity, "fb://facewebmodal/f?href=" + activity.getString(R.string.facebook_url));
                return;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        updateLink(activity, activity.getString(R.string.facebook_url));
    }

    public static void twitterLink(Activity activity) {
        try {
            ApplicationInfo applicationInfo = activity.getPackageManager().getApplicationInfo("com.twitter.android", 0);
            if (applicationInfo.enabled) {
                updateLink(activity, activity.getString(R.string.twitter_user_id));
                return;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        updateLink(activity, activity.getString(R.string.twitter_url));
    }

    public static void googlePlusLink(Activity activity) {
        updateLink(activity, activity.getString(R.string.google_plus_url));
    }

    private static void updateLink(Activity activity, String text) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager packageManager = activity.getPackageManager();
        if (packageManager.resolveActivity(i,
                PackageManager.MATCH_DEFAULT_ONLY) != null) {
            activity.startActivity(i);
        }
    }

    public static void shareApp(Activity activity) {
        try {
            final String appPackageName = activity.getPackageName();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.share_text) + " https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rateThisApp(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moreApps(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse(activity.getString(R.string.developer_page)));
            activity.startActivity(intent);
        } catch (Exception e) {
            intent.setData(Uri.parse(activity.getString(R.string.alternate_dev_page)));
            activity.startActivity(intent);
        }


        activity.startActivity(intent);
    }
}
