package com.guercifzone.saveoffline.Adapters;

import static android.app.ProgressDialog.show;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageView trashIcon = convertView.findViewById(R.id.trashIcon);
        if (urlText != null){
            urlText.setText(webPage.getUrl());
        }else {
            urlText.setText("No url found");
        }
/*deleteButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Page")
                .setMessage("Are you sure you want to delete this page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            WebPage webPageToDelete = webpages.get(position);
                            webpages.remove(position);
                            notifyDataSetChanged();
                            webPageDatabase.deletePage(webPageToDelete.getUrl());
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .show();


    }
});*/
        trashIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebPage webPageToDelete = webpages.get(position);
                String urlToDelete = webPageToDelete.getUrl();
                webpages.remove(position);
                notifyDataSetChanged();
                try {
                    webPageDatabase.deletePage(urlToDelete);
                    if (!webPageDatabase.isWebPageExists(urlToDelete)) {
                        Log.d("WebPageAdapter", "WebPage deleted successfully from database.");
                        Toast.makeText(context, "WebPage deleted successfully from database.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("WebPageAdapter", "Failed to delete WebPage from database.");
                        Toast.makeText(context, "Failed to delete WebPage from database.", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        return convertView;
    }
}
