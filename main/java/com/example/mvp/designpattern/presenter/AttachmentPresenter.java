package com.example.mvp.designpattern.presenter;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;

import com.example.mvp.designpattern.model.AttachmentModel;
import com.example.mvp.designpattern.model.IAttachmentModel;
import com.example.mvp.designpattern.view.IAttachmentView;

import java.util.ArrayList;
import java.util.HashMap;

public class AttachmentPresenter implements IAttachmentPresenter{

    IAttachmentView view;
    IAttachmentModel model;
    AttachmentHelpher mAttachHelpherClass;
    ArrayList<Uri> uriArrayList;
    int MAX_ALLOWED = 1;

    //*********VIEW:START -> All below APIs called by VIEW************************
    public AttachmentPresenter(IAttachmentView attachView){
        view = attachView;
        model = new AttachmentModel(this);
        mAttachHelpherClass = new AttachmentHelpher(this);
    }

    @Override
    public void processAttachmentIntent(Intent intentData , int reqestCode, int resultCode) {
        //Process the intent then call async task to perform.
        if(model.getAttachListHashMap()!=null && model.getAttachListHashMap().size()>= MAX_ALLOWED){
            view.onFailure();
        } else {
            uriArrayList = mAttachHelpherClass.extractUriIntent(intentData);
            String destPath = view.getAppContext().getFilesDir().getPath() + "/" + System.currentTimeMillis() + ".png";
            mAttachHelpherClass.copyAndUpdate(uriArrayList.get(0).toString(), destPath);
        }
    }

    @Override
    public HashMap<String, Uri> getAttachedHashMap() {
        return model.getAttachListHashMap();
    }
    //*****VIEW END

    public void attachTaskDone(String localCopyOfFile) {
        if(localCopyOfFile != null) {
            model.updateDataOfCopiedFile(localCopyOfFile, uriArrayList.get(0));
            view.showCopiedFilePath(localCopyOfFile);
            view.onSuccess();
        } else {
            view.onFailure();
        }
    }

    public ContentResolver getResolver() {
        return view.getAppContext().getContentResolver();
    }
}

