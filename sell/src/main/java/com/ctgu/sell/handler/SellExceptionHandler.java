package com.ctgu.sell.handler;

import com.ctgu.sell.config.ProjectUrlConfig;
import com.ctgu.sell.exception.SellException;
import com.ctgu.sell.exception.SellerAuthorizeException;
import com.ctgu.sell.utils.ResultVoUtil;
import com.ctgu.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	//拦截登陆异常
	@ExceptionHandler(value = SellerAuthorizeException.class)
	public ModelAndView handlerAuthorizeException() {
		return new ModelAndView("redirect:"
		//.concat(projectUrlConfig.getWechatMpAuthorize())
		//.concat("/sell/wechat/qrAuthorize").
		//concat("?returnUrl=")
		.concat(projectUrlConfig.getSell())
		//.concat("/sell/seller/login"));
		.concat("/sell/seller/user/"));
	}

	@ExceptionHandler(value = SellException.class)
	@ResponseBody
	public ResultVo handerSellerException( SellException e) {
		return ResultVoUtil.error(e.getCode(), e.getMessage());
	}

}
