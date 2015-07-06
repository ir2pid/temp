package com.noisyninja.abheda_droid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.pojo.SimpleQuestion;
import com.noisyninja.abheda_droid.pojo.SimpleQuiz;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.IDialogCallback;
import com.noisyninja.abheda_droid.util.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sudipta.a.dutta on 27/06/15.
 */
public class SimpleQuizDetailFrag extends Fragment implements IDialogCallback{
    Context context;
    Fragment fragment;
    View window;

    List<SimpleQuestion> simpleQuestions;
    int correct;
    int wrong;
    int progress;


    enum STATES{
        NORMAL,
        LAST
    }
    STATES states;
    Button next;
    TextView question;
    EditText answer;
    String correctAnswer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        fragment = this;
        states = STATES.NORMAL;
        progress = 0;
        correct = 0;
        wrong = 0;

        if (getArguments().containsKey(Constants.FRAGMENT_DATA)) {

            String data = getArguments().getString(Constants.FRAGMENT_DATA);

            SimpleQuiz simpleQuiz = (SimpleQuiz) Utils.getFromJson(data, SimpleQuiz.class);
            simpleQuestions = Arrays.asList((SimpleQuestion[]) Utils.getObject(getActivity(),
                    simpleQuiz.getSimpleQuestions(), SimpleQuestion[].class));

            Utils.handleInfo(getActivity(), simpleQuiz.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        window = inflater.inflate(R.layout.frag_simple_quiz_detail, container, false);
        question = (TextView) window.findViewById(R.id.textView1);
        answer = (EditText) window.findViewById(R.id.editText1);
        next = (Button) window.findViewById(R.id.button1);

        answer.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Utils.buttonActivate(next);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showResult(context,true,question.getText().toString(),correctAnswer,answer.getText().toString(),fragment);
            }
        });
        loadQuestions();

        return window;
    }

    void loadQuestions(){
        SimpleQuestion simpleQuestion = simpleQuestions.get(progress);
        question.setText("Q:" + (progress+1) + "/" + simpleQuestions.size() + ") " + simpleQuestion.getQuestion());
        answer.setText("");
        correctAnswer = simpleQuestion.getAnswer();
        Utils.buttonDeactivate(next);
    }

    @Override
    public void ok(DialogInterface dialog) {
        if(progress<simpleQuestions.size()-1){
            progress++;
            loadQuestions();
        }else {
            Utils.backPress(getActivity());
        }
    }

    @Override
    public void cancel(DialogInterface dialog) {

    }
}
