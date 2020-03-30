# 我对Redis都做了什么
项目中都对Redis做了什么?

## 1、Redis的Key统一前缀
背景：
1.1 因为多个项目公用一套Redis,以及多团队同时使用一套Redis的情况，就会导致项目之间、不同开发人员之间的Key存在冲突，所以我们需要对Redis的key添加前缀。
1.2 项目迭代时，发生重大业务调整，导致需要发布时让整个项目的缓存失效。

技术： springboot、 spring data redis

实现效果：
1、 写成统一的工具包，供各个项目使用
2、 自动配置
3、 各个项目通过修改各自配置来完成前缀修改

为了添加前缀，所以我们自定义为RedisTemplate自定义了两个Serializer，只需要在Redis的Spring配置中使用者两个Redis就行了。
```java
package com.liyong.commons.redis.cache.serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import com.liyong.commons.redis.cache.config.RedisKeyPrefixProperties;

/**
 * @desc 对字符串序列化新增前缀
 * @author create by liyong on 2018-11-20 18:09:51
 */
public class PrefixStringKeySerializer extends StringRedisSerializer {
	private Charset charset = StandardCharsets.UTF_8;
	private RedisKeyPrefixProperties prefix;
	
	public PrefixStringKeySerializer(RedisKeyPrefixProperties prefix) {
		super();
		this.prefix = prefix;
	}

	@Override
	public String deserialize(@Nullable byte[] bytes) {
		if(bytes == null) {
			return null;
		}
		String saveKey = new String(bytes, charset);
		if (prefix.getEnable() != null && prefix.getEnable()) {
			String prefixKey = spliceKey(prefix.getKey());
			int indexOf = saveKey.indexOf(prefixKey);
			if (indexOf > 0) {
				saveKey = saveKey.substring(indexOf);
			}
		}
		return (saveKey.getBytes() == null ? null : saveKey);
	}

	@Override
	public byte[] serialize(@Nullable String key) {
		if (prefix.getEnable() != null && prefix.getEnable()) {
			key = spliceKey(prefix.getKey()) + key;
		}
		return (key == null ? null : key.getBytes(charset));
	}
	
	private String spliceKey(String prefixKey) {
		if (StringUtils.isNotBlank(prefixKey) && !prefixKey.endsWith(":")) {
			prefixKey = prefixKey + "::";
		}
		return prefixKey;
	}
}
```

```java
package com.liyong.commons.redis.cache.serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.liyong.commons.redis.cache.config.RedisKeyPrefixProperties;

/**
 * @desc 在Jdk序列化的基础上添加统一前缀
 * @author created by liyong on 2018年4月18日 上午10:29:11
 */
public class PrefixObjectKeySerializer extends JdkSerializationRedisSerializer {
	private Charset charset = StandardCharsets.UTF_8;
	private final RedisKeyPrefixProperties prefixProperties;

	public PrefixObjectKeySerializer(RedisKeyPrefixProperties prefix) {
		super();
		this.prefixProperties = prefix;
	}

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		byte[] rawKey = super.serialize(object);
		byte[] keys = null;
		if (prefixProperties.getEnable() != null && prefixProperties.getEnable()) {
			byte[] prefix = prefixProperties.getKey().getBytes(charset);
			keys = Arrays.copyOf(prefix, prefix.length + rawKey.length);
			System.arraycopy(rawKey, 0, keys, prefix.length, rawKey.length);
		} else {
			keys = rawKey;
		}
		return keys;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		byte[] keys = null;
		if (prefixProperties.getEnable() != null && prefixProperties.getEnable()) {
			byte[] prefix = prefixProperties.getKey().getBytes(charset);
			keys = Arrays.copyOfRange(bytes, prefix.length - 1, bytes.length);
		} else {
			keys = bytes;
		}
		return super.deserialize(keys);
	}
}
```

```java
package com.liyong.commons.redis.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis.prefix")
public class RedisKeyPrefixProperties {
	private Boolean enable = Boolean.FALSE;
	private String key;
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
```

所以只需要在application.yml中配置上前缀即可
```yaml
spring:
  application.name: xxx-api
  redis:
    prefix:
      enable: true
      key: xxx-api
    database: 0
    url: redis://@192.168.100.81:6379
    timeout: 10000
    lettuce:
      pool: 
        max-active: 200
        max-wait: 1000
        max-idle: 30
        min-idle: 30
```

只是新增了如下的配置，这样在Springboot装配时，就会将配置读取到RedisKeyPrefixProperties的实例中。
```yaml
spring:
  redis:
    prefix:
      enable: true
      key: xxx-api
```

spring config配置类的编写，在redis的相关配置时，主要就是针对Key、Value的Serializer配置。


### 使用Redis，基于Lua实现站内信数据存储以及接口提供

send_message.lua 



```lua
-- 1、用户消息列表KEY
local user_msg_list_key=KEYS[1];
-- 2、用户指定类型的消息-未读计数KEY
local user_msg_unread_count_key=KEYS[2];
-- 2、用户所有消息-未读计数KEY
local user_msg_unread_total_count_key=KEYS[3];

-- 消息对象Json
local msg_info=ARGV[1];
-- 消息的主键ID
local msg_id=ARGV[2];

-- 检查消息是否已经存在 ZCOUNT key min max
local count = redis.call("zcount", user_msg_list_key, msg_id, msg_id);
-- count不等于0，则说明已经存在该消息，直接返回false，代表失败
if count ~= 0 then
	return false;
end

-- 放入消息到zset中 ZADD key score member [[score member] [score member] ...]
redis.call("zadd", user_msg_list_key, msg_id, msg_info);
-- 单个类型的消息计数器 +1
redis.call("incr", user_msg_unread_count_key);
-- 用户所有未读消息计数器 +1
redis.call("incr", user_msg_unread_total_count_key);

-- 有效期设置30天
redis.call("expire", user_msg_list_key, 2592000);
redis.call("expire", user_msg_unread_count_key, 2592000);
redis.call("expire", user_msg_unread_total_count_key, 2592000);

return true;
```

### 使用Redis，基于Lua来做Oauth2的授权信息存储


### 使用Redis，基于Lua做数据统计


### 使用Redis，提供数据刷新服务基础实现
