package com.ctgu.sell.utils;

import com.ctgu.sell.vo.ResultVo;

public class ResultVoUtil {

	public static ResultVo success(Object object) {
		ResultVo resultVo = new ResultVo();
		resultVo.setData(object);
		resultVo.setCode(200);
		resultVo.setMsg("成功");
		return resultVo;
	}

	public static ResultVo success(){
		return null;
	}

	public static ResultVo error(Integer code, String msg) {
		ResultVo resultVo = new ResultVo();
		resultVo.setCode(code);
		resultVo.setMsg(msg);
		return resultVo;
	}

}
