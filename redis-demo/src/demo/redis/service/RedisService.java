package demo.redis.service;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import demo.redis.common.Constant;
import demo.redis.util.PropertyUtil;

/**
 * 简单的封装
 * 
 * @author zhangfan
 *
 */
public class RedisService {

	private static final Logger logger = Logger.getLogger(RedisService.class);
	

	public static JedisPool pool;
	public static JedisPoolConfig conf;

	static {
		conf = new JedisPoolConfig();
		/* 空闲时测试,免得redis连接空闲时间长了断线 */
		conf.setTestWhileIdle(true);
		conf.setMaxTotal(100);
		pool = new JedisPool(
				conf,
				PropertyUtil.readValue(Constant.REDIS_HOST),
				Integer.parseInt(PropertyUtil.readValue(Constant.REDIS_PORT)),
				Integer.parseInt(PropertyUtil.readValue(Constant.REDIS_TIMEOUT)),
				PropertyUtil.readValue(Constant.REDIS_PASSWORD), Integer
						.parseInt(PropertyUtil
								.readValue(Constant.REDIS_DATABASE)));
	}

	/**
	 * 获取Jedis实例，类似数据库连接池中获取一个连接
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		Jedis jedis = pool.getResource();
		try {
			if (jedis != null) 
				return jedis;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("deprecation")
	public static void close(Jedis jedis){
		pool.returnResource(jedis);
	}
	
	public void set(String key, String val) {
		Jedis jedis = getJedis();
		logger.info("-> save : " + jedis.set(key, val));
		close(jedis);
	}

	public String get(String key) {
		Jedis jedis = getJedis();
		close(jedis);
		return jedis.get(key);
	}
	
	/**
	 * 设置失效时间
	 * @param key
	 * @param seconds
	 * @return 0 | 1
	 */
	public Long expire(String key, int seconds) {
		Jedis jedis = getJedis();
		long tf = jedis.expire(key, seconds);
		close(jedis);
		return tf;
	}
	
	
	
	
}
