package com.yzforex.tools;

/*
 * 该类通过一个web url页面获取传入参数获取结果，类似于调用一个web service
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class WebHelper {
	String urlString;

	public WebHelper(String url) {
		urlString = new String(url);
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
}
