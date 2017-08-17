package com.example.xiaojun.linghejiedao.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xiaojun.linghejiedao.DownloadService.DownloadService;
import com.example.xiaojun.linghejiedao.DownloadService.DownloadTuPianService;
import com.example.xiaojun.linghejiedao.MyApplication;
import com.example.xiaojun.linghejiedao.R;
import com.example.xiaojun.linghejiedao.beans.ChuanJianUserBean;
import com.example.xiaojun.linghejiedao.beans.GongGaoBean;
import com.example.xiaojun.linghejiedao.beans.IpAddress;
import com.example.xiaojun.linghejiedao.beans.MoShengRenBean;
import com.example.xiaojun.linghejiedao.beans.MoShengRenBean2;
import com.example.xiaojun.linghejiedao.beans.MoShengRenBeanDao;
import com.example.xiaojun.linghejiedao.beans.ShiBieBean;
import com.example.xiaojun.linghejiedao.beans.ShiBieJiLuBean;
import com.example.xiaojun.linghejiedao.beans.ShiPingBean;
import com.example.xiaojun.linghejiedao.beans.ShiPingBeanDao;
import com.example.xiaojun.linghejiedao.beans.TanChuangBean;
import com.example.xiaojun.linghejiedao.beans.TianQiBean;
import com.example.xiaojun.linghejiedao.beans.TuPianBean;
import com.example.xiaojun.linghejiedao.beans.User;
import com.example.xiaojun.linghejiedao.beans.WBBean;
import com.example.xiaojun.linghejiedao.beans.WeiShiBieBean;
import com.example.xiaojun.linghejiedao.beans.ZhuJiBean;
import com.example.xiaojun.linghejiedao.dialog.CaiDanDialog;
import com.example.xiaojun.linghejiedao.media.IjkVideoView;
import com.example.xiaojun.linghejiedao.media.Settings;
import com.example.xiaojun.linghejiedao.utils.DateUtils;
import com.example.xiaojun.linghejiedao.utils.GlideCircleTransform;
import com.example.xiaojun.linghejiedao.utils.GsonUtil;

import com.example.xiaojun.linghejiedao.utils.LibVLCUtil;
import com.example.xiaojun.linghejiedao.utils.Utils;
import com.example.xiaojun.linghejiedao.utils.X5WebView;
import com.example.xiaojun.linghejiedao.view.DividerItemDecoration;
import com.example.xiaojun.linghejiedao.view.ScrollSpeedLinearLayoutManger;
import com.example.xiaojun.linghejiedao.view.WrapContentLinearLayoutManager;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.sdsmdg.tastytoast.TastyToast;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sun.misc.BASE64Decoder;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class VlcVideoActivity extends Activity  {
	private IVLCVout vlcVout=null;
	private MediaPlayer mediaPlayer=null;
	private final static String TAG = "VlcVideoActivity";
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private List<ShiPingBean> shiPingBeanList;
//    private View mLoadingView;
//	private int mVideoHeight;
//	private int mVideoWidth;
//	private int mVideoVisibleHeight;
//	private int mVideoVisibleWidth;
//	private int mSarNum;
//	private int mSarDen;
	private WindowManager wm;
	private ScrollView scollview;
	private LayoutInflater mInflater = null;
	private View view = null;
	private String fileType=null;
	private String fileNames=null;
	private static int quyu=0;
	private static List<String> gonggaoList=null;
	private LinearLayout linearLayout2;
	private Media media;
	private RelativeLayout tanchuang_RL;
	private MyReceiver myReceiver=null;
	//private MyReceiverFile myReceiverFile=null;
	private RecyclerView recyclerView;
//	private RecyclerView recyclerView2;
	private MyAdapter adapter=null;
//	private MyAdapter2 adapter2=null;
	//private Bitmap logo=null;
	private MoShengRenBeanDao daoSession=null;
	private ShiPingBeanDao shiPingBeanDao=null;
//	private ShiBieJiLuBeanDao shiBieJiLuBeanDao=null;
	private static List<String> voidePathList=new ArrayList<>();
	private static List<String> photoPathList=new ArrayList<>();
//	private static Vector<YongHuBean> moShengRenBean2List=new Vector<>();
	private WrapContentLinearLayoutManager manager;
	//private ScrollSpeedLinearLayoutManger manager2;
	//private GridLayoutManager gridLayoutManager;
	private WebSocketClient webSocketClient=null;
	private Runnable runnable=null;
	private Handler conntionHandler=null;
	public static String zhuji_string=null,shiping_string=null;
	private String ss;
	private String tupianPath=null;
	//private TextView textView;
	private String zhuji=null;
	private static final String zhuji2="http://121.46.3.20";
	//private AutoScrollTextView autoScrollTextView;
	private IVLCVout.Callback callback;
	private LibVLC libvlc;
	private TextView xiaoshi,riqi,xingqi,tianqi,wendu,tishi_tv33;
	private boolean isTO=true;
	//private MarqueeView marqueeView,marqueeView2;
	//private  static Vector<String> stringVector=new Vector<>();
	private IjkMediaPlayer ijkMediaPlayer=null;
//	private IjkVideoView ijkVideoView=null;
	private static int Video_index = 0;
	//private RollPagerView rollPagerView=null;
	private  static Vector<TanChuangBean> tanchuangList=null;
	private  static Vector<ShiBieJiLuBean> shiBieJiLuList=null;
	private int dw,dh;
	private static int shiPingCount=0;

	private ImageView touxiang33,tishi_im33;
	private LinearLayout zixunLL;

	private long timing=0;
	private boolean isChange=true;
	private static TextView gonggao;
	private TextView gonggaobiaoti;
	private TextView gonggaoriqi;
	private static ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
			Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
	private static ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
			Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
	public  Handler handler=new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(final Message msg) {
			switch (msg.what){
				case 111:
					//更新地址
					try {

						WebsocketPushMsg websocketPushMsg=new WebsocketPushMsg();
						if (zhuji_string!=null && shiping_string!=null){

							websocketPushMsg.startConnection(zhuji_string,shiping_string);
						}else {

							TastyToast.makeText(VlcVideoActivity.this, "请先设置主机和视频流", Toast.LENGTH_LONG,TastyToast.ERROR).show();

						}

					} catch (URISyntaxException e ) {

						Log.d(TAG, e.getMessage());
					}

					break;
				case 110:

//					CaiDanDialog danDialog=new CaiDanDialog(VlcVideoActivity.this,R.style.dialog_ALL,daoSession,adapter,null);
//					danDialog.show();
					Log.d(TAG, "dddd111");
//					ijkVideoView.setVisibility(View.VISIBLE);
//					//播放广告视频
//					if (voidePathList.size()!=0)
//					play(voidePathList.get(0));

//					if (photoPathList.size()!=0){
//						rollPagerView.setAdapter(new TestLoopAdapter(rollPagerView));
//						rollPagerView.setHintView(null);
//
//					}

					break;
				case 999:

				AnimatorSet animatorSet=new AnimatorSet();
					animatorSet.playTogether(
							ObjectAnimator.ofFloat(tanchuang_RL,"scaleX",1,0),
							ObjectAnimator.ofFloat(tanchuang_RL,"scaleY",1,0)
					);
					animatorSet.setInterpolator(new DecelerateInterpolator());
					animatorSet.setDuration(1000);
					animatorSet.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {

							tanchuang_RL.setVisibility(View.GONE);
						}
					});
					animatorSet.start();


					if (tanchuangList.size()>1) {
						tanchuangList.remove(1);

								adapter.notifyItemRemoved(1);
								//adapter.notifyItemChanged(1);
								//adapter.notifyItemRangeChanged(1,tanchuangList.size());
								//adapter.notifyDataSetChanged();
								manager.scrollToPosition(tanchuangList.size() - 1);
								Log.d(TAG, "tanchuangList.size():" + tanchuangList.size());

					}

					if (tanchuangList.size()>25){
						tanchuangList.clear();

						TanChuangBean bean=new TanChuangBean();
						bean.setBytes(null);
						bean.setName(null);
						bean.setType(-2);
						bean.setTouxiang(null);
						tanchuangList.add(bean);
					}

					break;
				case 19: //更新识别记录
					int size=shiBieJiLuList.size();

					//adapter2.notifyItemInserted(size-1);
					//manager2.smoothScrollToPosition(recyclerView2,null,size-1);

					break;


			}


			if (msg.arg1==1){

				ShiBieBean.PersonBeanSB dataBean= (ShiBieBean.PersonBeanSB) msg.obj;
				ShiBieJiLuBean bean2=new ShiBieJiLuBean();
				bean2.setCount(msg.arg2+"");
				bean2.setUrlPath(dataBean.getAvatar());

//				shiBieJiLuList.add(bean2);
//				int size2=shiBieJiLuList.size();
//				Log.d(TAG, "size2:" + size2);
//				adapter.notifyItemInserted(size2-1);
//				manager.smoothScrollToPosition(recyclerView,null,size2-1);


			//	Log.d(TAG, dataBean.getTrack()+"tttttttttttt");
				BASE64Decoder decoder = new BASE64Decoder();
				// Base64解码
				final byte[][] b;
					try {
//						b = new byte[][]{decoder.decodeBuffer(dataBean.getSrc().replace("data:image/jpeg;base64,",""))};
//
//						for (int i = 0; i < b[0].length; ++i) {
//							if (b[0][i] < 0) {// 调整异常数据
//								b[0][i] += 256;
//							}
//						}
					//	Log.d("WebsocketPushMsg", zhuji);
						//textView.setText(msg.arg2+"");
						//double aa=dataBean.getAttr().getAge();

//						final RadioButtonDialog dialog=new RadioButtonDialog(VlcVideoActivity.this,"","员工","a",b[0],R.style.dialog_style,
//								1,zhuji2,dataBean.getAvatar());
//
//						dialog.show();
					//	int size=tanchuangList.size();

						final TanChuangBean bean=new TanChuangBean();
						bean.setBytes(null);
						bean.setType(dataBean.getSubject_type());
						bean.setName(dataBean.getName());
						bean.setTouxiang(dataBean.getAvatar());
						tanchuangList.add(bean);

					//	if (size<=1){

//							ViewGroup.LayoutParams lp =  recyclerView.getLayoutParams();
//
//							lp.width = dw/4;
//							lp.height=dh/3;
////							lp.topMargin=dip2px(VlcVideoActivity.this,30);
////							lp.leftMargin=dip2px(VlcVideoActivity.this,50);
////							lp.rightMargin=dip2px(VlcVideoActivity.this,50);
////							lp.height = dh/2;
//							//Log.d(TAG, "dh:" + dh);
//							linearLayout.setLayoutParams(lp);
//							linearLayout.invalidate();

					//	}



						if (tanchuangList.size()>2){

							tanchuang_RL.setVisibility(View.GONE);
							recyclerView.setVisibility(View.VISIBLE);

						}else {

							tanchuang_RL.setVisibility(View.VISIBLE);
							recyclerView.setVisibility(View.GONE);
							Glide.with(MyApplication.getAppContext())
									.load(zhuji+dataBean.getAvatar())
									.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
									.into(touxiang33);
							tishi_im33.setBackgroundResource(R.drawable.tike);
							tishi_tv33.setText("欢迎你！");
							ViewAnimator
									.animate(tanchuang_RL)
									.scale(0,1)
//					.alpha(0,1)
									.duration(1000)
									.start();
						}

						adapter.notifyItemInserted(tanchuangList.size());
						manager.scrollToPosition(tanchuangList.size()-1);


						new Thread(new Runnable() {
							@Override
							public void run() {

								try {
									Thread.sleep(8000);
//									runOnUiThread(new Runnable() {
//										@Override
//										public void run() {
////											AnimatorSet animatorSet = new AnimatorSet();
////											animatorSet.playTogether(
////												//	ObjectAnimator.ofFloat(manager.getChildAt(1),"translationY",-1000,0),
////													ObjectAnimator.ofFloat(manager.getChildAt(1),"scaleX",1f,0f),
////													ObjectAnimator.ofFloat(manager.getChildAt(1),"scaleY",1f,0f)
////											);
////											//animatorSet.setInterpolator(new DescelerateInterpolator());
////											animatorSet.setDuration(1000);
////											animatorSet.addListener(new AnimatorListenerAdapter(){
////												@Override public void onAnimationEnd(Animator animation) {
////
////
////
////
////												}
////											});
////											animatorSet.start();
////											ViewAnimator
////													.animate(adapter.getViewByPosition(recyclerView,1,R.layout.tanchuang_item))
////													.scale(1,0)
////													.duration(1000)
////
////													.start();
////											ViewAnimator
////													.animate(manager.getChildAt(1))
////													.scale(1,0)
////													.duration(1000)
////													.start();
//										}
//									});




									//Thread.sleep(500);

									Message message=Message.obtain();
									message.what=999;
									handler.sendMessage(message);


								} catch (InterruptedException e) {
									e.printStackTrace();
								}


							}
						}).start();



					} catch (Exception e) {
						//Log.d("WebsocketPushMsg", e.getMessage());
						e.printStackTrace();
					}

			}else if (msg.arg1==2) {
			final 	int ii=msg.arg2;

			//	Type resultType = new TypeToken<String>() {
			//	}.getType();
			//	String time = System.currentTimeMillis() + "";
			//	String data = DateUtils.timedate(time.substring(0, time.length() - 3));
			//	String s = null;
//				try {
//					s = Reservoir.get("time", resultType);
//
//				if (s != null) {
//					if (!data.equals(s)) {
//						//保存当前时间
//						//textView.setText("1");
//						Reservoir.put("time", data);
//						Reservoir.put("shulianshu", 1);
//					}
//					}
//				} catch (IOException e) {
//				e.printStackTrace();
//			}
				final WeiShiBieBean dataBean = (WeiShiBieBean) msg.obj;


				new Thread(new Runnable() {
					@Override
					public void run() {

						try {

							BASE64Decoder decoder = new BASE64Decoder();
							// Base64解码
							final byte[][] b;

							b = new byte[][]{decoder.decodeBuffer(dataBean.getFace().getImage())};
							for (int i = 0; i < b[0].length; ++i) {
								if (b[0][i] < 0) {// 调整异常数据
									b[0][i] += 256;
								}
							}

							ShiBieJiLuBean bean2=new ShiBieJiLuBean();
							bean2.setCount(ii+"");
							bean2.setBytes(b[0]);

//							final int size=shiBieJiLuList.size();
//							if (size>50){
//
//								shiBieJiLuList.clear();
//								adapter.notifyDataSetChanged();
//
//							}
//							shiBieJiLuList.add(bean2);
//							runOnUiThread(new Runnable() {
//								@Override
//								public void run() {
//									int size2=shiBieJiLuList.size();
//									adapter.notifyItemInserted(size2-1);
//									manager.smoothScrollToPosition(recyclerView,null,size2-1);
//									Log.d(TAG, "size2:" + size2);
//								}
//							});

							TanChuangBean bean = new TanChuangBean();
							bean.setBytes(b[0]);
							bean.setName("陌生人");
							bean.setType(-1);
							bean.setTouxiang(null);
							tanchuangList.add(bean);

							runOnUiThread(new Runnable() {
								@Override
								public void run() {

									if (tanchuangList.size()>2){
										tanchuang_RL.setVisibility(View.GONE);
										recyclerView.setVisibility(View.VISIBLE);
									}else {
										tanchuang_RL.setVisibility(View.VISIBLE);
										recyclerView.setVisibility(View.GONE);
										Glide.with(MyApplication.getAppContext())
												.load(b[0])
												.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
												.into(touxiang33);
										tishi_im33.setBackgroundResource(R.drawable.f);

										ViewAnimator
												.animate(tanchuang_RL)
												.scale(0,1)
//												.alpha(0,1)
												.duration(1000)
												.start();
									}

									adapter.notifyItemInserted(tanchuangList.size());
									manager.scrollToPosition(tanchuangList.size() - 1);
								}
							});

							Thread.sleep(8000);
//
//											ViewAnimator
//													.animate(adapter.getViewByPosition(recyclerView,1,R.layout.tanchuang_item))
//													.scale(1,0)
//													.duration(1000)
//													.start();
//
//								if (tanchuangList.size()>1)
//									tanchuangList.remove(1);

							Message message = Message.obtain();
							message.what = 999;
							handler.sendMessage(message);


						} catch (Exception e) {

							Log.d(TAG, e.getMessage() + "陌生人解码");
						}

					}
				}).start();
			}

			return false;
		}
	});



	private class TestLoopAdapter extends LoopPagerAdapter {

		public TestLoopAdapter(RollPagerView viewPager) {
			super(viewPager);
		}

		@Override
		public View getView(ViewGroup container, int position) {
			ImageView view = new ImageView(container.getContext());
			try {

				view.setImageDrawable(getImageDrawable(photoPathList.get(position)));
			} catch (IOException e) {
				Log.d("test", e.getMessage());
			}

			view.setScaleType(ImageView.ScaleType.FIT_XY);
			view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			return view;
		}

		@Override
		public int getRealCount() {
			return photoPathList.size();
		}
	}
	/**
	 * 将文件生成位图
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public BitmapDrawable getImageDrawable(String path)
			throws IOException
	{
		//打开文件
		File file = new File(path);
		if(!file.exists())
		{
			return null;
		}

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] bt = new byte[1024];

		//得到文件的输入流
		InputStream in = new FileInputStream(file);

		//将文件读出到输出流中
		int readLength = in.read(bt);
		while (readLength != -1) {
			outStream.write(bt, 0, readLength);
			readLength = in.read(bt);
		}

		//转换成byte 后 再格式化成位图
		byte[] data = outStream.toByteArray();
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// 生成位图
		BitmapDrawable bd = new BitmapDrawable(getResources(),bitmap);

		return bd;
	}

//	private void play(String path){
//		//Log.d(TAG, "播放完成了222");
//		ijkVideoView.setVideoPath(path);
//
//		ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
//			@Override
//			public void onCompletion(IMediaPlayer iMediaPlayer) {
//			//	Log.d(TAG, "播放完成了");
//				++Video_index;
//				if (Video_index == voidePathList.size())
//					Video_index = 0;
//				play(voidePathList.get(Video_index));
//
//			}
//		});
//		ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//			@Override
//			public void onPrepared(IMediaPlayer mp) {
//
//				ijkVideoView.start();
//				Log.d(TAG, "准备播放");
//				Intent intent=new Intent("delectShiPing");
//				intent.putExtra("idid","fffff");
//				sendBroadcast(intent);
//
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						try {
//							Log.d(TAG, "进来线程");
//							Thread.sleep(3000);
//							Intent intent=new Intent("delectShiPing");
//							intent.putExtra("idid","fffff");
//							sendBroadcast(intent);
//							Log.d(TAG, "进来线程22");
//
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//
//		});
//
//	}

	 static Handler handlerGongGao = new Handler();
	static Runnable runnableGongGao = new Runnable() {

		@Override
		public void run() {
			int cou=gonggaoList.size();
			if (quyu<cou){
				gonggao.startAnimation(sato0);
				gonggao.setText(gonggaoList.get(quyu));

				quyu++;
				handlerGongGao.postDelayed(runnableGongGao,12000);//4秒后再次执行

			}else {
				quyu=0;
				handlerGongGao.postDelayed(runnableGongGao,0);//4秒后再次执行
			}

		}
	};

//	private static Handler handlerGongGao = new Handler(){
//		public  void handleMessage(android.os.Message msg) {
//
//
//
////				//逻辑处理
////				switch (quyu)
////
////				if (isTO){
////					isTO=false;
////					gonggao.setText(ss.substring(0,72));
////				}else {
////					gonggao.setText(ss.substring(72,ss.length()));
////				}
//
//
//		}
//	};



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		dw=dm.widthPixels;
		dh=dm.heightPixels;
	//	DisplayMetrics dm = getResources().getDisplayMetrics();
//		w = dm.widthPixels;
//		h = dm.heightPixels;
		//String rid = JPushInterface.getRegistrationID(getApplicationContext());
		//Log.d(TAG, "极光id"+rid);

		new Thread(new Runnable() {
			@Override
			public void run() {
				JPushInterface.setAlias(VlcVideoActivity.this,1,"linhe");
			}
		}).start();


		//无title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//全屏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
				WindowManager.LayoutParams. FLAG_FULLSCREEN);


		IjkMediaPlayer.loadLibrariesOnce(null);
		IjkMediaPlayer.native_profileBegin("libijkplayer.so");

		tanchuangList=new Vector<>();
		shiBieJiLuList=new Vector<>();
		gonggaoList=new ArrayList<>();

//		if (w > h) { //横屏
//			/**
//			 * 设置为横屏
//			 */
//			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//			}
//
//		} else {

			//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//}

		setContentView(R.layout.activity_video_vlc);
		Button bb=new Button(getApplicationContext());
		bb.setText("hahaha");

