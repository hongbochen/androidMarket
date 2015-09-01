package com.sdu.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader;

public class BFImageCache implements ImageLoader.ImageCache {
    /** ���� */
    private static BFImageCache cache;
    /** �ڴ滺�� */
    private HashMap<String, Bitmap> memory;
    /** ����Ŀ¼ */
    private String cacheDir;
    
    /** ��ȡ���� */
    public static BFImageCache getInstance() {
        if (null == cache) {
            cache = new BFImageCache();
        }
        return cache;
    }
    /** ��ʼ��������Application��onCreate�е��� */
    public void initilize(Context context) {
        memory = new HashMap<String, Bitmap>();
        cacheDir = context.getCacheDir().toString()+File.separator;
    }
    
    @Override
    public Bitmap getBitmap(String url) {
        // ��ȡͼƬ
        try {
            String key = url;
            if (memory.containsKey(key)) {
                return memory.get(key);
            } else {
                File file = new File(cacheDir + key);
                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                    return bitmap;
                }
            }
        } catch (Exception e) {
            Log.d("halfman", e.getMessage());
        }
        return null;
    }
    
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        // �ߴ糬��10ʱ�������沢�����ڴ�
        if (memory.size() == 10) {
            Iterator<String> it = memory.keySet().iterator();
            while (it.hasNext()) {
                try {
                    String key = it.next();
                    Bitmap temp = memory.get(key);
                    File file = new File(cacheDir+key);
                    FileOutputStream os;
                    os = new FileOutputStream(file, false);
                    temp.compress(CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            memory.clear();
        }
        // ����ͼƬ���ڴ�
        memory.put(url, bitmap);
    }
}