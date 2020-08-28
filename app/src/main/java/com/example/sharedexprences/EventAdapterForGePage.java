package com.example.sharedexprences;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EventAdapterForGePage extends ArrayAdapter<EventDataInGElist> {

    private static final String TAG = "GEAdapterForUserPage";
    private Context mContext;
    Integer mResource;
    int lastPosition = -1;

    static class ViewHolder {
        TextView eventName;
        TextView sum;
    }

    public EventAdapterForGePage(@NonNull Context context, int resource, @NonNull ArrayList<EventDataInGElist> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Integer id = getItem(position).getEventID();
        String name = getItem(position).getName();
        Integer sum = getItem(position).getSum();

        EventDataInGElist person = new EventDataInGElist(id, name, sum);

        final View result;
        ViewHolder holder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView=inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.eventName = (TextView) convertView.findViewById(R.id.nameID);
            holder.sum = (TextView) convertView.findViewById(R.id.sumID);
            result = convertView;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(
                mContext, (position>lastPosition ? R.anim.load_down_anim : R.anim.load_up_anim)
        );
        result.startAnimation(animation);
        lastPosition=position;

        holder.eventName.setText(person.getName());
        holder.sum.setText(person.getSum().toString());

        return convertView;

    }
}