//		wm=(WindowManager)getApplicationContext().getSystemService("window");
//		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
//		mInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		view = mInflater.inflate(R.layout.activity_video_vlc, null);
//		wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
//		wmParams.format = PixelFormat.OPAQUE;
//		wmParams.flags=WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//				| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;;
//		wmParams.width=dw;
//		wmParams.height=dh;

		tishi_im33= (ImageView) findViewById(R.id.tishi_im33);
		tishi_tv33= (TextView) findViewById(R.id.tishi_tv33);
		touxiang33= (ImageView) findViewById(R.id.touxiang33);

		scollview= (ScrollView) findViewById(R.id.scollview);
		gonggaobiaoti= (TextView) findViewById(R.id.gonggaobiaoti);
		gonggaoriqi=(TextView) findViewById(R.id.gonggaoriqi);
		gonggao=(TextView)findViewById(R.id.gonggao);
		sato0.setDuration(800);
		sato1.setDuration(800);

		sato0.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				gonggao.startAnimation(sato1);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		Type resultType22222 = new TypeToken<ShiPingBean>() {}.getType();
		Reservoir.getAsync("baocuntupian", resultType22222, new ReservoirGetCallback<ShiPingBean>() {
			@Override
			public void onSuccess(ShiPingBean strings) {
				tupianPath=strings.getVideo();
			}

			@Override
			public void onFailure(Exception e) {

			}

		});

		Type resultType = new TypeToken<GongGaoBean>() {}.getType();
		Reservoir.getAsync("baocungonggao", resultType, new ReservoirGetCallback<GongGaoBean>() {
			@Override
			public void onSuccess(GongGaoBean strings) {
				ss=strings.getContent();
				Log.d(TAG, "获取本地公告:"+ss);
				if (gonggaoList.size()>0){
					gonggaoList.clear();
				}
				if (ss.length()>80){

					StringBuilder sb=new StringBuilder();
					//遍历原始字符串的每一位字符,把它依次加入到sb中
					for(int i=0;i<ss.length();i++){
						sb.append((ss.charAt(i)));//依次加入sb中
						if(i%80==0){
							if (i>0){
								sb.append("&");
							}
						}
					}
					String[] sourceStrArray = sb.toString().split("&");
					for (String aSourceStrArray : sourceStrArray) {
						gonggaoList.add("     "+aSourceStrArray);
						//Log.d(TAG, aSourceStrArray);
					}

					handlerGongGao.postDelayed(runnableGongGao, 1);//启动handler，实现4秒定时循环执行

				}else {
					gonggao.startAnimation(sato0);
					gonggao.setText(ss);

				}

				gonggaobiaoti.setText(strings.getTitle());
				gonggaoriqi.setText(strings.getTimeDays());

			}

			@Override
			public void onFailure(Exception e) {

			}

		});




//		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//			TastyToast.makeText(VlcVideoActivity.this, "该设备不支持蓝牙4.0", Toast.LENGTH_LONG,TastyToast.ERROR).show();
//		}

//		//启动公告滚动条
//		autoScrollTextView = (AutoScrollTextView)findViewById(R.id.TextViewNotic);
//		autoScrollTextView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//			}
//		});



		//marqueeView= (MarqueeView) findViewById(R.id.mv_bar1);
//		marqueeView2= (MarqueeView) findViewById(R.id.mv_bar12);
//		marqueeView.startWithList(vector1);
//		marqueeView2.startWithList(vector2);

		//autoScrollTextView.setText(".");
		//autoScrollTextView.init(getWindowManager());
		//autoScrollTextView.startScroll();
		Type resultType2 = new TypeToken<String>() {}.getType();
		Reservoir.getAsync("guanggao", resultType2, new ReservoirGetCallback<String>() {
			@Override
			public void onSuccess(String strings) {
				//autoScrollTextView.setText(strings);
				//autoScrollTextView.init(getWindowManager());
				//autoScrollTextView.startScroll();
			}

			@Override
			public void onFailure(Exception e) {
				//TastyToast.makeText(TianJiaIPActivity.this, e.getMessage(), Toast.LENGTH_SHORT,TastyToast.ERROR).show();
				//   finish();

			}
		});

		//textView = (TextView) findViewById(R.id.number);

		//实例化过滤器并设置要过滤的广播
		myReceiver = new MyReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("duanxianchonglian");
		intentFilter.addAction("gxshipingdizhi");
		intentFilter.addAction("shoudongshuaxin");
		intentFilter.addAction("updateGonggao");
		intentFilter.addAction("updateTuPian");
		intentFilter.addAction("updateShiPing");
		intentFilter.addAction("delectShiPing");
		intentFilter.addAction(Intent.ACTION_TIME_TICK);

		// 注册广播
		registerReceiver(myReceiver, intentFilter);

		//myReceiverFile = new MyReceiverFile();
//		IntentFilter intentFilter2 = new IntentFilter();
//		intentFilter2.addAction(Intent.ACTION_MEDIA_EJECT);
//		intentFilter2.addAction(Intent.ACTION_MEDIA_MOUNTED);
//		intentFilter2.addDataScheme("file");  //与其它 action 有冲突
//		registerReceiver(myReceiverFile, intentFilter2);

		//logo = BitmapFactory.decodeResource(super.getResources(), R.drawable.yixuanze_22);
		daoSession = MyApplication.getAppContext().getDaoSession().getMoShengRenBeanDao();
		shiPingBeanDao = MyApplication.getAppContext().getDaoSession().getShiPingBeanDao();


		//shiBieJiLuBeanDao = MyApplication.getAppContext().getDaoSession().getShiBieJiLuBeanDao();

//		recyclerView = (RecyclerView) findViewById(R.id.rccy);
//		((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
//		recyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				//Log.i("abc","hasfocus:"+hasFocus);
//				if (hasFocus) {
//					if (recyclerView.getChildCount() > 0) {
//						recyclerView.getChildAt(0).requestFocus();
//					}
//				}
//			}
//		});



		//rollPagerView= (RollPagerView) findViewById(R.id.lunbo);


