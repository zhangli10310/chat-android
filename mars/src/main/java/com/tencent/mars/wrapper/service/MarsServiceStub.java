package com.tencent.mars.wrapper.service;

import android.content.Context;
import android.os.RemoteException;
import com.tencent.mars.BaseEvent;
import com.tencent.mars.app.AppLogic;
import com.tencent.mars.sdt.SdtLogic;
import com.tencent.mars.stn.StnLogic;
import com.tencent.mars.xlog.Log;
import com.zl.mars.remote.MarsPushMessageFilter;
import com.zl.mars.remote.MarsTaskWrapper;
import com.zl.mars.remote.TaskHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p></p>
 * <p>
 * Created by zhangli on 2018/12/11 16:56.<br/>
 */
public class MarsServiceStub extends TaskHandler.Stub implements StnLogic.ICallBack, SdtLogic.ICallBack, AppLogic.ICallBack {

    private static final String TAG = "MarsServiceStub";

    private Context context;

    private static Map<Integer, MarsTaskWrapper> TASK_ID_TO_WRAPPER = new ConcurrentHashMap<>();

    private ConcurrentLinkedQueue<MarsPushMessageFilter> filters = new ConcurrentLinkedQueue<>();

    public MarsServiceStub(Context context) {
        this.context = context;
    }


    @Override
    public int send(MarsTaskWrapper taskWrapper) throws RemoteException {

        StnLogic.Task task = new StnLogic.Task(StnLogic.Task.EShort, 0, "", null);

        task.cmdID = taskWrapper.getCmdId();
        task.channelSelect = taskWrapper.getChannelSelect();

        task.shortLinkHostList = new ArrayList<>();
        task.shortLinkHostList.add(taskWrapper.getHost());
        task.cgi = taskWrapper.getCgiPath();

        task.sendOnly = taskWrapper.sendOnly();
        task.needAuthed = taskWrapper.needAuthed();

        TASK_ID_TO_WRAPPER.put(task.taskID, taskWrapper);

        StnLogic.startTask(task);

        if (StnLogic.hasTask(task.taskID)) {
            Log.i(TAG, "stn task started with id %d", task.taskID);

        } else {
            Log.e(TAG, "stn task start failed with id %d", task.taskID);
        }

        return task.taskID;
    }

    @Override
    public void setForeground(boolean foreground) throws RemoteException {
        BaseEvent.onForeground(foreground);
    }

    @Override
    public void registerMarsPushMessageFilter(MarsPushMessageFilter filter) throws RemoteException {
        filters.remove(filter);
        filters.add(filter);
    }

    @Override
    public void unregisterMarsPushMessageFilter(MarsPushMessageFilter filter) throws RemoteException {
        filters.remove(filter);
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
        Log.d(TAG, "reportSignalDetectResults: " + resultsJson);
    }

    @Override
    public boolean makesureAuthed() {
        Log.d(TAG, "makesureAuthed: ");
        return true;//权限验证直接通过
    }

    @Override
    public String[] onNewDns(String host) {
        Log.d(TAG, "onNewDns: ");
        return null;
    }

    @Override
    public void onPush(int cmdid, byte[] data) {
        Log.d(TAG, "onPush id:" + cmdid + ",data:" + new String(data));

        for (MarsPushMessageFilter filter : filters) {
            try {
                filter.onRecv(cmdid, data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean req2Buf(int taskID, Object userContext, ByteArrayOutputStream reqBuffer, int[] errCode, int channelSelect) {
        Log.i(TAG, "req2Buf: taskId" + taskID);
        MarsTaskWrapper wrapper = TASK_ID_TO_WRAPPER.get(taskID);

        if (wrapper == null) {
            Log.e(TAG, "invalid req2Buf for task, taskID=%d", taskID);
            return false;
        }
        try {
            reqBuffer.write(wrapper.req2buf());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int buf2Resp(int taskID, Object userContext, byte[] respBuffer, int[] errCode, int channelSelect) {
        MarsTaskWrapper wrapper = TASK_ID_TO_WRAPPER.get(taskID);

        if (wrapper == null) {
            Log.e(TAG, "buf2Resp: wrapper not found for stn task, taskID=%", taskID);
            return StnLogic.RESP_FAIL_HANDLE_TASK_END;
        }

        try {
            return wrapper.buf2resp(respBuffer);
        } catch (Exception e) {
            Log.e(TAG, "remote wrapper disconnected, clean this context, taskID=%d", taskID);
            TASK_ID_TO_WRAPPER.remove(taskID);
        }
        return StnLogic.RESP_FAIL_HANDLE_TASK_END;
    }

    @Override
    public int onTaskEnd(int taskID, Object userContext, int errType, int errCode) {
        final MarsTaskWrapper wrapper = TASK_ID_TO_WRAPPER.remove(taskID);
        if (wrapper == null) {
            Log.w(TAG, "stn task onTaskEnd callback may fail, null wrapper, taskID=%d", taskID);
            return 0; // TODO: ???
        }

        try {
            wrapper.onTaskEnd(errType, errCode);

        } catch (RemoteException e) {
            e.printStackTrace();

        }

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

//        try {
//            identifyReqBuf.write("check".getBytes());
//            hashCodeBuffer.write("check".hashCode());
//            reqRespCmdID[0] = 1;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return StnLogic.ECHECK_NEVER;
    }

    @Override
    public boolean onLongLinkIdentifyResp(byte[] buffer, byte[] hashCodeBuffer) {
        Log.d(TAG, "onLongLinkIdentifyResp: " + new String(buffer));
        return false;
    }

    @Override
    public void requestDoSync() {
        Log.d(TAG, "requestDoSync: ");
    }

    @Override
    public String[] requestNetCheckShortLinkHosts() {
        Log.d(TAG, "requestNetCheckShortLinkHosts: ");
        return new String[0];
    }

    @Override
    public boolean isLogoned() {
        Log.d(TAG, "isLogoned: ");
        return false;
    }

    @Override
    public void reportTaskProfile(String taskString) {
        Log.d(TAG, "reportTaskProfile: " + taskString);
    }

//    @Override
    public void cancel(int taskID) throws RemoteException {
        Log.d(TAG, "cancel wrapper with taskID=%d using stn stop", taskID);
        StnLogic.stopTask(taskID);
        TASK_ID_TO_WRAPPER.remove(taskID); // TODO: check return
    }
}
