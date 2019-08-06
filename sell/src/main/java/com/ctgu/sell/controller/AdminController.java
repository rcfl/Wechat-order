package com.ctgu.sell.controller;

import com.ctgu.sell.constant.CookieConstant;
import com.ctgu.sell.constant.RedisConstant;
import com.ctgu.sell.domain.SellerInfo;
import com.ctgu.sell.enums.ResultEnum;
import com.ctgu.sell.service.SellerService;
import com.ctgu.sell.utils.CookieUtil;
import com.ctgu.sell.vo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: AdminController
 * Description:
 * date: 2019/8/2 14:39
 *
 * @author crwen
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private SellerService sellerService;

	@RequestMapping("/login")
	public CommonResult login(@RequestBody SellerInfo sellerInfo, HttpServletResponse response) {

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
