package demo.redis;

import org.apache.log4j.Logger;

import demo.redis.service.RedisService;

/**
 * 大概的一个步骤：<br>
 * 
 * 	1）win+r进入cmd <br>
 * 	2）d: //解压到哪里去哪里<br>
 *  3）cd redis<br>
 *  4）redis-server.exe  redis.windows.conf<br>
 *  
 *  redis就启动了，能看到一个类似正方体绘图就成功了<br>
 *	看不到的话把4换成下面的 <br>
 *  redis-server.exe redis.windows.conf --maxheap 200m
 * 
 * @author zhangfan
 *
 */
public class Run {

	private static final Logger logger = Logger.getLogger(Run.class);
	
	public static void main(String[] args) {
		RedisService redisService = new RedisService();
		String key = "zhangfan.cn";
		String val = "taoxue";
		redisService.set(key, val);
		logger.info("{ zhangfan.cn : " + redisService.get(key) + " }");
		redisService.expire(key, 60);
	}
}