//		linearLayout = (RelativeLayout) findViewById(R.id.are);
//		linearLayout.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(VlcVideoActivity.this, XiTongSheZhiActivity.class));
//			}
//		});
//		if (w > h) {
//
//			gridLayoutManager = new GridLayoutManager(this, 2);
//			gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//			recyclerView.setLayoutManager(gridLayoutManager);
//			adapter = new MyAdapter(R.layout.erweima_item, moShengRenBean2List);
//			//adapter.addHeaderView(getView(),1);
//			recyclerView.setAdapter(adapter);
//			adapter.openLoadAnimation();
//			//adapter.addFooterView(getView());
//			adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
//			//recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
//		} else {
//
		TanChuangBean bean=new TanChuangBean();
		bean.setBytes(null);
		bean.setName(null);
		bean.setType(-2);
		bean.setTouxiang(null);
		tanchuangList.add(bean);

		mSurfaceView = (SurfaceView) findViewById(R.id.video);


		recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
		//recyclerView2= (RecyclerView) findViewById(R.id.recyclerView2);
//		recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
//		//	用来标记是否正在向最后一个滑动
//			boolean isSlidingToLast = false;
//
//			@Override
//			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//				LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//				// 当不滚动时
//				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//					//获取最后一个完全显示的ItemPosition
//					int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
//					int totalItemCount = manager.getItemCount();
////					Log.d(TAG, "lastVisibleItem:" + lastVisibleItem);
////					Log.d(TAG, "totalItemCount:" + totalItemCount);
//					// 判断是否滚动到底部，并且是向右滚动
//					if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//						//加载更多功能的代码
//						manager2.smoothScrollToPosition(recyclerView2,null,0);
//					}
//
//					if (lastVisibleItem==4 && !isSlidingToLast){
//						manager2.smoothScrollToPosition(recyclerView2,null,shiBieJiLuList.size()-1);
//					}
//
//				}
//			}
//
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//
//				//dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//				//大于0表示正在向下滚动
//				//小于等于0表示停止或向上滚动
//				isSlidingToLast = dy > 0;
//			}
//		});

		tianqi= (TextView) findViewById(R.id.tianqi);
		wendu= (TextView) findViewById(R.id.wendu);
		tanchuang_RL= (RelativeLayout) findViewById(R.id.ytutyii);

		xiaoshi= (TextView) findViewById(R.id.shijian);
		riqi= (TextView) findViewById(R.id.riqi);
		xingqi= (TextView) findViewById(R.id.xingqi);
		final String time=(System.currentTimeMillis()/1000)+"";
		xiaoshi.setText(DateUtils.timeMinute(time));
		riqi.setText(DateUtils.timesTwo(time));
		xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
		//TextView gonggao= (TextView) findViewById(R.id.gonggao);
	//	gonggao.setText("智慧互联 * 开创共赢\n");
		//	mSurfaceView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//		LinearLayout relativeLayout= (LinearLayout) findViewById(R.id.ffgghh);
//		relativeLayout.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//
//			}
//		});

//		Log.d(TAG, "findViewById(R.id.toptv).getWidth():" + findViewById(R.id.toptv).getWidth());

		//mLoadingView = findViewById(R.id.video_loading);

		mSurfaceHolder = mSurfaceView.getHolder();

		mSurfaceHolder.setFormat(PixelFormat.RGBX_8888);
		mSurfaceView.setKeepScreenOn(true);


		libvlc = LibVLCUtil.getLibVLC(null);

		mediaPlayer = new MediaPlayer(libvlc);

		vlcVout = mediaPlayer.getVLCVout();

		callback = new IVLCVout.Callback() {
			@Override
			public void onNewLayout(IVLCVout ivlcVout, int i, int i1, int i2, int i3, int i4, int i5) {
				Log.d(TAG, "i:" + i);
				Log.d(TAG, "i1:" + i1);

			}

			@Override
			public void onSurfacesCreated(IVLCVout ivlcVout) {

				try {

					changeSurfaceSize();

				} catch (Exception e) {
					Log.d("vlc-newlayout", e.toString());
				}
			}

			@Override
			public void onSurfacesDestroyed(IVLCVout ivlcVout) {

					ivlcVout.removeCallback(callback);
			}
		};


		vlcVout.addCallback(callback);
		vlcVout.setVideoView(mSurfaceView);
		vlcVout.attachViews();
		//this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//		mMediaPlayer.setMediaList();
		//		mMediaPlayer.getMediaList().add(new Media(mMediaPlayer, "http://live.3gv.ifeng.com/zixun.m3u8"), false);
		//		mMediaPlayer.playIndex(0);
		//	mMediaPlayer.playMRL("rtsp://192.168.2.52/user=admin&password=&channel=1&stream=0.sdp");

		Type resultType111 = new TypeToken<List<ZhuJiBean>>() {
		}.getType();
		Reservoir.getAsync("fuwuqi", resultType111, new ReservoirGetCallback<List<ZhuJiBean>>() {
			@Override
			public void onSuccess(List<ZhuJiBean> strings) {

				if (strings.size() >= 1) {
					int a = strings.size();
					for (int i = 0; i < a; i++) {
						if (strings.get(i).getIsTrue() == 1) {
							shiping_string = strings.get(i).getDizhi();

							//mMediaPlayer.playMRL(shiping_string);
//							media = new Media(libvlc, Uri.parse(shiping_string));
//							mediaPlayer.setMedia(media);
//
//							if (mediaPlayer != null) {
//								mediaPlayer.play();
//								mSurfaceView.setKeepScreenOn(true);
//							}
							Type resultType = new TypeToken<List<ZhuJiBean>>() {
							}.getType();
							Reservoir.getAsync("zhujidizhi", resultType, new ReservoirGetCallback<List<ZhuJiBean>>() {
								@Override
								public void onSuccess(List<ZhuJiBean> strings) {
									if (strings.size() >= 1) {
										int a = strings.size();
										try {
											for (int i = 0; i < a; i++) {
												if (strings.get(i).getIsTrue() == 1) {
													zhuji_string = strings.get(i).getDizhi();

													if (zhuji_string!=null){
														String[] a1=zhuji_string.split("//");
														String[] b1=a1[1].split(":");
														zhuji="http://"+b1[0];
													}

													Message message = new Message();
													message.what = 111;
													handler.sendMessage(message);

													break;
												}
											}
										}catch (Exception e ){
											Log.d("VlcVideoActivity", e.getMessage());
										}

									}
								}

								@Override
								public void onFailure(Exception e) {

								}
							});

							break;

						}
					}
				}
			}

			@Override
			public void onFailure(Exception e) {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						TastyToast.makeText(VlcVideoActivity.this, "请先设置主机和视频流", Toast.LENGTH_LONG, TastyToast.ERROR).show();
					}
				});
			}
		});

		Settings mSettings = new Settings(this);
		ijkMediaPlayer=new IjkMediaPlayer();
		ijkMediaPlayer.setLogEnabled(false);

//		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
//		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
//		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
//		ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 60);

//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 0);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "fps", 30);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_YV12);
//
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "nobuffer");
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max-buffer-size", 1024);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 10);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probsize", "4096");
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", "2000000");


//		ijkVideoView = (IjkVideoView) findViewById(R.id.vitamio);
//		ijkVideoView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				Message message=Message.obtain();
//				message.what=110;
//				handler.sendMessage(message);
//			}
//		});
//		ijkVideoView.setHudView(new TableLayout(this));


		//String rtspUrl="http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
		//String rtspUrl="mnt/usb_storage/USB_DISK1"+File.separator+"a1.mp4";
		//ijkVideoView.setVideoURI(Uri.parse(rtspUrl));
		//ijkVideoView.setVideoPath(rtspUrl);


//		File storage = new File("/storage");
//		File[] files = storage.listFiles();
//		for (final File file : files) {
//			if (file.canRead()) {
//				if (!file.getName().equals(Environment.getExternalStorageDirectory().getName())) {
//					//满足该条件的文件夹就是u盘在手机上的目录
//					Log.d(TAG, file.getName());
//					Log.d(TAG, Environment.getExternalStorageDirectory().getName());
//
//				}
//			}
//		}
//		File file=new File("mnt/usb_storage/USB_DISK1"+File.separator+"a1.mp4");
//		Log.d(TAG, "file.length():" + file.length());

		manager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
		recyclerView.setLayoutManager(manager);

	//	manager2 = new ScrollSpeedLinearLayoutManger(VlcVideoActivity.this);
	//	manager2.setOrientation(LinearLayoutManager.VERTICAL);
	//	recyclerView2.setLayoutManager(manager2);

		adapter = new MyAdapter(R.layout.tanchuang_item2,tanchuangList);

		//adapter2 = new MyAdapter2(R.layout.shibiejilu_item,shiBieJiLuList);
		//adapter.openLoadAnimation();
		//adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		recyclerView.setAdapter(adapter);
	  recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

	//	recyclerView2.setAdapter(adapter2);
		//recyclerView2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

		daoSession.deleteAll();

		shiPingBeanList=shiPingBeanDao.loadAll();
		Log.d("VlcVideoActivity", "获取视频个数:" + shiPingBeanList.size());
//		try {
//
//		if (shiPingBeanList.size()>0){
//			for (int i=0;i<shiPingBeanList.size();i++){
//				if (shiPingBeanList.get(i).getIsDownload()){
//
//					ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory()+File.separator+"linhefile"+File.separator+shiPingBeanList.get(0).getId()+"."+shiPingBeanList.get(0).getVideoType().split("\\/")[1]);
//
//				}else {
//					Log.d(TAG, "删除没下载的视频记录");
//					shiPingBeanDao.deleteByKey(shiPingBeanList.get(i).getId());
//				}
//			}
//		}
//
//		ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
//			@Override
//			public void onCompletion(IMediaPlayer iMediaPlayer) {
//				shiPingCount++;
//				Log.d(TAG, "视频个数:" + shiPingBeanList.size());
//				if (shiPingCount<=shiPingBeanList.size()-1){
//					String pa=Environment.getExternalStorageDirectory()+File.separator+"linhefile"+File.separator+shiPingBeanList.get(shiPingCount).getId()+"."+shiPingBeanList.get(shiPingCount).getVideoType().split("\\/")[1];
//					ijkVideoView.setVideoPath(pa);
//					ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//						@Override
//						public void onPrepared(IMediaPlayer iMediaPlayer) {
//							ijkVideoView.start();
//						}
//					});
//				}else {
//					shiPingCount=0;
//					ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory()+File.separator+"linhefile"+File.separator+shiPingBeanList.get(0).getId()+"."+shiPingBeanList.get(0).getVideoType().split("\\/")[1]);
//					ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//						@Override
//						public void onPrepared(IMediaPlayer iMediaPlayer) {
//							ijkVideoView.start();
//						}
//					});
//				}
//
//			}
//		});
//
//		}catch (Exception e){
//			Log.d(TAG, "得到本地视频个数异常"+e.getMessage());
//		}
//
//		ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//				@Override
//				public void onPrepared(IMediaPlayer iMediaPlayer) {
//					ijkVideoView.start();
//				}
//			});
//		ijkVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
//			@Override
//			public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
//				Log.d(TAG, "i:" + i);
//				Log.d(TAG, "i1:" + i1);
//				Toast toast=TastyToast.makeText(VlcVideoActivity.this,"播放视频出错",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//				toast.setGravity(Gravity.CENTER,0,0);
//				toast.show();
//
//				//ijkVideoView.pause();
//
//				return true;
//			}
//		});

		link_chengshi();
	}


	// in code.
//	public  void screenShot(View view)
//	{
////xxxxx
//		EGL10 egl = (EGL10) EGLContext.getEGL();
//		GL10 gl = (GL10)egl.eglGetCurrentContext().getGL();
//		Bitmap bp = SavePixels(0, 0, view.getWidth(), view.getHeight(), gl);
//		//保存截屏图片
//		File file = new File(Environment.getExternalStorageDirectory()	  +"/"+ System.currentTimeMillis() + "aaa.jpg");
//		FileOutputStream fos;
//		try {
//			fos = new FileOutputStream(file);
//			bp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//			fos.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////xxx
//	}
//
//	public  Bitmap SavePixels(int x, int y, int w, int h, GL10 gl)
//	{
//		int b[]=new int[w*(y+h)];
//		int bt[]=new int[w*h];
//		IntBuffer ib=IntBuffer.wrap(b);
//		ib.position(0);
//		gl.glReadPixels(x, 0, w, y+h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
//
//		for(int i=0, k=0; i<h; i++, k++)
//		{//remember, that OpenGL bitmap is incompatible with Android bitmap
//			//and so, some correction need.
//			for(int j=0; j<w; j++)
//			{
//				int pix=b[i*w+j];
//				int pb=(pix>>16)&0xff;
//				int pr=(pix<<16)&0x00ff0000;
//				int pix1=(pix&0xff00ff00) | pr | pb;
//				bt[(h-k-1)*w+j]=pix1;
//			}
//		}


