package com.atguigu.entity;
import lombok.Data;
@Data
public class Community extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//小区名称
	private String name;
	//描述
	private String description;
	//省id：（字典id）
	private Long provinceId;
	//城市id：（字典id）
	private Long cityId;
	//区域id：（字典id）
	private Long areaId;
	//板块id：（字典id）
	private Long plateId;
	//详细地址
	private String address;
	//经度
	private String longitude;
	//纬度
	private String latitude;
	//建筑年代
	private String buildYears;
	//物业价格
	private String propertyPrice;
	//物业公司
	private String propertyCompany;
	//开发商
	private String developer;
	//楼栋数
	private Integer buildNum;
	//房屋数
	private Integer houseNum;
	//均价
	private String averagePrice;

	//区域id：（字典id）
	private String areaName;
	//板块id：（字典id）
	private String plateName;
}

