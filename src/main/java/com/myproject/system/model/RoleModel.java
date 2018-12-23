package com.myproject.system.model;

public class RoleModel {
	
	private Integer roleId;    // 角色id
	private String roleName;    // 角色名称
	private String roleCode;    // 角色编号
	private String description;    // 角色描述
	private String status;    // 状态(0:启用，1:禁用)
	private String updateTime;    // 更新时间
	private String createTime;    // 创建时间
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleCode=" + roleCode + ", description="
				+ description + ", status=" + status + ", updateTime=" + updateTime + ", createTime=" + createTime
				+ "]";
	}
	
}