//		Bitmap bp=Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
//
//		return bp;
//	}

	// 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
	private void getAllFiles(File root){
		File files[] = root.listFiles();

		if(files != null){
			for (File f : files){
				if(f.isDirectory()){
					getAllFiles(f);
				}else{
				String name=f.getName();
					if (name.substring(name.length()-3,name.length()).equals("mp4")){
						Log.d(TAG, f.getAbsolutePath());
						voidePathList.add(f.getAbsolutePath());

					}else if (name.substring(name.length()-3,name.length()).equals("jpg")||name.substring(name.length()-3,name.length()).equals("png")){
						photoPathList.add(f.getAbsolutePath());
					}

				}
			}
			Log.d(TAG, "voidePathList.size():" + voidePathList.size());
			Message message=Message.obtain();
			message.what=110;
			handler.sendMessage(message);

		}
	}



	@Override
	protected void onStart() {
		super.onStart();

	}

	public  class MyAdapter extends BaseQuickAdapter<TanChuangBean,BaseViewHolder> {
		private View view;
	//	private List<TanChuangBean> d;
		private int p;
		private int vid;


		private MyAdapter(int layoutResId, List<TanChuangBean> data) {
			super(layoutResId, data);
			vid=layoutResId;
		//d=data;
		}


//		private View getView(int p){
//			this.p=p;
//			return view;
//		}

		@Override
		protected void convert(BaseViewHolder helper, TanChuangBean item) {
			//Log.d(TAG, "动画执行");
			ViewAnimator
					.animate(helper.itemView)
					.scale(0,1)
//					.alpha(0,1)
					.duration(1000)
					.start();

		//	LinearLayout toprl= helper.getView(R.id.ffflll);

			ImageView imageView= helper.getView(R.id.touxiang);
			ImageView tishi_im= helper.getView(R.id.tishi_im);
			TextView tishi_tv= helper.getView(R.id.tishi_tv);


			//autoScrollTextView.setText(intent.getStringExtra("ggg"));
//					//
//					//
			if (helper.getLayoutPosition()==0){

				imageView.setImageBitmap(null);
				tishi_im.setBackgroundColor(Color.parseColor("#00000000"));
				tishi_tv.setText("");


			}else {
				switch (item.getType()){
					case -1:
						//陌生人
					//	toprl.setBackgroundResource(R.drawable.tanchuang);
						tishi_tv.setText("您好,这里是工作人员区域\n如办理业务，请前往大厅");
						tishi_im.setBackgroundResource(R.drawable.f);

						break;
					case 0:
						//员工
					//	toprl.setBackgroundResource(R.drawable.tanchuang);
						tishi_tv.setText("欢迎老板");
						tishi_im.setBackgroundResource(R.drawable.tike);

						break;

					case 1:
						//访客
					//	toprl.setBackgroundResource(R.drawable.tanchuang);

						//richeng.setText("");
						//name.setText(item.getName());
						//autoScrollTextView.setText("欢迎你来本公司参观指导。");


						break;
					case 2:
						//VIP访客
						//toprl.setBackgroundResource(R.drawable.tanchuang);
					//	richeng.setText("");
					//	name.setText(item.getName());
						//autoScrollTextView.setText("欢迎VIP访客 "+item.getName()+" 来本公司指导工作。");

						break;


				}


			if (item.getTouxiang()!=null){

				Glide.with(MyApplication.getAppContext())
						.load(zhuji+item.getTouxiang())
						.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
						.into((ImageView) helper.getView(R.id.touxiang));
			}else {
				Glide.with(MyApplication.getAppContext())
						.load(item.getBytes())
						.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
						.into((ImageView) helper.getView(R.id.touxiang));
		    	}
			}


//			RelativeLayout linearLayout_tanchuang = helper.getView(R.id.ffflll);
//				ViewGroup.LayoutParams lp =  linearLayout_tanchuang.getLayoutParams();
//
//				lp.width=dw/2+50;
//				lp.height=dh/7;
//				linearLayout_tanchuang.setLayoutParams(lp);
//			linearLayout_tanchuang.invalidate();

		}

	}



	private  class MyAdapter2 extends BaseQuickAdapter<ShiBieJiLuBean,BaseViewHolder> {

		private MyAdapter2(int layoutResId, List<ShiBieJiLuBean> data) {
			super(layoutResId, data);

		}

		@Override
		protected void convert(BaseViewHolder helper, ShiBieJiLuBean item) {
			//Log.d(TAG, "动画执行");
			ViewAnimator
					.animate(helper.itemView)
					.scale(0,1)
//					.alpha(0,1)
					.duration(1000)
					.start();

//			RelativeLayout toprl= helper.getView(R.id.top11111);
//			RelativeLayout boonemrl= helper.getView(R.id.bootn111);
		//	ImageView imageView= helper.getView(R.id.touxiang);
			TextView miaoshu=helper.getView(R.id.miaoshu);
			TextView time=helper.getView(R.id.time);
		//	TextView autoScrollTextView=helper.getView(R.id.richeng);
			miaoshu.setText(item.getCount());
			time.setText(item.getTimes());

				if (item.getUrlPath()!=null){

					Glide.with(MyApplication.getAppContext())
							.load(zhuji+item.getUrlPath())
							.transform(new GlideCircleTransform(MyApplication.getAppContext()))
							.into((ImageView) helper.getView(R.id.touxiang));
				}else {

					Glide.with(MyApplication.getAppContext())
							.load(item.getBytes())
							.transform(new GlideCircleTransform(MyApplication.getAppContext()))
							.into((ImageView) helper.getView(R.id.touxiang));
				}
			}

	}

//	/**
//	 * 生成二维码
//	 * @param string 二维码中包含的文本信息
//	 * @param mBitmap logo图片
//	 * @param format  编码格式
//	 * [url=home.php?mod=space&uid=309376]@return[/url] Bitmap 位图
//	 * @throws WriterException
//	 */
//	private static final int IMAGE_HALFWIDTH = 1;//宽度值，影响中间图片大小
//	public Bitmap createCode(String string,Bitmap mBitmap, BarcodeFormat format)
//			throws WriterException {
//		Matrix m = new Matrix();
//		float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
//		float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
//		m.setScale(sx, sy);//设置缩放信息
//		//将logo图片按martix设置的信息缩放
//		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
//				mBitmap.getWidth(), mBitmap.getHeight(), m, false);
//		MultiFormatWriter writer = new MultiFormatWriter();
//		Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
//		hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");//设置字符编码
//		BitMatrix matrix = writer.encode(string, format, 600, 600, hst);//生成二维码矩阵信息
//		int width = matrix.getWidth();//矩阵高度
//		int height = matrix.getHeight();//矩阵宽度
//		int halfW = width/2;
//		int halfH = height/2;
//		int[] pixels = new int[width * height];//定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
//		for (int y = 0; y < height; y++) {//从行开始迭代矩阵
//			for (int x = 0; x < width; x++) {//迭代列
//				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
//						&& y > halfH - IMAGE_HALFWIDTH
//						&& y < halfH + IMAGE_HALFWIDTH) {//该位置用于存放图片信息
//			//记录图片每个像素信息
//					pixels[y * width + x] = mBitmap.getPixel(x - halfW
//							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);              } else {
//					if (matrix.get(x, y)) {//如果有黑块点，记录信息
//						pixels[y * width + x] = 0xff000000;//记录黑块信息
//					}
//				}
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Bitmap.Config.ARGB_8888);
//		// 通过像素数组生成bitmap
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		return bitmap;
//	}
//	private class MyReceiverFile  extends BroadcastReceiver {
//
//		@Override
//		public void onReceive(Context context, final Intent intent) {
//			String action = intent.getAction();
//			if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
//				//USB设备移除，更新UI
//				Log.d(TAG, "设备被移出");
////				if (rollPagerView!=null){
////					if (rollPagerView.isPlaying()){
////						rollPagerView.pause();
////					}
////
////
////				}
//				if (ijkMediaPlayer!=null){
//					Log.d(TAG, "播放暂停");
//					//ijkVideoView.pause();
//					//ijkVideoView.canPause();
//
//					//ijkVideoView.stopPlayback();
//					//ijkMediaPlayer.stop();
//				}
//			}
////			else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
////				//USB设备挂载，更新UI
////				Log.d(TAG, "设备插入");
////				new Thread(new Runnable() {
////					@Override
////					public void run() {
////						String usbPath = intent.getDataString();//（usb在手机上的路径）
////
////						getAllFiles(new File(usbPath.split("file:///")[1]+File.separator+"file"));
////						Log.d(TAG, usbPath);
////					}
////				}).start();
////			}
//
//		}
//	}


	private class MyReceiver  extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, final Intent intent) {
			//Log.d(TAG, "intent:" + intent.getAction());

			if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
				//ijkVideoView.requestFocus();
				String time=(System.currentTimeMillis()/1000)+"";
				xiaoshi.setText(DateUtils.timeMinute(time));
				riqi.setText(DateUtils.timesTwo(time));
				xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
				if (DateUtils.timeMinute(time).equals("06:00")){
					link_chengshi();
					Reservoir.putAsync("shulianshu",  0, new ReservoirPutCallback() {
						@Override
						public void onSuccess() {
							//Log.d("WebsocketPushMsg", "保存刷脸人数成功"+i);

						}

						@Override
						public void onFailure(Exception e) {
							Log.d("WebsocketPushMsg", e.getMessage());
							//error
						}
					});
				}


//				if (System.currentTimeMillis()-timing>30000 &&  System.currentTimeMillis()-timing<93000){
//					if (ijkMediaPlayer!=null && ijkVideoView!=null){
//							if (!ijkVideoView.isPlaying()){
//								zixunLL.setVisibility(View.GONE);
//								ijkVideoView.setVisibility(View.VISIBLE);
//								ijkVideoView.start();
//								Log.d(TAG, "设置视频显示");
//								ijkVideoView.requestFocus();
//								scollview.setVisibility(View.GONE);
//							}else {
//								zixunLL.setVisibility(View.GONE);
//								scollview.setVisibility(View.GONE);
//								ijkVideoView.setVisibility(View.VISIBLE);
//								ijkVideoView.requestFocus();
//							}
//					}
//
//
//				}


			} else {
				if (intent.getAction().equals("duanxianchonglian")) {
					//断线重连
					//if (webSocketClient!=null){
					//	if (!webSocketClient.isOpen()){
					try {
						WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
						if (zhuji_string != null && shiping_string != null) {
							websocketPushMsg.startConnection(zhuji_string, shiping_string);
						}
						//恢复视频流
						startActivity(new Intent(VlcVideoActivity.this, ErWeiMaActivity.class));

					} catch (URISyntaxException e) {
						e.printStackTrace();

					}
				}
				if (intent.getAction().equals("gxshipingdizhi")) {
					//更新视频流地址
					//Log.d(TAG, "收到更新地址广播");
					String a = intent.getStringExtra("gxsp");
					String b = intent.getStringExtra("gxzj");
					if (!a.equals("")) {
						shiping_string = a;
						media = new Media(libvlc, Uri.parse(shiping_string));
						mediaPlayer.setMedia(media);
						mediaPlayer.play();
						//mMediaPlayer.playMRL(shiping_string);
						startActivity(new Intent(VlcVideoActivity.this, ErWeiMaActivity.class));
					}
					if (!b.equals("")) {

						zhuji_string = b;
						try {
							String[] a1=zhuji_string.split("//");
							String[] b1=a1[1].split(":");
							zhuji="http://"+b1[0];

							WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
							if (zhuji_string != null && shiping_string != null) {
								websocketPushMsg.startConnection(zhuji_string, shiping_string);
							}
						} catch (URISyntaxException e) {
							e.printStackTrace();

						}

					}

				}
				if (intent.getAction().equals("shoudongshuaxin")) {
					if (shiping_string != null && zhuji_string != null) {

						media = new Media(libvlc, Uri.parse(shiping_string));
						mediaPlayer.setMedia(media);
						mediaPlayer.play();
						//mLoadingView.setVisibility(View.GONE);

						try {
							WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
							if (zhuji_string != null && shiping_string != null) {
								websocketPushMsg.startConnection(zhuji_string, shiping_string);
							}
						} catch (URISyntaxException e) {
							e.printStackTrace();

						}

						startActivity(new Intent(VlcVideoActivity.this, ErWeiMaActivity.class));
					}

				}
//				if (intent.getAction().equals("guanggao")) {
//
//					//autoScrollTextView.setText(intent.getStringExtra("ggg"));
//					//autoScrollTextView.init(getWindowManager());
//					//autoScrollTextView.startScroll();
//
//				}

			}
			if (intent.getAction().equals("updateGonggao")){

				handlerGongGao.removeCallbacks(runnableGongGao);

				Type resultType = new TypeToken<GongGaoBean>() {}.getType();
				Reservoir.getAsync("baocungonggao", resultType, new ReservoirGetCallback<GongGaoBean>() {
					@Override
					public void onSuccess(GongGaoBean strings) {

						ss=strings.getContent();

						if (gonggaoList.size()>0){
							gonggaoList.clear();
						}
						if (ss.length()>80){
							isChange=true;
							StringBuilder sb=new StringBuilder();
							//遍历原始字符串的每一位字符,把它依次加入到sb中
							for(int i=0;i<ss.length();i++){
								sb.append((ss.charAt(i)));//依次加入sb中
								if(i%80==0){
									if (i>0){
										sb.append("&");
									}
								}
							}
							String[] sourceStrArray = sb.toString().split("&");
							for (String aSourceStrArray : sourceStrArray) {
								gonggaoList.add("     "+aSourceStrArray);
							}

							handlerGongGao.postDelayed(runnableGongGao,0);//4秒后再次执行

						}else {
							gonggao.startAnimation(sato0);
							gonggao.setText(ss);

						}

						gonggaobiaoti.setText(strings.getTitle());
						gonggaoriqi.setText(strings.getTimeDays());
					}

					@Override
					public void onFailure(Exception e) {


					}

						});

			}
