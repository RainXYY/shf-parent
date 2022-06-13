package com.atguigu.entity;


import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class House extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//小区id
	private Long communityId;
	//房源名称
	private String name;
	//描述
	private String description;
	//总价：万元
	private String totalPrice;
	//单位价格
	private String unitPrice;
	//建筑面积
	private String buildArea;
	//套内面积
	private String insideArea;
	//户型：（字典id）
	private Long houseTypeId;
	//楼层（字典id）
	private Long floorId;
	//建筑结构：（字典id）
	private Long buildStructureId;
	//朝向：（字典id）
	private Long directionId;
	//装修情况：（字典id）
	private Long decorationId;
	//房屋用途：（字典id）
	private Long houseUseId;
	//电梯比例
	private String elevatorRatio;
	//挂牌日期
	private Date listingDate;
	//上次交易日期
	private Date lastTradeDate;
	//状态
	private Integer status;

	//户型：（字典id）
	private String houseTypeName;
	//楼层（字典id）
	private String floorName;
	//建筑结构：（字典id）
	private String buildStructureName;
	//朝向：（字典id）
	private String directionName;
	//装修情况：（字典id）
	private String decorationName;
	//房屋用途：（字典id）
	private String houseUseName;

	/**
	 * 以字符串类型获取挂牌日期
	 * @return
	 */
	public String getListingDateString() {
		Date date = this.getListingDate();
		if(null == date) {
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		return dateString;
	}

	/**
	 * 以字符串方式设置挂牌日期
	 * @param value
	 */
	public void setListingDateString(String value) {
		try {
			if(null == value || "".equals(value)) {
				return;
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(value);
			this.setListingDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 以字符串方式获取上次交易日期
	 * @return
	 */
	public String getLastTradeDateString() {
		Date date = this.getLastTradeDate();
		if(null == date) {
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		return dateString;
	}

	/**
	 * 以字符串方式设置上次交易日期
	 * @param value
	 */
	public void setLastTradeDateString(String value) {
		try {
			if(null == value || "".equals(value)) {
				return;
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(value);
			this.setLastTradeDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

