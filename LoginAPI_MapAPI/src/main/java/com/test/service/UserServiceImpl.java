package com.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.domain.UserVO;
import com.test.persistence.UserDAOSpring;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	
	private UserDAOSpring userDAO;
	
	@Override
	public void insertUser(UserVO vo) {
		userDAO.insertUser(vo);
	}

	@Override
	public void updateUser(UserVO vo) {
		//userDAO.updateUser(vo);
	}

	@Override
	public void deleteUser(UserVO vo) {
		//userDAO.deleteUser(vo);
	}

	@Override
	public UserVO getUser(UserVO vo) {
		// TODO Auto-generated method stub
		//return userDAO.getUser(vo);
		return null;
	}

	@Override
	public List<UserVO> getUserList() {
		// TODO Auto-generated method stub
		//return userDAO.getUserList();
		return null;
	}
	
}
