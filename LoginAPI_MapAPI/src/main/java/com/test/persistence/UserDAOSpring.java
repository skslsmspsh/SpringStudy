package com.test.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.domain.UserVO;

@Repository
public class UserDAOSpring{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String USER_INSERT = "insert into users(id,email,username,sns_id) values (?,?,?,?)";
	private final String USER_UPDATE = "update users set passwd=?, name=?, role=? where uid=?";
	private final String USER_DELETE = "delete users where uid=? and passwd=?";
	private final String USER_GET = "select * from users where uid=?";
	private final String USER_LIST = "select * from users";
	
	public void insertUser(UserVO vo) {
		System.out.println("===> Spring JDBC로 insertUser() 기능 처리");
		jdbcTemplate.update(USER_INSERT, vo.getId(), vo.getEmail(), vo.getUsername(), vo.getSns_id());
	}
	
/*	
	public void updateUser(UserVO vo) {
		System.out.println("===> Spring JDBC로 updateUser() 기능 처리");
		jdbcTemplate.update(USER_UPDATE, vo.getPasswd(), vo.getName(), vo.getRole(), vo.getUid());
	}
	public void deleteUser(UserVO vo) {
		System.out.println("===> Spring JDBC로 deleteUser() 기능 처리");
		jdbcTemplate.update(USER_DELETE, vo.getUid(), vo.getPasswd());
	}
	
	public UserVO getUser(UserVO vo) {
		System.out.println("===> Spring JDBC로 getBoard() 기능 처리");
		Object [] args = {vo.getUid()};
		UserVO user = jdbcTemplate.queryForObject(USER_GET, args, new UserRowMapper());
		
		return user;
	}
	
	public List<UserVO> getUserList(){
		System.out.println("===> Spring JDBC로 getUserList() 기능 처리");
		List<UserVO> user = jdbcTemplate.query(USER_LIST, new UserRowMapper());
		return user;
	}
*/
}
