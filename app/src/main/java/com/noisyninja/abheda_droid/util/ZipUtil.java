package com.noisyninja.abheda_droid.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by ir2pi on 2/25/2015.
 */

public class ZipUtil {

    private static final int BUFFER = 2048;
    private String[] files;
    private String zipFile;
    private String location;

    private static ZipUtil zipUtil = new ZipUtil( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private ZipUtil(){ }

    /* Static 'instance' method */
    public static ZipUtil getInstance( ) {
        return zipUtil;
    }

    public static String TAG = ZipUtil.class.getCanonicalName();
    public void unzip(String zipFile, String location){
        unzip(zipFile, location, null);
    }
    public void unzip(String zipFile, String location, DownloadFileAsync downloadFileAsync) {
        this.zipFile = zipFile;
        this.location = location;

        Utils.dirChecker(this.location, "");

        try  {
            FileInputStream fin = new FileInputStream(zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;

            while ((ze = zin.getNextEntry()) != null) {
                Log.v(TAG, Utils.getTempString("Decompress..." , ze.getName()));
                setProgressHandle(downloadFileAsync,Utils.getTempStringBuilder("Decompress..." , ze.getName()));


                if(ze.isDirectory()) {
                    Utils.dirChecker(this.location, ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(location + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch(Exception e) {
            Log.e(TAG, Utils.getTempString("Decompress ",e.getMessage()));

        }
        Log.v(TAG, Utils.getTempString("Decompress ","All done!"));
        setProgressHandle(downloadFileAsync,Utils.getTempStringBuilder("Decompress ","All done!"));

    }

    public void zip(String[] files, String zipFile){
        zip(files, zipFile, null);
    }
    public void zip(String[] files, String zipFile, DownloadFileAsync downloadFileAsync) {
        this.files = files;
        this.zipFile = zipFile;
        try  {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFile);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for(int i=0; i < files.length; i++) {
                Log.v(TAG, Utils.getTempString("Compress ","adding...", files[i]));
                setProgressHandle(downloadFileAsync,Utils.getTempStringBuilder("Compress ","adding...", files[i]));
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, Utils.getTempString("Compress ","All done!"));
        setProgressHandle(downloadFileAsync,Utils.getTempStringBuilder("Compress ","All done!"));
    }

    private void setProgressHandle(DownloadFileAsync downloadFileAsync, StringBuilder stringBuilder)
    {
        if(downloadFileAsync != null)
        {
            downloadFileAsync.setProgress(stringBuilder.toString());
        }
    }

}

