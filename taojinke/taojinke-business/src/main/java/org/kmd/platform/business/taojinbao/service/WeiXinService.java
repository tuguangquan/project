package org.kmd.platform.business.taojinbao.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.kmd.platform.business.taojinbao.servlet.process.impl.NewsRespProcess;
import org.kmd.platform.business.taojinbao.servlet.process.impl.TextRespProcess;
import org.kmd.platform.business.taojinbao.util.AccessToken;
import org.kmd.platform.business.taojinbao.util.MessageUtil;
import org.kmd.platform.business.taojinbao.util.MyX509TrustManager;
import org.kmd.platform.business.taojinbao.weixin.QRCode.ActionInfo;
import org.kmd.platform.business.taojinbao.weixin.QRCode.Constant;
import org.kmd.platform.business.taojinbao.weixin.QRCode.QRCode;
import org.kmd.platform.business.taojinbao.weixin.QRCode.Scene;
import org.kmd.platform.business.taojinbao.weixin.mass.resp.MassTextMessage;
import org.kmd.platform.business.taojinbao.weixin.mass.resp.Text;

import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/18 0018.
 */
@Service
public class WeiXinService {

    @Autowired
    public TextRespProcess textRespProcess;
    @Autowired
    public NewsRespProcess newsRespProcess;
    private static PlatformLogger log = PlatformLogger.getLogger(WeiXinService.class);

    public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String  get_user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
    public final static String  send_msg_url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
    public final static String  get_qrCode_ticket_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
    public final static String  get_qrCode_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    /**
     * 发起https请求并获取结果
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public  JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }

    public  int createMenu(String jsonMenu, String accessToken) {
        int result = 0;
        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }

    public  AccessToken getAccessToken(String appId,String appSecret ) {
        AccessToken accessToken = null;
        String requestUrl = access_token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }



    public  String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                return textRespProcess.getRespMessage(requestMap);
            }
            // 图文消息
            else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)) {
                return newsRespProcess.getRespMessage(requestMap);
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 自定义菜单权没有开放，暂不处理该类消息
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }

    public List getUser(String accessToken) {
        // 拼装创建菜单的url
        String url = get_user_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", null);
        if (null != jsonObject) {
            JSONArray   jsonArray =  jsonObject.getJSONObject("data").getJSONArray("openid");
            return jsonArray.subList(0, jsonArray.size());
        }
        return null;
    }
    public String getQRCode(String ticket) {
        String url = get_qrCode_url.replace("TICKET", ticket);
        // 调用接口ticket
        JSONObject json = httpRequest(url, "POST", null);
        if (null != json) {
            return json.getString("ticket");
        }
        return null;
    }
    public String getQRCodeTicket(String accessToken,String scene_str) {
        Scene scene = new Scene();
        scene.setScene_str(scene_str);
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setScene(scene);
        QRCode qrCode = new QRCode();
        qrCode.setAction_info(actionInfo);
        qrCode.setAction_name(Constant.qr_limit_scene);
        JSONObject jsonObject = JSONObject.fromObject(qrCode);
        // 拼装创建菜单的url
        String url = get_qrCode_ticket_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口ticket
        JSONObject json = httpRequest(url, "POST", jsonObject.toString());
        if (null != json) {
            return json.getString("ticket");
        }
        return null;
    }

    public int sendMsgToSomeUser(String msg,JSONArray jsonArray,String accessToken) {
        int result = 0;
        // 拼装创建菜单的url
        String url = send_msg_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口创建菜单
        // 默认返回的文本消息内容
        List<String> users = new ArrayList<String>();
        for(int i =0;i<jsonArray.size()-1;i++){
             users.add(jsonArray.getString(i));
         }
        // 回复文本消息
        MassTextMessage textMessage = new MassTextMessage();
        textMessage.setTouser(users);
        Text text = new Text();
        text.setContent(msg);
        textMessage.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setText(text);
        String respMessage = MessageUtil.textMessageToXml(textMessage);
        JSONObject jsonObject = httpRequest(url, "POST", respMessage);
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("消息发送失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }


    public static void main(String[] args){
        Scene scene = new Scene();
        scene.setScene_str("2343253");
        ActionInfo actionInfo = new ActionInfo();
        actionInfo.setScene(scene);
        QRCode qrCode = new QRCode();
        qrCode.setAction_info(actionInfo);
        qrCode.setAction_name(Constant.qr_limit_scene);
        JSONObject jsonObject = JSONObject.fromObject(qrCode);
        String json = "{\"total\":23000," + " \"count\":10000," + " \"data\":{\"openid\":[" +
                "\"OPENID1\"," +
                "\"OPENID2\"," +
                "\"OPENID10000\"" +
                "]" +
                "}," +
                "\"next_openid\":\"OPENID10000\"" +"}" ;
        JSONObject jsonResult =   JSONObject.fromObject(json);

        if (null != jsonResult) {
            JSONArray jsonArray =  jsonResult.getJSONObject("data").getJSONArray("openid");
            List list =  jsonArray.subList(0, jsonArray.size());
            System.out.print(23432);

        }
    }
}
