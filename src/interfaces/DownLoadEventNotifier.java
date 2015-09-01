package interfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.sdu.utils.StaticValue;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownLoadEventNotifier {

	private DownInterface dif;
	
	//处理数据接收完成之后，调用接口函数
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 0){
				
				String back = (String)msg.obj;
				dif.onDownloadSuccess(back);
			}
		}
		
	};
	
	public DownLoadEventNotifier(DownInterface dif){
		this.dif = dif;
		
	}
	
	//开始进行下载
	public void start(String req,String url){
		new Thread(new DealThread(req, url)).start();
	}
	
	class DealThread implements Runnable{

		private String req;
		private String url;
		
		public DealThread(String req,String url){
			this.req = req;
			this.url = url;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			deal();
		}
		
		private void deal(){
			Log.e("req",req); //获取响应内容
			
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();  
			params.add(new BasicNameValuePair("REQUEST", req));
			
			try {
				//http://jiduoduo.duapp.com
				//http://211.87.227.124/study.php
			    HttpPost postMethod = new HttpPost(StaticValue.URL+url);
			    postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中
							
			    //Log.e("url",StaticValue.URL+url); //获取响应内容
			    
			    HttpResponse response = new DefaultHttpClient().execute(postMethod); //执行POST方法
			    String back = EntityUtils.toString(response.getEntity(), "utf-8");
			    
			   Log.e("result", "result = " + back); //获取响应内容
				
			    Message msg = Message.obtain();
			    msg.obj = back;
			    msg.what = 0;
			    
			    handler.sendMessage(msg);

			} catch (UnsupportedEncodingException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (ClientProtocolException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		}
		
	}
	
}
