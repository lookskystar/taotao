package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.taotao.common.uitl.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserServiceImpl userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//在Handler执行之前处理，返回值决定Handler是否执行，true执行，false不执行；
		//判断用户是否登入，1从cookie中取token；
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//2根据token换取用户信息，需要调用sso系统接口；
		TbUser user = userService.getUserByToken(token);
		//如果取不到用户信息
		if (null == user) {
			//需要跳转到登陆页面，把用户请求url作为参数传递给登入页面;
			response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN 
					+ "?redirect=" + request.getRequestURL());
			//返回false
			return false;
		}
		//取到用户信息，放行
		//把用户信息放入request
		request.setAttribute("user", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//在Handler执行之后，返回ModelAndView之前

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//返回ModelAndView之后执行

	}

}