//			if (intent.getAction().equals("updateShiPing")){
//
//
//				ShiPingBean shiBieJiLuBean=shiPingBeanDao.load(intent.getStringExtra("idid"));
////				Log.d("VlcVideoActivity", "更新视频ID："+shiBieJiLuBean.getId());
//				if (!shiBieJiLuBean.getIsDownload()){
//
//				Intent intent33 = new Intent(VlcVideoActivity.this, DownloadService.class);
//				intent33.putExtra("url", "http://192.168.2.100:80/rtps/a"+shiBieJiLuBean.getVideo());
//				//intent33.putExtra("url", "http://183.60.197.32/4/w/k/d/l/wkdlmnucuyduwzdqfjtmdvsxfsotsv/he.yinyuetai.com/C033015644E6D35D99022E014A4761A1.flv?sc\\u003d35b0e75f99e3878e\\u0026br\\u003d3138\\u0026vid\\u003d2650626\\u0026aid\\u003d167\\u0026area\\u003dHT\\u0026vst\\u003d2");
//				fileNames=shiBieJiLuBean.getId();
//					try {
//						fileType=shiBieJiLuBean.getVideoType().split("\\/")[1];
//					}catch (Exception e){
//						Log.d(TAG, "截取最后视频格式错误"+e.getMessage());
//						return;
//					}
//
//				intent33.putExtra("receiver", new DownloadReceiver(new Handler()));
//				intent33.putExtra("urlName",fileNames);
//				intent33.putExtra("nameType",fileType);
//				startService(intent33);
//				Log.d(TAG, "收到下载视频的广播　,下载地址"+fileType);
//				}else {
//
//					if (shiPingBeanList.size()>0){
//						shiPingBeanList.clear();
//					}
//					shiPingBeanList=shiPingBeanDao.loadAll();
//
//					ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory()+File.separator+"linhefile"+File.separator+shiBieJiLuBean.getId()+"."+shiBieJiLuBean.getVideoType().split("\\/")[1]);
//					ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//						@Override
//						public void onPrepared(IMediaPlayer iMediaPlayer) {
//							shiPingCount=-1;
//							ijkVideoView.start();
//						}
//					});
//				}
//
//			}
//			if (intent.getAction().equals("delectShiPing")){
//
//				final String idid=intent.getStringExtra("idid");
//
//				Log.d(TAG, "收到删除视频的广播");
//
//				try {
//
////				int ii= shiPingBeanList.size();
////				Log.d(TAG, "iiiiii:" + ii);
////				int i2=0;
////				if (ii>1){
////					for (int i=ii-1;i>=0;i--){
////						Log.d(TAG, "i:" + i);
////
////						if (shiPingBeanList.get(i).getIsDownload()){
////							i2++;
////							if (i2>=1){
////								//视频文件大于２　并且都是下载过的,可以暂停　删除本地文件
////								//删除数据库
//								final String ty=shiPingBeanDao.load(idid).getVideoType().split("\\/")[1];
//
//								new Thread(new Runnable() {
//									@Override
//									public void run() {
//										String path= Environment.getExternalStorageDirectory()+File.separator+"linhefile";
//
//										getAllFiles(new File(path),idid+"."+ty);
//									}
//								}).start();
//
//								shiPingBeanDao.deleteByKey(idid);
//
//								if (ijkVideoView.isPlaying()){
//									ijkVideoView.pause();
//								}
//								//更新视频列表
//								if (shiPingBeanList.size()>0){
//									shiPingBeanList.clear();
//								}
//								shiPingBeanList=shiPingBeanDao.loadAll();
//								int spl=shiPingBeanList.size();
//								Log.d(TAG, "现在shiPingCount:" + shiPingCount);
//								if (spl>0){
//									if (shiPingCount>0){
//										--shiPingCount;
//									}
//									ShiPingBean sssss=shiPingBeanList.get(shiPingCount);
//									ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory()+File.separator+"linhefile"+File.separator+ sssss.getId()+"."+sssss.getVideoType().split("\\/")[1]);
//									ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//										@Override
//										public void onPrepared(IMediaPlayer iMediaPlayer) {
//											ijkVideoView.start();
//										}
//									});
//								}
//
//
//
////							}else {
////								TastyToast.makeText(VlcVideoActivity.this,"不能删除，必须有一个可以播放的视频",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
////							}
////						}
////
////					}
////
////				}else {
////					TastyToast.makeText(VlcVideoActivity.this,"不能删除，必须有一个可以播放的视频",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
////				}
//
//				}catch (Exception e){
//					Log.d(TAG, "捕捉到异常delectShiPing"+e.getMessage());
//			}
//
////				}catch (Exception e){
////					Log.d(TAG, e.getMessage());
////					TastyToast.makeText(VlcVideoActivity.this,"删除视频出错",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
////				}
//
//
//			}
//			if (intent.getAction().equals("updateTuPian")){
//				Log.d(TAG, "收到图片推送");
//				Type resultType = new TypeToken<ShiPingBean>() {}.getType();
//				Reservoir.getAsync("baocuntupian", resultType, new ReservoirGetCallback<ShiPingBean>() {
//					@Override
//					public void onSuccess(ShiPingBean strings) {
//						Log.d(TAG, "收到图片推送2");
//						tupianPath=strings.getVideo();
//                        Intent intent33 = new Intent(VlcVideoActivity.this, DownloadTuPianService.class);
//                        intent33.putExtra("receiver", new DownloadReceiverTuPian(new Handler()));
//                        intent33.putExtra("url", "http://192.168.2.100:80/rtps/a"+tupianPath);
//                        startService(intent33);
//						Log.d(TAG, "收到图片推送3");
//					}
//
//					@Override
//					public void onFailure(Exception e) {
//
//
//					}
//
//				});
//			}
		}

	}

