package com.sdu.ui;

import interfaces.DownInterface;

import com.sdu.androidmarket.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * @version 1.0
 * @author 洪波
 *
 */
public class CommentDialog extends Dialog{

	private Context context = null;
	private static CommentDialog comDialog = null;
	
	//评论
	private static EditText et_text_reply;
	
	public CommentDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public CommentDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public CommentDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		

    	
	}

	public static CommentDialog createDialog(Context context,final DownInterface dif){
		comDialog = new CommentDialog(context,R.style.CustomProgressDialog);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=inflater.inflate(R.layout.dialog_reply_layout, null); 
		
        et_text_reply = (EditText)view.findViewById(R.id.et_text_reply);
		Button commitBtn = (Button)view.findViewById(R.id.btn_commit);
        
		commitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dif.onDownloadSuccess(et_text_reply.getText().toString());
				
				comDialog.dismiss();
			}
		});
		
		comDialog.setContentView(view);
		comDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		
		comDialog.setCancelable(true);
        
		return comDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	if (comDialog == null){
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
    public CommentDialog setTitile(String strTitle){
    	return comDialog;
    }
    
    /**
     * 
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public CommentDialog setMessage(String strMessage){
    	return comDialog;
    }
	
	public String getText(){
		return et_text_reply.getText().toString();
	}

}
