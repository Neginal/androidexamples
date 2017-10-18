package com.example.mvp.designpattern.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp.R;
import com.example.mvp.designpattern.presenter.AttachmentPresenter;
import com.example.mvp.designpattern.presenter.IAttachmentPresenter;

public class AttachmentMVP extends AppCompatActivity implements IAttachmentView{

    IAttachmentPresenter mAttachPresenter;
    TextView mFilePaths;
    int REQUEST_CODE = 1;
    Button image1 ;
    Button image2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAttachPresenter.processAttachmentIntent(data, requestCode, resultCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment_mvp);
        mAttachPresenter = new AttachmentPresenter(this);
        mFilePaths = (TextView)this.findViewById(R.id.filepath);

        image1 = (Button) this.findViewById(R.id.image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(mAttachPresenter.getAttachedHashMap().values().iterator().next());
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            }
        });

        Button btn = (Button) this.findViewById(R.id.launchGallery);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent targetIntent = new Intent(Intent.ACTION_GET_CONTENT);
                targetIntent.addCategory(Intent.CATEGORY_OPENABLE);
                targetIntent.setType("image/*");
                startActivityForResult(targetIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    public Context getAppContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showCopiedFilePath(final String path) {
        //*************Note this call is done by THEAD, so if we need to update the status to UI then we have to run on UI THREAD.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mAttachPresenter.getAttachedHashMap().size() == 1){
                    image1.setVisibility(View.VISIBLE);
                }else if(mAttachPresenter.getAttachedHashMap().size() == 2) {
                    image2.setVisibility(View.VISIBLE);
                }
                mFilePaths.setVisibility(View.VISIBLE);
                mFilePaths.setText("Copied File Path:" + path);
            }
        });

    }
    @Override
    public void onFailure() {
        //*************Note this call is done by THEAD, so if we need to update the status to UI then we have to run on UI THREAD.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Failed to copy", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onSuccess() {
        //*************Note this call is done by THEAD, so if we need to update the status to UI then we have to run on UI THREAD.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "File copied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