//	private void link_gengxing_erweima() {
//
//		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();
//		RequestBody body = new FormBody.Builder()
//				.add("cmd","getUnSignList")
////                .add("subjectId",zhaoPianBean.getId()+"")
////                .add("subjectPhoto",zhaoPianBean.getAvatar())
//				.build();
//		Request.Builder requestBuilder = new Request.Builder()
//				.header("Content-Type", "application/json")
//				.post(body)
//				.url("http://192.168.2.17:8080/sign");
//
//		// step 3：创建 Call 对象
//		Call call = okHttpClient.newCall(requestBuilder.build());
//
//		//step 4: 开始异步请求
//		call.enqueue(new Callback() {
//			@Override
//			public void onFailure(Call call, IOException e) {
//
//				Log.d("AllConnects", "请求获取二维码失败"+e.getMessage());
//			}
//
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				//Log.d("AllConnects", "请求获取二维码成功"+call.request().toString());
//				//获得返回体
//				//List<YongHuBean> yongHuBeanList=new ArrayList<>();
//				//List<MoShengRenBean2> intlist=new ArrayList<>();
//			//	intlist.addAll(moShengRenBean2List);
//				try {
//
//				if (moShengRenBean2List.size()!=0)
//				 moShengRenBean2List.clear();
//				ResponseBody body = response.body();
//				//  Log.d("AllConnects", "aa   "+response.body().string());
//
//					JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
//					Gson gson=new Gson();
//						int code=jsonObject.get("resultCode").getAsInt();
//						if (code==0){
//					JsonArray array=jsonObject.get("data").getAsJsonArray();
//					int a=array.size();
//					for (int i=0;i<a;i++){
//						YongHuBean zhaoPianBean=gson.fromJson(array.get(i),YongHuBean.class);
//						moShengRenBean2List.add(zhaoPianBean);
//						//Log.d("VlcVideoActivity", zhaoPianBean.getSubjectId());
//					}
//						//	Log.d("VlcVideoActivity", "moShengRenBean2List.size():" + moShengRenBean2List.size());
////					int a1=intlist.size();
////					int b=yongHuBeanList.size();
////
////						for (int i=0;i<a1;i++){
////							for (int j=0;j<b;j++){
////								Log.d("VlcVideoActivity", intlist.get(i).getId()+"ttt");
////								Log.d("VlcVideoActivity", yongHuBeanList.get(j).getSubjectId()+"ttt");
////								if (intlist.get(i).getId().equals(yongHuBeanList.get(j).getSubjectId())){
////									moShengRenBean2List.add(intlist.get(i));
////								}
////							}
////						}
//
//					Message message=Message.obtain();
//					message.what=12;
//					//  message.obj=yongHuBeanList;
//					handler.sendMessage(message);
//					}
//
//				}catch (Exception e){
//					Log.d("WebsocketPushMsg", e.getMessage());
//				}
//
//			}
//		});
//
//	}


	// 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
	public static void getAllFiles(File root,String nameaaa){
		File files[] = root.listFiles();

		if(files != null){
			for (File f : files){
				if(f.isDirectory()){
					getAllFiles(f,nameaaa);
				}else{
					String name=f.getName();
					if (name.equals(nameaaa)){
						Log.d(TAG, "视频文件删除:" + f.delete());
					}
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
//		 dw = Utils.getDisplaySize(getApplicationContext()).x;
//		 dh = Utils.getDisplaySize(getApplicationContext()).y;



//		if (ijkVideoView!=null){
//			if (!ijkVideoView.isPlaying()){
//				ijkVideoView.start();
//			}
//		}

//		if (mediaPlayer==null){
//			mediaPlayer = new MediaPlayer(libvlc);
//			vlcVout = mediaPlayer.getVLCVout();
//
//			callback = new IVLCVout.Callback() {
//				@Override
//				public void onNewLayout(IVLCVout ivlcVout, int i, int i1, int i2, int i3, int i4, int i5) {
//
//				}
//
//				@Override
//				public void onSurfacesCreated(IVLCVout ivlcVout) {
//					try {
//
//						changeSurfaceSize();
//
//					} catch (Exception e) {
//						Log.d("vlc-newlayout", e.toString());
//					}
//				}
//
//				@Override
//				public void onSurfacesDestroyed(IVLCVout ivlcVout) {
//
//				}
//			};
//
//			vlcVout.addCallback(callback);
//			vlcVout.setVideoView(mSurfaceView);
//			vlcVout.attachViews();
//		}
//
//		if (mediaPlayer != null) {
//
//			mSurfaceView.setKeepScreenOn(true);
//			if (shiping_string!=null){
//				media = new Media(libvlc, Uri.parse(shiping_string));
//				mediaPlayer.setMedia(media);
//			//	Log.d(TAG, "ggggggggggggggggg"+shiping_string);
//				mediaPlayer.play();
//
//			}
//
//		}

//		Type resultType = new TypeToken<Integer>() {
//		}.getType();
//		Reservoir.getAsync("shulianshu", resultType, new ReservoirGetCallback<Integer>() {
//			@Override
//			public void onSuccess(Integer i) {
//				//Log.d("WebsocketPushMsg", "i:" + i);
//				//textView.setText(i+"");
//
//			}
//
//			@Override
//			public void onFailure(Exception e) {
//				//Log.d("WebsocketPushMsg", e.getMessage());
//
//
//			}
//		});

	//	link_gengxing_erweima();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
//
//		if (ijkVideoView!=null){
//			if (!ijkVideoView.isPlaying()){
//				ijkVideoView.start();
//			}
//		}
//
//		if (mediaPlayer==null){
//			mediaPlayer = new MediaPlayer(libvlc);
//			vlcVout = mediaPlayer.getVLCVout();
//
//			callback = new IVLCVout.Callback() {
//				@Override
//				public void onNewLayout(IVLCVout ivlcVout, int i, int i1, int i2, int i3, int i4, int i5) {
//
//				}
//
//				@Override
//				public void onSurfacesCreated(IVLCVout ivlcVout) {
//					try {
//
//						changeSurfaceSize();
//
//					} catch (Exception e) {
//						Log.d("vlc-newlayout", e.toString());
//					}
//				}
//
//				@Override
//				public void onSurfacesDestroyed(IVLCVout ivlcVout) {
//
//				}
//			};
//
//			vlcVout.addCallback(callback);
//			vlcVout.setVideoView(mSurfaceView);
//			vlcVout.attachViews();
//		}
//
//		if (mediaPlayer != null) {
//
//			mSurfaceView.setKeepScreenOn(true);
//			if (shiping_string!=null){
//				media = new Media(libvlc, Uri.parse(shiping_string));
//				mediaPlayer.setMedia(media);
//				//	Log.d(TAG, "ggggggggggggggggg"+shiping_string);
//				mediaPlayer.play();
//
//			}
//
//		}

	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "暂停");
//		if (mediaPlayer != null) {
//			mediaPlayer.pause();
//		}
//		if (ijkVideoView!=null){
//			ijkVideoView.pause();
//		}

	}

	@Override
	protected void onDestroy() {
		shiPingCount=0;

		quyu=0;
		gonggaoList=null;
		handlerGongGao.removeCallbacks(runnableGongGao);
		super.onDestroy();
//		if (mediaPlayer != null) {
//			mediaPlayer.release();
//			mediaPlayer=null;
//			vlcVout=null;
//		}
		//ijkVideoView.stopPlayback();

		handler.removeCallbacksAndMessages(null);
		if (myReceiver!=null)
		unregisterReceiver(myReceiver);
		//unregisterReceiver(myReceiverFile);

	}

//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		setSurfaceSize(mVideoWidth, mVideoHeight, mVideoVisibleWidth, mVideoVisibleHeight, mSarNum, mSarDen);
//		super.onConfigurationChanged(newConfig);
//}

//	@Override
//	public void surfaceCreated(SurfaceHolder holder) {
//		if (mMediaPlayer != null) {
//			mSurfaceHolder = holder;
//			mMediaPlayer.attachSurface(holder.getSurface(), this);
//		}
//	}
//
//	@Override
//	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//		mSurfaceHolder = holder;
//		if (mMediaPlayer != null) {
//			mMediaPlayer.attachSurface(holder.getSurface(), this);//, width, height
//		}
//		if (width > 0) {
//			mVideoHeight = height;
//			mVideoWidth = width;
//		}
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		if (mMediaPlayer != null) {
//			mMediaPlayer.detachSurface();
//		}
//	}
//
//	@Override
//	public void setSurfaceSize(int width, int height, int visible_width, int visible_height, int sar_num, int sar_den) {
//
//		mVideoHeight = height;
//		mVideoWidth = width;
//		mVideoVisibleHeight = visible_height;
//		mVideoVisibleWidth = visible_width;
//		mSarNum = sar_num;
//		mSarDen = sar_den;
//		mHandler.removeMessages(HANDLER_SURFACE_SIZE);
//		mHandler.sendEmptyMessage(HANDLER_SURFACE_SIZE);
//	}

	//private static final int HANDLER_BUFFER_START = 1;
//	private static final int HANDLER_BUFFER_END = 2;
	//private static final int HANDLER_SURFACE_SIZE = 3;

//	private static final int SURFACE_BEST_FIT = 0;
//	private static final int SURFACE_FIT_HORIZONTAL = 1;
//	private static final int SURFACE_FIT_VERTICAL = 2;
//	private static final int SURFACE_FILL = 3;
//	private static final int SURFACE_16_9 = 4;
//	private static final int SURFACE_4_3 = 5;
	//private static final int SURFACE_ORIGINAL = 6;
//	private int mCurrentSize = SURFACE_BEST_FIT;

//	private Handler mVlcHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg == null || msg.getData() == null)
//				return;
//
//			switch (msg.getData().getInt("event")) {
//			case EventHandler.MediaPlayerTimeChanged:
//				break;
//			case EventHandler.MediaPlayerPositionChanged:
//				break;
//			case EventHandler.MediaPlayerPlaying:
//				mHandler.removeMessages(HANDLER_BUFFER_END);
//				mHandler.sendEmptyMessage(HANDLER_BUFFER_END);
//				break;
//			case EventHandler.MediaPlayerBuffering:
//				break;
//			case EventHandler.MediaPlayerLengthChanged:
//				break;
//			case EventHandler.MediaPlayerEndReached:
//				//播放完成
//				break;
//			}
//
//		}
//	};

//	private Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case HANDLER_BUFFER_START:
//                showLoading();
//				break;
//			case HANDLER_BUFFER_END:
//                hideLoading();
//				break;
//			case HANDLER_SURFACE_SIZE:
//				changeSurfaceSize();
//				break;
//			}
//		}
//	};


	private void changeSurfaceSize() {
		// get screen size
		int dw = Utils.getDisplaySize(getApplicationContext()).x;
		int dh = Utils.getDisplaySize(getApplicationContext()).y;
		Log.d(TAG, "dw:" + dw);
		//Log.d(TAG, "dh:" + dh);
//		// calculate aspect ratio
//		double ar = (double) mVideoWidth / (double) mVideoHeight;
//		// calculate display aspect ratio
//		double dar = (double) dw / (double) dh;
//
//		switch (mCurrentSize) {
//		case SURFACE_BEST_FIT:
//			if (dar < ar)
//				dh = (int) (dw / ar);
//			else
//				dw = (int) (dh * ar);
//			break;
//		case SURFACE_FIT_HORIZONTAL:
//			dh = (int) (dw / ar);
//			break;
//		case SURFACE_FIT_VERTICAL:
//			dw = (int) (dh * ar);
//			break;
//		case SURFACE_FILL:
//			break;
//		case SURFACE_16_9:
//			ar = 16.0 / 9.0;
//			if (dar < ar)
//				dh = (int) (dw / ar);
//			else
//				dw = (int) (dh * ar);
//			break;
//		case SURFACE_4_3:
//			ar = 4.0 / 3.0;
//			if (dar < ar)
//				dh = (int) (dw / ar);
//			else
//				dw = (int) (dh * ar);
//			break;
//		case SURFACE_ORIGINAL:
//			dh = mVideoHeight;
//			dw = mVideoWidth;
//			break;
//		}
//		Log.d(TAG, "mVideoHeight:" + mVideoHeight);
//		Log.d(TAG, "(dh*3)/2:" + ((dh /3) * 2));
//		Log.d(TAG, "mVideoWidth:" + mVideoWidth);
	//	mSurfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);

		//if (w<h){
//			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
//
//			lp.width = dw;
//			lp.height = dh;
//			//Log.d(TAG, "dh:" + dh);
//			linearLayout.setLayoutParams(lp);
//			linearLayout.invalidate();

		//Log.d(TAG, "mediaPlayer22:" + mediaPlayer);
//
//		}else {
//
//			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
//			lp.gravity= Gravity.TOP;
//
//			lp.width = (dw/2)+dip2px(context,30);
//			lp.height = dh-dip2px(this,90);
//			lp.topMargin=dip2px(this,30);
//			lp.leftMargin=dip2px(this,30);
//			linearLayout.setLayoutParams(lp);
//			linearLayout.invalidate();
//		}

		//mSurfaceView.setLayoutParams(lp);
		//mSurfaceView.invalidate();
		if (mediaPlayer != null) {
			if (shiping_string!=null){
				media = new Media(libvlc, Uri.parse(shiping_string));
				mediaPlayer.setMedia(media);
				Log.d(TAG, "ggggggggggggggggg"+shiping_string);
				mediaPlayer.play();
				mSurfaceView.setKeepScreenOn(true);
			}

		}



	}
//	/**
//	 * websocket接口返回face.image
//	 * image为base64编码的字符串
//	 * 将字符串转为可以识别的图片
//	 * @param imgStr
//	 * @return
//	 */
//	public Bitmap generateImage(String imgStr, int cont, WBWeiShiBieDATABean dataBean, Context context) throws Exception {
//		// 对字节数组字符串进行Base64解码并生成图片
//		if (imgStr == null) // 图像数据为空
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// Base64解码
//			final byte[][] b = {decoder.decodeBuffer(imgStr)};
//			for (int i = 0; i < b[0].length; ++i) {
//				if (b[0][i] < 0) {// 调整异常数据
//					b[0][i] += 256;
//				}
//			}
//			MoShengRenBean2 moShengRenBean2=new MoShengRenBean2();
//			moShengRenBean2.setId(dataBean.getTrack());
//			moShengRenBean2.setBytes(b[0]);
//			moShengRenBean2.setUrl("dd");
//
//			moShengRenBean2List.add(moShengRenBean2);
//
//				adapter.notifyDataSetChanged();
//
//
//
//
//
//			//   Bitmap bitmap= BitmapFactory.decodeByteArray(b[0],0, b[0].length);
//
//			//  Log.d("WebsocketPushMsg", "bitmap.getHeight():" + bitmap.getHeight());
//
//			// 生成jpeg图片
//			//  OutputStream out = new FileOutputStream(imgFilePath);
//			//   out.write(b);
//			//  out.flush();
//			//  out.close();
//
//
////			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
////				@Override
////				public void onDismiss(DialogInterface dialog) {
////					Log.d("VlcVideoActivity", "Dialog销毁2");
////					b[0] =null;
////				}
////			});
//			//dialog.show();
//
//
//			return null;
//		} catch (Exception e) {
//			throw e;
//
//		}
//	}

	public  int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dipValue * scale + 0.5f);
	}
	public  int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(pxValue / scale + 0.5f);
	}
	/**
	 * 识别消息推送
	 * 主机盒子端API ws://[主机ip]:9000/video
	 * 通过 websocket 获取 识别结果
	 * @author Wangshutao
	 */
	private class WebsocketPushMsg {
		/** * 识别消息推送
		 * @param wsUrl websocket接口 例如 ws://192.168.1.50:9000/video
		 * @param rtspUrl 视频流地址 门禁管理-门禁设备-视频流地址
		 *                例如 rtsp://192.168.0.100/live1.sdp
		 *                或者 rtsp://admin:admin12345@192.168.1.64/live1.sdp
		 *                或者 rtsp://192.168.1.103/user=admin&password=&channel=1&stream=0.sdp
		 *                或者 rtsp://192.168.1.100/live1.sdp
		 *                       ?__exper_tuner=lingyun&__exper_tuner_username=admin
		 *                       &__exper_tuner_password=admin&__exper_mentor=motion
		 *                       &__exper_levels=312,1,625,1,1250,1,2500,1,5000,1,5000,2,10000,2,10000,4,10000,8,10000,10
		 *                       &__exper_initlevel=6
		 * @throws URISyntaxException
		 * @throws
		 * @throws
		 *
		 *  ://192.168.2.52/user=admin&password=&channel=1&stream=0.sdp
		 *
		 *   rtsp://192.166.2.55:554/user=admin_password=tljwpbo6_channel=1_stream=0.sdp?real_stream
		 */
		public void startConnection(String wsUrl, String rtspUrl) throws URISyntaxException {
			//当视频流地址中出现&符号时，需要进行进行url编码
			if (rtspUrl.contains("&")){
				try {
					//Log.d("WebsocketPushMsg", "dddddttttttttttttttt"+rtspUrl);
					rtspUrl = URLEncoder.encode(rtspUrl,"UTF-8");

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					//Log.d("WebsocketPushMsg", e.getMessage());
				}
			}

			URI uri = URI.create(wsUrl + "?url=" + rtspUrl);
		//	Log.d("WebsocketPushMsg", "url="+uri);
			  webSocketClient = new WebSocketClient(uri) {
			//	private Vector vector=new Vector();

				@Override
				public void onOpen(ServerHandshake serverHandshake) {
					//Log.d("WebsocketPushMsg onOpen", serverHandshake.getHttpStatusMessage());
						if (this.isOpen()){
							if (conntionHandler!=null && runnable!=null){
								conntionHandler.removeCallbacks(runnable);
								conntionHandler=null;
								runnable=null;
								//Log.d("WebsocketPushMsg", "终止runnable");
							}

						}
				}

				@Override
				public void onMessage(String ss) {

//					  Log.d("WebsocketPushMsg", "onMessage:"+ss);
//
//					     if(ss.length() > 3000) {
//                        for(int i=0;i<ss.length();i+=3000){
//                            if(i+3000<ss.length())
//                                Log.i("fffffffff"+i,ss.substring(i, i+3000));
//
//                            else
//                                Log.i("fffffffff"+i,ss.substring(i, ss.length()));
//                        }
//                    } else
//                        Log.i("ffffffffff", ss);


				//
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					WBBean wbBean= gson.fromJson(jsonObject, WBBean.class);

					//Log.d("WebsocketPushMsg", wbBean.getType());
					if (wbBean.getType().equals("recognized")) { //识别
						//Log.d("WebsocketPushMsg", "识别出了");

						//String s = ss.replace("\\\\\\", "").replace("\"tag\": \"{\"", "\"tag\": {\"").replace("jpg\"}\"", "jpg\"}");
					//	JsonObject jsonObject5 = GsonUtil.parse(ss).getAsJsonObject();

					//	JsonObject jsonObject1 = jsonObject.get("data").getAsJsonObject();
						//JsonObject jsonObject2 = jsonObject5.get("person").getAsJsonObject();
						//   JsonObject jsonObject3=jsonObject.get("screen").getAsJsonObject();
						final ShiBieBean dataBean = gson.fromJson(jsonObject, ShiBieBean.class);
						//Log.d("WebsocketPushMsg", dataBean.getPerson().getSrc()+"kkkk");

					//	final WBShiBiePersonBean personBean = gson.fromJson(jsonObject2, WBShiBiePersonBean.class);
						//Log.d("WebsocketPushMsg", "personBean.getSubject_type():" + personBean.getSubject_type());

//						if (dataBean.getPerson().getSubject_type() == 2) {
//
//							//Log.d("WebsocketPushMsg", personBean.getAvatar());
//							runOnUiThread(new Runnable() {
//								@Override
//								public void run() {
//
//									stringVector.add("欢迎VIP访客 "+dataBean.getPerson().getName()+" 来访！ 来访时间: "+DateUtils.getCurrentTime_Today());
//									Collections.reverse(stringVector);
//
//									delet();
//
//									runOnUiThread(new Runnable() {
//										@Override
//										public void run() {
//											marqueeView.stopFlipping();
//											marqueeView.startWithList(stringVector);
//
//										}
//									});
////									VipDialog dialog=new VipDialog(VlcVideoActivity.this,personBean.getAvatar(),R.style.dialog_style,personBean.getName());
////									Log.d("WebsocketPushMsg", "vip");
////									dialog.show();
//								}
//							});
//
//
//						}else {

							try {

							MoShengRenBean bean = new MoShengRenBean(dataBean.getPerson().getId(), "sss");
//							ShiBieJiLuBean shiBieJiLuBean=new ShiBieJiLuBean();
//								shiBieJiLuBean.setId(dataBean.getPerson().getId());
//								shiBieJiLuBean.setName(dataBean.getPerson().getName());
//								shiBieJiLuBean.setTimes(DateUtils.times(System.currentTimeMillis()));
//								shiBieJiLuBean.setUrlPath(dataBean.getPerson().getAvatar());
								daoSession.insert(bean);
								//更新右边上下滚动列表
								//shiBieJiLuBeanDao.insert(shiBieJiLuBean);
//								shiBieJiLuList.add(shiBieJiLuBean);
//								Message message = Message.obtain();
//								message.what = 19;
//								handler.sendMessage(message);

//								if (vector2.size()>30){
//									vector2.clear();
//									vector2.add("欢迎 "+dataBean.getPerson().getName()+" 签到:"+DateUtils.getCurrentTime_Today());
//								}
//
//							vector2.add("欢迎 "+dataBean.getPerson().getName()+" 签到:"+DateUtils.getCurrentTime_Today());
//								Collections.reverse(vector2);


//								runOnUiThread(new Runnable() {
//									@Override
//									public void run() {
//										marqueeView2.stopFlipping();
//										marqueeView2.startWithList(vector2);
//
//									}
//								});


							//异步保存今天刷脸的人数
							Type resultType = new TypeToken<Integer>() {
							}.getType();
							Reservoir.getAsync("shulianshu", resultType, new ReservoirGetCallback<Integer>() {
								@Override
								public void onSuccess(final Integer i) {
									//Log.d("WebsocketPushMsg", "i:" + i);
									Message message = new Message();
									message.arg1 = 1;
									message.arg2 = i + 1;
									message.obj = dataBean.getPerson();
									handler.sendMessage(message);

									Reservoir.putAsync("shulianshu", i + 1, new ReservoirPutCallback() {
										@Override
										public void onSuccess() {
											//Log.d("WebsocketPushMsg", "保存刷脸人数成功"+i);

										}

										@Override
										public void onFailure(Exception e) {
											Log.d("WebsocketPushMsg", e.getMessage());
											//error
										}
									});
								}

								@Override
								public void onFailure(Exception e) {
									//Log.d("WebsocketPushMsg", e.getMessage());
									Message message = new Message();
									message.arg1 = 1;
									message.arg2 = 1;
									message.obj = dataBean.getPerson();
									handler.sendMessage(message);
									try {
										Reservoir.put("shulianshu", 1);
										//Log.d("WebsocketPushMsg", "ffffffff");
									} catch (IOException e1) {
										//	Log.d("WebsocketPushMsg", e1.getMessage());

									}

								}
							});



							}catch (Exception e){
								Log.d("WebsocketPushMsg", e.getMessage());
							}finally {
								try {
									Thread.sleep(400);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								try {
									daoSession.deleteByKey(dataBean.getPerson().getId());
								//	Log.d("WebsocketPushMsg", "删除");
								}catch (Exception e){
									Log.d("WebsocketPushMsg", e.getMessage());
								}
							}

//							//	Log.d("WebsocketPushMsg", "第一次");
//						} else if (vector.size() == 2) {
//							WBShiBieDataBean b1 = (WBShiBieDataBean) vector.get(0);
//							WBShiBieDataBean b2 = (WBShiBieDataBean) vector.get(1);
//							//Log.d("WebsocketPushMsg", "b1.getTrack():" + b1.getTrack());
//							//Log.d("WebsocketPushMsg", "b2.getTrack():" + b2.getTrack());
//
//							if (b1.getTrack() != b2.getTrack()) {
//								//异步保存今天刷脸的人数
//								Type resultType = new TypeToken<Integer>() {
//								}.getType();
//								Reservoir.getAsync("shulianshu", resultType, new ReservoirGetCallback<Integer>() {
//									@Override
//									public void onSuccess(Integer i) {
//										//Log.d("WebsocketPushMsg", "i:" + i);
//										Message message = new Message();
//										message.arg1 = 1;
//										message.arg2 = i + 1;
//										message.obj = personBean;
//										handler.sendMessage(message);
//
//										Reservoir.putAsync("shulianshu", i + 1, new ReservoirPutCallback() {
//											@Override
//											public void onSuccess() {
//												//	Log.d("WebsocketPushMsg", "保存刷脸人数成功");
//
//											}
//
//											@Override
//											public void onFailure(Exception e) {
//												//Log.d("WebsocketPushMsg66666", e.getMessage());
//												//error
//											}
//										});
//									}
//
//									@Override
//									public void onFailure(Exception e) {
//										//Log.d("WebsocketPushMsg", e.getMessage());
//										Message message = new Message();
//										message.arg1 = 1;
//										message.arg2 = 1;
//										message.obj = personBean;
//										handler.sendMessage(message);
//										try {
//											Reservoir.put("shulianshu", 1);
//											//	Log.d("WebsocketPushMsg", "ffffffff");
//										} catch (IOException e1) {
//											//	Log.d("WebsocketPushMsg", e1.getMessage());
//
//										}
//
//									}
//								});
//
//								vector.remove(0);
//								//	Log.d("WebsocketPushMsg", "第二次");
//							} else {
//
//								vector.remove(0);
//								//Log.d("WebsocketPushMsg", "删除");
//							}
//
//						}
				//	}

						//   WBShiBieScreenBean screenBean=gson.fromJson(jsonObject3,WBShiBieScreenBean.class);
//                    try {
//
//                        generateImage(dataBean.getFace().getImage(), Environment.getExternalStorageDirectory().getPath()+
//                                File.separator+"aaaa.jpg");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }


					}else if (wbBean.getType().equals("unrecognized")) {

//						if (vector2.size()>30){
//							vector2.clear();
//							vector2.add("欢迎领导签到:"+DateUtils.getCurrentTime_Today());
//						}
//
//						vector2.add("欢迎领导签到:"+DateUtils.getCurrentTime_Today());
//						Collections.reverse(vector2);


//						runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								marqueeView2.stopFlipping();
//								marqueeView2.startWithList(vector2);
//
//							}
//						});

					//	String s= ss.replace("\\","").replace("\"tag\": \"{\"","\"tag\": {\"").replace("jpg\"}\"","jpg\"}");
					//	JsonObject jsonObject5= GsonUtil.parse(s).getAsJsonObject();
						JsonObject jsonObject1 = jsonObject.get("data").getAsJsonObject();
						//  JsonObject jsonObject3=jsonObject.get("screen").getAsJsonObject();

						final WeiShiBieBean dataBean = gson.fromJson(jsonObject1, WeiShiBieBean.class);
						//Log.d("WebsocketPushMsg", "dataBean.getAttr().getAge():" + dataBean.getAttr().getAge());
						//Log.d("WebsocketPushMsg", dataBean.getFace().getImage());
						//Log.d("WebsocketPushMsg", "识别陌生人3333");
//						vector2.addElement(dataBean);
						//Log.d("WebsocketPushMsg", "dataBean:" + dataBean.toString());
						//Log.d("WebsocketPushMsg", "未识别"+dataBean.getAttr().getAge());
//
//						Log.d("WebsocketPushMsg", "vector2.size():" + vector2.size());
//						if (vector2.size()==1){
//
//							Message message=new Message();
//							message.arg1=2;
//							message.obj=dataBean;
//							handler.sendMessage(message);
//							Log.d("WebsocketPushMsg", "未识别的第一次");
//
//						}
//						if (vector2.size()==2){
//							Log.d("WebsocketPushMsg", "vector2.size()222:" + vector2.size());
//							WBWeiShiBieDATABean b1=(WBWeiShiBieDATABean)vector2.get(0);
//							WBWeiShiBieDATABean b2=(WBWeiShiBieDATABean)vector2.get(1);
//							Log.d("WebsocketPushMsg", "b1.getTrack():" + b1.getTrack()+"    "+b1.getPerson().getId()+"   "+b1.getPerson().getTag().getAvatar());
//							Log.d("WebsocketPushMsg", "b2.getTrack():" + b2.getTrack()+"     "+b2.getPerson().getId()+"   "+b2.getPerson().getTag().getAvatar());
//
//							if (b1.getTrack()!=b2.getTrack()){
//								if (b1.getPerson().getId().equals(b2.getPerson().getId())){


					//	Log.d("WebsocketPushMsg", "识别陌生人");
						try {

						MoShengRenBean bean = new MoShengRenBean(dataBean.getTrack(), "sss");

						daoSession.insert(bean);

//							stringVector.add("欢迎你参观！  "+DateUtils.getCurrentTime_Today());
//							Collections.reverse(stringVector);
//
//							delet();

//							runOnUiThread(new Runnable() {
//								@Override
//								public void run() {
//									marqueeView.stopFlipping();
//									marqueeView.startWithList(stringVector);
//
//								}
//							});


							//异步保存今天刷脸的人数
							Type resultType = new TypeToken<Integer>() {
							}.getType();
							Reservoir.getAsync("shulianshu", resultType, new ReservoirGetCallback<Integer>() {
								@Override
								public void onSuccess(Integer i) {
									//Log.d("WebsocketPushMsg", "i:" + i);
									Message message = new Message();
									message.arg1 = 2;
									message.arg2 = i + 1;
									message.obj=dataBean;
									handler.sendMessage(message);

									Reservoir.putAsync("shulianshu", i + 1, new ReservoirPutCallback() {
										@Override
										public void onSuccess() {
											//	Log.d("WebsocketPushMsg", "保存刷脸人数成功");

										}

										@Override
										public void onFailure(Exception e) {
											Log.d("WebsocketPushMsg66666", e.getMessage());
											//error
										}
									});
								}

								@Override
								public void onFailure(Exception e) {
									//Log.d("WebsocketPushMsg", e.getMessage());
									Message message = new Message();
									message.arg1 = 2;
									message.arg2 = 1;
									message.obj=dataBean;
									handler.sendMessage(message);
									try {
										Reservoir.put("shulianshu", 1);
										//	Log.d("WebsocketPushMsg", "ffffffff");
									} catch (IOException e1) {
										//	Log.d("WebsocketPushMsg", e1.getMessage());

									}

								}
							});

						//	Log.d("WebsocketPushMsg", "ddddoooooo");

							//Double d= dataBean.getAttr().getAge();
							//if (d==0){
								//创建用户\
							//	creatUser(b[0],dataBean.getTrack(),"32");
						//	}else {
								//creatUser(b[0],dataBean.getTrack(),"");
							//}


						} catch (Exception e) {
							Log.d("WebsocketPushMsg", e.getMessage());
							//e.printStackTrace();
						}finally {
							try {
								Thread.sleep(400);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							try {
								daoSession.deleteByKey(dataBean.getTrack());
								//Log.d("WebsocketPushMsg", "删除");
							}catch (Exception e){
								Log.d("WebsocketPushMsg", e.getMessage());
							}
						}
					}
				}

				@Override
				public void onClose(int i, String s, boolean b) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TastyToast.makeText(VlcVideoActivity.this,"连接出现异常,10秒后重新连接", Toast.LENGTH_LONG,TastyToast.ERROR).show();
						}
					});
					//Log.d("WebsocketPushMsg", "onClose " + s + " " + i + " " + b);

					if (conntionHandler==null && runnable==null){
						Looper.prepare();
						conntionHandler=new Handler();
						runnable=new Runnable() {
							@Override
							public void run() {
								Intent intent=new Intent("duanxianchonglian");
								sendBroadcast(intent);
							}
						};
						conntionHandler.postDelayed(runnable, 13000);
						Looper.loop();
					}

					//Intent bindIntent = new Intent(VlcVideoActivity.this, MyService.class);
					//bindService(bindIntent, connection, BIND_AUTO_CREATE);

				}

				@Override
				public void onError(Exception e) {
					Log.d("WebsocketPushMsg", "onError"+e.getMessage());

				}
			};

			webSocketClient.connect();
		}


	}

