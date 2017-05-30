package org.kmd.platform.business.taojinbao.service;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.kmd.platform.business.taojinbao.dto.Material;
import org.kmd.platform.business.taojinbao.entity.*;
import org.kmd.platform.business.taojinbao.mapper.MsgTempMapper;
import org.kmd.platform.business.taojinbao.servlet.process.impl.ImageRespProcess;
import org.kmd.platform.business.taojinbao.servlet.process.impl.NewsRespProcess;
import org.kmd.platform.business.taojinbao.servlet.process.impl.TextRespProcess;
import org.kmd.platform.business.taojinbao.util.*;
import org.kmd.platform.business.taojinbao.weixin.QRCode.ActionInfo;
import org.kmd.platform.business.taojinbao.weixin.Constant;
import org.kmd.platform.business.taojinbao.weixin.QRCode.QRCode;
import org.kmd.platform.business.taojinbao.weixin.QRCode.Scene;
import org.kmd.platform.business.taojinbao.weixin.mass.resp.MassTextMessage;
import org.kmd.platform.business.taojinbao.weixin.mass.resp.Text;

import org.kmd.platform.business.taojinbao.weixin.test.Template;
import org.kmd.platform.business.taojinbao.weixin.test.TemplateParam;
import org.kmd.platform.fundamental.logger.PlatformLogger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class WeiXinService {

    private static PlatformLogger log = PlatformLogger.getLogger(WeiXinService.class);
    @Autowired
    public TextRespProcess textRespProcess;
    @Autowired
    public NewsRespProcess newsRespProcess;
    @Autowired
    public ImageRespProcess imageRespProcess;

    @Autowired
    public MsgTempService msgTempService;
    @Autowired
    public MsgTempMapper msgTempMapper;
    @Autowired
    private MsgSubService msgSubService;
    @Autowired
    private PosterService posterService;
    @Autowired
    private AgentInfoService agentInfoService;
    @Autowired
    private ShopService shopService;



    public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String  get_user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
    public final static String  send_msg_url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
    public final static String  get_qrCode_ticket_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
    public final static String  get_qrCode_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    public final static String  send_temp_msg_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    /**
     * 发起https请求并获取结果
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */


    public   JSONObject httpRequestForImage(String requestUrl, String requestMethod, String outputStr) {
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

            httpUrlConn.setRequestProperty("Accept-Ranges","bytes");
            httpUrlConn.setRequestProperty("Cache-control","max-age=604800");
            httpUrlConn.setRequestProperty("Content-Type","image/jpg");
            httpUrlConn.setRequestProperty("Cache-control","max-age=604800");

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



    public    JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
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

    public    int createMenu(String jsonMenu, String accessToken) {
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

    public  String getMenu(String accessToken) {
        // 拼装获取菜单的url
        String url = menu_get_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", null);
        if (null != jsonObject) {
            return jsonObject.get("menu").toString();
        }else{
            return null;
        }
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
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 消息类型
            String msgTypeReq = requestMap.get("MsgType");
            String weiXinOriginId = requestMap.get("ToUserName");
            List<MsgTemp> msgTempList = msgTempService.getMsgTempByOriginId(weiXinOriginId);
            // 文本消息
            if (msgTypeReq.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                if(msgTempList.size()==0){//没有设置，发送系统默认消息
                    requestMap.put("Content","默认消息");
                    return textRespProcess.getRespMessage(requestMap);
                }else {//设置了，发送设置消息，
                    //首先查看是否有规则和优先级
                    String msg = requestMap.get("Content");
                    List<MsgTemp> msgTempMatchList = msgTempService.getMsgTempByOriginIdAndModeMatch(weiXinOriginId,msg);
                    if (msgTempMatchList.size()==0){//没有设置规则，发送代理商设置的默认消息
                        String content = msgTempList.get(0).getModeContent();
                        requestMap.put("Content",content);
                        String msgType = msgTempList.get(0).getMsgType();
                        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){//如果设置的是文本消息
                            return textRespProcess.getRespMessage(requestMap);
                        }
                        if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)){//如果设置的是图文消息
                            return newsRespProcess.getRespMessage(requestMap);
                        }
                    }else{ //设置了规则，发送优先级最高的消息
                        int priority = msgTempMatchList.get(0).getPriority();
                        String content = msgTempMatchList.get(0).getModeContent();
                        String msgType = msgTempMatchList.get(0).getMsgType();
                        if (msgTempMatchList.size()>1){
                            for (int i=1;i<msgTempMatchList.size()-1;i++){
                                if (priority>msgTempMatchList.get(i).getPriority()){
                                    priority=msgTempMatchList.get(i).getPriority();
                                    content = msgTempMatchList.get(i).getModeContent();
                                    msgType = msgTempMatchList.get(i).getMsgType();
                                }
                            }
                        }
                        requestMap.put("Content",content);
                        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){//如果设置的是文本消息
                            return textRespProcess.getRespMessage(requestMap);
                        }
                        if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)){//如果设置的是图文消息
                            return newsRespProcess.getRespMessage(requestMap);
                        }
                    }
                }
            }
            // 事件推送
            else if (msgTypeReq.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                String eventKey = requestMap.get("EventKey");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    long parentId = 0;
                    if (eventKey.equals("")){    //非扫码关注公众号
                         parentId = 0;
                    } else{                 //扫码关注公众号
                        String [] split = eventKey.split("_");
                        parentId = Long.parseLong(split[1]);
                    }
                    MsgSub msgSub = msgSubService.getMsgSubByWeiXinOriginId(weiXinOriginId) ;
                    AgentInfo agentInfo = agentInfoService.getAgentInfoByWeiXinOriginId(weiXinOriginId);
                    //关注者openid
                    String fromUserName = requestMap.get("FromUserName");
                    synchronized (this){                 //微信发多次消息时，可能添加多条信息
                        Shop shop = shopService.getByOpenId(fromUserName);
                        if (shop == null){
                            Shop add = new Shop();
                            add.setAgentId(agentInfo.getAgentId());
                            add.setOpenId(fromUserName);
                            add.setStatus(ShopEunm.RESP_STATUS_SUB);//粉丝
                            add.setParentId(parentId);
                            shopService.add(add);
                        }
                    }
                    log.info("粉丝添加成功！");

                    if (null == msgSub){   //没有设置关注回复
                        requestMap.put("Content","感谢您的关注！");
                        return textRespProcess.getRespMessage(requestMap);
                    }else{
                        String contentSub =  msgSub.getContent();
                        requestMap.put("Content",contentSub);    //关注回复一定是文本类型
                        return textRespProcess.getRespMessage(requestMap);
                    }
                }
                // 浏览
                if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    MsgSub msgSub = msgSubService.getMsgSubByWeiXinOriginId(weiXinOriginId) ;
                    if (null == msgSub){   //没有设置关注回复
                        requestMap.put("Content","感谢您的关注！");
                        return textRespProcess.getRespMessage(requestMap);
                    }else{
                        String contentSub =  msgSub.getContent();
                        requestMap.put("Content",contentSub);    //关注回复一定是文本类型
                        return textRespProcess.getRespMessage(requestMap);
                    }
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    if (eventKey.equals("V1001_TODAY_MUSIC")){
                        Poster poster =  posterService.getPosterByWeiXinOriginId(weiXinOriginId) ;
                        if (poster== null){
                            requestMap.put("Content","海报获取失败，请稍后再试！"+weiXinOriginId);
                            return textRespProcess.getRespMessage(requestMap);
                        }else{
                            requestMap.put("Media_id",poster.getMedia_id());
                            return imageRespProcess.getRespMessage(requestMap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
   //得到所有关注用户
    public List getUser(String accessToken) {
        // 拼装创建菜单的url
        String url = get_user_url.replace("ACCESS_TOKEN", accessToken);
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", null);
        if (null != jsonObject) {
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("openid");
            return jsonArray.subList(0, jsonArray.size());
        }
        return null;
    }

    //得到二维码
    public  void  get_QR( String filename,String savePath,String ticket) throws IOException {
       String urlString = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
       URL url = new URL(urlString);
            // 打开连接
       URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5*1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf=new File(savePath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }

    //得到二维码的ticket
    public  String getQRCodeTicket(String accessToken,long scene_id) {
        Scene scene = new Scene();
        scene.setScene_id(scene_id);
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
    //给用户群发文本消息
    public  int sendMsgToSomeUser(String msg,JSONArray jsonArray,String accessToken) {
        int result = 0;
        // 拼装发消息的url
        String url = send_msg_url.replace("ACCESS_TOKEN", accessToken);
        // 默认返回的文本消息内容
        List<String> users = new ArrayList<String>();
        for(int i =0;i<jsonArray.size();i++){
             users.add(jsonArray.getString(i));
         }
        // 回复文本消息
        MassTextMessage textMessage = new MassTextMessage();
        textMessage.setTouser(users);
        textMessage.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent(msg);
        String respMessage = textMessage.toJSON();
        JSONObject jsonObject = httpRequest(url, "POST", respMessage);
        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("消息发送失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return result;
    }
    /**
     * 上传其他永久素材(图片素材的上限为5000，其他类型为1000)
     * @param accessToken
     * @param fileurl
     * @param type
     * @return
     * @throws Exception
     */
    public  JSONObject addMaterialEver(String fileurl, String type, String accessToken) throws Exception {
        try {
            File file = new File(fileurl);
            //上传素材
            String path = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + accessToken + "&type=" + type;
            String result = connectHttpsByPost(path,file);
            result = result.replaceAll("[\\\\]", "");
            System.out.println("result:" + result);
            JSONObject resultJSON = JSONObject.fromObject(result);
            if (resultJSON != null) {
                if (resultJSON.get("media_id") != null) {
                    log.info("上传" + type + "永久素材成功");
                    return resultJSON;
                } else {
                    log.error("上传" + type + "永久素材失败");
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传图片到微信服务器(本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下)
     * @param accessToken
     * @param fileurl
     * @return
     * @throws Exception
     */
    public  JSONObject addMaterialEver(String fileurl,String accessToken) throws Exception {
        try {
            log.info("开始上传图文消息内的图片---------------------");
            //上传图片素材
            String path="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+accessToken;
            File file = new File(fileurl);
            String result = connectHttpsByPost(path,file);
            result = result.replaceAll("[\\\\]", "");
            System.out.println("result:" + result);
            JSONObject resultJSON = JSONObject.fromObject(result);
            if (resultJSON != null) {
                return resultJSON;
            }
            return null;
        } catch (Exception e) {
            log.error("程序异常", e);
            throw e;
        }finally{
            log.info("结束上传图文消息内的图片---------------------");
        }
    }

    //上传媒体文件到微信服务器
    public  String connectHttpsByPost(String path, File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL urlObj = new URL(path);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        String result = null;
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="
                        + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\""
                + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }
    //获取公众号下指定类型的素材
    public  JSONObject get_material(String token,String type,int offset,int count) throws Exception{
        try {
            log.info("开始上传图文消息内的图片---------------------");
            //上传图片素材
            String requestUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
            requestUrl=requestUrl.replace("ACCESS_TOKEN", token);
            Material material = new Material();
            material.setType(type);
            material.setOffset(offset);
            material.setCount(count);
            JSONObject jsonResult = httpRequest(requestUrl, "POST", material.toJSON());
            if (jsonResult != null) {
                return jsonResult;
            }
            return null;
        } catch (Exception e) {
            log.error("程序异常", e);
            throw e;
        }finally{
            log.info("结束上传图文消息内的图片---------------------");
        }
    }

    public static void main(String[] args) throws Exception {


            String appId = "wx3920e6874f8f44ba";
            String appSecret ="56043821d2d4ac42174fc76facfa2ccd";
            WeiXinService weiXinService = new WeiXinService();
            AccessToken accessToken = weiXinService.getAccessToken(appId, appSecret);


        //生成二维码
        String ticket = weiXinService.getQRCodeTicket(accessToken.getToken(), 123);

        weiXinService.get_QR("10.jpg", "D:/", ticket);
//        String json = " {\n" +
//                "        \"button\": [\n" +
//                "            {\n" +
//                "                \"type\": \"click\", \n" +
//                "                \"name\": \"今日歌曲\", \n" +
//                "                \"key\": \"V1001_TODAY_MUSIC\", \n" +
//                "                \"sub_button\": [ ]\n" +
//                "            }, \n" +
//                "            {\n" +
//                "                \"type\": \"click\", \n" +
//                "                \"name\": \"歌手简介\", \n" +
//                "                \"key\": \"V1001_TODAY_SINGER\", \n" +
//                "                \"sub_button\": [ ]\n" +
//                "            }, \n" +
//                "            {\n" +
//                "                \"name\": \"菜单\", \n" +
//                "                \"sub_button\": [\n" +
//                "                    {\n" +
//                "                        \"type\": \"view\", \n" +
//                "                        \"name\": \"搜索\", \n" +
//                "                        \"url\": \"http://www.soso.com/\", \n" +
//                "                        \"sub_button\": [ ]\n" +
//                "                    }, \n" +
//                "                    {\n" +
//                "                        \"type\": \"view\", \n" +
//                "                        \"name\": \"视频\", \n" +
//                "                        \"url\": \"http://v.qq.com/\", \n" +
//                "                        \"sub_button\": [ ]\n" +
//                "                    }, \n" +
//                "                    {\n" +
//                "                        \"type\": \"click\", \n" +
//                "                        \"name\": \"赞一下我们\", \n" +
//                "                        \"key\": \"V1001_GOOD\", \n" +
//                "                        \"sub_button\": [ ]\n" +
//                "                    }\n" +
//                "                ]\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }";
        //createMenu(json,accessToken.getToken()) ;
        }
}
