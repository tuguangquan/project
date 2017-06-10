package tuguangquan;

/**
 * 微信好友关系Bean
 * 
 * @author BCH
 *
 */
public class WeixinFriendsInfo {

	private String fakeId;
	private String nickName; // 好友昵称
	private String referUrlInRrichCondition; // 完善好友资料时候的请求头： Referer
	
	private String signature; // 签名
	private String location; // 地区
	private String sex; // 性别
	
	public WeixinFriendsInfo() {}
	
	public WeixinFriendsInfo(String fakeId, String nickName, String referUrlInRrichCondition) {
		this.fakeId = fakeId;
		this.nickName = nickName;
		this.referUrlInRrichCondition = referUrlInRrichCondition;
	}
	
	public String getFakeId() {
		return fakeId;
	}
	public void setFakeId(String fakeId) {
		this.fakeId = fakeId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getReferUrlInRrichCondition() {
		return referUrlInRrichCondition;
	}

	public void setReferUrlInRrichCondition(String referUrlInRrichCondition) {
		this.referUrlInRrichCondition = referUrlInRrichCondition;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "nickName=" + this.nickName + " , fakeId=" + this.fakeId + ", referUrlInRrichCondition=" + this.referUrlInRrichCondition
				+ ", signature=" + this.signature + ", location=" + this.location + ", sex=" + this.sex; 
	}

}
