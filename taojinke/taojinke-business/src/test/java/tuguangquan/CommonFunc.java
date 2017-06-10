package tuguangquan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import net.sf.json.JSONObject;

/**
 * 系统所属常用操作方法类
 * @author 
 * @version 1.0
 * @email 13842093845@139.com
 * @createDateTime 2011-6-2 上午10:31:15
 */

public class CommonFunc {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static HashMap<String, String> returnCodeMap = new HashMap<String, String>();
	static{
		returnCodeMap.put("-1", "系统繁忙");
		returnCodeMap.put("0", "请求成功");
		returnCodeMap.put("40001", "获取access_token时AppSecret错误，或者access_token无效");
		returnCodeMap.put("40002", "不合法的凭证类型");
		returnCodeMap.put("40003", "不合法的OpenID");
		returnCodeMap.put("40004", "不合法的媒体文件类型");
		returnCodeMap.put("40005", "不合法的文件类型");
		returnCodeMap.put("40006", "不合法的文件大小");
		returnCodeMap.put("40007", "不合法的媒体文件id");
		returnCodeMap.put("40008", "不合法的消息类型");
		returnCodeMap.put("40009", "不合法的图片文件大小");
		returnCodeMap.put("40010", "不合法的语音文件大小");
		returnCodeMap.put("40011", "不合法的视频文件大小");
		returnCodeMap.put("40012", "不合法的缩略图文件大小");
		returnCodeMap.put("40013", "不合法的APPID");
		returnCodeMap.put("40014", "不合法的access_token");
		returnCodeMap.put("40015", "不合法的菜单类型");
		returnCodeMap.put("40016", "不合法的按钮个数");
		returnCodeMap.put("40017", "不合法的按钮个数");
		returnCodeMap.put("40018", "不合法的按钮名字长度");
		returnCodeMap.put("40019", "不合法的按钮KEY长度");
		returnCodeMap.put("40020", "不合法的按钮URL长度");
		returnCodeMap.put("40021", "不合法的菜单版本号");
		returnCodeMap.put("40022", "不合法的子菜单级数");
		returnCodeMap.put("40023", "不合法的子菜单按钮个数");
		returnCodeMap.put("40024", "不合法的子菜单按钮类型");
		returnCodeMap.put("40025", "不合法的子菜单按钮名字长度");
		returnCodeMap.put("40026", "不合法的子菜单按钮KEY长度");
		returnCodeMap.put("40027", "不合法的子菜单按钮URL长度");
		returnCodeMap.put("40028", "不合法的自定义菜单使用用户");
		returnCodeMap.put("40029", "不合法的oauth_code");
		returnCodeMap.put("40030", "不合法的refresh_token");
		returnCodeMap.put("40031", "不合法的openid列表");
		returnCodeMap.put("40032", "不合法的openid列表长度");
		returnCodeMap.put("40033", "不合法的请求字符，不能包含\\uxxxx格式的字符");
		returnCodeMap.put("40035", "不合法的参数");
		returnCodeMap.put("40038", "不合法的请求格式");
		returnCodeMap.put("40039", "不合法的URL长度");
		returnCodeMap.put("40050", "不合法的分组id");
		returnCodeMap.put("40051", "分组名字不合法");
		returnCodeMap.put("41001", "缺少access_token参数");
		returnCodeMap.put("41002", "缺少appid参数");
		returnCodeMap.put("41003", "缺少refresh_token参数");
		returnCodeMap.put("41004", "缺少secret参数");
		returnCodeMap.put("41005", "缺少多媒体文件数据");
		returnCodeMap.put("41006", "缺少media_id参数");
		returnCodeMap.put("41007", "缺少子菜单数据");
		returnCodeMap.put("41008", "缺少oauth code");
		returnCodeMap.put("41009", "缺少openid");
		returnCodeMap.put("42001", "access_token超时");
		returnCodeMap.put("42002", "refresh_token超时");
		returnCodeMap.put("42003", "oauth_code超时");
		returnCodeMap.put("43001", "需要GET请求");
		returnCodeMap.put("43002", "需要POST请求");
		returnCodeMap.put("43003", "需要HTTPS请求");
		returnCodeMap.put("43004", "需要接收者关注");
		returnCodeMap.put("43005", "需要好友关系");
		returnCodeMap.put("44001", "多媒体文件为空");
		returnCodeMap.put("44002", "POST的数据包为空");
		returnCodeMap.put("44003", "图文消息内容为空");
		returnCodeMap.put("44004", "文本消息内容为空");
		returnCodeMap.put("45001", "多媒体文件大小超过限制");
		returnCodeMap.put("45002", "消息内容超过限制");
		returnCodeMap.put("45003", "标题字段超过限制");
		returnCodeMap.put("45004", "描述字段超过限制");
		returnCodeMap.put("45005", "链接字段超过限制");
		returnCodeMap.put("45006", "图片链接字段超过限制");
		returnCodeMap.put("45007", "语音播放时间超过限制");
		returnCodeMap.put("45008", "图文消息超过限制");
		returnCodeMap.put("45009", "接口调用超过限制");
		returnCodeMap.put("45010", "创建菜单个数超过限制");
		returnCodeMap.put("45015", "回复时间超过限制");
		returnCodeMap.put("45016", "系统分组，不允许修改");
		returnCodeMap.put("45017", "分组名字过长");
		returnCodeMap.put("45018", "分组数量超过上限");
		returnCodeMap.put("46001", "不存在媒体数据");
		returnCodeMap.put("46002", "不存在的菜单版本");
		returnCodeMap.put("46003", "不存在的菜单数据");
		returnCodeMap.put("46004", "不存在的用户");
		returnCodeMap.put("47001", "解析JSON/XML内容错误");
		returnCodeMap.put("48001", "api功能未授权");
		returnCodeMap.put("50001", "用户未授权该api");
	}

