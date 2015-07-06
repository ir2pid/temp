package com.noisyninja.abheda_droid.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.control.GridViewAdapter;
import com.noisyninja.abheda_droid.pojo.OrderGameQuestion;
import com.noisyninja.abheda_droid.pojo.OrderGameQuiz;
import com.noisyninja.abheda_droid.pojo.misc.IntegerStringPair;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.IDialogCallback;
import com.noisyninja.abheda_droid.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ir2pi on 11/30/2014.
 */
public class OrderGameDetailFragNew extends Fragment implements View.OnClickListener, IDialogCallback{

    View window;
    Context context;
    ArrayList<IntegerStringPair> wordsList;
    ArrayList<IntegerStringPair> wordsSelected;
    Button buttonSubmit;
    GridView gridView1;
    GridView gridView2;
    TextView textViewQuestion;
    List<OrderGameQuestion> orderGameQuestions;
    OrderGameQuestion orderGameQuestion;
    GridViewAdapter gridViewAdapter1;
    GridViewAdapter gridViewAdapter2;
    int progress;
    int correct;
    int wrong;
    enum STATE{
        NORMAL,
        RESULT,
        LAST_RESULT,
        END_QUIZ,
        NO_SELECTION
    };
    STATE state;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        progress = 0;
        state = STATE.NORMAL;
        if (getArguments().containsKey(Constants.FRAGMENT_DATA)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            /*mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
                    ARG_ITEM_ID));*/
            String data = getArguments().getString(Constants.FRAGMENT_DATA);
            OrderGameQuiz orderGameQuiz = (OrderGameQuiz) Utils.getFromJson(data, OrderGameQuiz.class);
            orderGameQuestions = Arrays.asList((OrderGameQuestion[]) Utils.getObject(getActivity(),
                    orderGameQuiz.getOrderGameQuestions(), OrderGameQuestion[].class));
            Utils.handleInfo(getActivity(), orderGameQuiz.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        window = inflater.inflate(R.layout.frag_order_game_detail_new, container, false);
        buttonSubmit = ((Button) window.findViewById(R.id.submit));
        textViewQuestion = ((TextView) window.findViewById(R.id.question));

        gridView1 = ((GridView) window.findViewById(R.id.gridView1));
        gridView2 = ((GridView) window.findViewById(R.id.gridView2));
        wordsList = setupQuestion(progress);

        buttonSubmit.setOnClickListener(this);
        return window;
    }



    @Override
    public void onClick(View view){
        if(wordsSelected.size()<1){
            state = STATE.NO_SELECTION;
            Utils.showDialog(this,Constants.ERROR,Constants.ERROR_NO_SELECTION,true);
            return;
        }
        switch (view.getId()){
            case R.id.submit:{


                    state = STATE.RESULT;
                progress++;
                    if(checkAnswers())
                    {
                        correct++;
                    }else {
                        wrong++;
                    }
                    if(progress<orderGameQuestions.size())
                    {
                    setupQuestion(progress);
                    gridViewAdapter1.notifyDataSetChanged();
                    gridViewAdapter2.notifyDataSetInvalidated();
                }

                break;
            }
        }
    }


    private ArrayList<IntegerStringPair> setupQuestion(int questionNo){
        Utils.buttonDeactivate(buttonSubmit);
        orderGameQuestion = orderGameQuestions.get(questionNo);
        textViewQuestion.setText("("+(questionNo+1)+"/"+orderGameQuestions.size()+") "+orderGameQuestion.getQuestion());
        Utils.makeToast(context, orderGameQuestion.getAnswers().toString());
        wordsList = Utils.map2IntegerStringPairList(orderGameQuestion.getWords());
        wordsSelected = new ArrayList<IntegerStringPair>();

        refreshAdapters();

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Utils.buttonActivate(buttonSubmit);
                IntegerStringPair integerStringPair = wordsList.get(position);
                wordsSelected.add(integerStringPair);
                wordsList.remove(integerStringPair);
                refreshAdapters();
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                IntegerStringPair integerStringPair = wordsSelected.get(position);
                wordsList.add(integerStringPair);
                wordsSelected.remove(integerStringPair);
                if(wordsSelected.size()==0){
                    Utils.buttonDeactivate(buttonSubmit);
                }
                refreshAdapters();
            }
        });

     return wordsList;
    }

    private boolean checkAnswers(){
        ArrayList<ArrayList<Integer>> arrayArrayList = orderGameQuestion.getAnswers();
        boolean status = false;
        StringBuffer answer = new StringBuffer();
        for(IntegerStringPair isp : wordsSelected){
            answer.append(isp.getS());
            answer.append(Constants.SPACE);
        }

        StringBuffer corrects = new StringBuffer();

        for (ArrayList<Integer> arrayList:arrayArrayList) {
        StringBuffer sb = new StringBuffer();
            for (Integer i : arrayList) {
                sb.append(orderGameQuestion.getWords().get(i));
                sb.append(Constants.SPACE);
            }
            if(sb.toString().compareTo(answer.toString()) == 0){
                status = true;
            }
            if(corrects.length()<1)//enter first only
            {
                corrects.append(sb);
            }

        }

        String question = orderGameQuestion.getQuestion();

        if(status){
            Utils.showResult(context, true, question, corrects.toString(), null, this);
        }else {
            Utils.showResult(context, false, question, corrects.toString(), answer.toString(), this);
        }

        return status;
    }

    private void refreshAdapters(){
        gridViewAdapter1 = new GridViewAdapter(context, wordsList);
        gridView1.setAdapter(gridViewAdapter1);
        gridViewAdapter2 = new GridViewAdapter(context, wordsSelected);
        gridView2.setAdapter(gridViewAdapter2);
        gridViewAdapter1.notifyDataSetChanged();
        gridViewAdapter2.notifyDataSetChanged();
    }


    @Override
    public void ok(DialogInterface dialog) {
        switch (state){
            case NO_SELECTION:{
                break;
            }
            case END_QUIZ:{
                Utils.backPress(getActivity());
                dialog.dismiss();
                break;
            }
            case RESULT:{

                if(progress == orderGameQuestions.size()){//HANDLE END OF QUIZ
                    state = STATE.END_QUIZ;
                    Utils.showDialog(this, Constants.QUIZ_COMPLETED_TEXT, correct+" correct of "+(correct+wrong), true);

                }
                break;
            }
            case LAST_RESULT:{

            }
            default:{

            }
        }
    }

    @Override
    public void cancel(DialogInterface dialog) {

    }
}