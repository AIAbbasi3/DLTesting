package com.example.arham.dltesting;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        String url = "https://dps.psx.com.pk/scripts/communicator.php?f=20170829.lis.Z&l=Hd";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Market data");
        request.setTitle("20170829");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "20170829.lis.z");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
    public void displayActivity(View view){
        Intent viewMkt = new Intent(DownloadActivity.this,ReadDisplay.class);

        startActivity(viewMkt);
    }
}
