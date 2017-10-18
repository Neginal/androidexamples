package com.example.mvp.designpattern.presenter;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * This presenter is used to process the intent data and check create attachment object to be updated in Model.
 */
public class AttachmentHelpher {
    AttachmentPresenter mAttachPresenter;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    AttachmentHelpher(AttachmentPresenter attachPresenter) {
        mAttachPresenter = attachPresenter;
    }

    void copyAndUpdate(final String fileToBecopied, final String destDir) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String copiedPath = null;
                if(copyFile(fileToBecopied, destDir)){
                    copiedPath = destDir;
                }
                mAttachPresenter.attachTaskDone(copiedPath);
            }
        }).start();
    }


    private boolean copyFile(String pathToBeCopied, String destPath) {
        boolean isFileCopied  = false;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        AssetFileDescriptor srcFileFD = null;
        try {
            srcFileFD = mAttachPresenter.getResolver().openAssetFileDescriptor(Uri.parse(pathToBeCopied), "r");
            fis = srcFileFD.createInputStream();
            fos = new FileOutputStream(destPath);
            long size = copyLarge(fis, fos);
            if (size > 0) {
                isFileCopied = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFileCopied;
    }

    private long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public ArrayList<Uri> extractUriIntent(Intent intent) {
        ArrayList<Uri> uriList = new ArrayList<Uri>();
        if (intent == null) {
            return uriList;
        }
        if (intent.getData() != null) {
            uriList.add(intent.getData());
        }
        return uriList;
    }
}

