package com.yong.mybatis.basic;

import com.yong.mybatis.basic.dao.IUserInfoDao;
import com.yong.mybatis.basic.entity.UserInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class QuickStart {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws IOException {
		String resource = "mybatis-config.xml";
		try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
	}

	@Test
	public void quickStart() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			IUserInfoDao userInfoDao = sqlSession.getMapper(IUserInfoDao.class);
			Optional<UserInfo> userInfo = userInfoDao.selectUserInfo(10L);
			System.out.println(userInfo.isPresent());
		}
	}

	@Test
	public void testInsert() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			IUserInfoDao userInfoDao = sqlSession.getMapper(IUserInfoDao.class);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(11L);
			userInfo.setUsername("u1");
			userInfo.setPassword("p1");
			userInfo.setAge(18);
			int count = userInfoDao.insert(userInfo);
			System.out.println("insert success count: " + count);
			sqlSession.commit();
		}
	}
}
