package com.ssafy.banana.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.banana.api.service.AuthService;
import com.ssafy.banana.dto.request.LoginRequest;
import com.ssafy.banana.dto.request.VerifyRequest;
import com.ssafy.banana.dto.response.LoginResponse;
import com.ssafy.banana.security.jwt.JwtFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@Api(tags = "인증관련 API")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final AuthService authService;

	@PostMapping("/login")
	@ApiOperation(value = "일반 로그인")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "email", value = "로그인할 이메일", required = true),
		@ApiImplicitParam(name = "password", value = "비밀번호", required = true)
	})
	public ResponseEntity<LoginResponse> authorize(@Valid @RequestBody LoginRequest loginRequest) {

		LoginResponse loginResponse = authService.login(loginRequest);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());

		return ResponseEntity.ok().headers(httpHeaders).body(loginResponse);
	}

	@PostMapping("/verify")
	@ApiOperation(value = "이메일 인증 코드 검증")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "email", value = "로그인할 이메일", required = true),
		@ApiImplicitParam(name = "code", value = "인증코드", required = true)
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "이메일 인증 성공"),
		@ApiResponse(code = 401, message = "저장된 인증코드와 일치하지 않음", response = Exception.class),
		@ApiResponse(code = 404, message = "저장된 인증코드가 없음 = 만료", response = Exception.class)
	})
	public ResponseEntity getVerify(@RequestBody VerifyRequest verifyRequest) {
		authService.verifyEmail(verifyRequest);
		return ResponseEntity.ok().body("인증되었습니다.");
	}
}