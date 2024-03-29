package com.ssafy.cgmg.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cgmg.model.dao.BoardDao;
import com.ssafy.cgmg.model.dao.CommentDao;
import com.ssafy.cgmg.model.dao.ExerciseLogDao;
import com.ssafy.cgmg.model.dao.NotifyDao;
import com.ssafy.cgmg.model.dao.UserDao;
import com.ssafy.cgmg.model.dto.ContinuedStreak;
import com.ssafy.cgmg.model.dto.ExerciseLog;
import com.ssafy.cgmg.model.dto.FollowLog;
import com.ssafy.cgmg.model.dto.Notify;
import com.ssafy.cgmg.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	BoardDao boardDao;
	
	@Autowired
	ExerciseLogDao exerciseLogDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	NotifyDao notifyDao;
	
	@Override
	public int signup(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User login(User user) {
		User tmp = userDao.selectOne(user.getUserId());
		if(tmp != null && tmp.getPassword().equals(user.getPassword())) {
			
			// 이 회원이 어제, 오늘 운동한 기록 가져오기
			String userId = tmp.getUserId();
			List<ExerciseLog> yesterday = exerciseLogDao.selectCntYesterday(userId);
			List<ExerciseLog> today = exerciseLogDao.selectCntToday(userId);
			
			// 어제, 오늘 운동한 기록이 없으면 
			if (yesterday.size() == 0 && today.size() == 0) {
				// 연속 스트릭 reset
				ContinuedStreak continuedStreak = new ContinuedStreak(userId, "reset");
				exerciseLogDao.updateContinuedStreak(continuedStreak);
			}
			
			// 회원 정보 반환
			return tmp;
		}
		return null;
	}

	@Override
	public User getUser(String id) {
		return userDao.selectOne(id);
	}
	
	@Override
	public List<User> getUserList() {
		return userDao.selectAll();
	}

	@Override
	public int modifyUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int removeUser(String userId) {
		
		userDao.deleteAllFollowLog(userId); // 팔로우한 기록 모두 삭제
		boardDao.deleteAllLikeLog(userId); // 좋아요한 기록 모두 삭제
		boardDao.deleteAllBoard(userId); // 내가 작성한 게시글 모두 삭제
		commentDao.deleteAllComment(userId); // 내가 작성한 코멘트 삭제
		exerciseLogDao.deleteAllExerciseLog(userId); // 내 운동 기록 모두 삭제
		notifyDao.deleteAllNotify(userId); // 내 알림 기록 모두 삭제
		
		return userDao.deleteUser(userId);
	}
	
	@Override
	public User getProfile(String userId) {
		return userDao.selectProfile(userId);
	}

	@Override
	public List<User> searchByUserId(String word) {
		return userDao.searchByUserId(word);
	}

	@Override
	public List<FollowLog> getFollowingList(String userId) {
		return userDao.selectFollowingList(userId);
	}

	@Override
	public List<FollowLog> getFollowerList(String userId) {
		return userDao.selectFollowerList(userId);
	}

	@Override
	public int writeFollowId(FollowLog followLog) {
		Notify notify = new Notify();
		
		User user1 = userDao.selectOne(followLog.getUserId());
		User user2 = userDao.selectOne(followLog.getFollowingId());
		String message = user1.getNickName() + "님이 " + user2.getNickName() + "님을 <br>팔로우했습니다.";
		
		notify.setUserId(followLog.getFollowingId());
		notify.setCausedBy(followLog.getUserId());
		notify.setMessage(message);
		notify.setNotType("follow");
		notifyDao.insertNotify(notify);
		
		return userDao.insertFollowId(followLog);
	}

	@Override
	public int removeFollowId(FollowLog followLog) {
		return userDao.deleteFollowId(followLog);
	}

}
