package com.noisyninja.abheda_droid.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.noisyninja.abheda_droid.util.Constants.PROGRESS_STYLE;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
/**
 * Created by ir2pi on 1/10/2015.
 */
public class DownloadFileAsync extends AsyncTask<String, String, String> {
    private static String TAG = DownloadFileAsync.class.getCanonicalName();
    IDownloadFileAsyncCallback iDownloadFileAsyncCallback;
    Context context;

    private DownloadFileAsync()
    {

    }

    public DownloadFileAsync(IDownloadFileAsyncCallback iDownloadFileAsyncCallback, Context context)
    {
        this.iDownloadFileAsyncCallback = iDownloadFileAsyncCallback;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(iDownloadFileAsyncCallback == null)
            return;
        iDownloadFileAsyncCallback.init();
        Utils.showProgress(context, PROGRESS_STYLE.DETERMINATE);
    }

    @Override
    protected String doInBackground(String... aurl) {

        if(iDownloadFileAsyncCallback == null || !Utils.isNetworkAvailable(context)) {
            return null;
        }
        iDownloadFileAsyncCallback.start();
        int count;

        try {

            URL url = new URL(aurl[0]);
            String path = aurl[1];

            URLConnection conexion = url.openConnection();
            conexion.connect();

            int lenghtOfFile = conexion.getContentLength();
            Log.d(TAG, "Lenght of file: " + lenghtOfFile);

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(path);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                setProgress(Constants.DOWNLOAD_TEXT, String.valueOf((int) ((total * 100) / lenghtOfFile)));
                output.write(data, 0, count);
            }

            if(aurl.length>2)
            {//is zip and has unpack directory
                ZipUtil.getInstance().unzip(aurl[1], aurl[2], this);
            }
            output.flush();
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
           // Utils.handleError(context, e);
        }
        setProgress(Constants.INFO_SUCCESS_DOWNLOAD);
        return "";

    }
    protected void onProgressUpdate(String... progress) {
        Log.d(TAG, progress[0]);
        Log.i(DownloadFileAsync.class.getSimpleName(),progress[0]);
        if(progress.length>1)
            Utils.updateProgressDeterminate(progress[0], Integer.valueOf(progress[1]));
        else
            Utils.updateProgressDeterminate(progress[0], 100);
    }

    @Override
    protected void onPostExecute(String unused) {

        if(iDownloadFileAsyncCallback == null || unused == null )
        {
            Utils.handleError(context, Constants.ERROR_NO_NETWORK);
            Utils.hideProgress();
            return;
        }
        iDownloadFileAsyncCallback.end();
        Utils.hideProgress();
    }

    public void setProgress(String... arg){
        if(arg!=null)
            publishProgress(arg);
    }

}
