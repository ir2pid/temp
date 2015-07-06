package com.noisyninja.abheda_droid.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.pojo.MCQQuestion;
import com.noisyninja.abheda_droid.pojo.MCQQuiz;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.IDialogCallback;
import com.noisyninja.abheda_droid.util.Utils;

import java.util.Arrays;
import java.util.List;

import at.markushi.ui.CircleButton;

/**
 * Created by ir2pi on 11/30/2014.
 */
public class MCQDetailFrag extends Fragment implements IDialogCallback{

    View window;
    ScrollView scrollView;
    List<MCQQuestion> mcqQuestions;
    int progress;
    int correct;
    int wrong;
    boolean isWrong;
    boolean isPicture;
    int selectedId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = -1;
        correct = 0;
        wrong = 0;
        isWrong = false;
        if (getArguments().containsKey(Constants.FRAGMENT_DATA)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            /*mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
                    ARG_ITEM_ID));*/
            String data = getArguments().getString(Constants.FRAGMENT_DATA);


            MCQQuiz mcqQuiz = (MCQQuiz)Utils.getFromJson(data, MCQQuiz.class);
            try {
                mcqQuestions = Arrays.asList((MCQQuestion[]) Utils.getObject(getActivity(),
                        mcqQuiz.getMcqQuestions(), MCQQuestion[].class));
            }catch(Exception e){
                Utils.handleError(getActivity(),e);
            }

            Utils.handleInfo(getActivity(), mcqQuiz.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        window = inflater.inflate(R.layout.frag_mcq_detail, container, false);
        scrollView = (ScrollView)window.findViewById(R.id.scrollView);

        Button buttonNext = ((Button) window.findViewById(R.id.button));
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(selectedId == -1)
                {
                    Utils.playSound(getActivity(), Constants.Sound.WRONG);
                    return;
                }*/

                LinearLayout root1 = (LinearLayout) window.findViewById(R.id.root1);
                LinearLayout root2 = (LinearLayout) window.findViewById(R.id.root2);
                RadioGroup radioGroup = ((RadioGroup) window.findViewById(R.id.radioGroup));
                selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) window.findViewById(selectedId);
                //Utils.makeToast(getActivity(), String.valueOf(idx));

                if (isWrong) {
                    isWrong = false;
                    radioGroup.clearCheck();
                    loadQuestions(progress);
                } else if (radioButton != null && Integer.valueOf(radioButton.getTag().toString()) == mcqQuestions.get(progress).getCorrect()) {

                    correct++;
                    radioGroup.clearCheck();
                    CircleButton circleButton = (CircleButton) window.findViewById(R.id.correct_button);
                    Utils.animateFlip(root1, circleButton, circleButton);

                    TextView textViewCorrect = ((TextView) window.findViewById(R.id.correctno));
                    textViewCorrect.setText(""+correct);

                    Utils.showResult(getActivity(), true, Utils.getTempString(Constants.QUESTION, mcqQuestions.get(progress).getQuestion()), Utils.getTempString(Constants.CORRECT, radioButton.getText().toString()), null);
                    loadQuestions(progress);
                } else if (radioButton != null && selectedId != -1) {
                    isWrong = true;
                    wrong++;
                    CircleButton circleButton = (CircleButton) window.findViewById(R.id.wrong_button);
                    Utils.animateFlip(root2, circleButton, circleButton);

                    TextView textViewWrong = ((TextView) window.findViewById(R.id.wrongno));
                    textViewWrong.setText("" + wrong);

                    Button submit = (Button) window.findViewById(R.id.button);
                    submit.setText(Utils.getStringResource(getActivity(), R.string.next));

                    radioButton.setTextColor(Utils.getColorResource(getActivity(), R.color.button_red));
                    Utils.makeAnimation(radioButton, Techniques.Shake);
                    String correctAnswer = "";
                    switch (mcqQuestions.get(progress).getCorrect()) {
                        case 1: {
                            correctAnswer = mcqQuestions.get(progress).getOption1();
                            break;
                        }
                        case 2: {
                            correctAnswer = mcqQuestions.get(progress).getOption2();
                            break;
                        }
                        case 3: {
                            correctAnswer = mcqQuestions.get(progress).getOption3();
                            break;
                        }
                        case 4: {
                            correctAnswer = mcqQuestions.get(progress).getOption4();
                            break;
                        }
                        default:
                            correctAnswer = Constants.ERROR_INVALID_JSON;
                    }

                    Utils.showResult(getActivity(), false, Utils.getTempString(Constants.QUESTION, mcqQuestions.get(progress).getQuestion()), Utils.getTempString(Constants.CORRECT, correctAnswer), Utils.getTempString(Constants.WRONG, radioButton.getText().toString()));

                    int correct = mcqQuestions.get(progress).getCorrect();
                    int count = radioGroup.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View o = radioGroup.getChildAt(i);

                        if (o instanceof RadioButton) {
                            RadioButton r = (RadioButton) o;
                            r.setEnabled(false);
                            if (Integer.valueOf(o.getTag().toString()) == correct) {
                                r.setTextColor(Utils.getColorResource(getActivity(), R.color.button_green));
                                Utils.makeAnimation(r, Techniques.Tada);
                                radioGroup.check(r.getId());

                            }
                        }
                    }
                }
            }
        });

        loadQuestions(progress);

        return window;
    }

    void loadQuestions(int no)
    {
        selectedId = -1;
        if(mcqQuestions == null)
        {
            Utils.makeToast(getActivity(), Constants.ERROR_INVALID_JSON);
            return;
        }
        if(progress < mcqQuestions.size()-1)
        {
            progress++;
            no++;
        }
        else
        {
               Utils.showDialog(this, Constants.QUIZ_COMPLETED_TEXT, correct+" correct of "+(correct+wrong), true);
        }

        //Utils.animateFlip(window,scrollView,scrollView);
        RadioGroup radioGroup = (RadioGroup) window.findViewById(R.id.radioGroup);

        TextView textViewQuestion  = ((TextView) window.findViewById(R.id.question));
        RadioButton radioButton1 = ((RadioButton) window.findViewById(R.id.radioButton1));
        RadioButton radioButton2 = ((RadioButton) window.findViewById(R.id.radioButton2));
        RadioButton radioButton3 = ((RadioButton) window.findViewById(R.id.radioButton3));
        RadioButton radioButton4 = ((RadioButton) window.findViewById(R.id.radioButton4));
        Button submit = (Button) window.findViewById(R.id.button);

        textViewQuestion.setText((no + 1) + "/" + mcqQuestions.size() + ") " + mcqQuestions.get(no).getQuestion());
        radioButton1.setText(mcqQuestions.get(no).getOption1());
        radioButton1.setTextColor(Utils.getColorResource(getActivity(), R.color.black));
        radioButton1.setChecked(false);
        radioButton1.setEnabled(true);
        radioButton2.setText(mcqQuestions.get(no).getOption2());
        radioButton2.setTextColor(Utils.getColorResource(getActivity(), R.color.black));
        radioButton2.setChecked(false);
        radioButton2.setEnabled(true);
        radioButton3.setText(mcqQuestions.get(no).getOption3());
        radioButton3.setTextColor(Utils.getColorResource(getActivity(), R.color.black));
        radioButton3.setChecked(false);
        radioButton3.setEnabled(true);
        radioButton4.setText(mcqQuestions.get(no).getOption4());
        radioButton4.setTextColor(Utils.getColorResource(getActivity(), R.color.black));
        radioButton4.setChecked(false);
        radioButton4.setEnabled(true);
        submit.setText(Utils.getStringResource(getActivity(), R.string.submit));

    }

    @Override
    public void ok(DialogInterface dialog){

        Utils.backPress(getActivity());
        dialog.dismiss();
    }
    @Override
    public void cancel(DialogInterface dialog){
        // do nothing
    }
}