//	private void delet(){
//		if (stringVector.size()>=10){
//			stringVector.remove(stringVector.firstElement());
//		}else {
//			return;
//		}
//		delet();
//	}

	private void creatUser(byte[] bytes, Long tt, String age) {
		//Log.d("WebsocketPushMsg", "创建用户");
		String fileName="tong"+System.currentTimeMillis()+".jpg";
		//通过bytes数组创建图片文件
		createFileWithByte(bytes,fileName,tt,age);
		//上传
	//	addPhoto(fileName);
	}

	/**
	 * 根据byte数组生成文件
	 *
	 * @param bytes
	 *            生成文件用到的byte数组
	 * @param age
	 */
	private void createFileWithByte(byte[] bytes, String filename, Long tt, String age) {
		// TODO Auto-generated method stub
		/**
		 * 创建File对象，其中包含文件所在的目录以及文件的命名
		 */
		File file=null;
		String	sdDir = this.getFilesDir().getAbsolutePath();//获取跟目录
		makeRootDirectory(sdDir);

		// 创建FileOutputStream对象
		FileOutputStream outputStream = null;
		// 创建BufferedOutputStream对象
		BufferedOutputStream bufferedOutputStream = null;

		try {
			file = new File(sdDir +File.separator+ filename);
			// 在文件系统中根据路径创建一个新的空文件
		//	file2.createNewFile();
		//	Log.d(TAG, file.createNewFile()+"");

			// 获取FileOutputStream对象
			outputStream = new FileOutputStream(file);
			// 获取BufferedOutputStream对象
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			// 往文件所在的缓冲输出流中写byte数据
			bufferedOutputStream.write(bytes);
			// 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
			bufferedOutputStream.flush();
			//上传文件
			addPhoto(sdDir,filename,bytes,tt,age);

		} catch (Exception e) {
			// 打印异常信息
			//Log.d(TAG, "ssssssssssssssssss"+e.getMessage());
		} finally {
			// 关闭创建的流对象
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}

	private void addPhoto(final String path, final String fname, final byte[] b, final Long truck, final String age){

		if (zhuji_string!=null){
			String[] a=zhuji_string.split("//");
			String[] b1=a[1].split(":");
			zhuji="http://"+b1[0];
		}

		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();

         /* 第一个要上传的file */
		File file1 = new File(path,fname);
		RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
		final String file1Name = System.currentTimeMillis()+"testFile.jpg";

//    /* 第二个要上传的文件,这里偷懒了,和file1用的一个图片 */
//        File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg");
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
//        String file2Name = "testFile2.txt";


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";

		MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
				.addFormDataPart("photo" , file1Name , fileBody1)
                  /* 上传一个普通的String参数 */
				//  .addFormDataPart("subject_id" , subject_id+"")
//                .addFormDataPart("file" , file2Name , fileBody2)
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				// .header("Content-Type", "application/json")
				.post(mBody)
				.url(zhuji2+"/subject/photo");
		//    .url(HOST+"/mobile-admin/subjects");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());
		//step 4: 开始异步请求
		final String finalZhuji = zhuji;
		call.enqueue(new Callback() {
			@Override
			public void onFailure(final Call call, final IOException e) {
			//	Log.d("AllConnects", "照片上传失败"+e.getMessage());

			}

			@Override
			public void onResponse(final Call call, Response response) throws IOException {
				Log.d("AllConnects", "照片上传成功"+call.request().toString());

				try{

				//获得返回体
				ResponseBody body = response.body();
				// Log.d("WebsocketPushMsg", "aa   "+response.body().string());
				JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
				Gson gson=new Gson();
				int code=jsonObject.get("code").getAsInt();
				if (code==0){
					JsonObject array=jsonObject.get("data").getAsJsonObject();
					TuPianBean zhaoPianBean=gson.fromJson(array,TuPianBean.class);
					//创建用户
				//	Log.d("VlcVideoActivity", "zhaoPianBean.getId():" + zhaoPianBean.getId());
					link(finalZhuji,"a",zhaoPianBean.getId()+"",b,age);

				}else {

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							TastyToast.makeText(getApplicationContext(),
									"图片不够清晰，请再来一张", TastyToast.LENGTH_LONG, TastyToast.ERROR);
						}
					});

				}
				//删除照片
				Log.d("VlcVideoActivity", "删除照片:" + VlcVideoActivity.this.deleteFile(fname));

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}


		});


		}


	private void link(String zhuji, String name, String id, final byte[] b, final String age){

		final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();

		List<Long> longs=new ArrayList<>();
		longs.add(Long.valueOf(id));
		ChuanJianUserBean bean=new ChuanJianUserBean();
		bean.setName(name);
		bean.setPhoto_ids(longs);
		bean.setSubject_type("0");

		String json = new Gson ().toJson(bean);
		RequestBody requestBody = RequestBody.create(JSON, json);


//		RequestBody body = new FormBody.Builder()
//				.add("name",name)
//				.add("subject_type",0+"")
//				.add("photo_ids","["+id+"]")
//				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(requestBody)
				.url(zhuji2+"/subject");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//Log.d("AllConnects", "请求成功"+call.request().toString());
				//获得返回体
				try{

				ResponseBody body = response.body();
				//  Log.d("AllConnects", "aa   "+response.body().string());

				JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
				Gson gson=new Gson();
				int code=jsonObject.get("code").getAsInt();
				if (code==0){
					JsonObject array=jsonObject.get("data").getAsJsonObject();
					User zhaoPianBean=gson.fromJson(array,User.class);
					link_houtai(zhaoPianBean);
					final MoShengRenBean2 moShengRenBean2 = new MoShengRenBean2();
					moShengRenBean2.setId(zhaoPianBean.getId());
					moShengRenBean2.setAge(age);
					moShengRenBean2.setBytes(b);
				//	moShengRenBean2.setUrl("http://192.168.2.7:8080/sign?cmd=signScan&subjectId="+zhaoPianBean.getId());



				}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}

			}
		});


	}

	private void link_houtai(User zhaoPianBean) {
		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
		OkHttpClient okHttpClient= MyApplication.getOkHttpClient();

//		List<Long> longs=new ArrayList<>();
//		longs.add(Long.valueOf(id));
//		ChuanJianUserBean bean=new ChuanJianUserBean();
//		bean.setName(name);
//		bean.setPhoto_ids(longs);
//		bean.setSubject_type("0");
//
//		String json = new Gson ().toJson(bean);
//		RequestBody requestBody = RequestBody.create(JSON, json);
	//	http://192.168.2.4:8080/sign?cmd=addSign&subjectId=jfgsdf
		//Log.d(TAG, zhaoPianBean.getPhotos().get(0).getUrl()+"ggggggggggggggggggggggggggggg");
		RequestBody body = new FormBody.Builder()
				.add("cmd","addSign")
				.add("subjectId",zhaoPianBean.getId()+"")
				.add("subjectPhoto",zhaoPianBean.getPhotos().get(0).getUrl())
				.build();
		Request.Builder requestBuilder = new Request.Builder()
				.header("Content-Type", "application/json")
				.post(body)
				.url("http://192.168.2.17:8080/sign");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求添加陌生人成功"+call.request().toString());
				//获得返回体
				try {

				ResponseBody body = response.body();
			//	Log.d("AllConnects", "aa   "+response.body().string());

				JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
				Gson gson=new Gson();
				int code=jsonObject.get("resultCode").getAsInt();
				if (code==0){
//					JsonObject array=jsonObject.get("data").getAsJsonObject();
//					User zhaoPianBean=gson.fromJson(array,User.class);
//					link_houtai(zhaoPianBean);
					//link_gengxing_erweima();
				}

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});


		}



	//得到View的bitmap
	public  Bitmap loadBitmapFromView(View v) {
		if (v == null) {
			return null;
		}

		Bitmap screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(screenshot);

		v.draw(canvas);// 将 view 画到画布上
	//	Log.d(TAG, Environment.getDataDirectory().getPath() + "/" + System.currentTimeMillis() + "aaa.jpg");
		//保存截屏图片
		File file = new File(Environment.getExternalStorageDirectory()	  +"/"+ System.currentTimeMillis() + "aaa.jpg");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			screenshot.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenshot;

	}


	public static final int TIMEOUT = 1000 * 60;
	private void link_chengshi() {
		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
		OkHttpClient okHttpClient= new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.retryOnConnectionFailure(true)
				.build();

//		RequestBody body = new FormBody.Builder()
//				.add("cityCode","101040100")
//				.add("weatherType","1")
//				.build();

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.get()
				.url("http://api.map.baidu.com/location/ip?ak=OkLLk9ojkdcEsUEWTpc2MVoY6DDSptik" +
						"&mcode=7D:2D:D4:76:15:CE:C5:44:3D:25:01:FF:4C:0A:96:3C:92:4B:0E:FD;com.example.xiaojun.linghejiedao");
		//.url("http://wthrcdn.etouch.cn/weather_mini?city=广州市");

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求添加陌生人成功"+call.request().toString());
				//获得返回体
				try {

					ResponseBody body = response.body();
				//	Log.d("AllConnects", "aa   "+response.body().string());
					String ss=body.string();
					Log.d("VlcVideoActivity", ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
				//	JsonObject object=jsonObject.get("ContentBean").getAsJsonObject();

					IpAddress zhaoPianBean=gson.fromJson(jsonObject,IpAddress.class);


					/**从assets中读取txt
					 * 按行读取txt
					 * @param
					 * @return
					 * @throws Exception
					 */

						InputStream is = getAssets().open("tianqi.txt");
						InputStreamReader reader = new InputStreamReader(is);
						BufferedReader bufferedReader = new BufferedReader(reader);
						//StringBuffer buffer = new StringBuffer("");
						String str;
						String aa=zhaoPianBean.getContent().getAddress_detail().getCity();
						if (aa.length()>2){
							aa=aa.substring(0,2);
						//	Log.d("VlcVideoActivity", "fffff9"+aa);
						}
						while ((str = bufferedReader.readLine()) != null) {


							if (str.contains(aa)){
								//Log.d("VlcVideoActivity", "fffff3"+str);
								link_tianqi(str);
								break;
							}
						}



				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});

	}

	private void link_tianqi(String bean) {
		//final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
		//http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
		OkHttpClient okHttpClient= new OkHttpClient.Builder()
				.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
				.retryOnConnectionFailure(true)
				.build();
//		RequestBody body = new FormBody.Builder()
//				.add("cityCode","101040100")
//				.add("weatherType","1")
//				.build();

		Request.Builder requestBuilder = new Request.Builder()
				//.header("Content-Type", "application/json")
				.get()
				//.url("https://api.map.baidu.com/location/ip?ak=uTTmEt0NeHSsgAKsXGLAMC8mvg6zPNLm" +
					//	"&mcode=21:21:DA:F2:00:51:3B:AB:C4:E6:19:18:31:C6:98:CA:D6:7B:44:AE;com.example.xiaojun.linghejiedao");

				.url("http://wthrcdn.etouch.cn/weather_mini?citykey=" + bean.substring(bean.length() - 9, bean.length()));

		// step 3：创建 Call 对象
		Call call = okHttpClient.newCall(requestBuilder.build());

		//step 4: 开始异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.d("AllConnects", "请求添加陌生人失败"+e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.d("AllConnects", "请求天气成功"+call.request().toString());
				//获得返回体
				try {

					ResponseBody body = response.body();
					//Log.d("AllConnects", "aa   "+response.body().string());
					String ss=body.string();
					Log.d("VlcVideoActivity", ss);
					JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
					Gson gson=new Gson();
					//JsonObject object=jsonObject.get("ContentBean").getAsJsonObject();

					final TianQiBean zhaoPianBean=gson.fromJson(jsonObject,TianQiBean.class);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tianqi.setText(zhaoPianBean.getData().getCity());
							wendu.setText(zhaoPianBean.getData().getWendu()+" 度");
							//tianqi2.setText(zhaoPianBean.getData().getGanmao());
						}
					});

				}catch (Exception e){
					Log.d("WebsocketPushMsg", e.getMessage());
				}
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if( KeyEvent.KEYCODE_MENU == keyCode ){  //如果按下的是菜单
			Log.d(TAG, "按下菜单键");
			CaiDanDialog danDialog=new CaiDanDialog(VlcVideoActivity.this,R.style.dialog_ALL,daoSession,null,null);
			danDialog.show();
		}


		return super.onKeyDown(keyCode, event);


	}


