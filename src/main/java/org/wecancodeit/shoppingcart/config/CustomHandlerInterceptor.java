package org.wecancodeit.shoppingcart.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CustomHandlerInterceptor extends HandlerInterceptorAdapter {
	
	// Called before controller
	// Can be used to stop or redirect a request
	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {
		long startTime = System.currentTimeMillis();
		String requestUrl = request.getRequestURL().toString();
		System.out.println("Request URL: " + requestUrl + ":: Start Time=" + startTime);
		
		request.setAttribute("startTime", startTime);
		
		// You can return false to stop the request from further processing
		// In that case, use the response object to send response to client
		return true;
	}
	
	// Called after controller but before render
	// Can be used to add additional attributes to the model
	@Override
	public void postHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		ModelAndView modelAndView
	) {
		long startTime = System.currentTimeMillis();
		String requestUrl = request.getRequestURL().toString();
		System.out.println("Request URL: " + requestUrl + ":: Sent to Handler=" + startTime);
		
		if (modelAndView != null) {
			// It is POSSIBLE to add attributes to the model here, but if your controller method
			// returns a redirect ("redirect:" string or RedirectView object), these attributes will be appended
			// to the redirect URL as request parameters, which can be ugly, unwieldy, and unexpected
			// modelAndView.addObject("interceptorMessage", "This message was defined in CustomHandlerInterceptor.java");
		}
	}
	
	// Called after render
	// Can be used to check processing time of request
	@Override
	public void afterCompletion(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		Exception ex
	) {
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		String requestUrl = request.getRequestURL().toString();
		
		System.out.println("Request URL::" + requestUrl + ":: End Time=" + endTime);
		System.out.println("Request URL::" + requestUrl + ":: Time Taken=" + (endTime - startTime));
	}
}
