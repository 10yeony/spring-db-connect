package kr.com.inspect.dao.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.User;
import kr.com.inspect.mapper.PostgreMapper;

@Repository
public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertuser(User user) {
		int result = 0;
		LoginDao mapper = sqlSession.getMapper(LoginDao.class);

		try {
			result = mapper.insertuser(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return result;
		}

		return result;
	}

	@Override
	public User login(User user) {
		User result=null;
		
		LoginDao mapper=sqlSession.getMapper(LoginDao.class);
	
		try {
			result=mapper.login(user);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return result;
		}
		return result;
	}


}
