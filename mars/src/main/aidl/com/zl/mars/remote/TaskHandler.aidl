// TaskHandler.aidl
package com.zl.mars.remote;

import com.zl.mars.remote.MarsTaskWrapper;
import com.zl.mars.remote.MarsPushMessageFilter;

// Declare any non-default types here with import statements

interface TaskHandler {

    int send(MarsTaskWrapper taskWrapper);

    void setForeground(boolean foreground);

    void registerMarsPushMessageFilter(MarsPushMessageFilter filter);

    void unregisterMarsPushMessageFilter(MarsPushMessageFilter filter);
}
