package com.ctgu.sell.controller;

import com.ctgu.sell.config.ProjectUrlConfig;
import com.ctgu.sell.constant.CookieConstant;
import com.ctgu.sell.constant.RedisConstant;
import com.ctgu.sell.domain.SellerInfo;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.service.SellerService;
import com.ctgu.sell.utils.CookieUtil;
import com.ctgu.sell.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller/user")
@Slf4j
public class SellerUserController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	private String verifyCode;

	@RequestMapping
	public String index() {
		//LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
		//lineCaptcha.write("d:/line.png");
		//String code = lineCaptcha.getCode();
		//lineCaptcha.write(response.g);
		return "index";
	}

	//@RequestMapping("/verify")
	//public void test1(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
	//	response.setContentType("image/png");//以图片形式打出
	//	LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);//设置长和宽
	//	lineCaptcha.createCode();//创建验证码，同事生产随机验证码字符串和验证码图片
	//	this.verifyCode = lineCaptcha.getCode();//获取到验证码
	//	System.out.println("验证码为："+ verifyCode);
	//	OutputStream os = response.getOutputStream();
	//	lineCaptcha.write(os);//输出
	//}


	//@RequestMapping("/login")
	//public ModelAndView login(@RequestParam("openid") String openid,
	//                          HttpServletResponse response,
	//                          Map<String, Object> map) {
	//	//1.openid去和数据库里的数据匹配
	//	SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
	//	if (sellerInfo == null) {
	//		map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
	//		map.put("url", "/sell/seller/order/list");
	//	}
	//
	//	// 2. 设置token至redis
	//	String token = UUID.randomUUID().toString();
	//	Integer expire = RedisConstant.EXPIRE;
	//
	//	redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);
	//	//3. 设置token 至 cookie
	//	CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
	//
	//
	//	return new ModelAndView("redirect:" + projectUrlConfig.getSell() +"/sell/seller/order/list");
	//}


	//@RequestMapping("/login")
	//public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password,
	//                          HttpServletResponse response,
	//                          Map<String, Object> map) {
	//	//1.openid去和数据库里的数据匹配
	//	SellerInfo sellerInfo = sellerService.findByUsernameAndPassword(username, password);
	//	if (sellerInfo == null) {
	//		map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
	//		map.put("url", "/sell/seller/user/");
	//	}
	//	//else if (!this.verifyCode.equals(verify)) {
	//	//	map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
	//	//	map.put("url", "/sell/seller/user/");
	//	//	map.put("verify", "验证码错误");
	//	//}
	//	else {
	//		map.put("url", "/sell/seller/order/list");
	//		// 2. 设置token至redis
	//		String token = UUID.randomUUID().toString();
	//		Integer expire = RedisConstant.EXPIRE;
	//
	//		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),username,expire, TimeUnit.SECONDS);
	//		//3. 设置token 至 cookie
	//		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
	//	}
	//
	//
	//
	//
	//	//return new ModelAndView("redirect:" + projectUrlConfig.getSell() + map.get("url"));
	//	return new ModelAndView("redirect:" + projectUrlConfig.getSell() + map.get("url"));
	//}

	@RequestMapping("/login")
	@ResponseBody
	public CommonResult login(@RequestBody SellerInfo sellerInfo, HttpServletResponse response) {
		System.out.println(sellerInfo);
		SellerInfo result = sellerService.findByUsernameAndPassword(sellerInfo.getUsername(), sellerInfo.getPassword());
		if (result == null) {
			return CommonResult.validateFailed();
		}
		// 2. 设置token至redis
		String token = UUID.randomUUID().toString();
		Integer expire = RedisConstant.EXPIRE;

		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),sellerInfo.getUsername(),expire, TimeUnit.SECONDS);
		//3. 设置token 至 cookie
		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
		return CommonResult.success(sellerInfo);
	}


	//@GetMapping("/logout")
	//public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	//                   Map<String, Object> map) {
	//	// 从cookie里查询
	//	Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
	//	if (cookie != null) {
	//		//清除redis
	//		redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
	//
	//		//清除cookie
	//		CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
	//
	//	}
	//
	//	map.put("msg", ResultEnum.LOGOUT);
	//	map.put("url", "/sell/seller/user/");
	//
	//	session.invalidate();
	//
	//	return new ModelAndView("common/success", map);
	//}

	@PostMapping("/logout")
	@ResponseBody
	public CommonResult logout(HttpServletRequest request, HttpServletResponse response, HttpSession session,
	                           Map<String, Object> map) {
		// 从cookie里查询
		Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
		if (cookie != null) {
			//清除redis
			redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

			//清除cookie
			CookieUtil.set(response, CookieConstant.TOKEN, null, 0);

		}

		map.put("msg", ResultEnum.LOGOUT);
		map.put("url", "/sell/seller/user/");

		session.invalidate();

		return CommonResult.success();
	}

}
