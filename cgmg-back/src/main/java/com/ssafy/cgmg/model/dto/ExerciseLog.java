package com.ssafy.cgmg.model.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "운동기록 DTO")
public class ExerciseLog {

	private int id; // 운동 기록 번호
	private String regDate; // 운동 날짜
	private String exerciseName; // 운동 이름
	private String bodyPart; // 운동 부위
	private int cnt; // 운동 세트수
	private String userId; // 운동한 회원의 id
	private String nickName; // 닉네임
	private String profileImg; // 프로필 이미지

	public ExerciseLog() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public String getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	@Override
	public String toString() {
		return "ExerciseLog [id=" + id + ", regDate=" + regDate + ", exerciseName=" + exerciseName + ", bodyPart="
				+ bodyPart + ", cnt=" + cnt + ", userId=" + userId + ", nickName=" + nickName + ", profileImg="
				+ profileImg + "]";
	}

}