	/**
	 * 
	 * 输出操作结果方法
	 * @param request：HttpServletRequest
	 * @param response：HttpServletResponse
	 * @param result：操作结果标记
	 * @return 无
	 * @author 马进友
	 * @version 1.0
	 * @email 13842093845@139.com
	 * @createDateTime 2011-6-7 上午09:36:26
	 */
	public static void printResult(HttpServletRequest request,HttpServletResponse response,String result)throws Exception {

		PrintWriter out = null;
		
		try{
			request.setCharacterEncoding("UTF-8");   
	        response.setContentType("text/html;charset=UTF-8");
	        out = response.getWriter();
	        
	        //System.out.println("---result----"+result);
	        out.print(result);
	        
		}catch(Exception e){
			throw e;
		}finally{
			if(out != null){
				//out.flush();
		        out.close();
			}
		}
	}

	/**
	 * 
	 * 输出错误信息到log文件中
	 * @param e：Exception
	 * @param classPath：出现异常的类路径
	 * @return 无
	 * @author 马进友
	 * @version 1.0
	 * @email 13842093845@139.com
	 * @createDateTime 2012-05-15 上午09:36:26
	 */
	public void writeErrorLog(Exception e, String classPath) {  

		//step 1 获取详细错误日志信息
    	StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));

        //step 2 将错误信息输送到文件
        logger.error("执行"+classPath+"."+e.getStackTrace()[0].getMethodName()+"方法失败，错误原因：" + sw.toString());

    }
	
	/* 
	 * 处理通过Ajax提交的Form数据
	 */
	public static String decodeAjaxTransferData(String data)
			throws UnsupportedEncodingException {
		String transfer = URLDecoder.decode(data, "UTF-8");
		return URLDecoder.decode(transfer, "UTF-8");
	}

	/**
	 * MD5 加密
	 * @param originalText
	 * @return
	 * @throws Exception
	 */
	public static String md5(String originalText) throws Exception {
		byte buf[] = originalText.getBytes("ISO-8859-1");
		StringBuffer hexString = new StringBuffer();
		String result = "";
		String digit = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(buf);
			byte[] digest = algorithm.digest();
			for (int i = 0; i < digest.length; i++) {
				digit = Integer.toHexString(0xFF & digest[i]);
				if (digit.length() == 1) {
					digit = "0" + digit;
				}
				hexString.append(digit);
			}
			result = hexString.toString();
		} catch (Exception ex) {
			result = "";
		}
		return result.toUpperCase();
	}
	
	/**
	 * Cookie 累加操作
	 * 
	 * @param client
	 * @param currentCookie 当前的Cookie 总和
	 * @return
	 */
	public static String addCookie (DefaultHttpClient client, String currentCookie) {
		String backCookie = ""; // 返回的Cookie
		
		if (currentCookie.equals("")) {
			for(Cookie c : client.getCookieStore().getCookies()) {
				backCookie += (c.getName() + "=" + c.getValue() + ";");
			}
		} else {
			// 先看当前Cookie 中是否已经含有
			String[] cookies = currentCookie.split(";");
			ArrayList<String> alreadyCookieKeys = new ArrayList<String>();
			for (String c : cookies) {
				alreadyCookieKeys.add(c.split("=")[0]);
			}
			
			String addCookie = ""; // 本次新追加的Cookie
			for(Cookie c : client.getCookieStore().getCookies()) {
				if (!alreadyCookieKeys.contains(c.getName())) { 
					// 如果当前Cookie 中不含有, 则在当前的Cookie基础上 追加新的Cookie
					addCookie += (c.getName() + "=" + c.getValue() + ";");
				}
			}
			// 本次最新的Cookie = 当前Cookie + 本次Client请求新追加的Cookie
			backCookie = currentCookie + addCookie;
		}
		
		return backCookie;
	}

	public static void nativeDebugNew(String content) {
		try {
			String logFilePath = "D:/var/info2.log";
			File file = new File(logFilePath);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			String time = new Date() + " | " ;
			String writeContent = "[native_log] " + time + content + "\r\n";
			
//			FileOutputStream out = new FileOutputStream(file, true);
			FileOutputStream out = new FileOutputStream(file);
			out.write(writeContent.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}