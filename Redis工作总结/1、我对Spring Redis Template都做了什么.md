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


