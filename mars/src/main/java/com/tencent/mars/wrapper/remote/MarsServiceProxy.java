package com.tencent.mars.wrapper.remote;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import com.tencent.mars.wrapper.service.CoreService;
import com.tencent.mars.xlog.Log;
import com.zl.mars.remote.MarsPushMessageFilter;
import com.zl.mars.remote.MarsTaskWrapper;
import com.zl.mars.remote.TaskHandler;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p></p>
 * <p>
 * Created by zhangli on 2018/12/12 12:16.<br/>
 */
public class MarsServiceProxy implements ServiceConnection {

    private static final String TAG = "MarsServiceProxy";

    public static MarsServiceProxy inst;

    private Handler mHandler;
    private TaskHandler taskHandler;

    private String accountId;
    private ConcurrentLinkedQueue<PushMessageHandler> pushMessageHandlerQueue = new ConcurrentLinkedQueue<>();
    private MarsPushMessageFilter filter = new MarsPushMessageFilter.Stub() {

        @Override
        public boolean onRecv(int cmdId, byte[] buffer) throws RemoteException {

            for (PushMessageHandler messageHandler : pushMessageHandlerQueue) {
                PushMessage message = new PushMessage(cmdId, buffer);
                messageHandler.process(message);
            }

            return false;
        }
    };

    private Context context;

    public static void init(Context context) {
        if (inst != null) {
            return;
        }
        inst = new MarsServiceProxy(context);
    }

    private MarsServiceProxy(Context context) {
        this.context = context.getApplicationContext();
        HandlerThread thread = new HandlerThread("handleTaskThread");
        thread.start();
        mHandler = new Handler(thread.getLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startService();
            }
        }, 50);
    }

    private void handleTask(MarsTaskWrapper taskWrapper) {
        if (startService()) {
            try {
                taskHandler.send(taskWrapper);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean startService() {
        if (taskHandler == null) {
            Log.d(TAG, "try to bind remote mars service");
            Intent i = new Intent(context, CoreService.class);
            context.startService(i);
            if (!context.bindService(i, inst, Service.BIND_AUTO_CREATE)) {
                Log.e(TAG, "remote mars service bind failed");
                return false;
            }
            if (taskHandler == null) {
                Log.e(TAG, "startService: fail");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "remote mars service connected");
        try {
            taskHandler = TaskHandler.Stub.asInterface(service);
            taskHandler.setAccountId(accountId);
            taskHandler.registerMarsPushMessageFilter(filter);
            taskHandler.setForeground(true);
        } catch (Exception e) {
            e.printStackTrace();
            taskHandler = null;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        try {
            taskHandler.unregisterMarsPushMessageFilter(filter);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        taskHandler = null;

        // TODO: need reconnect ?
        Log.d(TAG, "remote mars service disconnected");
    }

    public void send(final MarsTaskWrapper taskWrapper) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handleTask(taskWrapper);
            }
        });
    }

    public void setForeground(boolean foreground) {
        try {
            taskHandler.setForeground(foreground);
        } catch (Exception e) {
            Log.e(TAG, "setForeground: " + e);
        }
    }

    public void setAccountId(String id) {
        accountId = id;
        try {
            taskHandler.setAccountId(id);
        } catch (Exception e) {
            Log.e(TAG, "setAccountId: " + e);
        }
    }

    public void addPushMessageHandler(PushMessageHandler handler) {
        pushMessageHandlerQueue.remove(handler);
        pushMessageHandlerQueue.add(handler);
    }

    public void removePushMessageHandler(PushMessageHandler handler) {
        pushMessageHandlerQueue.remove(handler);

    }
}
