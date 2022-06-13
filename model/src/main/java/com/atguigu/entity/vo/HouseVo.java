package com.atguigu.entity.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class HouseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String communityName;
	private Long id;
	private String name;
	private String buildArea;
	private BigDecimal totalPrice;
	private BigDecimal unitPrice;
	private Long houseTypeId;
	private Long floorId;
	private Long directionId;
	private String defaultImageUrl;
	private Date createTime;
	private String houseTypeName;
	private String floorName;
	private String directionName;

	public String getCreateTimeString() {
		Date date = this.getCreateTime();
		if(null == date) {
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		return dateString;
	}
}

