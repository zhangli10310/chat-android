package com.zl.mars.remote;

interface MarsPushMessageFilter {

    // returns processed ?
    boolean onRecv(int cmdId, inout byte[] buffer);

}