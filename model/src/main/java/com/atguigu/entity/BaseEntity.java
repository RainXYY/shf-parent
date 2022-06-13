package com.atguigu.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 包名:com.atguigu.entity
 *
 * @author Leevi
 * 日期2022-03-22  21:41
 */
@Data
public class BaseEntity implements Serializable {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
}
