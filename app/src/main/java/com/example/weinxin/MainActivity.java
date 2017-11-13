package com.example.weinxin;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.HashMap;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class MainActivity extends AppCompatActivity     implements PlatformActionListener {
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(MainActivity.this, "微博分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(MainActivity.this, "微信分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(MainActivity.this, "朋友圈分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(MainActivity.this, "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 5:
                    Toast.makeText(MainActivity.this, "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(MainActivity.this, "分享失败啊" + msg.obj, Toast.LENGTH_LONG).show();
                    Log.d("======",msg.obj.toString());
                    break;
                case 7:
                    Toast.makeText(MainActivity.this, "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Log.d("==", getPackageName());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();

//                showShare(WechatMoments.NAME);
            }
        });
    }
    private void showShare(String s ) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText("demo");
//        sp.setImagePath("/sdcard/wu.png");
        Platform weibo = ShareSDK.getPlatform(s);
        weibo.setPlatformActionListener(this); // 设置分享事件回调
// 执行图文分享
        weibo.share(sp);
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
oks.setImagePath("/sdcard/wu.png");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
//        oks.setCallback(new OneKeyShareCallback());
// site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
//// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(this);
    }






        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            if (platform.getName().equals(SinaWeibo.NAME

            )) {// 判断成功的平台是不是新浪微博
                handler.sendEmptyMessage(1);
            } else if (platform.getName().equals(Wechat.NAME

            )) {
                handler.sendEmptyMessage(2);
            } else if (platform.getName().equals(WechatMoments.NAME

            )) {
                handler.sendEmptyMessage(3);
            } else if (platform.getName().equals(QQ.NAME

            )) {
                handler.sendEmptyMessage(4);
            }else if (platform.getName().equals(QZone.NAME

            )) {
                handler.sendEmptyMessage(7);
            }
            }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            throwable.printStackTrace();
            Message msg = new Message();
            msg.what = 6;
            msg.obj = throwable.getMessage();
            handler.sendMessage(msg);


        }

        @Override
        public void onCancel(Platform platform, int i) {
            handler.sendEmptyMessage(5);

        }




}
