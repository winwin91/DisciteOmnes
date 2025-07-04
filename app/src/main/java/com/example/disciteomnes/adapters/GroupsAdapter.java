package com.example.disciteomnes.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.disciteomnes.R;
import com.example.disciteomnes.model.Group;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder> {
    private List<Group> groupList;

    private int[] cardColors = {
            Color.parseColor("#FBC02D"), // Gelb
            Color.parseColor("#E91E63"), // Pink
            Color.parseColor("#7E57C2"), // Lila
            Color.parseColor("#26A69A"), // Türkis
            Color.parseColor("#FF9800")  // Orange
    };

    public GroupsAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    // Methode wurde generiert
    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    // Methode wurde generiert
    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.groupName.setText(group.getName());
        holder.groupDescription.setText(group.getDescription());
        int colorIndex = position % cardColors.length; // Schleife
        holder.groupCard.setCardBackgroundColor(cardColors[colorIndex]);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupName, groupDescription;
        CardView groupCard;

        GroupViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
            groupDescription = itemView.findViewById(R.id.groupDescription);
            groupCard = itemView.findViewById(R.id.groupCard);
        }
    }

    public List<Group> getGroupList() {
        return groupList;
    }
}

