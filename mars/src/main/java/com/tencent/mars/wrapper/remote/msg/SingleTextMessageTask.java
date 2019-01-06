package com.tencent.mars.wrapper.remote.msg;

import android.os.RemoteException;
import androidx.annotation.NonNull;
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

        if (Constant.SUCCESS.equals(json)){
            onSendSuccess(getId());
        } else {
            onSendFail(getId());
        }
    }

    @Override
    public void onTaskEnd(int errType, int errCode) throws RemoteException {
        super.onTaskEnd(errType, errCode);
        onSendFail(getId());
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


    public abstract void onSendSuccess(String id);
    public abstract void onSendFail(String id);

}


