package com.lux.study.model;

public class DataStudent {
	
	private String name;
	private String group;
	private boolean isSWTDOne;
	private  int id;

	public DataStudent(String name, String group, boolean isSWTDOne,int id) {
		super();
		this.name = name;
		this.group = group;
		this.isSWTDOne = isSWTDOne;
		this.id=id;
	}

	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}

	public String getGroup() {
		return group;
	}

	public boolean isSWTDOne() {
		return isSWTDOne;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setSWTDOne(boolean isSWTDOne) {
		this.isSWTDOne = isSWTDOne;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result=+id;
		if (result <= 0)
			result *= -1;
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
		DataStudent other = (DataStudent) obj;
		if (!name.equals(other.name))
			return false;
		if (!group.equals(other.group))
			return false;
		if (isSWTDOne != other.isSWTDOne())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name + " " + group;
	}

}
