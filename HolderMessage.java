package com.eastedge.mobilesurvey.bean;

import java.util.List;

/***
 * 调查问卷所有信息，包括问卷，家庭成员，已经参与过的调查
 * @author lianghan
 *
 */
public class HolderMessage {
	/** 用户id */
	public String holerId;
	/** 新信息数目 */
	public String count;
	/**调查问卷信息*/
	public List<Survey> surveys;
	/**家庭成员信息*/
	public List<Family> families;
	/**已经参与过的调查*/
	public List<SurveyFinished> surveyFinisheds ;
}
