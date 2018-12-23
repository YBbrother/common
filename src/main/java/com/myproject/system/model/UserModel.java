package com.myproject.system.model;

import java.io.Serializable;

public class UserModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public UserModel() {
		
	}
	
	public UserModel (UserModel userModel) {
		this.userId = userModel.getUserId();
		this.userName = userModel.getUserName();
		this.nickname = userModel.getNickname();
		this.password = userModel.getPassword();
		this.salt = userModel.getSalt();
		this.email = userModel.getEmail();
		this.sex = userModel.getSex();
		this.birth = userModel.getBirth();
		this.liveAddress = userModel.getLiveAddress();
		this.status = userModel.getStatus();
		this.createById = userModel.getCreateById();
		this.lastLoginTime = userModel.getLastLoginTime();
		this.updateTime = userModel.getUpdateTime();
		this.createTime = userModel.getCreateTime();
	}
	
	public static final Integer _0 = new Integer(0);	// 0:禁止登录
	public static final Integer _1 = new Integer(1);	// 1:有效
	
	private Integer userId;    // 用户ID
	private String userName;    // 用户名
    private String nickname;    // 昵称
    private transient String password;    // 密码  被声明为transient的属性不会被序列化
    private String salt;    // 盐
    private String email;    // 邮箱
    private String sex;    // 性别
    private String birth;    // 生日
    private String liveAddress;    // 居住地址
    private String icon;    // 头像
    private Integer status;    // 状态（0：冻结，1：正常）
    private String createById;    // 创建人id
    private String lastLoginTime;    // 最后登录时间
    private String updateTime;    // 更新时间
    private String createTime;    // 创建时间

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", userName=" + userName + ", nickname=" + nickname + ", salt=" + salt
				+ ", email=" + email + ", sex=" + sex + ", birth=" + birth + ", liveAddress=" + liveAddress + ", icon="
				+ icon + ", status=" + status + ", createById=" + createById + ", lastLoginTime=" + lastLoginTime
				+ ", updateTime=" + updateTime + ", createTime=" + createTime + "]";
	}
    
}
