package com.schoolapp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String md5PasswordEncryption(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static void showSnackbar(Context context, View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 250);
        toast.show();
    }

    public static boolean isValidateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void startActivityAfterCleanup(Context context, Class<?> cls, int catId, String catName) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("CAT_ID", catId);
        intent.putExtra("CAT_NAME", catName);
        context.startActivity(intent);
    }

    public static String changeDateFormat(String date) {
        SimpleDateFormat input = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date parsedDate = null;
        try {
            parsedDate = input.parse(date);                 // parse input
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(parsedDate);
    }

    public static String changeDateFormats(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date parsedDate = null;
        try {
            parsedDate = input.parse(date);                 // parse input
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(parsedDate);
    }

//    public static void showExitDialog(final Activity activity) {
//        new AlertDialog.Builder(activity)
//                .setTitle(R.string.exit_menu_text)
//                .setMessage(R.string.exit_message)
//
//                .setPositiveButton(R.string.yes_text,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                activity.finish();
//                                dialog.dismiss();
//
//                            }
//                        })
//                .setNegativeButton(R.string.no_text, null)
//                .create();
//    }
}
