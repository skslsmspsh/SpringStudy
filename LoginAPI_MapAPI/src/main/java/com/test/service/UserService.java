package com.test.service;

import java.util.List;

import com.test.domain.UserVO;

public interface UserService {
	public void insertUser(UserVO vo);
	public void updateUser(UserVO vo);
	public void deleteUser(UserVO vo);
	public UserVO getUser(UserVO vo);
	public List<UserVO> getUserList();
	
}
