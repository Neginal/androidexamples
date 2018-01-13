package com.example.viewmodelsample.viewmodel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ActivityDataModelClass extends ViewModel {
    public MutableLiveData<String> userLiveData = new MutableLiveData<>();
    public String mNonLiveDataType = "Default ViewModel Non Live Data Type";

    public ActivityDataModelClass() {
    }
    public void setActivityData(String editedText){
        userLiveData.setValue(editedText);
    }

    public void setNonLiveObjectVlaue(String updatedData){
        mNonLiveDataType = updatedData;
    }

    public String getNonLiveObjectVlaue(){
        return mNonLiveDataType;
    }
}
