package com.sdu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownloader {

	public static File downLoadFile(String httpUrl) {
		
		final String fileName = "updata.apk";

		File file = new File(StaticValue.ROOT_DIR + fileName);
		try {
			URL url = new URL(httpUrl);
			try {
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				// FileOutputStream fos = c.openFileOutput(fileName,
				// Context.MODE_WORLD_READABLE);
				byte[] buf = new byte[1024];
				conn.connect();
				double count = 0;
				if (conn.getResponseCode() >= 400) {
					return null;
				} else {
					while (count <= 100) {
						if (is != null) {
							int numRead = is.read(buf);
							if (numRead <= 0) {
								break;
							} else {
								fos.write(buf, 0, numRead);
							}

						} else {
							break;
						}

					}
				}

				conn.disconnect();
				fos.close();
				is.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return file;
	}
}
