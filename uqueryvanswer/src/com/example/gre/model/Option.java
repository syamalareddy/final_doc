package com.example.gre.model;

import java.io.Serializable;

public class Option implements Serializable {

	private static final long serialVersionUID = 1L;



	private int optionId;

	private String optionName;

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	
}
