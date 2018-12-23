package com.tencent.mars.wrapper.remote;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.tencent.mars.stn.StnLogic;
import com.tencent.mars.xlog.Log;
import com.zl.mars.remote.MarsTaskWrapper;

/**
 * <p></p>
 * <p>
 * Created by zhangli on 2018/12/21 10:14.<br/>
 */
public abstract class LongLinkJsonTaskAdapter extends MarsTaskWrapper.Stub {

    private static final String TAG = "LongLinkMarsTaskAdapter";

    private Gson gson;

    public LongLinkJsonTaskAdapter() {
        gson = new Gson();

    }

    @Override
    public int getChannelSelect() throws RemoteException {
        return StnLogic.Task.ELong;
    }

    @Override
    public String getCgiPath() throws RemoteException {
        return null;
    }

    @Override
    public String getHost() throws RemoteException {
        return null;
    }

    @Override
    public boolean sendOnly() throws RemoteException {
        return false;
    }

    @Override
    public boolean needAuthed() throws RemoteException {
        return true;
    }

    @Override
    public byte[] req2buf() throws RemoteException {
        try {
            String json = gson.toJson(request());
            Log.d(TAG, "req2buf: " + json);
            return json.getBytes();
        } catch (Exception e) {

        }
        return new byte[0];
    }

    @Override
    public int buf2resp(byte[] buf) throws RemoteException {
        try {
            String resp = new String(buf);
            Log.d(TAG, "buf2resp: " + resp);
            onResponse(resp);
            return StnLogic.RESP_FAIL_HANDLE_NORMAL;
        } catch (Exception e) {

        }
        return StnLogic.RESP_FAIL_HANDLE_TASK_END;
    }

    @Override
    public void onTaskEnd(int errType, int errCode) throws RemoteException {

    }

    @Nullable
    protected  <S> S toResponse(String json, Class<S> sClass) {
        try {
            gson.fromJson(json, sClass);
        } catch (Exception e) {

        }
        return null;
    }

    public abstract Object request();

    public abstract void onResponse(@NonNull String json);
}
