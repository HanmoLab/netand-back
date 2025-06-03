package org.pro.netandback.core.auth.dao;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenDao {

	private static final String TOKEN_PREFIX = "refresh:";
	private static final String USER_TOKEN_PREFIX = "userRefresh:";

	private final StringRedisTemplate stringRedisTemplate;

	public RefreshTokenDao(StringRedisTemplate redis) {
		this.stringRedisTemplate = redis;
	}

	public void storeRefreshToken(String refreshToken, String userId, Duration ttl) {
		stringRedisTemplate.opsForValue().set(TOKEN_PREFIX + refreshToken, userId, ttl);
		stringRedisTemplate.opsForValue().set(USER_TOKEN_PREFIX + userId, refreshToken, ttl);
	}

	public Optional<String> findUserIdByRefreshToken(String refreshToken) {
		return Optional.ofNullable(stringRedisTemplate.opsForValue().get(TOKEN_PREFIX + refreshToken));
	}

	public void removeRefreshToken(String refreshToken) {
		stringRedisTemplate.delete(TOKEN_PREFIX + refreshToken);
	}

	public void rotateRefreshToken(String userId, String newToken, Duration ttl) {
		String oldToken = stringRedisTemplate.opsForValue().get(USER_TOKEN_PREFIX + userId);
		if (oldToken != null) {
			stringRedisTemplate.delete(TOKEN_PREFIX + oldToken);
		}
		stringRedisTemplate.opsForValue().set(TOKEN_PREFIX + newToken, userId, ttl);
		stringRedisTemplate.opsForValue().set(USER_TOKEN_PREFIX + userId, newToken, ttl);
	}
}
