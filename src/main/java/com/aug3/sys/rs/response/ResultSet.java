package com.aug3.sys.rs.response;

import java.util.List;
import java.io.Serializable;

/**
 * 用于分页查询;当程序正确执行时使用；
 * 
 * 使用方法： 得到 ResultSet示例 ResultSet resultSet =XXXServies.getResultSet();
 * 
 * RespObj obj =new RespObj();
 * 
 * obj.setCode(responseType.getCode());
 * 
 * obj.setType(responseType.toString());
 * 
 * RespObj属性设置 obj.setMessage(resultSet);
 * 
 * @author Fourer.Yin
 * 
 */
@SuppressWarnings("all")
public class ResultSet implements Serializable{

	private Long total;
	private List resultList;

	public Long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

}
