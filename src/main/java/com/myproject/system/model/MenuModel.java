package com.myproject.system.model;

public class MenuModel {
	
	private Integer menuId;    // 菜单ID
	private Integer parentId;    // 父菜单ID，一级菜单为0
	private String menuName;    // 菜单名称
	private String menu_url;    // 菜单URL
	private String perms;    // 授权(多个用逗号分隔，如：user:list,user:create)
	private String type;    // 类型(0:目录，1:菜单，2:按钮)
	private String icon;    // 菜单图标
	private String order_num;    // 菜单顺序
	private String stauts;    // 菜单状态(0:显示，1:隐藏)
	private String update_time;    // 更新时间
	private String create_time;    // 创建时间
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getPerms() {
		return perms;
	}
	public void setPerms(String perms) {
		this.perms = perms;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public String getStauts() {
		return stauts;
	}
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "MenuModel [menuId=" + menuId + ", parentId=" + parentId + ", menuName=" + menuName + ", menu_url="
				+ menu_url + ", perms=" + perms + ", type=" + type + ", icon=" + icon + ", order_num=" + order_num
				+ ", stauts=" + stauts + ", update_time=" + update_time + ", create_time=" + create_time + "]";
	}
	
}
