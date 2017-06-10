package tuguangquan;
/** 
 * @author miaowei 
 * @version 创建时间：2013-11-25 上午9:40:06 
 * 类说明 
 */

public class Tools {
	//private static Log log = LogFactory.getLog(ReadSendSMSMOReq.class);
	public static String StringtoNull(String str) {
		if (str == null) {
			str = "";
		} else if (str.trim().equals("NULL") || str.trim().equals("null")) {
			str = "";
		} else {
			str = str.trim();
		}
		return str;
	}
	
}
