package com.clover.remote.client.transport.usb.pos;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import com.clover.remote.client.transport.usb.UsbCloverManager;
//import com.clover.common.analytics.ALog;
//import com.clover.common.util.CloverUsbManager;


public class PosUsbBroadcastReceiver extends BroadcastReceiver{

  public static final String ACTION_USB_PERMISSION = "com.clover.USB_PERMISSION";
  private static final String TAG = PosUsbBroadcastReceiver.class.getSimpleName();
  private UsbManager mUsbManager;

  public void onReceive(final Context context, Intent intent) {
    String action = intent.getAction();
    Bundle extras = intent.getExtras();


    final Intent serviceIntent = new Intent().setClass(context, PosUsbRemoteProtocolService.class).putExtras(extras);

    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

    mUsbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);


    if (UsbCloverManager.isMatch(device, UsbAccessorySetupUsbManager.VENDOR_PRODUCT_IDS)) {
      if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {

        Runnable runnable = new Runnable(){
          @Override public void run() {
            serviceIntent.setAction(PosUsbRemoteProtocolService.ACTION_USB_SETUP);
//            ALog.d(this, "Starting service: %s", serviceIntent);
            context.startService(serviceIntent);
          }
        };

        if(mUsbManager.hasPermission(device)) {
          runnable.run();
        } else {
          requestPermission(device, runnable, context);
        }
        return;
      }
    }

    if (UsbCloverManager.isMatch(device, RemoteUsbManager.VENDOR_PRODUCT_IDS)) {
      if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
        Runnable runnable = new Runnable() {
          @Override public void run() {
            serviceIntent.setAction(PosUsbRemoteProtocolService.ACTION_USB_CONNECT);
//            ALog.d(this, "Starting service: %s", serviceIntent);
            context.startService(serviceIntent);
          }
        };
        if(mUsbManager.hasPermission(device)) {
          runnable.run();
        } else {
          requestPermission(device, runnable, context);
        }
        return;
      } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
        Runnable runnable = new Runnable() {
          @Override public void run() {
            serviceIntent.setAction(PosUsbRemoteProtocolService.ACTION_USB_DISCONNECT);
//            ALog.d(this, "Starting service: %s", serviceIntent);
            context.startService(serviceIntent);
          }
        };
        if(true || mUsbManager.hasPermission(device)) {
          runnable.run();
        } else {
          requestPermission(device, runnable, context);
        }
        return;

      }
    }
  }

  private void requestPermission(UsbDevice device, final Runnable runnable, Context context) {
    BroadcastReceiver usbReceiver = new BroadcastReceiver() {
      @Override public void onReceive(Context context, Intent intent) {
        synchronized (this) {
          UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

          if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
            if(device != null){
              runnable.run();
            }
          }
          else {
            Log.d(TAG, "permission denied for device " + device);
          }
        }
      }
    };
    PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
    IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
    context.registerReceiver(usbReceiver, filter);
    mUsbManager.requestPermission(device, permissionIntent);
  }

}
