package com.lgbt.LGBTsCLUB.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lgbt.LGBTsCLUB.R;
import com.lgbt.LGBTsCLUB.model.usermodel.ReligiousModel;

import java.util.ArrayList;

public class MultiSelectReligionAdapter extends RecyclerView.Adapter<MultiSelectReligionAdapter.ViewHolder> {
    private final ArrayList<ReligiousModel> list;
    private final Context context;

    public MultiSelectReligionAdapter(ArrayList<ReligiousModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.multiselect_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (list != null) {
            holder.checkBox.setChecked(list.get(position).isSelected());
            holder.checkBox.setText(list.get(position).getName());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    list.get(position).setSelected(holder.checkBox.isChecked());

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
