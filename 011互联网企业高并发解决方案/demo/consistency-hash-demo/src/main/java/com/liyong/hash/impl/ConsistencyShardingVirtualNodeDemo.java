package com.liyong.hash.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import com.liyong.hash.util.HashUtils;

public class ConsistencyShardingVirtualNodeDemo {
	/** 集群地址列表 */
	private static String[] groups = { 
			"192.168.0.0:8080", 
			"192.168.0.1:8080", 
			"192.168.0.2:8080", 
			"192.168.0.3:8080",
			"192.168.0.4:8080",
			"192.168.0.5:8080",
			"192.168.0.6:8080",
			"192.168.0.7:8080"};

	/** 虚拟节点映射关系 */
	private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();

	private static final int VIRTUAL_NODE_NUM = 1000;
	
	static {
		// 将虚拟节点映射到Hash环上
		for (int i = 0; i < groups.length; i++) {
			String group = groups[i];
			for (int j = 0; j < VIRTUAL_NODE_NUM; j++) {
				String virtualNodeName = getVirtualNodeName(group, j);
				int hash = HashUtils.getHash(virtualNodeName);
				virtualNodes.put(hash, virtualNodeName);
			}
		}
	}
	
	private static String getVirtualNodeName(String realName, int num) {
        return realName + "&VN-" + String.valueOf(num);
    }
	
	private static String getRealNodeName(String virtualName) {
        return virtualName.split("&")[0];
    }
	
	private static String getServer(String widgetKey) {
		int hash = HashUtils.getHash(widgetKey);
		// 只取出所有大于该hash值的部分而不必遍历整个Tree
		SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
		String virtualNodeName;
		if (subMap == null || subMap.isEmpty()) {
			// hash值在最尾部，应该映射到第一个group上
			virtualNodeName = virtualNodes.get(virtualNodes.firstKey());
		} else {
			virtualNodeName = subMap.get(subMap.firstKey());
		}
		return getRealNodeName(virtualNodeName);
	}
	
	private static void randomTest() {
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
	
	public static void main(String[] args) {
		randomTest();
	}
}
