package com.tencent.mars.wrapper.remote.msg;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.tencent.mars.stn.StnLogic;
import com.tencent.mars.wrapper.Constant;
import com.tencent.mars.wrapper.remote.LongLinkJsonTaskAdapter;

import java.util.UUID;

/**
 * Created by zhangli on 2019/1/6,21:21<br/>
 */
public abstract class SingleTextMessageTask extends LongLinkJsonTaskAdapter {

    private String id;

    @Override
    public void onResponse(@NonNull String json) {

    }

    @Override
    public int getCmdId() throws RemoteException {
        return Constant.CID_SEND_SINGLE_TEXT_MSG;
    }

    protected String getId(){
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    @Override
    public int buf2resp(byte[] buf) throws RemoteException {
        if (buf == null) {
            onResponse("");
            return StnLogic.RESP_FAIL_HANDLE_NORMAL;
        }
        return super.buf2resp(buf);
    }

}


