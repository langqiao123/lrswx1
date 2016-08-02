说明：
1.生成二维码的url,http://m.mr-x.com.cn/getQrCodeServlet
url返回结果如下：
{
    "uuid": 87956,  //请求的客户端的标识
    "qrCodeImg": "http://m.mr-x.com.cn/images/87956_1469971358.png" //二维码路径 
}

2.微信扫码后跳转的url(这个客户端在使用时不需要关心),http://m.mr-x.com.cn/phoneLoginServlet?version=3
url返回结果：true

3.检查登录状态url:http://m.mr-x.com.cn/longConnectionCheckServlet?uuid=uuid,
uuid是获取二维码接口返回的字段
url返回结果：
(1)登录成功
{
    "uname": "oTx17xNEV2lmdpbJ3bCQrCJ2A8OU"
}
(2)登录失败或者没有登录则为空
注意：客户端需要不断轮询该接口，以判断登录状态。
