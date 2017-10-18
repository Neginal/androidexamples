package com.example.mvp.designpattern.model;

import android.net.Uri;

import java.util.HashMap;

/**
 * Attachment MODEL interface
 */

public interface IAttachmentModel {
    HashMap<String, Uri> getAttachListHashMap();
    void updateDataOfCopiedFile(String copiedFile, Uri uri);
}
