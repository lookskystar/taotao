package com.taotao.sso.dao;

//根据redis命名定义相关方法名
public interface JedisClient {
	String get(String key);
	String set(String key, String value);
	String hget(String hkey, String key);
	long hset(String hkey, String key, String value);
	long incr(String key);
	long expire(String key, int second); //设置key的有效时间
	long ttl(String key);
	long del(String key);
	long hdel(String hkey, String key);
}
