package com.ctgu.sell.controller;

import com.ctgu.sell.config.ProjectUrlConfig;
import com.ctgu.sell.constant.CookieConstant;
import com.ctgu.sell.constant.RedisConstant;
import com.ctgu.sell.domain.SellerInfo;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.service.SellerService;
import com.ctgu.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping
	public String index() {
		return "index";
	}

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


	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("username") String username,@RequestParam("password") String password,
	                          HttpServletResponse response,
	                          Map<String, Object> map) {
		//1.openid去和数据库里的数据匹配
		SellerInfo sellerInfo = sellerService.findByUsernameAndPassword(username, password);
		if (sellerInfo == null) {
			map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
			map.put("url", "/sell/seller/user/");
		} else {
			map.put("url", "/sell/seller/order/list");
			// 2. 设置token至redis
			String token = UUID.randomUUID().toString();
			Integer expire = RedisConstant.EXPIRE;

			redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),username,expire, TimeUnit.SECONDS);
			//3. 设置token 至 cookie
			CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
		}




		return new ModelAndView("redirect:" + projectUrlConfig.getSell() + map.get("url"));
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session,
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
		map.put("url", "/sell/seller/order/list");

		session.invalidate();

		return new ModelAndView("common/success", map);
	}

}
