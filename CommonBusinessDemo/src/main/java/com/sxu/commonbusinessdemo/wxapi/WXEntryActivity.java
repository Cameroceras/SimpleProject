package com.sxu.commonbusinessdemo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.commonbusiness.share.ShareConstants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI wxApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxApi =  WXAPIFactory.createWXAPI(this, ShareConstants.APP_WECHAT_KEY, false);
        wxApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxApi.handleIntent(intent, this);
    }

    // 微信发送消息给app，app接受并处理的回调函数
    @Override
    public void onReq(BaseReq req) {

    }

    //app发送消息给微信，微信返回的消息回调函数,根据不同的返回码来判断操作是否成功
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                ToastUtil.show("分享成功");
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                ToastUtil.show("分享失败");
                finish();
                break;
            default:
                finish();
                break;
        }
    }
}