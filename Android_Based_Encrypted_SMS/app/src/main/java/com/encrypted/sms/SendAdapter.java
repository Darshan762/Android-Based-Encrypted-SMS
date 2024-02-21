package com.encrypted.sms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SendAdapter extends RecyclerView.Adapter<SendAdapter.ViewHolder> {
    Context context;
    ArrayList send_to=new ArrayList();
    ArrayList send_subject=new ArrayList();
    ArrayList send_data=new ArrayList();
    ArrayList send_time=new ArrayList();

    public SendAdapter(Send send, ArrayList send_to, ArrayList send_subject, ArrayList send_data, ArrayList send_time) {
        this.context=send;
        this.send_to=send_to;
        this.send_subject=send_subject;
        this.send_data=send_data;
        this.send_time=send_time;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.send_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.from_address.setText(send_to.get(position).toString().trim());
        holder.dtime.setText(send_time.get(position).toString().trim());
        holder.subject.setText(send_subject.get(position).toString().trim());
        holder.data.setText(send_data.get(position).toString().trim());

    }


    @Override
    public int getItemCount() {
        return send_to.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout,layout;
        TextView from_address,dtime,subject,data;
        int z = 0;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            from_address=itemView.findViewById(R.id.frm);
            dtime=itemView.findViewById(R.id.dt);
            subject=itemView.findViewById(R.id.sub);
            data=itemView.findViewById(R.id.mail);
            linearLayout=itemView.findViewById(R.id.parent);
            layout=itemView.findViewById(R.id.dta);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (z == 0) {
                        layout.setVisibility(View.VISIBLE);
                        z++;
                    } else {
                        layout.setVisibility(View.GONE);
                        z = 0;
                    }

                }
            });
        }
    }
}
