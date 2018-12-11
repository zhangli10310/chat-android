package com.tencent.mars.wrapper.service;

import android.content.Context;
import com.tencent.mars.app.AppLogic;
import com.tencent.mars.sdt.SdtLogic;
import com.tencent.mars.stn.StnLogic;
import com.tencent.mars.xlog.Log;
import com.zl.mars.TaskHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * <p></p>
 * <p>
 * Created by zhangli on 2018/12/11 16:56.<br/>
 */
public class MarsServiceStub extends TaskHandler.Stub implements StnLogic.ICallBack, SdtLogic.ICallBack, AppLogic.ICallBack {

    private static final String TAG = "MarsServiceStub";

    private Context context;

    public MarsServiceStub(Context context) {
        this.context = context;
    }

    @Override
    public String getAppFilePath() {
        if (null == context) {
            return null;
        }

        try {
            File file = context.getFilesDir();
            if (!file.exists()) {
                file.createNewFile();
            }
            return file.toString();
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }

        return null;
    }

    @Override
    public AppLogic.AccountInfo getAccountInfo() {
        return new AppLogic.AccountInfo();
    }

    @Override
    public int getClientVersion() {
        return 100;
    }

    @Override
    public AppLogic.DeviceInfo getDeviceType() {
        return new AppLogic.DeviceInfo(android.os.Build.MANUFACTURER + "-" + android.os.Build.MODEL, "android-" + android.os.Build.VERSION.SDK_INT);
    }

    @Override
    public void reportSignalDetectResults(String resultsJson) {

    }

    @Override
    public boolean makesureAuthed() {
        return true;//权限验证直接通过
    }

    @Override
    public String[] onNewDns(String host) {
        return null;
    }

    @Override
    public void onPush(int cmdid, byte[] data) {
        Log.d(TAG, "onPush id:" + cmdid + ",length:" + data.length);
    }

    @Override
    public boolean req2Buf(int taskID, Object userContext, ByteArrayOutputStream reqBuffer, int[] errCode, int channelSelect) {
        return false;
    }

    @Override
    public int buf2Resp(int taskID, Object userContext, byte[] respBuffer, int[] errCode, int channelSelect) {
        return 0;
    }

    @Override
    public int onTaskEnd(int taskID, Object userContext, int errType, int errCode) {
        Log.i(TAG, "onTaskEnd: " + taskID + "," + userContext + "," + errType + "," + errCode);
        return 0;
    }

    @Override
    public void trafficData(int send, int recv) {
        Log.d(TAG, "流量发送:" + send + ",接收:" + recv);
    }

    @Override
    public void reportConnectInfo(int status, int longlinkstatus) {
        Log.i(TAG, "reportConnectInfo: " + status + "," + longlinkstatus);
    }

    @Override
    public int getLongLinkIdentifyCheckBuffer(ByteArrayOutputStream identifyReqBuf, ByteArrayOutputStream hashCodeBuffer, int[] reqRespCmdID) {
        return StnLogic.ECHECK_NEVER;
    }

    @Override
    public boolean onLongLinkIdentifyResp(byte[] buffer, byte[] hashCodeBuffer) {
        return false;
    }

    @Override
    public void requestDoSync() {

    }

    @Override
    public String[] requestNetCheckShortLinkHosts() {
        return new String[0];
    }

    @Override
    public boolean isLogoned() {
        return false;
    }

    @Override
    public void reportTaskProfile(String taskString) {
        Log.d(TAG, "reportTaskProfile: " + taskString);
    }
}
