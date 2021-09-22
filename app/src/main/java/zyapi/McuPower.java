package zyapi;

import android.text.TextUtils;
import android.util.Log;

public class McuPower {

    private CommonApi mCommonApi = null;

    public McuPower(){
        mCommonApi = new CommonApi();
    }


    public boolean openDevice(String model){
        if(TextUtils.isEmpty(model)) return false;

        if(model.equalsIgnoreCase("A980")){
            mCommonApi.setGpioOut(53,1);
            Log.d("hello","~~~~~~");
            mCommonApi.setGpioMode(54,0);
            mCommonApi.setGpioDir(54,1);
            mCommonApi.setGpioOut(54,0);
        }


        return false;
    }


    public boolean closeDevice(String model){
        if(TextUtils.isEmpty(model)) return false;

        if(model.equalsIgnoreCase("R018")){
            Log.d("hello","!!!!");
            mCommonApi.setGpioOut(53,0);
            mCommonApi.setGpioOut(54,0);
        }


        return false;
    }
}
