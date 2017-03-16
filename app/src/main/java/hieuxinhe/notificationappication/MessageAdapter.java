package hieuxinhe.notificationappication;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import object.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private Context mContext;
    private List<Message> albumList;
    class MyViewHolder extends RecyclerView.ViewHolder                                         {
        TextView title, count , content;
        ImageView thumbnail, overflow;

        MyViewHolder(View view) {
            super(view);
            content  = (TextView)view.findViewById(R.id.content);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
        //    thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
         //   overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }
    MessageAdapter(Context mContext, List<Message> albumList)                                  {
        this.mContext = mContext;
        this.albumList = albumList;
    }
    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)           {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_card, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override public void onBindViewHolder(final MyViewHolder holder, int position)            {
        Message album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.count.setText(album.getDayCreate() + ".");
        holder.content.setText(album.getSMS());
        // loading album cover using Glide library
       // Glide.with(mContext).load(R.drawable.logo).into(holder.thumbnail);
        /*
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        */
    }
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view)                                                      {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_message, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }
    /**
     * Click listener for popup menu items
     */
    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener         {

        MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Save", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
    @Override public int getItemCount()                                                       {
        return albumList.size();
    }
}