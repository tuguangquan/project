package tuguangquan;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 模拟微信添加素材， 并发送
 * 包含功能: 1.模拟登陆 2.模拟对微信号码主动发送图文信息
 * 
 * @author BCH
 *
 */
public class MoniWeixinAddSucai {

	public static void main(String[] args) {
		DefaultHttpClient client = new DefaultHttpClient();
		// ※ 设置HttpClietn 接受Cookie, 使用和浏览器一样的策略
		client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);//连接时间（10秒）
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,10000);//数据传输时间（10秒）
		try {
			// 模拟登陆微信
			// 变量初始化
			String pw = ""; // 写自己的公众账号 + 密码
			String loginPw = CommonFunc.md5(pw).toLowerCase().trim();
			String loginName = "2240224035@qq.com".trim();  // '口袋音乐'公众账号 
//			String loginName = "448220849@qq.com".trim();  // '音乐经典视听'公众账号
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
			
			// 模拟登陆成功,释放资源
			loginPost.releaseConnection();
			
			// 为向服务器上传资源做准备， 得到票据ID ， 即下面需要的ticket
			String ticket = "";
			String getTicketUrl = "https://mp.weixin.qq.com/cgi-bin/appmsg?t=media/appmsg_edit&action=edit&type=10&isMul=0&isNew=1&lang=zh_CN&token=" + token;
			HttpGet ticketGet = new HttpGet(getTicketUrl);
			// 请求头的设定
			ticketGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			ticketGet.addHeader("Accept-Encoding", "gzip, deflate");
			ticketGet.addHeader("Accept-Language", "zh-cn");
			ticketGet.addHeader("Cache-Control", "no-cache");
			ticketGet.addHeader("Connection", "Keep-Alive");
			ticketGet.addHeader("Host", "mp.weixin.qq.com");
			ticketGet.addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/appmsg?begin=0&count=10&t=media/appmsg_list&type=10&action=list&token=" + token + "&lang=zh_CN");
			ticketGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
			ticketGet.addHeader("Cookie", storeCookie);
			
			HttpResponse ticketResp = client.execute(ticketGet);
			String ticketContent = EntityUtils.toString(ticketResp.getEntity());
			CommonFunc.nativeDebugNew(ticketContent);
			Document doc = Jsoup.parse(ticketContent);
			Elements els = doc.select("script");
			for(Element e : els) {
				String html = e.html();
				if(html.indexOf("ticket") > -1) { // 定位票据ID内容块
					/* DATA BLOCK: 
					 *  window.wx ={
					        version:"4.0.0",
					        data:{
					            t:"1702302509",
					            ticket:"8f3b9f31f6765b8e453272f5824acdacec11db30",
					            lang:'zh_CN',
					            param:["&token=1702302509",'&lang=zh_CN'].join(""),
					            uin:"2399790341",
					            user_name:"PocketMusic",
					            time:"1388460776"||new Date().getTime()/1000
					        },
					 */
					String tmp = html.substring(html.indexOf("ticket") + "ticket".length()).trim();
					tmp = tmp.substring(2, tmp.indexOf(",") - 1);
					ticket = tmp; // 票据ID
				}
			}
			
			System.out.println("-- ticket=" + ticket);
			ticketGet.releaseConnection();
			
			// 向服务器上传资源（※ 这个地址的取得最为困难， 后来发现是下面script=的值， 个人理解Object标签是微信官方封装的上传组件）
			String upfileUrl = "https://mp.weixin.qq.com/cgi-bin/filetransfer?action=upload_material" +
					"&f=json&ticket_id=PocketMusic&ticket=" + ticket + "&token=" + token + "&lang=zh_CN";
			HttpPost upfilePost = new HttpPost(upfileUrl);
			FileBody bin = new FileBody(new File("D:/var/khbd.jpg")); // 模拟上传提交的文件
			
			upfilePost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			upfilePost.addHeader("Accept-Encoding", "gzip, deflate");
			upfilePost.addHeader("Accept-Language", "en-US,en;q=0.5");
			upfilePost.addHeader("Cache-Control", "no-cache");
			upfilePost.addHeader("Connection", "Keep-Alive");
			upfilePost.addHeader("Host", "mp.weixin.qq.com");
			upfilePost.addHeader("Referer", "http://mp.weixin.qq.com/cgi-bin/indexpage?t=wxm-index&lang=zh_CN");
			upfilePost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
			upfilePost.addHeader("Cookie", storeCookie);
			
			MultipartEntity upfile_entity = new MultipartEntity();
			upfile_entity.addPart("file", bin); // 这里的变量名一定不能弄错， 
				// 来源见下面的(method=POST&amp;queueSizeLimit=5&amp;simUploadLimit=1&amp;hideButton=true&amp;auto=true&amp;fileDataName=file 中‘fileDataName’ 参数对应的value)
				// script 变量对应的value 就是 上传提交的URL 
			/*
			 * 		微信的上传空间
			 *     <object id="js_appmsg_upload_coverUploader" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%">
	<param name="_cx" value="1326"></param>
	<param name="_cy" value="592"></param>
	<param name="FlashVars" 
		value="uploadifyID=js_appmsg_upload_cover&amp;pagepath=/cgi-bin/&amp;script=https%3A%2F%2Fmp.weixin.qq.com%2Fcgi-bin%2Ffiletransfer%3Faction%3Dupload_material%26f%3Djson%26ticket_id%3DPocketMusic%26ticket%3D86d8498f1539af0bc05e9fee2767e2c585e82c5a%26token%3D1603121151%26lang%3Dzh_CN&amp;folder=uploads&amp;width=50&amp;height=22&amp;wmode=opaque&amp;method=POST&amp;queueSizeLimit=5&amp;simUploadLimit=1&amp;hideButton=true&amp;auto=true&amp;fileDataName=file&amp;queueID=fileQueue"></param>
	<param name="Movie" value="https://res.wx.qq.com/mpres/zh_CN/htmledition/plprecorder/uploadify1a9519.swf"></param>
	<param name="Src" value="https://res.wx.qq.com/mpres/zh_CN/htmledition/plprecorder/uploadify1a9519.swf"></param>
	<param name="WMode" value="Opaque"></param><param name="Play" value="0"></param>
	<param name="Loop" value="-1"></param><param name="Quality" value="High"></param>
	<param name="SAlign" value="LT"></param><param name="Menu" value="-1"></param>
	<param name="Base" value=""></param><param name="AllowScriptAccess" value="always"></param>
	<param name="Scale" value="NoScale"></param><param name="DeviceFont" value="0"></param>
	<param name="EmbedMovie" value="0"></param><param name="BGColor" value=""></param>
	<param name="SWRemote" value=""></param><param name="MovieData" value=""></param>
	<param name="SeamlessTabbing" value="1"></param><param name="Profile" value="0"></param>
	<param name="ProfileAddress" value=""></param><param name="ProfilePort" value="0"></param>
	<param name="AllowNetworking" value="all"></param><param name="AllowFullScreen" value="false"></param>
	<param name="AllowFullScreenInteractive" value=""></param><param name="IsDependent" value="0"></param>
	<param name="quality" value="high"></param><param name="wmode" value="opaque"></param><param name="allowScriptAccess" value="always"></param>
	<param name="flashvars" value="uploadifyID=js_appmsg_upload_cover&amp;pagepath=/cgi-bin/&amp;script=https%3A%2F%2Fmp.weixin.qq.com%2Fcgi-bin%2Ffiletransfer%3Faction%3Dupload_material%26f%3Djson%26ticket_id%3DPocketMusic%26ticket%3D86d8498f1539af0bc05e9fee2767e2c585e82c5a%26token%3D1603121151%26lang%3Dzh_CN&amp;folder=uploads&amp;width=50&amp;height=22&amp;wmode=opaque&amp;method=POST&amp;queueSizeLimit=5&amp;simUploadLimit=1&amp;hideButton=true&amp;auto=true&amp;fileDataName=file&amp;queueID=fileQueue"></param>
	<param name="movie" value="https://res.wx.qq.com/mpres/zh_CN/htmledition/plprecorder/uploadify1a9519.swf"></param>
   </object>
			 */
			upfilePost.setEntity(upfile_entity);
			
			HttpResponse upFileResp = client.execute(upfilePost);
			System.out.println(upFileResp.getStatusLine().getStatusCode());
			String upFileBackContent = EntityUtils.toString(upFileResp.getEntity());
			/* upFileBackContent 返回参照： {"base_resp":{"ret":0,"err_msg":"ok"},"location":"bizfile","type":"image","content":"10014036"}  */
			JSONObject upFileBackContentObj = JSON.parseObject(upFileBackContent);
			JSONObject upFileBackContentRtnObj = upFileBackContentObj.getJSONObject("base_resp");
			boolean upSuccessFlg = false;
			if("0".equals(Tools.StringtoNull(upFileBackContentRtnObj.getString("ret")))) {
				// 上传成功
				upSuccessFlg = true;
			}
			// ※ 拿到上传成功的图片 在 微信服务器对应的 序列号, 这个序列号，是作为下一步给指定微信号码 发送图文信息时用的 重要依据
			String upFileTaskId = Tools.StringtoNull(upFileBackContentObj.getString("content"));
			
			upfilePost.releaseConnection();
			
			if(upSuccessFlg) { // 如果附件上传成功
				
				String sendTitle = "这里是标题"; // 标题
				String sendContent = "这里是正文"; // 正文
				String sendDigest = "这里是摘要"; // 摘要
				String preusername = "C_Rona7do"; // 发送目标微信号， 必须是该公众账号的好友
				
				// 如果上传成功， 则执行 对指定微信号码发送 图文微信的操作 [POST]
				String sendUrl = "https://mp.weixin.qq.com/cgi-bin/operate_appmsg";
				HttpPost sendPost = new HttpPost(sendUrl);
				
				sendPost.addHeader("Accept", "text/html, */*; q=0.01");
				sendPost.addHeader("Accept-Encoding", "gzip, deflate");
				sendPost.addHeader("Accept-Language", "en-US,en;q=0.5");
				sendPost.addHeader("Cache-Control", "no-cache");
				sendPost.addHeader("Connection", "Keep-Alive");
				sendPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				sendPost.addHeader("Host", "mp.weixin.qq.com");
				sendPost.addHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/appmsg?t=media/appmsg_edit&action=edit&type=10&isMul=0&isNew=1&lang=zh_CN&token=" + token);
				sendPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET4.0E)");
				sendPost.addHeader("x-requested-with", "XMLHttpRequest");
				sendPost.addHeader("Cookie", storeCookie);
						
				// 发送参数的设定
				List<NameValuePair> send_parameters = new ArrayList<NameValuePair>(Arrays.asList(
						new BasicNameValuePair("ajax", "1"), 
						new BasicNameValuePair("content0", sendContent),
						new BasicNameValuePair("count", "1"),
						new BasicNameValuePair("digest0", sendDigest),
						new BasicNameValuePair("f", "json"),
						new BasicNameValuePair("fileid0", upFileTaskId),
						new BasicNameValuePair("lang", "zh_CN"),
						new BasicNameValuePair("preusername", preusername),
						new BasicNameValuePair("random", "0.8389975940050889"),
						new BasicNameValuePair("show_cover_pic0", "1"),
						new BasicNameValuePair("sub", "preview"),
						new BasicNameValuePair("t", "ajax-appmsg-preview"),
						new BasicNameValuePair("title0", sendTitle),
						new BasicNameValuePair("token", token),
						new BasicNameValuePair("type", "10")));
			
				HttpEntity send_entity = new UrlEncodedFormEntity(send_parameters, "UTF-8"); //
				sendPost.setEntity(send_entity);
				HttpResponse sendResp = client.execute(sendPost);
				System.out.println("sendRespCode=" + sendResp.getStatusLine().getStatusCode());
				System.out.println("sendRespContent=" + EntityUtils.toString(sendResp.getEntity()));
				/* 返回文本 ： sendRespContent={"ret":"0", "msg":"preview send success!", "appMsgId":"10014045", "fakeid":""} */
				
				sendPost.releaseConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
}
