package com.example.viewmodelsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.viewmodelsample.datamodel.ActivityDataClass;
import com.example.viewmodelsample.viewmodel.ActivityDataModelClass;

public class ViewModelSample extends AppCompatActivity {
    EditText mEditText;
    TextView mNormalData;
    TextView mLiveData;
    TextView mNonLiveDataViewModelData;

    ActivityDataModelClass mViewModel;
    ActivityDataClass mNonViewModelData;

    String TAG ="ViewModel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        setContentView(R.layout.activity_view_model_sample);
        mEditText = this.findViewById((R.id.editText));
        mNormalData = this.findViewById(R.id.nonliveTextViewContent);
        mLiveData = this.findViewById(R.id.liveTextViewContent);
        mNonLiveDataViewModelData = this.findViewById(R.id.nonliveDataTypeTextViewContent);

        //Step1: Create viewModel object which holds the activityData.
        mViewModel = ViewModelProviders.of(this).get(ActivityDataModelClass.class);
        mNonViewModelData = new ActivityDataClass();
        Button update = this.findViewById(R.id.updateData);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onButtonClick");
                //Step2: Store the data, update the data we want to store in viewModel so that destroy of this activity should not loose the data.
                String editTextData = mEditText.getText().toString();
                mViewModel.setActivityData(editTextData);
                mViewModel.setNonLiveObjectVlaue(editTextData);
                mNonViewModelData.setActivityVlaue(editTextData);

                //Both normal and live data text views are updated with user entered data.
                mLiveData.setText(editTextData);
                mNormalData.setText(editTextData);
                mNonLiveDataViewModelData.setText(editTextData);

            }
        });

        //Step3: Restore data, Once the activity is recreated , it will get the viewModel object which has the data.
       mViewModel.userLiveData.observe(this, new Observer() {
            //When we rotate the device this method is called.
            @Override
            public void onChanged(@Nullable Object o) {
                Log.d(TAG,"onChanged");
                mLiveData.setText(o.toString());
                mNonLiveDataViewModelData.setText(mViewModel.getNonLiveObjectVlaue());
                mNormalData.setText(mNonViewModelData.getmActivityDataVlaue());
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG,"onConfigurationChanged");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}
