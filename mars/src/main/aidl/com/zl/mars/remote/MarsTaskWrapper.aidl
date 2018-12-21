// MarsTaskWrapper.aidl
package com.zl.mars.remote;

// Declare any non-default types here with import statements

interface MarsTaskWrapper {

    int getCmdId(); // 长连的 cgi 命令号，用于标识长连请求的 cgi。长连必填项，相当于短连的 cgi。

    int getChannelSelect(); //StnLogic.Task.EShort, StnLogic.Task.ELong, StnLogic.Task.EBoth

    String getHost(); //host

    String getCgiPath();

    boolean sendOnly(); // true 为不需要等待回包，false 为需要等待回包

    boolean needAuthed(); // true 为需要登陆态才能发送的任务，false 为任何状态下都可以发送的任务

    byte[] req2buf();

    int buf2resp(in byte[] buf);

    void onTaskEnd(in int errType, in int errCode);
}
