package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;

/***
 * 调查问卷
 * @author lianghan
 *
 */
public class Survey implements Serializable{
	
	/** 调查id */
	public String surveyid;
	/** 调查名称 */
	public String name;
	/** 调查描述 */
	public String description;
	/** 开始时间 */
	public String starttime;
	/** 结束时间 */
	public String endtime;
	/** 调查背景图片 */
	public String background;
	/** 调查版本 */
	public String version;


}
