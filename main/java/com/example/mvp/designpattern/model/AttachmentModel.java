package com.example.mvp.designpattern.model;

import android.net.Uri;

import com.example.mvp.designpattern.presenter.IAttachmentPresenter;

import java.util.HashMap;

/**
 * This will hold the attach related UI data.
 * This will store and provide the data to presenter which can be used to display on UI.
 */

public class AttachmentModel implements IAttachmentModel{
    HashMap<String, Uri> mAttachImages = new HashMap<>();
    IAttachmentPresenter mAttachmentPresenter;

    /**
     * This is MODEL/DATA constructor
     * @param presenter
     */
    public AttachmentModel(IAttachmentPresenter presenter){
        mAttachmentPresenter = presenter;
    }

    @Override
    public void updateDataOfCopiedFile(String copiedFile, Uri uri) {
        mAttachImages.put(copiedFile,uri);
    }

    @Override
    public HashMap<String, Uri> getAttachListHashMap() {
        return mAttachImages;
    }

}
