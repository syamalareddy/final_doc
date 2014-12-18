package com.example.gre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gre.model.Option;
import com.example.gre.model.Question;
import com.example.gre.model.Survey;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GREMockTestActivity extends Activity implements OnClickListener {
	Button createSurvey, cancelSurvey;
	TextView surveyTitle;
	Survey survey;
	ProgressDialog ringProgressDialog;
	String projectName;
	String isFor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.survey_template);
		LinearLayout questionsLayout = (LinearLayout) findViewById(R.id.questions);
		isFor = getIntent().getExtras().getString("isFor");
		survey = getSurvey();

		surveyTitle = (TextView) findViewById(R.id.surveyTitle);
		
		if(survey.getSurveyTitle().contains("GRE Mock")){
			surveyTitle.setVisibility(View.GONE);
			getActionBar().setTitle(survey.getSurveyTitle());
		}else{
			surveyTitle.setText(survey.getSurveyTitle());
			getActionBar().setTitle("Tofel Mock Test");
		}
		createSurvey = (Button) findViewById(R.id.submit);
		createSurvey.setOnClickListener(this);
		cancelSurvey = (Button) findViewById(R.id.cancel);
		cancelSurvey.setOnClickListener(this);

		for (Question question : survey.getQuestions()) {
			if (question.getQuestionType() == 0) {
				addRadioButtonQuestion(question, questionsLayout);
			} else if (question.getQuestionType() == 1) {
				addCheckboxQuestion(question, questionsLayout);
			} else if (question.getQuestionType() == 2) {
				addSpinnerQuestion(question, questionsLayout);
			} else if (question.getQuestionType() == 3) {
				addTextQuestion(question, questionsLayout);
			}
		}

	}

	private Survey getAnswerSurvey() {
		Survey answerSurvey = new Survey();

		for (int i = 0; i < survey.getQuestions().size(); i++) {
			Question que = survey.getQuestions().get(i);
			Question newQuestion = new Question();
			newQuestion.setQuestionId(que.getQuestionId());
			newQuestion.setQuestionTitle(que.getQuestionTitle());
			newQuestion.setQuestionType(que.getQuestionType());

			List<Option> option = new ArrayList<Option>();

			if (que.getQuestionType() == 0) {
				RadioGroup radioButtonGroup = (RadioGroup) findViewById(Integer
						.parseInt(que.getQuestionId()));
				int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
				View radioButton = radioButtonGroup.findViewById(radioButtonID);
				int id = radioButtonGroup.indexOfChild(radioButton);
				option.add(que.getOptions().get(id));
			} else if (que.getQuestionType() == 1) {
				for (Option op : que.getOptions()) {
					String questionId = que.getQuestionId();
					String optionId = op.getOptionId() + "";
					CheckBox checkBox = (CheckBox) findViewById(Integer
							.parseInt(questionId + optionId));
					if (checkBox.isChecked()) {
						option.add(op);
					}

				}

			} else if (que.getQuestionType() == 2) {

				Spinner spinner = (Spinner) findViewById(Integer.parseInt(que
						.getQuestionId()));

				option.add(que.getOptions().get(
						spinner.getSelectedItemPosition()));

			} else if (que.getQuestionType() == 3) {

				TextView textView = (TextView) findViewById(Integer
						.parseInt(que.getQuestionId()));
				Option a = new Option();
				a.setOptionName(textView.getText().toString());
				option.add(a);
			}
			newQuestion.setOptions(option);
			answerSurvey.addQuestion(newQuestion);
		}

		return answerSurvey;
	}

	@Override
	public void onClick(View v) {
		if (v == createSurvey) {
			Survey answerSurvey = getAnswerSurvey();
			int score = 0;
			for (int i = 0; i < answerSurvey.getQuestions().size(); i++) {

				Question answerQuestion = answerSurvey.getQuestions().get(i);
				Question questionQuestion = survey.getQuestions().get(i);
				if (questionQuestion.getAnswerOption().getOptionId() == answerQuestion
						.getOptions().get(0).getOptionId()) {
					score++;
				}
			}

			Intent scoreActivity = new Intent(this, ScoreActivity.class);
			scoreActivity.putExtra("score", score);
			startActivity(scoreActivity);

		} else if (v == cancelSurvey) {
			finish();
		}
	}

	private void addTextQuestion(Question question, LinearLayout questionsLayout) {

		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#000000"));
		textView.setText(question.getQuestionTitle());

		questionsLayout.addView(textView);
		EditText editText = new EditText(this);
		editText.setSingleLine();
		editText.setId(Integer.parseInt(question.getQuestionId()));
		editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		questionsLayout.addView(editText);
	}

	private void addCheckboxQuestion(Question question,
			LinearLayout questionsLayout) {

		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#000000"));
		textView.setText(question.getQuestionTitle());

		questionsLayout.addView(textView);
		for (Option option : question.getOptions()) {

			CheckBox button = new CheckBox(this);
			button.setText(option.getOptionName());
			String questionId = question.getQuestionId();
			String optionId = option.getOptionId() + "";
			button.setId(Integer.parseInt(questionId + optionId));
			questionsLayout.addView(button);
		}

	}

	private void addSpinnerQuestion(Question question,
			LinearLayout questionsLayout) {
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#000000"));
		textView.setText(question.getQuestionTitle());
		questionsLayout.addView(textView);
		Spinner spinner = new Spinner(this);
		spinner.setId(Integer.parseInt(question.getQuestionId()));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		for (Option option : question.getOptions()) {
			adapter.add(option.getOptionName());
		}
		spinner.setAdapter(adapter);
		questionsLayout.addView(spinner);

	}

	private void addRadioButtonQuestion(Question question,
			LinearLayout questionsLayout) {

		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#000000"));
		textView.setText(question.getQuestionTitle());
		questionsLayout.addView(textView);

		RadioGroup radioGroup = new RadioGroup(this);

		radioGroup.setId(Integer.parseInt(question.getQuestionId()));
		for (Option option : question.getOptions()) {

			RadioButton button = new RadioButton(this);
			button.setText(option.getOptionName());
			radioGroup.addView(button);
		}
		questionsLayout.addView(radioGroup);

	}

	private Survey getSurvey() {
		AssetManager assetManager = getAssets();
		try {

			InputStream is = null;
			is = assetManager.open(isFor);
			return new Gson().fromJson(convertStreamToString(is), Survey.class);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}

}
