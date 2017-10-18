package com.example.mvp.designpattern.presenter;

import android.content.Intent;
import android.net.Uri;

import java.util.HashMap;

/**
 * Attachment Presenter interface
 */
public interface IAttachmentPresenter {
    //Below API called by UI
    void processAttachmentIntent(Intent intentData , int reqestCode, int resultCode);
    HashMap<String, Uri> getAttachedHashMap();
}
