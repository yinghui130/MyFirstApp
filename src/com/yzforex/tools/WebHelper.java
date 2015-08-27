package com.yzforex.tools;

/*
 * ����ͨ��һ��web urlҳ���ȡ���������ȡ����������ڵ���һ��web service
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
     * ��ȡ����ͼƬ��Դ 
     * @param url
     * @return
     */
    public  Bitmap getHttpBitmap(){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(urlString);
            //�������
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������
            conn.setConnectTimeout(0);
            //�������û��������
            conn.setDoInput(true);
            //��ʹ�û���
            conn.setUseCaches(false);
            //�����п��ޣ�û��Ӱ��
            //conn.connect();
            //�õ�������
            InputStream is = conn.getInputStream();
            //�����õ�ͼƬ
            bitmap = BitmapFactory.decodeStream(is);
            //�ر�������
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
