package com.nihar.final_group4_groupproject.operations;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.nihar.final_group4_groupproject.R;

public class Operations {
    private Context context;
    public final String finalProj = "/finalProj/";

    public Operations(Context context) {
        this.context = context;
    }

    public String getValue(TextInputLayout value) {
        return value.getEditText() != null ? value.getEditText().getText().toString().trim() : "";
    }

    public String getValueET(EditText value) {
        return value.getText().toString().trim();
    }

    public boolean passwordMatch(TextInputLayout pass, TextInputLayout confirmPass) {
        //return TRUE if match
        if (!getValue(pass).equals(getValue(confirmPass))) {
            confirmPass.setError(context.getString(R.string.password_not_match));
            return false;
        } else {
            confirmPass.setError(null);
            return true;
        }
    }

    public boolean checkNullOrEmpty(TextInputLayout textInputLayout) {
        String value = getValue(textInputLayout);
        if (value.isEmpty()) {
            textInputLayout.setError(context.getString(R.string.required_field));
            return true;
        } else {
            textInputLayout.setError(null);
            return false;
        }
    }

    public boolean checkNullOrEmptyET(EditText editText) {
        String value = getValueET(editText);
        if (value.isEmpty()) {
            editText.setError(context.getString(R.string.required_field));
            return true;
        } else {
            editText.setError(null);
            return false;
        }
    }

    public void setText(TextInputLayout textInputLayout, String value) {
        textInputLayout.getEditText().setText(value);
    }

    public void performCall(String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        context.startActivity(callIntent);
    }

    public void sendSMS(String mobile) {
        Uri uri = Uri.parse("smsto:" + mobile);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);

        try {
            context.startActivity(smsIntent);
        } catch (ActivityNotFoundException ex) {
            displayToast("Can't resolve app for ACTION_SENDTO Intent");
        }
    }

    public void sendEmail(String emailId) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + emailId));

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail using"));
        } catch (ActivityNotFoundException ex) {
            displayToast("There are no email clients installed");
        }
    }

    public void sendToWhatsapp(String mobile) {
        Uri uri = Uri.parse("smsto:" + mobile);
        Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
        whatsappIntent.setPackage("com.whatsapp");

        try {
            context.startActivity(whatsappIntent);
        } catch (ActivityNotFoundException ex) {
            displayToast("Whatsapp not installed");
        }
    }

    public void mobileChooser(String actionType, String mobilePrimary, String mobileSecondary) {
        if (mobileSecondary != null && !mobileSecondary.isEmpty()) {
            Chooser dialog = new Chooser(context, actionType, mobilePrimary, mobileSecondary);
            dialog.setCancelable(true);
            dialog.show();

            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            if (actionType.equals(context.getString(R.string.type_call))) {
                performCall(mobilePrimary);
            }
            if (actionType.equals(context.getString(R.string.type_sms))) {
                sendSMS(mobilePrimary);
            }
            if (actionType.equals(context.getString(R.string.type_whatsapp))) {
                sendToWhatsapp(mobilePrimary);
            }
        }
    }

    public boolean internetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private final ProgressDialog progressDialog = new ProgressDialog(context);

    public void showProgressDialog() {
        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public void displayToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void displaySnackBar(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }


}