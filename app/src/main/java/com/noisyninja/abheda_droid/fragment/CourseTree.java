package com.noisyninja.abheda_droid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.activity.LessonsActivity;
import com.noisyninja.abheda_droid.control.AnimatedExpandableListView;
import com.noisyninja.abheda_droid.pojo.Courses;
import com.noisyninja.abheda_droid.pojo.Module;
import com.noisyninja.abheda_droid.pojo.Topic;
import com.noisyninja.abheda_droid.pojo.Topics;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.DataStore;
import com.noisyninja.abheda_droid.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ir2pi on 1/4/2015.
 */
public class CourseTree extends Fragment {

    private AnimatedExpandableListView animatedExpandableListView;
    private CustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View windows = inflater.inflate(R.layout.course_tree_frag, container, false);
        animatedExpandableListView = (AnimatedExpandableListView) windows.findViewById(R.id.listView);

        List<GroupItem> groupItems = new ArrayList<GroupItem>();
        Topics topics = DataStore.getInstance(getActivity()).getTopics();
        for(Topic topic:topics.getTopics())
        {
            if(topic.getCoursesList()!= null && topic.getCoursesList().size()>0) {
                for (Courses courses : topic.getCoursesList()) {
                    GroupItem groupItem = new GroupItem();
                    groupItem.name = courses.getCourseName();
                    groupItem.description = courses.getCourseLongDescription();
                    groupItem.completion = courses.getCompletion();
                    groupItem.marks = courses.getMarks();
                    if(courses.getModules() != null && courses.getModules().size()>0) {
                        for (Module module : courses.getModules()) {
                            ChildItem childItem = new ChildItem();
                            childItem.name = module.getName();
                            childItem.description = module.getDescription();
                            childItem.desc = module.getDesc();
                            childItem.level = module.getLevel();
                            childItem.instruction = module.getInstruction();
                            childItem.daysToComplete = module.getDaysToComplete();
                            childItem.marks = module.getMarks();
                            groupItem.items.add(childItem);
                        }
                    }
                    groupItems.add(groupItem);
                }
            }

        }

        adapter = new CustomAdapter(getActivity());
        adapter.setData(groupItems);

        animatedExpandableListView = (AnimatedExpandableListView) windows.findViewById(R.id.listView);
        animatedExpandableListView.setAdapter(adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        animatedExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (animatedExpandableListView.isGroupExpanded(groupPosition)) {
                    animatedExpandableListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    animatedExpandableListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

        animatedExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {

                //Utils.showQuickAction(getActivity(), view);
                //Utils.handleInfo(getActivity(), "value i "+i+" value i2 "+i2+" value l "+l);
                Constants.COURSE_ID = i;
                Constants.MODULE_ID = i2;
                Utils.startActivity(getActivity(), LessonsActivity.class);
                return false;
            }
        });

        return windows;
    }


    private static class GroupItem {
        String name;
        String description;
        int completion;
        int marks;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String name;
        String description;
        String desc;
        String instruction;
        int level;
        int marks;
        int daysToComplete;
    }

    private static class ChildHolder {
        TextView name;
        TextView description;
        TextView desc;
        TextView instruction;
        TextView marks;
        TextView daysToComplete;
    }

    private static class GroupHolder {
        TextView name;
        TextView description;
        TextView marks;
        ArcProgress arcProgress;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class CustomAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public CustomAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.tree_child_item, parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.textView1);
                holder.description = (TextView) convertView.findViewById(R.id.description);
                holder.daysToComplete = (TextView) convertView.findViewById(R.id.question);
                holder.instruction = (TextView) convertView.findViewById(R.id.instructions);
                holder.marks = (TextView) convertView.findViewById(R.id.marks);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.name.setText(item.name);
            holder.description.setText(item.description);
            holder.daysToComplete.setText(getString(R.string.days_to_complete)+item.daysToComplete);
            holder.instruction.setText(item.instruction);
            holder.marks.setText(getString(R.string.marks)+item.marks);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.item_tree_group, parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.textView1);
                holder.description = (TextView) convertView.findViewById(R.id.textView2);
                //holder.completion = (TextView) convertView.findViewById(R.id.textView3);
                holder.marks = (TextView) convertView.findViewById(R.id.textView4);
                holder.arcProgress = (ArcProgress) convertView.findViewById(R.id.arc_progress);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.name.setText(item.name);
            holder.description.setText(item.description);
            //holder.completion.setText(item.completion);
            holder.marks.setText(getString(R.string.marks)+item.marks);
            holder.arcProgress.setProgress(item.completion);
            Utils.styleArc(holder.arcProgress, getActivity());
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }

}
