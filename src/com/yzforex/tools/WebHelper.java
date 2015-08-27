package com.yzforex.tools;

/*
 * 该类通过一个web url页面获取传入参数获取结果，类似于调用一个web service
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebHelper {
	String urlString;

	public WebHelper(String url) {
		urlString = new String(url);
	}

	/**
     * 获取网落图片资源 
     * @param url
     * @return
     */
    public  Bitmap getHttpBitmap(){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(urlString);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(0);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    } 
	public String NetType(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
			if (typeName.equalsIgnoreCase("wifi")) {
			} else {
				typeName = info.getExtraInfo().toLowerCase();
				// 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
			}
			return typeName;
		} catch (Exception e) {
			return null;
		}
	}

	public String GetResult() {
		String result = "";
		InputStream iStream = null;
		BufferedReader bReader = null;
		try {
			URL url = new URL(this.urlString);
			iStream = url.openStream();
			bReader = new BufferedReader(new InputStreamReader(iStream));
			String line = null;
			while ((line = bReader.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (iStream != null) {
					iStream.close();
				}
			} catch (IOException ignore) {
			}
			try {
				if (bReader != null) {
					bReader.close();
				}
			} catch (IOException ignore) {
			}
		}
		return result;
	}

	public String GetResultByGet() {
		String result = "";
		BufferedReader in =null;
		try {
			URL url=new URL(this.urlString);
			URLConnection connection=url.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE6.0;Windows NT 5.1;SV1)");
			connection.connect();
			in =new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line=in.readLine())!=null)
			{
				result+=line;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return result;
	}
}
