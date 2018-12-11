package com.tencent.mars.wrapper.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.tencent.mars.Mars;
import com.tencent.mars.app.AppLogic;
import com.tencent.mars.sdt.SdtLogic;
import com.tencent.mars.stn.StnLogic;
import com.tencent.mars.xlog.Log;

/**
 * <p></p>
 * <p>
 * Created by zhangli on 2018/12/11 16:14.<br/>
 */
public class MarsService extends Service {

    private static final String TAG = "MarsService";

    private MarsServiceStub stub;
    private static MarsServiceProfileFactory gFactory = new MarsServiceProfileFactory() {
        @Override
        public MarsServiceProfile createMarsServiceProfile() {
            return new DefaultMarsServiceProfile();
        }
    };

    public static void setProfileFactory(MarsServiceProfileFactory factory) {
        gFactory = factory;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final MarsServiceProfile profile = gFactory.createMarsServiceProfile();
        stub = new MarsServiceStub(this);

        // set callback
        AppLogic.setCallBack(stub);
        StnLogic.setCallBack(stub);
        SdtLogic.setCallBack(stub);

        Mars.init(getApplicationContext(), new Handler(Looper.getMainLooper()));

        // Initialize the Mars
        StnLogic.setLonglinkSvrAddr(profile.longLinkHost(), profile.longLinkPorts());
        StnLogic.setShortlinkSvrAddr(profile.shortLinkPort());
        StnLogic.setClientVersion(profile.productID());
        Mars.onCreate(true);

        StnLogic.makesureLongLinkConnected();

        //
        Log.d(TAG, "mars service native created");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "mars service native destroying");

        // Leave Mars
        Mars.onDestroy();
        // ContentDistributionNetwork.onDestroy();

        Log.d(TAG, "mars service native destroyed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
