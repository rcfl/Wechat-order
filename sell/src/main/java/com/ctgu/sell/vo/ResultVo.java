package com.ctgu.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * http 请求返回的最外层对象
 */
@Data
public class ResultVo<T> implements Serializable {

	private static final long serialVersionUID = -1379213877275300766L;
	/**  错误码 */
	private Integer code;

	/**  提示信息 */
	private String msg;

	/**  具体内容 */
	private T data;

}
