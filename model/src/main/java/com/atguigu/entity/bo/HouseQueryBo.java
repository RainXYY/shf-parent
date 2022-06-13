package com.atguigu.entity.bo;


import lombok.Data;

import java.io.Serializable;
@Data
public class HouseQueryBo implements Serializable {
	private static final long serialVersionUID = 1L;
	//区域id：（字典id）
	private Long areaId;
	//板块id：（字典id）
	private Long plateId;
	//房源关键字
	private String keyword;
	//总价：万元
	private String totalPrice;
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
	//排序：默认 defaultSort=1按默认排序
	private Integer defaultSort;
	//排序：默认 priceSort=1按价格排序
	private Integer priceSort;
	//排序：默认 timeSort=1按时间排序
	private Integer timeSort;
}

