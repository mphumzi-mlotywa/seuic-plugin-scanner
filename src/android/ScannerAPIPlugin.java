package com.seuic.scanner.api;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONException;

import com.seuic.scanner.DecodeInfo;
import com.seuic.scanner.DecodeInfoCallBack;
import com.seuic.scanner.Scanner;
import com.seuic.scanner.ScannerFactory;

import android.content.Context;
import android.util.Log;

public class ScannerAPIPlugin extends CordovaPlugin{
	
	private Context mContext;
	
	private Scanner mScanner;
	
	private CallbackContext mCallbackContext;
	
	private OnDecodeInfoCallback mDecodeInfoCallback = new OnDecodeInfoCallback();
	
	@Override
	public boolean execute(String action, CordovaArgs args,
			CallbackContext callbackContext) throws JSONException {
		
		initScanner();
		
		if(action.equals("startScan")){
			startScan();
		}else if(action.equals("stopScan")){
			stopScan();
		}else if(action.equals("closeScanner")){
			closeScanner();
		}else if(action.equals("openScanner")){
			openScanner();
		}else if(action.equals("setDecodeCallback")){
			setDecodeCallback(callbackContext);
		}
		
		return true;
	}
	
	private void startScan(){
		cordova.getThreadPool().execute(new Runnable() {
			
			@Override
			public void run() {
				mScanner.startScan();
			}
		});
	}
	
	private void stopScan(){
		cordova.getThreadPool().execute(new Runnable() {
			
			@Override
			public void run() {
				mScanner.stopScan();
			}
		});
	}
	
	private void closeScanner(){
		cordova.getThreadPool().execute(new Runnable() {
			
			@Override
			public void run() {
				mScanner.close();
			}
		});
	}
	
	private void openScanner(){
		
		cordova.getThreadPool().execute(new Runnable() {
			
			@Override
			public void run() {
				boolean isOpened = mScanner.open();
				Log.i("ScannerAPIPlugin", isOpened +"");
			}
		});
	}
	
	private void setDecodeCallback(final CallbackContext callbackContext){
		cordova.getThreadPool().execute(new Runnable() {
			
			@Override
			public void run() {
				mCallbackContext = callbackContext;
				
				mScanner.setDecodeInfoCallBack(mDecodeInfoCallback);
			}
		});
	}
	
	private void initScanner(){
		if(mContext == null){
			mContext = this.cordova.getActivity().getApplicationContext();
		}
		
		if(mScanner == null){
			mScanner = ScannerFactory.getScanner(mContext);
		}
	}
	
	private class OnDecodeInfoCallback implements DecodeInfoCallBack{

		@Override
		public void onDecodeComplete(DecodeInfo decodeInfo) {
			PluginResult result = new PluginResult(PluginResult.Status.OK, decodeInfo.barcode);
			result.setKeepCallback(true);
			mCallbackContext.sendPluginResult(result);
		}
		
	}
	
}
