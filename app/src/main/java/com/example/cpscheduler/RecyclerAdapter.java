package com.example.cpscheduler;

import static android.content.Context.ALARM_SERVICE;

import static java.lang.Long.parseLong;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Information> listS;
    private Context context;
    public RecyclerAdapter(List<Information> listS,Context context){
        this.listS = listS;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,parent,false);
        createNoti();
        return new ViewHolder(v);
    }
    private void createNoti(){
        CharSequence name = "Channel";
        String description = " Channel for contest Reminder";
        int imp = NotificationManager.IMPORTANCE_DEFAULT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("noti",name,imp);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = holder.getAbsoluteAdapterPosition();
        Information s = listS.get(position);
        holder.startText.setText(s.getStart_time());
        holder.endText.setText(s.getEnd_time());
        holder.textView.setText(s.getName());
        holder.urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent contesUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(listS.get(i).getLink())));
                (v.getContext()).startActivity(contesUrl);
            }
        });
        holder.remainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context= v.getContext();
                long  tri = System.currentTimeMillis()+(1000*2);
                String myDate = s.getStart_time();
                LocalDateTime localDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    localDateTime = LocalDateTime.parse(myDate,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") );
                    tri = localDateTime
                            .atZone(ZoneId.systemDefault())
                            .toInstant().toEpochMilli();
                }

                Intent myIntent1 = new Intent(context,AlarmBroadCustReciver.class);
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 1253, myIntent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, tri - (1000*60), pendingIntent1);
                Toast.makeText(context, "Reminder has been set", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listS.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView startText;
        TextView endText;
        Button remainder;
        Button urlBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            startText = itemView.findViewById(R.id.startText);
            endText = itemView.findViewById(R.id.endText);
            remainder = itemView.findViewById(R.id.addRemainder);
            urlBtn = itemView.findViewById(R.id.urlBtn);
        }

    }
}
