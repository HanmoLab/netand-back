package org.pro.netandback.core.auth.service.impl;

import java.util.concurrent.TimeUnit;

import org.pro.netandback.core.auth.jwt.JwtProvider;
import org.pro.netandback.core.auth.service.BlacklistService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BlacklistServiceImpl implements BlacklistService {

	private final StringRedisTemplate redis;   // 문자열 전용 템플릿
	private final JwtProvider jwtProvider;

	public BlacklistServiceImpl(StringRedisTemplate redis, JwtProvider jwtProvider) {
		this.redis = redis;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public void blacklistTokens(String email) {

		String refresh = redis.opsForValue().get("userRefresh:" + email);
		redis.delete("userRefresh:" + email);
		if (refresh != null) {
			redis.delete("refresh:" + refresh);
		}
		String accessToken = jwtProvider.resolveAccessTokenFromHeader();
		if (accessToken != null) {
			long remainSec = jwtProvider.getRemainingAccessTokenValidity(accessToken);
			redis.opsForValue().set(
				"blacklist:access:" + accessToken,
				"true",
				remainSec,
				TimeUnit.SECONDS
			);
		}
	}
}
