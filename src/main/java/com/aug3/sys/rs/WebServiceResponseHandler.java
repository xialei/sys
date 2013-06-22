
/**
 * @Title: WebServiceResponseHandler.java
 * @Package com.aug3.sys.rs
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:数据库财务(上海)公司 url: www.chinascopefinancial.com
 * 
 * @author: jacky.chen --IT
 * @date: 2012-6-25 下午03:49:43
 * @version: V1.0
 */
package com.aug3.sys.rs;

import com.aug3.sys.rs.response.RespObj;
import com.aug3.sys.rs.response.RespType;
import com.aug3.sys.util.GsonUtil;

 /**
 * @ClassName: WebServiceResponseHandler
 * @Description: TODO
 * @author: jacky.chen --IT
 * @date 2012-6-25 下午03:49:43
 *
 */
public abstract class WebServiceResponseHandler {
	
	/**
	 * 当操作成功并且不需要返回任何数据时使用，例如数据增加情况
	 */
	public static final RespObj SUCCESS =new RespObj();
	
	
	static
	{
		SUCCESS.setCode(RespType.SUCCESS.getCode());
		SUCCESS.setType(RespType.SUCCESS.name());
	}
	
	
	/**
	 * 
	  * @Title: buidResponseResult
	  * @Description: 
	  * @param message
	  * @param responseTyp
	  * @return
	 */
	protected String buidResponseResult(Object message,RespType responseType){
		RespObj returnDTO = new RespObj();
		returnDTO.setCode(responseType.getCode());
		returnDTO.setType(responseType.toString());
		if(message == null){
			returnDTO.setMessage(responseType);
		}else{
			returnDTO.setMessage(message);
		}
		return GsonUtil.toJson(returnDTO);
	}

}

