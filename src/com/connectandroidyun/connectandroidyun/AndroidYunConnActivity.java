package com.connectandroidyun.connectandroidyun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.connectandroidyun.connectandroidyun.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroidYunConnActivity extends Activity 
{
	Button btnStart;
	Button btnStop;
	Button btnForward;
	Button btnBackward;
	Button btnLeft;
	Button btnRight;
	Button btnHalt;
	
	Button btnStartVideo;
	Button btnPanLeft;
	Button btnPanRight;
	Button btnTiltUp;
	Button btnTiltDown;
	
	String ip = "Yun IP";
	
	AndroidYunConnect AandYConnect;
	
	byte direction;
	char directHolder;
	
	int forwardD = 1;
	int backwardD = 2;
	int leftD = 3;
	int rightD = 4;
	int haltD = 5;
	int startVideo = 6;
	int panLeftD = 7;
	int panRightD = 8;
	int tiltUpD = 9;
	int tiltDownD = 10;
	
	
	TextView portNum;
	TextView dataSent;
	TextView dataReceived;
	TextView  InfoText1;
	EditText ipAddr;
	String serverIpAddress = "Yun IP";
	
	Boolean started = false;
	
	private long lastClickTime = 0;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_yun_conn);
		
		btnStart = (Button)findViewById(R.id.button_start);
		//btnStop = (Button)findViewById(R.id.button_stop);
		
		btnForward = (Button)findViewById(R.id.button_forward);
		btnBackward = (Button)findViewById(R.id.button_backward);
		btnLeft = (Button)findViewById(R.id.button_left);
		btnRight = (Button)findViewById(R.id.button_right);
		btnHalt = (Button)findViewById(R.id.button_halt);
		
		btnStartVideo = (Button)findViewById(R.id.button6);
		btnPanLeft = (Button)findViewById(R.id.button7);
		btnPanRight = (Button)findViewById(R.id.button8);
		btnTiltUp = (Button)findViewById(R.id.button9);
		btnTiltDown = (Button)findViewById(R.id.button10);
		
		
		btnStart.setOnClickListener(startListener);
		//btnStop.setOnClickListener(stopListener);
		
		btnForward.setOnClickListener(forwardListener);
		btnBackward.setOnClickListener(backwardListener);
		btnLeft.setOnClickListener(leftListener);
		btnRight.setOnClickListener(rightListener);
		btnHalt.setOnClickListener(haltListener);
		
		btnStartVideo.setOnClickListener(startVideoListener);
		btnPanLeft.setOnClickListener(btnPanLeftListener);
		btnPanRight.setOnClickListener(btnPanRightListener);
		btnTiltUp.setOnClickListener(btnTiltUpListener);
		btnTiltDown.setOnClickListener(btnTiltDownListener);
		
		
		
		TextView portNum = (TextView)findViewById(R.id.portnum);
		portNum.setText("6666");
		
		//TextView dataSent = (TextView)findViewById(R.id.DataSent);
		//dataSent.setText("Sent");
		
		//TextView dataReceived = (TextView)findViewById(R.id.DataReceived);
		//dataReceived.setText("Received");
		
		
		EditText ipAddr = (EditText)findViewById(R.id.ipaddr);
		
		ipAddr.addTextChangedListener(new TextWatcher() 
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
								
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) 
			{
				serverIpAddress = s.toString();
				
			}
		});
	}
	
	

	private OnClickListener startListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{	
			//String ip = "Yun IP";
			
			TextView InfoText1 = (TextView)findViewById(R.id.infoText1);
			
			if (SystemClock.elapsedRealtime() - lastClickTime < 1000)
			{
				return;
			}
			else
			{
				lastClickTime =
					
					
					SystemClock.elapsedRealtime();
			}
			if( started == false)
			{
			
				if(serverIpAddress.equals(ip))
				{
					Log.d("ZZZZZ", "startListener No IP can't start");
					
					InfoText1.setText("No IP can't start");
				}
				else
				{
					Log.d("ZZZZZ", "startListener");
					
					InfoText1.setText("Starting Connection");
					btnStart.setText(R.string.button_stop);
					
					started = true;
				
					AandYConnect = new AndroidYunConnect();
					AandYConnect.serverIpAddress = serverIpAddress;
					AandYConnect.execute();	
					InfoText1.setText("Yun Connected");
				}
			}
			else
			{
				Log.d("ZZZZZ", "stopListener");
				started = false;
				InfoText1.setText("Connection Closed ");
				
				btnStart.setText(R.string.button_start);
				
				AandYConnect.yunData = -1;
				
				//AandYConnect.closeSockets();
			}
		}
	};
	
