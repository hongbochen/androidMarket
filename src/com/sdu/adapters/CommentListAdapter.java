package com.sdu.adapters;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.sdu.androidmarket.R;
import com.sdu.beans.CommentBeans;
import com.sdu.beans.UserBean;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.CommentDialog;
import com.sdu.ui.LoadingDialog;
import com.sdu.ui.MessageBackDialog;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {

	private ArrayList<CommentBeans> commentList;
	private LayoutInflater inflater;
	private Context context;

	public CommentListAdapter(ArrayList<CommentBeans> commentList,
			Context context) {
		this.commentList = commentList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Holder holder;

		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.comment_item, null);

			holder.iv_user_head = (ImageView) convertView
					.findViewById(R.id.iv_user_head);
			holder.tv_user_name = (TextView) convertView
					.findViewById(R.id.tv_user_name);
			holder.tv_comment_time = (TextView) convertView
					.findViewById(R.id.tv_comment_time);
			holder.tv_zan_count = (TextView) convertView
					.findViewById(R.id.tv_zan_count);
			holder.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
			holder.iv_reply = (ImageView) convertView
					.findViewById(R.id.iv_reply);
			holder.tv_comment_content = (TextView) convertView
					.findViewById(R.id.tv_comment_content);
			holder.ll_reply_back = (LinearLayout) convertView
					.findViewById(R.id.ll_reply_back);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
			holder.ll_reply_back.removeAllViews();
		}

		if (commentList.get(position).getUserHeadPath() != null) {
			// TODO
		}

		if (commentList.get(position).getUserName() != null) {
			holder.tv_user_name
					.setText(commentList.get(position).getUserName());
		}

		if (commentList.get(position).getCommentTime() != null) {
			holder.tv_comment_time.setText(commentList.get(position)
					.getCommentTime());
		}

		if (commentList.get(position).getZanSize() != null) {
			holder.tv_zan_count.setText(commentList.get(position).getZanSize());
		}

		if (commentList.get(position).getCommentContent() != null) {
			holder.tv_comment_content.setText(commentList.get(position)
					.getCommentContent());
		}

		ArrayList<UserBean> backList = commentList.get(position).getBackList();
		if (backList != null) {
			if (backList.size() != 0) {
				addReplyComment(holder.ll_reply_back, backList);
			}
		}

		holder.iv_reply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(context,MessageBackDialog.class);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				//
				// intent.putExtra("TYPE", "BACK");
				// intent.putExtra("CID",
				// commentList.get(position).getCommentID());
				//
				// context.startActivity(intent);

				// 测试加载对话框
				final LoadingDialog ld = LoadingDialog.createDialog(context);

				CommentDialog mbd = CommentDialog.createDialog(context,
						new DownInterface() {

							@Override
							public void onDownloadSuccess(String result) {
								if (result.equals("")) {
									MSShow.show(context, "请输入评论");
								} else {
									comment(position, result, ld);
								}

							}
						});

				mbd.show();
			}
		});

		holder.iv_zan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				final LoadingDialog ld = LoadingDialog.createDialog(context);
				
				DownLoadEventNotifier den = new DownLoadEventNotifier(new DownInterface() {
					
					@Override
					public void onDownloadSuccess(String result) {
						ld.dismiss();
						
						JSONObject jo;
						try {
							jo = new JSONObject(result);

							if (jo.getInt(StaticValue.ERROR_LABEL) == StaticValue.ERROR_NO) {
								
								int zans = Integer.parseInt(commentList.get(position).getZanSize());
								commentList.get(position).setZanSize(""+(zans+1));
								
								CommentListAdapter.this.notifyDataSetChanged();

							} else {
								MSShow.show(context, "点赞失败，请稍候重试");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				ld.show();
				String req = MarketApplication.nu.reqZan(commentList.get(position).getCommentID());
				den.start(req, "servlet/AppDetail");
				
			}
		});

		return convertView;
	}

	// 发表评论回复
	private void comment(final int position, final String con,
			final LoadingDialog ld) {

		DownLoadEventNotifier den = new DownLoadEventNotifier(
				new DownInterface() {

					@Override
					public void onDownloadSuccess(String result) {
						ld.dismiss();

						JSONObject jo;
						try {
							jo = new JSONObject(result);

							if (jo.getInt(StaticValue.ERROR_LABEL) == StaticValue.ERROR_NO) {

								if (StaticValue.USER_ID.equals("-1")) {
									commentList
											.get(position)
											.getBackList()
											.add(new UserBean("-1", "匿名用户", con));
								} else {
									commentList
											.get(position)
											.getBackList()
											.add(new UserBean(
													StaticValue.USER_ID,
													StaticValue.USER_NAME, con));
								}
								
								CommentListAdapter.this.notifyDataSetChanged();

							} else {
								MSShow.show(context, "回复失败，请稍候重试");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		ld.show();
		String req = MarketApplication.nu.reqCommentComment(
				commentList.get(position).getCommentID(), con);
		den.start(req, "servlet/AppDetail");
	}

	private void addReplyComment(LinearLayout ll, ArrayList<UserBean> backList) {
		ll.removeAllViews();

		int count = backList.size();
		for (int i = 1; i <= count; i++) {
			LinearLayout rl = new LinearLayout(context);
			rl.setOrientation(LinearLayout.HORIZONTAL);

			TextView user = new TextView(context);
			user.setTextColor(context.getResources().getColor(
					R.color.name_color));
			user.setTextSize(16);
			user.setText(backList.get(i - 1).getUserName() + ":");

			rl.addView(user, LayoutParams.WRAP_CONTENT);

			TextView usr_com = new TextView(context);
			usr_com.setTextColor(context.getResources().getColor(R.color.black));
			usr_com.setTextSize(16);
			usr_com.setText(backList.get(i - 1).getReplyBack());

			rl.addView(usr_com, LayoutParams.WRAP_CONTENT);

			ll.addView(rl);
		}
	}

	class Holder {
		ImageView iv_user_head;
		TextView tv_user_name;
		TextView tv_comment_time;
		TextView tv_zan_count;
		ImageView iv_zan;
		ImageView iv_reply;
		TextView tv_comment_content;
		LinearLayout ll_reply_back;
	}

}
