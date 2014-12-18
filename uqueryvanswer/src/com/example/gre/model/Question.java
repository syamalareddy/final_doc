package com.example.gre.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

	private static final long serialVersionUID = 1L;

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	private Survey survey;

	private String questionId;
	
	private String answerId;

	private String questionTitle;

	private int questionType;

	private List<Option> options;
	
	private Option answerOption;

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public String getQuestionId() {
		return questionId;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Option getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(Option answerOption) {
		this.answerOption = answerOption;
	}
	
	
	
	

}
