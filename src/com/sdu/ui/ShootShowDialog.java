package com.sdu.ui;

import net.tsz.afinal.FinalBitmap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sdu.androidmarket.R;
import com.sdu.manager.MarketApplication;
import com.sdu.utils.BFImageCache;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * @version 1.0
 * @author 洪波
 *
 */
public class ShootShowDialog extends Dialog{

	private Context context = null;
	private static ShootShowDialog shootDialog = null;
	
	
	public ShootShowDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public ShootShowDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public ShootShowDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		

    	
	}

	public static ShootShowDialog createDialog(Context context,String url){
		shootDialog = new ShootShowDialog(context,R.style.CustomProgressDialog);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=inflater.inflate(R.layout.layout_shoot_show, null); 
		
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_shoot);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);  
	    
        /*
        FinalBitmap.create(context)
		.display(imageView, url, imageView.getWidth(),
				imageView.getHeight(), null, null);*/
        
        FinalBitmap.create(context).display(imageView, url);
       
		
        shootDialog.setContentView(view);
        shootDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        
        shootDialog.setCancelable(true);
        
		return shootDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	if (shootDialog == null){
    		return;
    	}
    	
        
    }
 
    /**
     * 
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public ShootShowDialog setTitile(String strTitle){
    	return shootDialog;
    }
    
    /**
     * 
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public ShootShowDialog setMessage(String strMessage){
    	return shootDialog;
    }
	
	

}
