package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Question;
import com.example.skinlab.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context context;
    private List<Question> quesList;
    private int totalScore;

    public QuestionAdapter(Context context, List<Question> quesList) {
        this.context = context;
        this.quesList = quesList;
        this.totalScore = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testquestion_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = quesList.get(position);
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
                notifyDataSetChanged(); // Cập nhật lại RecyclerView
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
                notifyDataSetChanged(); // Cập nhật lại RecyclerView
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
                notifyDataSetChanged(); // Cập nhật lại RecyclerView
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
                notifyDataSetChanged(); // Cập nhật lại RecyclerView
            }
        });
    }

    @Override
    public int getItemCount() {
        return quesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSTT, txtQuestion;
        RadioButton radA, radB, radC, radD;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSTT = itemView.findViewById(R.id.txtSTT);
            txtQuestion = itemView.findViewById(R.id.txtQuestion);
            radA = itemView.findViewById(R.id.radA);
            radB = itemView.findViewById(R.id.radB);
            radC = itemView.findViewById(R.id.radC);
            radD = itemView.findViewById(R.id.radD);
        }
    }
}
