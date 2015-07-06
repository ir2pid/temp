package com.noisyninja.abheda_droid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.control.ListLessonAdapter;
import com.noisyninja.abheda_droid.control.ListLessonItem;
import com.noisyninja.abheda_droid.pojo.BaseLesson;
import com.noisyninja.abheda_droid.pojo.Lesson;
import com.noisyninja.abheda_droid.pojo.Lessons;
import com.noisyninja.abheda_droid.pojo.MCQQuiz;
import com.noisyninja.abheda_droid.pojo.Module;
import com.noisyninja.abheda_droid.pojo.OrderGameQuiz;
import com.noisyninja.abheda_droid.pojo.PictureMatchQuiz;
import com.noisyninja.abheda_droid.pojo.Quizzes;
import com.noisyninja.abheda_droid.pojo.SimpleQuiz;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.Constants.MODULE_TYPE;
import com.noisyninja.abheda_droid.util.DataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ir2pi on 12/5/2014.
 */
public class LessonListFrag extends ListFragment {

    View window;
    List baseLessons;

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(MODULE_TYPE module_type, String data);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(MODULE_TYPE module_type, String data) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LessonListFrag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        window = inflater.inflate(R.layout.frag_lesson_list, container, false);
        return window;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
        baseLessons = new ArrayList();

        List<ListLessonItem> items = new ArrayList<ListLessonItem>();

        Module module = DataStore.getInstance(getActivity()).getModule();
        Lessons lessons = module.getLessons();
        Quizzes quizzes = module.getQuizzes();

        int quizNo=0;

        if(lessons != null) {
            for (Lesson lesson : lessons.getLessons()) {
                if (lesson == null)//handles a condition of null objects being iterated
                    continue;

                baseLessons.add(lesson);

                MODULE_TYPE module_type = lesson.isFlashCard() ? MODULE_TYPE.FLASHCARD : MODULE_TYPE.LESSON;
                lesson.setModule_type(module_type);

                ListLessonItem listLessonItem = new ListLessonItem(
                        lesson.getName(),
                        lesson.getDescription(), module_type);

                items.add(listLessonItem);
            }
        }

        if(quizzes != null) {

            if(quizzes.getMcqQuizs()!=null){
                for (MCQQuiz mcqQuiz : quizzes.getMcqQuizs()) {
                if (mcqQuiz == null)//handles a condition of null objects being iterated
                    continue;

                quizNo++;
                baseLessons.add(mcqQuiz);
                MODULE_TYPE module_type = MODULE_TYPE.MCQ_QUIZ;

                ListLessonItem listLessonItem = new ListLessonItem(
                        "Quiz " + quizNo,
                        "Quiz", module_type);

                items.add(listLessonItem);
            }}
            if(quizzes.getOrderGameQuizs()!=null){
            for (OrderGameQuiz orderGameQuiz : quizzes.getOrderGameQuizs()) {
                if (orderGameQuiz == null)//handles a condition of null objects being iterated
                    continue;

                quizNo++;
                baseLessons.add(orderGameQuiz);
                MODULE_TYPE module_type = MODULE_TYPE.ORDER_GAME_QUIZ;

                ListLessonItem listLessonItem = new ListLessonItem(
                        "Quiz " + quizNo,
                        "Order Game Quiz", module_type);

                items.add(listLessonItem);
            }}
            if(quizzes.getPictureMatchQuiz()!=null){
                for (PictureMatchQuiz pictureMatchQuiz : quizzes.getPictureMatchQuiz()) {
                    if (pictureMatchQuiz == null)//handles a condition of null objects being iterated
                        continue;

                    quizNo++;
                    baseLessons.add(pictureMatchQuiz);
                    MODULE_TYPE module_type = MODULE_TYPE.PICTURE_MATCH_QUIZ;

                    ListLessonItem listLessonItem = new ListLessonItem(
                            "Quiz " + quizNo,
                            "Picture Match Quiz", module_type);

                    items.add(listLessonItem);
                }}
            if(quizzes.getSimpleQuizs()!=null){
                for (SimpleQuiz simpleQuiz : quizzes.getSimpleQuizs()) {
                    if (simpleQuiz == null)//handles a condition of null objects being iterated
                        continue;

                    quizNo++;
                    baseLessons.add(simpleQuiz);
                    MODULE_TYPE module_type = MODULE_TYPE.SIMPLE_QUIZ;

                    ListLessonItem listLessonItem = new ListLessonItem(
                            "Quiz " + quizNo,
                            "Simple Quiz", module_type);

                    items.add(listLessonItem);
                }}
        }

        /*ListLessonItem listLessonItem1 = new ListLessonItem(
                getResources().getDrawable(R.drawable.ic_disclosure2),
                Constants.itemTypes[0],
                Constants.itemTypes2[0]);

        items.add(listLessonItem1);*/
        // TODO: replace with a real list adapter.
        setListAdapter(new ListLessonAdapter(getActivity(), items));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);
        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        Constants.LESSON_QUIZ_ID = position;
        BaseLesson baseLesson = (BaseLesson)baseLessons.get(position);
        mCallbacks.onItemSelected(baseLesson.getModule_type(), baseLesson.toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}