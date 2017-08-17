package com.example.xiaojun.linghejiedao.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.xiaojun.linghejiedao.R;
import com.example.xiaojun.linghejiedao.adapter.GalleryAdapter;
import com.example.xiaojun.linghejiedao.adapter.SpaceItemDecoration;
import com.example.xiaojun.linghejiedao.beans.ZhuJiBean;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TianJiaFWQActivity extends Activity implements View.OnClickListener {
    private Button button;
    private RecyclerView listView;
    private MyReceiver myReceiver=null;
    private List<ZhuJiBean> beanList = null;
    private  GalleryAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==1){
                if (beanList.size()!=0){
                    beanList.clear();
                }
                beanList.addAll((List<ZhuJiBean>)(msg.obj));
                adapter.notifyDataSetChanged();
                listView.setFocusable(true);
                listView.requestFocus();
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tian_jia_fuwuqi);
        listView= (RecyclerView) findViewById(R.id.lv);

        //实例化过滤器并设置要过滤的广播
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updatefuwuqi");
        //注册广播
        registerReceiver(myReceiver, intentFilter);

        button=(Button)findViewById(R.id.bt);
        button.setOnClickListener(this);

        beanList=new ArrayList<>();
        linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spae);
        listView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        adapter = new GalleryAdapter(TianJiaFWQActivity.this, beanList);
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(TianJiaFWQActivity.this,TianJiaFuwuqiActivity.class);
                intent.putExtra("xiugaifuwuqi",beanList.get(position).getDizhi());
                intent.putExtra("number2",position);
                startActivity(intent);

//                int a = beanList.size();
//                for (int i = 0; i < a; i++) {
//                    if (i != position) {
//
//                        beanList.get(i).setIsTrue(0);
//
//                    } else {
//
//                        beanList.get(i).setIsTrue(1);
//                    }
//                }

//                Reservoir.putAsync("fuwuqi", beanList, new ReservoirPutCallback() {
//                    @Override
//                    public void onSuccess() {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                TastyToast.makeText(TianJiaFWQActivity.this, "保存成功", Toast.LENGTH_SHORT,TastyToast.INFO).show();
//                            }
//                        });
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        //error
//                    }
//                });

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

        });

        Type resultType = new TypeToken<List<ZhuJiBean>>() {}.getType();
        Reservoir.getAsync("fuwuqi", resultType, new ReservoirGetCallback<List<ZhuJiBean>>() {
            @Override
            public void onSuccess(List<ZhuJiBean> strings) {
                Message message=new Message();
                message.what=1;
                message.obj=strings;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Exception e) {

                ZhuJiBean bean2=new ZhuJiBean();
                bean2.setIsTrue(0);
                bean2.setDizhi("rtsp://192.168.2.56:554/user=admin_password=tljwpbo6_channel=1_stream=0.sdp?real_stream");
                beanList.add(bean2);

                ZhuJiBean bean3=new ZhuJiBean();
                bean3.setIsTrue(1);
                bean3.setDizhi("rtsp://192.168.2.56/user=admin&password=&channel=1&stream=0.sdp");
                beanList.add(bean3);

                Reservoir.putAsync("fuwuqi", beanList, new ReservoirPutCallback() {
                    @Override
                    public void onSuccess() {

                        Intent intent=new Intent("updatefuwuqi");
                        sendBroadcast(intent);

                    }

                    @Override
                    public void onFailure(Exception e) {


                    }
                });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (myReceiver!=null)
            unregisterReceiver(myReceiver);
    }

    private class MyReceiver  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            if (intent.getAction().equals("updatefuwuqi")){

              //  Log.d("MyReceiver", "updatefuwuqi");

                Type resultType = new TypeToken<List<ZhuJiBean>>() {}.getType();
                Reservoir.getAsync("fuwuqi", resultType, new ReservoirGetCallback<List<ZhuJiBean>>() {
                    @Override
                    public void onSuccess(List<ZhuJiBean> strings) {
                        Message message=new Message();
                        message.what=1;
                        message.obj=strings;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TastyToast.makeText(TianJiaFWQActivity.this, "请先添加服务器地址", Toast.LENGTH_SHORT,TastyToast.ERROR).show();
                            }
                        });
                    }
                });

            }

        }

    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,TianJiaFuwuqiActivity.class));
    }
}
