package com.liyong.hash.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.util.CollectionUtils;

import com.liyong.hash.util.HashUtils;

/**
 * 一致性Hash分片
 * 
 * @author created by li.yong on 2020-7-31 18:49:02
 *
 */
public class ConsistencySharding {
	/** 集群地址列表 */
	private static String[] addresses = { 
			"192.168.0.0:8080", 
			"192.168.0.1:8080", 
			"192.168.0.2:8080", 
			"192.168.0.3:8080",
			"192.168.0.4:8080",
			"192.168.0.5:8080",
			"192.168.0.6:8080",
			"192.168.0.7:8080"};

	/** 保存Hash环上的节点地址的Hash值 -- 节点地址 */
	private static SortedMap<Integer, String> serverNodes = new TreeMap<>();

	static {
		// 将服务节点进行Hash
		for (String address : addresses) {
			int hash = HashUtils.getHash(address);
			serverNodes.put(hash, address);
		}
	}

	public static String getServer(String key) {
		int hash = HashUtils.getHash(key);
		// 取出所有大于hash值的部分
		SortedMap<Integer, String> subMap = serverNodes.tailMap(hash);
		if (CollectionUtils.isEmpty(subMap)) {
			// hash值在最尾部，应该映射到第一个group上
			return serverNodes.get(serverNodes.firstKey());
		}
		return subMap.get(subMap.firstKey());
	}

	public static void main(String[] args) {
		// 生成随机数进行测试
		Map<String, Integer> resMap = new HashMap<>();
		for (int i = 0; i < 1000000; i++) {
			String server = getServer(UUID.randomUUID().toString());
			if (resMap.containsKey(server)) {
				resMap.put(server, resMap.get(server) + 1);
			} else {
				resMap.put(server, 1);
			}
		}
		
		resMap.forEach((key, value) -> {
			System.out.println("server: " + key + " count: " + value + "(" + value / 10000.0D + "%)");
		});
	}
}
