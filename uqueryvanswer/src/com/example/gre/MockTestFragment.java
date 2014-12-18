package com.example.gre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

import com.example.gre.model.Option;
import com.example.gre.model.Question;
import com.example.gre.model.Survey;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class MockTestFragment extends Fragment implements OnClickListener {
	Button createSurvey, cancelSurvey;
	TextView surveyTitle;
	Survey survey;
	View view;
	ProgressDialog ringProgressDialog;
	String projectName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.survey_template, null);
		LinearLayout questionsLayout = (LinearLayout) view
				.findViewById(R.id.questions);
		survey = getSurvey();

		surveyTitle = (TextView) view.findViewById(R.id.surveyTitle);
		surveyTitle.setText(survey.getSurveyTitle());
		createSurvey = (Button) view.findViewById(R.id.submit);
		createSurvey.setOnClickListener(this);
		cancelSurvey = (Button) view.findViewById(R.id.cancel);
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

		return view;
	}

	@Override
	public void onClick(View v) {
		if (v == createSurvey) {

		} else if (v == cancelSurvey) {
			getActivity().finish();
		}
	}

	private void addTextQuestion(Question question, LinearLayout questionsLayout) {

		TextView textView = new TextView(getActivity());
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#808080"));
		textView.setText(question.getQuestionTitle());

		questionsLayout.addView(textView);
		EditText editText = new EditText(getActivity());
		editText.setSingleLine();
		editText.setId(Integer.parseInt(question.getQuestionId()));
		editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		questionsLayout.addView(editText);
	}

	private void addCheckboxQuestion(Question question,
			LinearLayout questionsLayout) {

		TextView textView = new TextView(getActivity());
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#808080"));
		textView.setText(question.getQuestionTitle());

		questionsLayout.addView(textView);
		for (Option option : question.getOptions()) {

			CheckBox button = new CheckBox(getActivity());
			button.setText(option.getOptionName());
			String questionId = question.getQuestionId();
			String optionId = option.getOptionId() + "";
			button.setId(Integer.parseInt(questionId + optionId));
			questionsLayout.addView(button);
		}

	}

	private void addSpinnerQuestion(Question question,
			LinearLayout questionsLayout) {
		TextView textView = new TextView(getActivity());
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#808080"));
		textView.setText(question.getQuestionTitle());
		questionsLayout.addView(textView);
		Spinner spinner = new Spinner(getActivity());
		spinner.setId(Integer.parseInt(question.getQuestionId()));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1);
		for (Option option : question.getOptions()) {
			adapter.add(option.getOptionName());
		}
		spinner.setAdapter(adapter);
		questionsLayout.addView(spinner);

	}

	private void addRadioButtonQuestion(Question question,
			LinearLayout questionsLayout) {

		TextView textView = new TextView(getActivity());
		textView.setTextSize(20);
		textView.setTextColor(Color.parseColor("#808080"));
		textView.setText(question.getQuestionTitle());
		questionsLayout.addView(textView);

		RadioGroup radioGroup = new RadioGroup(getActivity());

		radioGroup.setId(Integer.parseInt(question.getQuestionId()));
		for (Option option : question.getOptions()) {

			RadioButton button = new RadioButton(getActivity());
			button.setText(option.getOptionName());
			radioGroup.addView(button);
		}
		questionsLayout.addView(radioGroup);

	}

	private Survey getSurvey() {
		AssetManager assetManager = getActivity().getAssets();
		try {
			
			InputStream is = null;
			String[] fileNamesList = getActivity().getResources()
					.getStringArray(R.array.template_file_names_list);

			is = assetManager.open(fileNamesList[0]);

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 111 && resultCode == 0) {
			getActivity().finish();
		}
	}

}
