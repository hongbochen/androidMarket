package com.sdu.ui;

import com.sdu.androidmarket.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * @version 1.0
 * @author 洪波
 *
 */
public class LoadingDialog extends Dialog{

	private Context context = null;
	private static LoadingDialog loadingProgressDialog = null;
	
	
	public LoadingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public LoadingDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		

    	
	}

	public static LoadingDialog createDialog(Context context){
		loadingProgressDialog = new LoadingDialog(context,R.style.CustomProgressDialog);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=inflater.inflate(R.layout.layout_progress_dialog, null); 
		
        ImageView imageView = (ImageView) view.findViewById(R.id.loadingImageView);
        imageView.setImageResource(R.drawable.loading_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        
        loadingProgressDialog.setContentView(view);
        loadingProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        
        loadingProgressDialog.setCancelable(false);
        
		return loadingProgressDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	if (loadingProgressDialog == null){
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
    public LoadingDialog setTitile(String strTitle){
    	return loadingProgressDialog;
    }
    
    /**
     * 
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public LoadingDialog setMessage(String strMessage){
    	return loadingProgressDialog;
    }
	
	

}
