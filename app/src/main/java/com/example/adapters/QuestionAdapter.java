package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.models.Question;
import com.example.skinlab.R;

import java.util.List;

public class QuestionAdapter extends BaseAdapter {
    Context context;
    List<Question> quesList;
    int totalScore;

    public QuestionAdapter(Context context, List<Question> quesList) {
        this.context = context;
        this.quesList = quesList;
        this.totalScore = 0;
    }

    @Override
    public int getCount() {
        return quesList.size();
    }

    @Override
    public Object getItem(int position) {
        return quesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.testquestion_layout, parent, false);
            holder = new ViewHolder();
            holder.txtSTT = convertView.findViewById(R.id.txtSTT);
            holder.txtQuestion = convertView.findViewById(R.id.txtQuestion);
            holder.radA = convertView.findViewById(R.id.radA);
            holder.radB = convertView.findViewById(R.id.radB);
            holder.radC = convertView.findViewById(R.id.radC);
            holder.radD = convertView.findViewById(R.id.radD);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Question question = quesList.get(position);
        holder.txtSTT.setText(question.getQuesNumber());
        holder.txtQuestion.setText(question.getQuesContent());
        holder.radA.setText(question.getQuesA());
        holder.radB.setText(question.getQuesB());
        holder.radC.setText(question.getQuesC());
        holder.radD.setText(question.getQuesD());

        // Set trạng thái cho RadioButton dựa trên trạng thái lưu trữ trong đối tượng Question
        holder.radA.setChecked(question.isCheckedA());
        holder.radB.setChecked(question.isCheckedB());
        holder.radC.setChecked(question.isCheckedC());
        holder.radD.setChecked(question.isCheckedD());

        // Xử lý sự kiện khi RadioButton được chọn
        holder.radA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setCheckedA(true);
                question.setCheckedB(false);
                question.setCheckedC(false);
                question.setCheckedD(false);
                totalScore += 1;
                notifyDataSetChanged(); // Cập nhật lại ListView
            }
        });

        holder.radB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setCheckedA(false);
                question.setCheckedB(true);
                question.setCheckedC(false);
                question.setCheckedD(false);
                totalScore += 2;
                notifyDataSetChanged(); // Cập nhật lại ListView
            }
        });

        holder.radC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setCheckedA(false);
                question.setCheckedB(false);
                question.setCheckedC(true);
                question.setCheckedD(false);
                totalScore += 3;
                notifyDataSetChanged(); // Cập nhật lại ListView
            }
        });

        holder.radD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setCheckedA(false);
                question.setCheckedB(false);
                question.setCheckedC(false);
                question.setCheckedD(true);
                totalScore += 4;
                notifyDataSetChanged(); // Cập nhật lại ListView
            }
        });

        return convertView;
    }




    static class ViewHolder {
        TextView txtSTT, txtQuestion;
        RadioButton radA, radB, radC, radD;
    }
}
