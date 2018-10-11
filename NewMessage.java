package com.eastedge.mobilesurvey.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;


/**
 * 用户新消息取得
 * 
 * @author lianghan
 * 
 */
public class NewMessage implements Comparator{

	/** 数据库id */
	private long ID;
	/** 消息id */
	private String id;
	/** 标题id */
	private String topicMsgId;
	/** 内容 */
	private String text;
	/** 时间 */
	private String time;
	/** 用户ID */
    private String userid;
	public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    /** 标签，为2，则为已读的信息；为1，则为自己发送的信息 ，其余，为null*/
	private String flag;

	
	public String getTopicMsgId() {
		return topicMsgId;
	}

	public void setTopicMsgId(String topicMsgId) {
		this.topicMsgId = topicMsgId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 获取时间差值
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getTotalTimeByInt(String startTime, String endTime) {
		SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 格式化时间
		try {
			int result = (int) ((d.parse(endTime).getTime() - d
					.parse(startTime).getTime()) / 1000);
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int compare(Object lhs, Object rhs) {
		// TODO Auto-generated method stub
		return getTotalTimeByInt(((NewMessage)lhs).time, ((NewMessage)rhs).time);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((topicMsgId == null) ? 0 : topicMsgId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewMessage other = (NewMessage) obj;
		if (topicMsgId == null) {
			if (other.topicMsgId != null)
				return false;
		} else if (!topicMsgId.equals(other.topicMsgId))
			return false;
		return true;
	}

  
	
	
}