//	private OnClickListener stopListener = new OnClickListener() 
//	{
//		
//		@Override
//		public void onClick(View v) 
//		{
//			Log.d("ZZZZZ", "stopListener");
//			AandYConnect.yunData = -1;
//			
//		}
//	};

	private OnClickListener forwardListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "forwardListener");	
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("Forward");
			AandYConnect.SendDirectionToYun(forwardD);
			
		}
	};
	
	private OnClickListener backwardListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "backwardListener");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("Backward");
			AandYConnect.SendDirectionToYun(backwardD);
			
		}
	};
	
	
	private OnClickListener leftListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "leftListener");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("Left");
			AandYConnect.SendDirectionToYun(leftD);
			
		}
	};
	
	private OnClickListener rightListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "rightListener");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("Right");
			AandYConnect.SendDirectionToYun(rightD);
			
		}
	};
	
	private OnClickListener haltListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "haltListener");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("Halt");
			AandYConnect.SendDirectionToYun(haltD);
			
		}
	};
	
	private OnClickListener startVideoListener = new OnClickListener() 
	{
				
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "Start Video");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("6");
			AandYConnect.SendDirectionToYun(startVideo);
			
			if(serverIpAddress.equals(ip))
			{
				Log.d("ZZZZZ", "No IP can't start");
				InfoText1.setText("No IP can't start");
			}
			else
			{
				Log.d("ZZZZZ", "start webview");
				WebView myWebView = (WebView) findViewById(R.id.yunView);
				myWebView.loadUrl("http://" + serverIpAddress + ":8080/stream_simple.html");
			}
		}
	};
	
	private OnClickListener btnPanLeftListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "Pan Left");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("7");
			AandYConnect.SendDirectionToYun(panLeftD);
			
		}
	};
	private OnClickListener btnPanRightListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "Pan right");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("8");
			AandYConnect.SendDirectionToYun(panRightD);
			
		}
	};
	private OnClickListener btnTiltUpListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "Tilt Up");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("9");
			AandYConnect.SendDirectionToYun(tiltUpD);
			
		}
	};
	private OnClickListener btnTiltDownListener = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
			Log.d("ZZZZZ", "Tilt Down");
			TextView dataSent = (TextView)findViewById(R.id.DataSent);
			dataSent.setText("10");
			AandYConnect.SendDirectionToYun(tiltDownD);
			
		}
	};
		
	public class AndroidYunConnect extends AsyncTask<Void, byte[], Boolean>
	{
		Socket yunsocket;
		DataInputStream yunin;
		DataOutputStream yunout;
		//private String serverIpAddress = "10.0.1.25";
		String serverIpAddress;
		int yunData = 666;
		int yunDataCnt = 0;
		
		@Override
		protected void onPreExecute()
		{
			Log.d("ZZZZZ", "pre");
		}
		
		@Override
		protected Boolean doInBackground(Void... params) 
		{
			boolean result = false;
			
			try
			{
				Log.d("ZZZZZ", "doInBackground");
				
				InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
				yunsocket = new Socket(serverAddr, 6666);
				
				if(yunsocket.isConnected())
				{
					yunin = new DataInputStream(yunsocket.getInputStream());
					yunout = new DataOutputStream(yunsocket.getOutputStream());
					
					Log.d("ZZZZZ", "doInBackground socket connections up");
					
					
					byte[] directionBuffer = new byte[4];
					
					while(yunData != -1)
					{
						Log.d("ZZZZZ", "doInBackground loop");
						yunDataCnt = yunin.read(directionBuffer); 
					
						publishProgress(directionBuffer);
						Log.d("ZZZZZ", "doInBackground after read");
						//sleep(2000);
					}
					
					//closeSockets();
					
				}	
			}
			catch(IOException e)
			{
				e.printStackTrace();
				Log.d("ZZZZZ", "doInBackground IOException");
				result = true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Log.d("ZZZZZ", "doInBackground Exception");
				result = true;
			}					
			
			finally
			{
				Log.d("ZZZZZ", "doInBackground close sockets");
				try
				{
					yunin.close();
					yunout.close();
					yunsocket.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
					result = true;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					result = true;
				}
			}
			return result;
		}
		
	
		@Override 
		protected void onProgressUpdate(byte[]...direct)
		{
			super.onProgressUpdate(direct);
			
			ByteBuffer bb = ByteBuffer.wrap(direct[0]);
			bb.order(ByteOrder.LITTLE_ENDIAN);			
			int direction = bb.getInt();
			
			TextView dataReceived = (TextView)findViewById(R.id.DataReceived);
			
			switch (direction) {
			case 1:
				dataReceived.setText("Forward");
				break;
			case 2:
				dataReceived.setText("Backward");
				break;
			case 3:
				dataReceived.setText("Left");
				break;
			case 4:
				dataReceived.setText("Right");
				break;
			case 5:
				dataReceived.setText("Halt");
				break;
			case 6:
				dataReceived.setText("Start Video");
				break;
			case 7:
				dataReceived.setText("Pan left");
				break;
			case 8:
				dataReceived.setText("Pan Right");
				break;
			case 9:
				dataReceived.setText("Tilt Up");
				break;
			case 10:
				dataReceived.setText("Tilt Down");
				break;
			default:
				dataReceived.setText("Nothing");
				break;
			}
		}
		
		
		public void SendDirectionToYun(int direction) 
		{
        	TextView InfoText1 = (TextView)findViewById(R.id.infoText1);
			
			try {
                if (yunsocket.isConnected()) 
                {               	
                	//InfoText1.setText("Yun Connected");
                	
                    Log.d("ZZZZZ", "SendDirectionToYun: Writing received message to socket");
                    
                    yunout.writeInt(direction);
                    
                } else {
                	InfoText1.setText("Yun Not Connected. Socket closed");
                    Log.d("ZZZZZ", "SendDirectionToYun: Cannot send message. Socket is closed");
                }
            } catch (Exception e) {
            	InfoText1.setText("Yun Not Connected. Message send failed.");
                Log.d("ZZZZZ", "SendDirectionToYun: Message send failed. Caught an exception");
                e.printStackTrace();
            }
		}
		
//		public Boolean closeSockets()
//		{
//			boolean result = false;
//			
//			try
//			{
//				Log.d("ZZZZZ", "Close Datastreams");
//				yunin.close();
//				yunout.close();
//				Log.d("ZZZZZ", "Close Socket");
//				yunsocket.close();
//				InfoText1.setText("Yun Not Connected. Socket closed");
//			}
//			catch(IOException e)
//			{
//				e.printStackTrace();
//				result = true;
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//				result = true;
//			}
//			return result;
//		}
	}
}
