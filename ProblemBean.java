package com.eastedge.mobilesurvey.bean;

import java.io.Serializable;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;


public class ProblemBean implements Parcelable{

	private int id;

	private int number;

	private int Total;

	private String name;

	private String type;

	private String picurl;
	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public int getTotal() {
		return Total;
	}

	public String getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setTotal(int total) {
		Total = total;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(number);
		dest.writeInt(Total);
		dest.writeString(name);
		dest.writeString(type);
		dest.writeString(picurl);
	}
	
	public static final Parcelable.Creator<ProblemBean> CREATOR = new Creator<ProblemBean>() {

		@Override
		public ProblemBean createFromParcel(Parcel source) {
			ProblemBean temp = new ProblemBean();
			temp.id = source.readInt();
			temp.number = source.readInt();
			temp.Total = source.readInt();
			temp.name = source.readString();
			temp.type = source.readString();
			temp.picurl = source.readString();

			
			return temp;
		}

		@Override
		public ProblemBean[] newArray(int size) {

			return new ProblemBean[size];
		}

	};
}
