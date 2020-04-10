package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	/*// 单机连接
	@Test
	public void testJedisSingle() {
		// 创建一个jedis对象
		Jedis jedis = new Jedis("192.168.137.201", 6379);
		// 调用jedis对象方法名称和redis一样
		jedis.set("key1", "jedis test");

		String str = jedis.get("key1");
		System.out.println("str=" + str);
		// 关闭jedis
		jedis.close();
	}

	// 连接池连接
	@Test
	public void testJedisPool() {
		// 创建一个jedis连接池对象
		JedisPool pool = new JedisPool("192.168.137.201", 6379);
		// 从连接池中获取连接对象
		Jedis jedis = pool.getResource();
		String str = jedis.get("key1");
		System.out.println("str=" + str);
		// 关闭jedis
		jedis.close();
		// 关闭连接池
		pool.close();
	}

	// 连接集群
	@Test
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.137.11",6379));
		nodes.add(new HostAndPort("192.168.137.12",6379));
		nodes.add(new HostAndPort("192.168.137.13",6379));
		nodes.add(new HostAndPort("192.168.137.14",6379));
		nodes.add(new HostAndPort("192.168.137.15",6379));
		nodes.add(new HostAndPort("192.168.137.16",6379));
		
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("keyandy1","你好");
		String str=cluster.get("keyandy1");
		System.out.println("str="+str);
	}
	*/
	//测试spring集成redis单机版
	@Test
	public void testSpringJedisSingle() {
		//打开applicationContext-redis.xml单机版配置，注释集群版
		ApplicationContext applicationContext = 
		new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext applicationContext =
		new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster =  
		(JedisCluster) applicationContext.getBean("redisClient");
		String string = jedisCluster.get("keyandy1");
		System.out.println(string);
		jedisCluster.close();
	}
}
