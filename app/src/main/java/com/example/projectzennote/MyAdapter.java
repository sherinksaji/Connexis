package com.example.projectzennote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<NoteModel> notesList;
    ArrayList<Integer> emojisAL;

    public MyAdapter(Context context, RealmResults<NoteModel> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.emojisAL = new ArrayList<>();
        emojisAL.add(R.drawable.qn_mark);
        emojisAL.add(R.drawable.emoji_1);
        emojisAL.add(R.drawable.emoji_2);
        emojisAL.add(R.drawable.emoji_3);
        emojisAL.add(R.drawable.emoji_4);
        emojisAL.add(R.drawable.emoji_5);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        NoteModel note = notesList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String sendingFormatedTime = dateFormat.format(new Date(note.getSendingTime()));
        holder.sendingTime.setText("Send on "+sendingFormatedTime);

        //TODO: set before emoji
        holder.beforeEmoji.setImageResource(emojisAL.get(note.getMoodBefore()));
        //TODO: set arrow
        holder.arrow.setImageResource(R.drawable.arrow);
        //TODO: set after emoji
        holder.afterEmoji.setImageResource(emojisAL.get(note.getMoodAfter()));
        holder.text.setText(note.getText());

        String createdFormatedTime = dateFormat.format(new Date(note.getCreatedTime()));
        holder.createdTime.setText("Created on "+createdFormatedTime);

        if (note.getMoodAfter()==0){
            holder.arrow.setVisibility(View.GONE);
            holder.afterEmoji.setVisibility(View.GONE);
        }


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            //delete the note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView sendingTime;
        ImageView beforeEmoji;
        ImageView arrow;
        ImageView afterEmoji;
        TextView text;
        TextView createdTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sendingTime = itemView.findViewById(R.id.sendingTime);
            beforeEmoji=itemView.findViewById(R.id.beforeEmoji);
            arrow=itemView.findViewById(R.id.arrow);
            afterEmoji=itemView.findViewById(R.id.afterEmoji);
            text=itemView.findViewById(R.id.text);
            createdTime=itemView.findViewById(R.id.createdTime);


        }
    }
}