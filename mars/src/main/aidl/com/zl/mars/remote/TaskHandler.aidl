// TaskHandler.aidl
package com.zl.mars.remote;

import com.zl.mars.remote.MarsTaskWrapper;

// Declare any non-default types here with import statements

interface TaskHandler {

    int send(MarsTaskWrapper taskWrapper);
}
