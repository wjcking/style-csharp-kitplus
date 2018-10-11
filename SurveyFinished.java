package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;

/**
 * 已经参与过的调查
 * @author lianghan
 *
 */
public class SurveyFinished implements Serializable {

	/**调查id*/
	public String surveyid;
	/**调查名字*/
	public String name;
	/**调查结束时间*/
	public String endtime;
}