//	private class DownloadReceiver extends ResultReceiver {
//		public DownloadReceiver(Handler handler) {
//			super(handler);
//		}
//		@Override
//		protected void onReceiveResult(int resultCode, Bundle resultData) {
//			super.onReceiveResult(resultCode, resultData);
//			if (resultCode == DownloadService.UPDATE_PROGRESS) {
//				String ididid=resultData.getString("ididid2");
//				int progress = resultData.getInt("progress");
//
//				if (progress == 100) {
//					try {
//
//					//下载完成
//					//更新状态
//					Log.d(TAG, "ididididididd值："+ididid);
//					if (ididid!=null) {
//						ShiPingBean b = shiPingBeanDao.load(ididid);
//						b.setIsDownload(true);
//						shiPingBeanDao.update(b);
//
//						if (shiPingBeanList.size() > 0) {
//							shiPingBeanList.clear();
//						}
//						shiPingBeanList = shiPingBeanDao.loadAll();
//
//						ijkVideoView.setVideoPath(Environment.getExternalStorageDirectory() + File.separator + "linhefile" + File.separator + b.getId() + "." + b.getVideoType().split("\\/")[1]);
//						ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//							@Override
//							public void onPrepared(IMediaPlayer iMediaPlayer) {
//								ijkVideoView.start();
//							}
//						});
//					}else {
//						Log.d(TAG, "id的值是空");
//					}
//
//					}catch (Exception e){
//						Log.d(TAG, "捕捉到异常onReceiveResult"+e.getMessage());
//					}
//
//					//ijkVideoView.setVideoPath(mFile.getPath());
//					//ijkVideoView.start();
////					Intent install = new Intent();
////					install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////					install.setAction(android.content.Intent.ACTION_VIEW);
////					install.setDataAndType(Uri.fromFile(mFile),"application/vnd.android.package-archive");
////					startActivity(install);  //下载完成打开APK
//				}
//			}
//		}
//	}

//    private class DownloadReceiverTuPian extends ResultReceiver {
//        public DownloadReceiverTuPian(Handler handler) {
//            super(handler);
//        }
//        @Override
//        protected void onReceiveResult(int resultCode, Bundle resultData) {
//            super.onReceiveResult(resultCode, resultData);
//            if (resultCode == DownloadTuPianService.UPDATE_PROGRESS2) {
//                int progress = resultData.getInt("progress");
//
//                if (progress == 100) {
//                    try {
//                        //下载完成
//                       // Environment.getExternalStorageDirectory()+ File.separator+"linhefile"+File.separator+"tupian111.jpg"
//                        Log.d(TAG, "图片下载完成");
//
//                    }catch (Exception e){
//                        Log.d(TAG, "捕捉到异常onReceiveResult"+e.getMessage());
//                    }
//
//                }
//            }
//        }
//    }
}
