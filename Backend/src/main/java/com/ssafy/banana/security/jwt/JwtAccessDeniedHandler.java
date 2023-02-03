package com.ssafy.banana.security.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ssafy.banana.exception.CustomException;
import com.ssafy.banana.exception.CustomExceptionType;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) {
		//필요한 권한이 없이 접근하려 할때 403
		// response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 기능을 요청할 권한이 없습니다.");
		throw new CustomException(CustomExceptionType.AUTHORITY_ERROR);
	}
}