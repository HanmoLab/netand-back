package org.pro.netandback.core.auth.dao;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmailCodeDao {

	private static final String CODE_PREFIX     = "auth:code:";
	private static final String VERIFIED_PREFIX = "auth:verified:";
	private final RedisTemplate<String,String> redis;

	public void saveCode(String code, String email, long ttlSeconds) {
		redis.opsForValue().set(CODE_PREFIX + code, email, ttlSeconds, TimeUnit.SECONDS);
	}

	public String getEmail(String code) {
		return redis.opsForValue().get(CODE_PREFIX + code);
	}

	public void deleteCode(String code) {
		redis.delete(CODE_PREFIX + code);
	}

	public void markVerified(String email, long ttlSeconds) {
		redis.opsForValue().set(VERIFIED_PREFIX + email, "true", ttlSeconds, TimeUnit.SECONDS);
	}

	public boolean isVerified(String email) {
		return Boolean.TRUE.equals(redis.hasKey(VERIFIED_PREFIX + email));
	}

	public void clearVerified(String email) {
		redis.delete(VERIFIED_PREFIX + email);
	}
}
