package com.example.mvp.designpattern.view;

import android.content.Context;

/**
 * Attachment VIEW interface
 */

public interface IAttachmentView {
    Context getAppContext();
    void showCopiedFilePath(final String path);
    void onFailure();
    void onSuccess();
}
