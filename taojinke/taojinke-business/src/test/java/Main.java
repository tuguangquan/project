import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tuguangquan.CommonFunc;
import tuguangquan.Tools;
import tuguangquan.WeixinFriendsInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 17-5-28
 * Time: 下午6:31
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 200;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    private static BufferedImage createImage(String content, String imgPath,
                                             boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        Main.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source
     *            二维码图片
     * @param imgPath
     *            LOGO图片地址
     * @param needCompress
     *            是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param imgPath
     *            LOGO地址
     * @param destPath
     *            存储地址
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath,
                              boolean needCompress) throws Exception {
        BufferedImage image = Main.createImage(content, imgPath,
                needCompress);
        FileUtil.mkDirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param imgPath
     *            LOGO地址
     * @param destPath
     *            存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath)
            throws Exception {
        Main.encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param destPath
     *            存储地址
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath,
                              boolean needCompress) throws Exception {
        Main.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param destPath
     *            存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        Main.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     * @param imgPath
     *            LOGO地址
     * @param output
     *            输出流
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = Main.createImage(content, imgPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码
     *
     * @param content
     *            内容
     * @param output
     *            输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output)
            throws Exception {
        Main.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     *
     * @param file
     *            二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path
     *            二维码图片地址
     * @return
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return Main.decode(new File(path));
    }
    public static void main(String[] args) {
            DefaultHttpClient client = new DefaultHttpClient();
            // ※ 设置HttpClietn 接受Cookie, 使用和浏览器一样的策略
            client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);//连接时间（10秒）
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,10000);//数据传输时间（10秒）

            try {
                // 模拟登陆
                // 变量初始化
                String pw = "srq15549332079"; // 写自己的公众账号 + 密码
                String loginPw = CommonFunc.md5(pw).toLowerCase().trim();
                String loginName = "3469394016@qq.com".trim();  // '音乐经典视听'公众账号
                String storeCookie = "";
                System.out.println("loginPw=" + loginPw);

                HttpPost loginPost = new HttpPost("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN");
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(Arrays.asList(
                        new BasicNameValuePair("f", "json"),
                        new BasicNameValuePair("imgcode", ""),
                        new BasicNameValuePair("username", loginName),
                        new BasicNameValuePair("pwd", loginPw)));

                HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8"); //
                loginPost.setEntity(entity);

                loginPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                loginPost.addHeader("Accept-Encoding", "gzip, deflate");
                loginPost.addHeader("Accept-Language", "zh-cn");
                loginPost.addHeader("Cache-Control", "no-cache");
                loginPost.addHeader("Connection", "Keep-Alive");
                loginPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                loginPost.addHeader("Host", "mp.weixin.qq.com");
                loginPost.addHeader("Referer", "https://mp.weixin.qq.com/");
                loginPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
                loginPost.addHeader("x-requested-with", "XMLHttpRequest");

                HttpResponse loginResponse = client.execute(loginPost);
                String loginResponseStr = EntityUtils.toString(loginResponse.getEntity(), "UTF-8");
                System.out.println("loginRs=" + loginResponseStr);

                JSONObject loginResponseJsonObject = JSON.parseObject(loginResponseStr);
                // ErrMsg 中含有token，取得这个token
                String ErrMsgContent = loginResponseJsonObject.getString("ErrMsg");
                System.out.println("ErrMsg=" + ErrMsgContent);
                String token =  ErrMsgContent.substring(ErrMsgContent.indexOf("token=") + 6); // token
                System.out.println("token=" + token);

                // 保存Cookie
                storeCookie = CommonFunc.addCookie(client, storeCookie);
                System.out.println("[1]storeCookie=" + storeCookie);

                // 模拟登陆成功，释放资源
                loginPost.releaseConnection();

                // 请求个人主页
			/* 防止通过通过个人主页添加额外Cookie == 不前不需要这段代码
			String myPageURL = "https://mp.weixin.qq.com/cgi-bin/home?t=home/index&lang=zh_CN&token=" + token;
			HttpGet mypageGet = new HttpGet(myPageURL);
			mypageGet.addHeader("Accept-Encoding", "gzip, deflate");
			mypageGet.addHeader("Accept-Language", "zh-cn");
			mypageGet.addHeader("Cache-Control", "no-cache");
			mypageGet.addHeader("Connection", "Keep-Alive");
			mypageGet.addHeader("Host", "mp.weixin.qq.com");
			mypageGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
			mypageGet.addHeader("Cookie", storeCookie);

			HttpResponse mypageResp = client.execute(mypageGet);
			System.out.println("mypageRespCode=" + mypageResp.getStatusLine().getStatusCode());
			// 保存Cookie
			storeCookie = CommonFunc.addCookie(client, storeCookie);
			System.out.println("[2]storeCookie=" + storeCookie);

			mypageGet.releaseConnection();
			*/

                // 好友关系列表
                ArrayList<WeixinFriendsInfo> fList = new ArrayList<WeixinFriendsInfo>();
                // 请求用户管理URL，得到好友关系
                String friendsRelationURL = "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=0&type=0&token=" + token + "&lang=zh_CN";
                HttpGet friendsGet = new HttpGet(friendsRelationURL);
                friendsGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                friendsGet.addHeader("Accept-Encoding", "gzip, deflate");
                friendsGet.addHeader("Accept-Language", "zh-cn");
                friendsGet.addHeader("Cache-Control", "no-cache");
                friendsGet.addHeader("Connection", "Keep-Alive");
                friendsGet.addHeader("Host", "mp.weixin.qq.com");
                friendsGet.addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/home?t=home/index&lang=zh_CN&token=" + token);
                friendsGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
                friendsGet.addHeader("Cookie", storeCookie);

                HttpResponse friendsResp = client.execute(friendsGet);
                System.out.println("friendsRespCode=" + friendsResp.getStatusLine().getStatusCode());
                // 好友关系主页面
                String friendsPageHTML = EntityUtils.toString(friendsResp.getEntity());

                Document doc = Jsoup.parse(friendsPageHTML);

                Elements scripts = doc.select("script");
                String friendsHTML = "";
                for(Element script : scripts) {
                    String html = script.html();
                    if(html.indexOf("groupsList") > -1) {
                        friendsHTML = html;
					/* DATA DEMO:
					 *
						wx.cgiData={
						        isVerifyOn: "0"*1,
						        pageIdx : 0,
						            pageCount : 4, // 总页数
						            pageSize : 10,
						            groupsList : ({"groups":[{"id":0,"name":"未分组","cnt":35},{"id":1,"name":"黑名单","cnt":0},{"id":2,"name":"星标组","cnt":4}]}).groups,
						                        friendsList : ({"contacts":[{"id":2193162461,"nick_name":"白春辉","remark_name":"","group_id":0},{"id":2458400680,"nick_name":"乌鸦","remark_name":"","group_id":0},{"id":2301415800,"nick_name":"chapter1","remark_name":"","group_id":0},{"id":2992030403,"nick_name":"ろ回眸","remark_name":"","group_id":0},{"id":411128055,"nick_name":"马文政","remark_name":"","group_id":0},{"id":12910055,"nick_name":"C.Ronaldo","remark_name":"","group_id":0},{"id":2836651722,"nick_name":"尉灵芝","remark_name":"","group_id":0},{"id":1583968083,"nick_name":"齊天飛翔","remark_name":"","group_id":0},{"id":667242020,"nick_name":"喻豆🐶","remark_name":"","group_id":0},{"id":1258045123,"nick_name":"蔡大叔🚁","remark_name":"","group_id":2}]}).contacts,
						                                    currentGroupId : '',
						                        type : "0" * 1 || 0,
						            userRole : '1' * 1,
						            verifyMsgCount : '0' * 1,
						            totalCount : '39' * 1
						    };

					 */
                    }
                }
                if(!friendsHTML.equals("")&& friendsHTML != null) {
                    // 如果存在好友关系

                    // 得到总页数
                    String pageSizeHTML = friendsHTML.substring(friendsHTML.indexOf("pageCount") + "pageCount".length());
                    pageSizeHTML = pageSizeHTML.substring(0, pageSizeHTML.indexOf(",")).trim();
                    pageSizeHTML = pageSizeHTML.substring(pageSizeHTML.indexOf(":") + 1).trim();

                    // 得到friendsList(好友关系) 对应的JSON数据
                    String contentHTML = friendsHTML.substring(friendsHTML.indexOf("=") + 1, friendsHTML.lastIndexOf("};") + 1);
                    String tempHTML = contentHTML.substring(contentHTML.indexOf("friendsList") + 11).trim();
                    tempHTML = tempHTML.substring(0, tempHTML.indexOf(".contacts,"));
                    tempHTML = tempHTML.substring(tempHTML.indexOf(":") + 1).trim();
                    tempHTML = tempHTML.substring(1, tempHTML.length() - 1);
                    System.out.println("tempHTML=" + tempHTML);
                    JSONObject tempObj = JSON.parseObject(tempHTML);
                    JSONArray array = tempObj.getJSONArray("contacts");
                    String nickName = ""; // 昵称
                    String fakeId = ""; // fakeid
                    for(int i = 0, size = array.size() ; i < size ; i++) {
                        Object eachObj = array.get(i);
                        JSONObject info = (JSONObject) eachObj;
                        nickName = info.getString("nick_name");
                        fakeId = info.getString("id");
                        WeixinFriendsInfo fakeBean = new WeixinFriendsInfo(fakeId, nickName, friendsRelationURL);
                        fList.add(fakeBean);
                    }

                    // 释放本次请求
                    friendsGet.releaseConnection();

                    // 请求其余页的好友关系
                    int pageSize = 1;
                    try {
                        pageSize = Integer.valueOf(pageSizeHTML).intValue();
                    } catch (Exception e) {
                        System.out.println("-- parse pageSize has error occured");
                    }

                    if(pageSize > 1) { // 如果有超过一页的好友关系 [page = 1,index = 0 | page = 2,index = 1...]
                        for(int pageIndex = 1 ; pageIndex < pageSize; pageIndex ++ ) {
                            // -- for begin

                            // 请求用户管理URL，得到好友关系
                            String friendsLoopRelationURL = "https://mp.weixin.qq.com/cgi-bin/contactmanage?t=user/index&pagesize=10&pageidx=" + pageIndex + "&type=0&token=" + token + "&lang=zh_CN";
                            HttpGet friendsLoopGet = new HttpGet(friendsLoopRelationURL);
                            friendsLoopGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                            friendsLoopGet.addHeader("Accept-Encoding", "gzip, deflate");
                            friendsLoopGet.addHeader("Accept-Language", "zh-cn");
                            friendsLoopGet.addHeader("Cache-Control", "no-cache");
                            friendsLoopGet.addHeader("Connection", "Keep-Alive");
                            friendsLoopGet.addHeader("Host", "mp.weixin.qq.com");
                            friendsLoopGet.addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/home?t=home/index&lang=zh_CN&token=" + token);
                            friendsLoopGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
                            friendsLoopGet.addHeader("Cookie", storeCookie);

                            HttpResponse friendsLoopResp = client.execute(friendsLoopGet);
                            System.out.println("friendsLoopRespCode=" + friendsLoopResp.getStatusLine().getStatusCode());
                            // 好友关系主页面
                            String friendsLoopPageHTML = EntityUtils.toString(friendsLoopResp.getEntity());

                            Document docLoop = Jsoup.parse(friendsLoopPageHTML);

                            Elements scriptsLoop = docLoop.select("script");
                            String friendsHTMLLoop = "";
                            for(Element script : scriptsLoop) {
                                String html = script.html();
                                if(html.indexOf("groupsList") > -1) {
                                    friendsHTMLLoop = html;
								/* DATA DEMO:
								 *
									wx.cgiData={
									        isVerifyOn: "0"*1,
									        pageIdx : 0,
									            pageCount : 4, // 总页数
									            pageSize : 10,
									            groupsList : ({"groups":[{"id":0,"name":"未分组","cnt":35},{"id":1,"name":"黑名单","cnt":0},{"id":2,"name":"星标组","cnt":4}]}).groups,
									                        friendsList : ({"contacts":[{"id":2193162461,"nick_name":"白春辉","remark_name":"","group_id":0},{"id":2458400680,"nick_name":"乌鸦","remark_name":"","group_id":0},{"id":2301415800,"nick_name":"chapter1","remark_name":"","group_id":0},{"id":2992030403,"nick_name":"ろ回眸","remark_name":"","group_id":0},{"id":411128055,"nick_name":"马文政","remark_name":"","group_id":0},{"id":12910055,"nick_name":"C.Ronaldo","remark_name":"","group_id":0},{"id":2836651722,"nick_name":"尉灵芝","remark_name":"","group_id":0},{"id":1583968083,"nick_name":"齊天飛翔","remark_name":"","group_id":0},{"id":667242020,"nick_name":"喻豆🐶","remark_name":"","group_id":0},{"id":1258045123,"nick_name":"蔡大叔🚁","remark_name":"","group_id":2}]}).contacts,
									                                    currentGroupId : '',
									                        type : "0" * 1 || 0,
									            userRole : '1' * 1,
									            verifyMsgCount : '0' * 1,
									            totalCount : '39' * 1
									    };

								 */
                                }
                            }

                            // 得到friendsList(好友关系) 对应的JSON数据
                            String contentHTMLLoop = friendsHTMLLoop.substring(friendsHTMLLoop.indexOf("=") + 1, friendsHTMLLoop.lastIndexOf("};") + 1);
                            String tempHTMLLoop = contentHTMLLoop.substring(contentHTMLLoop.indexOf("friendsList") + 11).trim();
                            tempHTMLLoop = tempHTMLLoop.substring(0, tempHTMLLoop.indexOf(".contacts,"));
                            tempHTMLLoop = tempHTMLLoop.substring(tempHTMLLoop.indexOf(":") + 1).trim();
                            tempHTMLLoop = tempHTMLLoop.substring(1, tempHTMLLoop.length() - 1);
                            System.out.println("tempHTMLLoop=" + tempHTMLLoop);
                            JSONObject tempObjLoop = JSON.parseObject(tempHTMLLoop);
                            JSONArray arrayLoop = tempObjLoop.getJSONArray("contacts");
                            String nickNameLoop = ""; // 昵称
                            String fakeIdLoop = ""; // fakeid
                            for(int i = 0, size = arrayLoop.size() ; i < size ; i++) {
                                Object eachObj = arrayLoop.get(i);
                                JSONObject info = (JSONObject) eachObj;
                                nickNameLoop = info.getString("nick_name");
                                fakeIdLoop = info.getString("id");
                                WeixinFriendsInfo fakeBean = new WeixinFriendsInfo(fakeIdLoop, nickNameLoop, friendsLoopRelationURL);
                                fList.add(fakeBean);
                            }

                            // 释放本次请求
                            friendsLoopGet.releaseConnection();
                            // -- for end
                        }
                    }

                }

                int fsize = fList.size();
                System.out.println("size=" + fsize);
//			System.out.println(fList);


                for(int i = 0; i < fsize; i ++) {

                    WeixinFriendsInfo f = fList.get(i);

                    // 完善好友资料
                    String richFriendsUrl = "https://mp.weixin.qq.com/cgi-bin/getcontactinfo";
                    HttpPost richFriendsPost = new HttpPost(richFriendsUrl);
                    // 设定请求参数
                    List<NameValuePair> richFriendsPost_parameters = new ArrayList<NameValuePair>(Arrays.asList(
                            new BasicNameValuePair("ajax", "1"),
                            new BasicNameValuePair("f", "json"),
                            new BasicNameValuePair("fakeid", f.getFakeId()),
                            new BasicNameValuePair("lang", "zh_CN"),
                            new BasicNameValuePair("random", "0.9637480949493703"),
                            new BasicNameValuePair("token", token),
                            new BasicNameValuePair("t", "ajax-getcontactinfo")));
                    HttpEntity richFriendsPost_entity = new UrlEncodedFormEntity(richFriendsPost_parameters, "UTF-8");
                    richFriendsPost.setEntity(richFriendsPost_entity);
                    // 请求头的设定
                    richFriendsPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                    richFriendsPost.addHeader("Accept-Encoding", "gzip, deflate");
                    richFriendsPost.addHeader("Accept-Language", "zh-cn");
                    richFriendsPost.addHeader("Cache-Control", "no-cache");
                    richFriendsPost.addHeader("Connection", "Keep-Alive");
                    richFriendsPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    richFriendsPost.addHeader("Host", "mp.weixin.qq.com");
                    richFriendsPost.addHeader("Referer", f.getReferUrlInRrichCondition());
                    richFriendsPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
                    richFriendsPost.addHeader("x-requested-with", "XMLHttpRequest");
                    richFriendsPost.addHeader("Cookie", storeCookie);

                    HttpResponse richFriendsResp = client.execute(richFriendsPost);

                    String richFriendsRespHTML = EntityUtils.toString(richFriendsResp.getEntity());
                    JSONObject richFriendsRespObj = JSON.parseObject(richFriendsRespHTML);
                    JSONObject contactObj = richFriendsRespObj.getJSONObject("contact_info");
                    String signature = contactObj.getString("signature"); // 个性签名
                    String sex = "未知";
                    if("1".equals(contactObj.getString("gender"))) {
                        sex = "男";
                    } else if ("2".equals(contactObj.getString("gender"))) {
                        sex = "女";
                    }
                    // 地理位置
                    String location = Tools.StringtoNull(contactObj.getString("country")) + Tools.StringtoNull(contactObj.getString("province")) + Tools.StringtoNull(contactObj.getString("city"));
                    // 更新当前Bean 的个人信息
                    f.setSex(sex);
                    f.setSignature(signature);
                    f.setLocation(location);

                    fList.set(i, f); // 更新好友信息

                    richFriendsPost.releaseConnection();
                }

                System.out.println(fList);

			/* 请求头像
			HttpGet get = new HttpGet("https://mp.weixin.qq.com/cgi-bin/getheadimg?fakeid=2301415800&token=378496573&lang=zh_CN");
			get.addHeader("Cookie", storeCookie);
			HttpResponse resp = client.execute(get);
			FileOutputStream out = new FileOutputStream(new File("D:/var/test.jpg"));
			byte[] b = new byte[1024];
			while(resp.getEntity().getContent().read(b) != -1) {
				out.write(b);
			}

			out.close();
			System.out.println(" --- end ");
			*/

                // 主动推送(需要满足 24小时之内 用户主动和你说话)
                String sendMsgUrl = "https://mp.weixin.qq.com/cgi-bin/singlesend";
                HttpPost sendMsgPost = new HttpPost(sendMsgUrl);
                // 设定请求参数
                List<NameValuePair> sendMsgPost_parameters = new ArrayList<NameValuePair>(Arrays.asList(
                        new BasicNameValuePair("ajax", "1"),
                        new BasicNameValuePair("content", "美好新生活ABCD"),  // 文本内容
                        new BasicNameValuePair("f", "json"),
                        new BasicNameValuePair("lang", "zh_CN"),
                        new BasicNameValuePair("random", "0.9637480949493703"),
                        new BasicNameValuePair("token", token),
                        new BasicNameValuePair("tofakeid", "2193162461"),
                        new BasicNameValuePair("type", "1"),
                        new BasicNameValuePair("t", "ajax-response")));

                HttpEntity sendMsgPost_entity = new UrlEncodedFormEntity(sendMsgPost_parameters, "UTF-8");
                sendMsgPost.setEntity(sendMsgPost_entity);
                // 请求头的设定
                sendMsgPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                sendMsgPost.addHeader("Accept-Encoding", "gzip, deflate");
                sendMsgPost.addHeader("Accept-Language", "zh-cn");
                sendMsgPost.addHeader("Cache-Control", "no-cache");
                sendMsgPost.addHeader("Connection", "Keep-Alive");
                sendMsgPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                sendMsgPost.addHeader("Host", "mp.weixin.qq.com");
                sendMsgPost.addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&tofakeid=2193162461&token=" + token + "&lang=zh_CN");
                sendMsgPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
                sendMsgPost.addHeader("x-requested-with", "XMLHttpRequest");
                sendMsgPost.addHeader("Cookie", storeCookie);

                HttpResponse sendMsgResp = client.execute(sendMsgPost);
                System.out.println("send msg code=" + sendMsgResp.getStatusLine().getStatusCode());
			/*
			 *  失败返回值 ： send msg resp content={"base_resp":{"ret":10706,"err_msg":"customer block"}}
				成功返回值：  send msg resp content={"base_resp":{"ret":0,"err_msg":"ok"}}
			 */
                JSONObject rtnObj = JSON.parseObject(EntityUtils.toString(sendMsgResp.getEntity()));
                JSONObject base_respObj = rtnObj.getJSONObject("base_resp");
                String rtnCode = Tools.StringtoNull(base_respObj.getString("ret"));
                String rtnMsg = "";
                if("0".equals(rtnCode)) {
                    rtnMsg = "发送成功";
                } else {
                    rtnMsg = "发送失败";

                    if("10706".equals(rtnCode)) {
                        // 微信官方变更对应
                        rtnMsg = "由于该用户24小时未与你互动，你不能再主动发消息给他。直到用户下次主动发消息给你才可以对其进行回复。";
                    }
                }

                System.out.println(rtnMsg);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                client.getConnectionManager().shutdown();
            }

        }
}
