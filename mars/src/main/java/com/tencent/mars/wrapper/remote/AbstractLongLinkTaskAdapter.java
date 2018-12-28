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
public abstract class AbstractLongLinkTaskAdapter extends MarsTaskWrapper.Stub {

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
    public void onTaskEnd(int errType, int errCode) throws RemoteException {

    }
}
