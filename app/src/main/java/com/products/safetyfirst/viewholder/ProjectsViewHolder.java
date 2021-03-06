package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class ProjectsViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mUserTextView;
    public final TextView mComapnyTextView;
    public final TextView mDescriptionTextView;
    public final TextView mEvaluation;
    public final TextView mYears;

    public ProjectsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mUserTextView = itemView.findViewById(R.id.designation);
        mComapnyTextView = itemView.findViewById(R.id.company);
        mDescriptionTextView = itemView.findViewById(R.id.description);
        mEvaluation = itemView.findViewById(R.id.evaluation);
        mYears = itemView.findViewById(R.id.years);
    }
}

