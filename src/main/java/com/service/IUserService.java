package com.service;


import java.util.List;

import com.entity.NCUUser;
import com.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService {
	User login(String user_name,String user_pwd);
	Integer updateUserInfo(User user);
	User findUserById(long user_id);
	List<User> findUserByName(String name);
	List<User> findUserLikeName(String name);
	Integer addUser(User user);
	Integer deleteUser(long user_id);
	List<User> findAllUserInfos();
	PageInfo<User> findAllUserBySplitPage(Integer page,Integer limit,String keyword);

	/**
	 * 根据openId查找NCUUser对象
	 * @param openId
	 * @return
	 */
	@Transactional(readOnly = true)
	List<NCUUser> countAny(String openId);

	/**
	 * 新增NCUUser
	 * @param ncuUser
	 * @return
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED)
	Integer addUser(NCUUser ncuUser);
}
