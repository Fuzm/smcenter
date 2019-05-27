/******************************************************************
 *
 *
 *    Filename:    ResponseData.java
 *	  Project:	         广东省农信个人网络消费贷项目
 *    Description: TODO(用一句话描述该文件做什么)
 *    @author:     zhangyue
 *	  Mail:		   zhangyue2@yusys.com.cn
 *    @version:    1.0.0
 *    Create at:   2018年4月23日 下午3:16:49
 *    Revision:
 *    2018年4月23日 下午3:16:49
 *        - first revision
 *
 *****************************************************************/
package com.irdstudio.ssm.framework.constant;

/**
 * @ClassName ResponseData
 * @Description 可以作为消息返回使用
 * @author zhangyue
 * @Date 2018年4月23日 下午3:16:49
 * @version 1.0.0
 * @param <T>
 */
public class ResponseData<T> {
	
	private T rows;
	
	private boolean isSuccess;
	
	private String code;
	
	private String message;
	
	private int total;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ResponseData<T> success(T data) {
		return createData(data,"999999","查询成功",false);
	}
	
	public ResponseData<T> fail(T data){
		return createData(data,"000000","查询失败",false);
	}
	
	public ResponseData<T> createData(T data, String code, String message, boolean isSuccess) {
		this.rows = data;
		this.code = code;
		this.message = message;
		this.isSuccess = isSuccess;
		return this;
	}
	
	public T getRows() {
		return rows;
	}

	public void setRows(T data) {
		this.rows = data;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
