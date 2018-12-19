// MarsTaskWrapper.aidl
package com.zl.mars.remote;

// Declare any non-default types here with import statements

interface MarsTaskWrapper {

    int getCmdId(); // called locally

    int getChannelSelect(); //StnLogic.Task.EShort, StnLogic.Task.ELong, StnLogic.Task.EBoth

    String getHost(); //host

    String getCgiPath();

    byte[] req2buf();

    int buf2resp(in byte[] buf);

    void onTaskEnd(in int errType, in int errCode);
}
