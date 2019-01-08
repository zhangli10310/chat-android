package com.tencent.mars.wrapper.remote;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.tencent.mars.stn.StnLogic;
import com.tencent.mars.xlog.Log;

/**
 * <p></p>
 * <p>
 * Created by zhangli on 2018/12/28 18:18.<br/>
 */
public abstract class LongLinkJsonTaskAdapter extends AbstractLongLinkTaskAdapter {

    private static final String TAG = "LongLinkMarsTaskAdapter";

    protected Gson gson;

    private String jsonReq;

    public LongLinkJsonTaskAdapter() {
        gson = new Gson();

    }

    @Override
    public byte[] req2buf() throws RemoteException {
        try {
            if (jsonReq == null) {
                jsonReq = gson.toJson(request());
            }
            Log.d(TAG, "req2buf: " + jsonReq);
            return jsonReq.getBytes();
        } catch (Exception e) {
            Log.e(TAG, "req2buf: " + e);
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
            Log.e(TAG, "buf2resp: " + e);
        }
        return StnLogic.RESP_FAIL_HANDLE_TASK_END;
    }

    public abstract Object request();

    public abstract void onResponse(@NonNull String json);
}
