package com.eastedge.mobilesurvey.bean;

/**
 * 用户信息 ；
 * 主页上调用 ；
 * 取得用户信息，新消息（未读消息）数，可参与的调查
 * 
 * @author lianghan
 * 
 */
public class MyMessage {

	/**用户id*/
	private int id;
	/**新信息数目*/
	private int count;
	/**问卷id*/
	private int surveyId;
	/**问卷名称*/
	private String name; 
	/**问卷描述*/
	private String description;
	/**开始时间*/
	private String startTime;
	/**结束时间*/
	private String endTime; 
	/**版本*/
	private String version; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
