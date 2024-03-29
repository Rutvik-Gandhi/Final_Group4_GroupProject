package com.nihar.final_group4_groupproject.adapter;

import static com.nihar.final_group4_groupproject.api.APIClient.postClient;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nihar.final_group4_groupproject.R;
import com.nihar.final_group4_groupproject.activities.categories.Newsletters;
import com.nihar.final_group4_groupproject.api.APIInterface;
import com.nihar.final_group4_groupproject.model.ListItemNewsletter;
import com.nihar.final_group4_groupproject.operations.Operations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsletterAdapter extends RecyclerView.Adapter<NewsletterAdapter.ViewHolder> {

    private static final String TAG = "NewsletterAdapterTAG";

    private Context context;
    private ArrayList<ListItemNewsletter> newsletterList;
    private Operations operations;
    private File filePath;

    public NewsletterAdapter(Context context, ArrayList<ListItemNewsletter> newsletterList) {
        this.context = context;
        this.newsletterList = newsletterList;
        this.operations = new Operations(context);
        this.filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + operations.finalProj);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_newsletter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItemNewsletter item = newsletterList.get(position);

        holder.tvNewsletterName.setText(item.getFileName());
        holder.tvUploadTIme.setText(item.getUploadTime());
        setDownloadState(holder, item);

        holder.itemView.setOnClickListener(view -> downloadNewsletter(item.getFileName(), item.getType()));
    }

    private void setDownloadState(ViewHolder holder, ListItemNewsletter item) {
        String fileName = item.getFileName() + "." + item.getType();
        File file = new File(filePath, fileName);
        if (file.exists()) {
            holder.imgDownloaded.setVisibility(View.VISIBLE);
            holder.imgDownload.setVisibility(View.GONE);
        } else {
            holder.imgDownloaded.setVisibility(View.GONE);
            holder.imgDownload.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return newsletterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNewsletterName, tvUploadTIme;
        ImageView imgDownload, imgDownloaded;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNewsletterName = itemView.findViewById(R.id.tvNewsletterName);
            tvUploadTIme = itemView.findViewById(R.id.tvUploadTime);
            imgDownload = itemView.findViewById(R.id.imgDownload);
            imgDownloaded = itemView.findViewById(R.id.imgDownloaded);
        }
    }

    private void downloadNewsletter(String name, String type) {
        String fileName = name + "." + type;
        File file = new File(filePath, fileName);
        if (file.exists()) {
            Log.d(TAG, "File already exist");
            openFile(name, type);
            return;
        }

        HttpUrl baseURL = postClient().baseUrl();
        String fileURL = "FileUploader/Uploads/Newsletters/" + fileName;
        Log.d(TAG, baseURL + fileURL);

        APIInterface apiService = postClient().create(APIInterface.class);
        Call<ResponseBody> call = apiService.downloadFileByUrl(fileURL);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    operations.displayToast("Downloaded successfully");
                    Log.d(TAG, "Got the body for the file");
                    saveDownloadedFile(response.body(), name, type);
                } else {
                    operations.displayToast("Download failed");
                    Log.d(TAG, "Download failed" + response.errorBody());
                }
                operations.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", t.toString());
                operations.hideProgressDialog();
            }
        });
    }

    private void requestStoragePermission() {
        Dexter.withContext(context)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.d(Newsletters.TAG, "Permission granted");
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            operations.displayToast("Permanently denied");
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(error -> operations.displayToast("Error occurred"))
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Need Permissions");
        alertDialog.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        alertDialog.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        ((Activity) context).startActivityForResult(intent, Newsletters.REQUEST_CODE_PERMISSION_SETTING);
    }

    private void saveDownloadedFile(ResponseBody body, String name, String type) {
        String fileName = name + "." + type;
        try {
            if (!filePath.exists()) {
                filePath.mkdir();
            }

            File file = new File(filePath, fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                byte[] data = new byte[4096];
                int count;
                long fileSize = body.contentLength();
                Log.d(TAG, "File Size: " + fileSize);

                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();
                Log.d(TAG, file.getParent());

                // file saved to storage
                notifyDataSetChanged();
                openFile(name, type);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to save file: " + e.getMessage());
                return;
            } finally {
                inputStream.close();
                try {
                    outputStream.close();
                } catch (NullPointerException e) {
                    Log.e(TAG, "null error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return;
        }
    }

    private void openFile(String name, String type) {
        String fileName = name + "." + type;

        File file = new File(filePath, fileName);
        Log.d(TAG, file.getAbsolutePath());

        if (file.exists()) {
            Uri path;
            if (Build.VERSION.SDK_INT >= 24) {
                path = FileProvider.getUriForFile(
                        Objects.requireNonNull(context),
                        BuildConfig.APPLICATION_ID + ".provider", file);
            } else {
                path = Uri.fromFile(file);
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (type.equals("pdf")) {
                intent.setDataAndType(path, "application/pdf");
            }
            if (type.equals("jpg") || type.equals("png") || type.equals("jpeg")) {
                intent.setDataAndType(path, "image/*");
            }
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                operations.displayToast("No compatible application found");
            } catch (FileUriExposedException e) {
                operations.displayToast("File URI Exposed");
                Log.e(TAG, e.getMessage());
            }
        } else {
            operations.displayToast("File not exist");
        }
    }
}