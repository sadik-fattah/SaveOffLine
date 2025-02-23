package com.guercifzone.saveoffline.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.guercifzone.saveoffline.Database.WebPageDatabase;
import com.guercifzone.saveoffline.Models.WebPage;
import com.guercifzone.saveoffline.R;

import java.util.ArrayList;

public class WebPageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WebPage> webpages;
    private WebPageDatabase webPageDatabase;
    public WebPageAdapter(Context context, ArrayList<WebPage> webpages) {
        this.context = context;
        this.webpages = webpages;
    }


    @Override
    public int getCount() {
        return webpages.size();
    }

    @Override
    public Object getItem(int position) {
        return webpages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("WebPageAdapter", "Position: " + position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
            Log.d("WebPageAdapter", "convertView is being inflated");
        }
        WebPage webPage = webpages.get(position);
        TextView urlText = convertView.findViewById(R.id.urlText);
        if (urlText != null){
            urlText.setText(webPage.getUrl());
        }else {
            urlText.setText("No url found");
        }
        Button deleteButton = convertView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webPageDatabase.deleteWebPage(webPage.getId());
                webpages.remove(position);
                notifyDataSetChanged();
            }
        });
        if (deleteButton != null){
            deleteButton.setText("Delete");
            }


        return convertView;
    }
}
