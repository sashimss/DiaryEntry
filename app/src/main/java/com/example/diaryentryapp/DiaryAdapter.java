package com.example.diaryentryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder>{
    private List<DiaryItem> entries;
    public interface ClickListener {
        void onItemClick(DiaryItem item);
    }
    private final ClickListener listener;

    public DiaryAdapter(List<DiaryItem> entries, ClickListener listener){
        this.entries = entries;
        this.listener = listener;
    }

    public void RefreshList(List<DiaryItem> entries){
        this.entries.clear();
        this.entries.addAll(entries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryItem entry = entries.get(position);

        holder.TV_date.setText(entry.getDate());
        holder.TV_content.setText(entry.getContent());
        holder.itemView.setOnClickListener(v-> this.listener.onItemClick(entry));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView TV_date, TV_content;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            TV_date = itemView.findViewById(R.id.text_cardDate);
            TV_content = itemView.findViewById(R.id.text_cardContent);
        }

    }
}
