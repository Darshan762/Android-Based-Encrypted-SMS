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

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder> {
    Context context;
    ArrayList to=new ArrayList();
    ArrayList subject=new ArrayList();
    ArrayList data =new ArrayList();
    ArrayList datetime=new ArrayList();
    ArrayList id=new ArrayList();
    ArrayList status=new ArrayList();
    public DeleteAdapter(Delete delete, ArrayList to, ArrayList subject, ArrayList data, ArrayList datetime, ArrayList id, ArrayList status) {

        this.context=delete;
        this.to=to;
        this.subject=subject;
        this.data=data;
        this.datetime=datetime;
        this.id=id;
        this.status=status;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.delete_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.from_address.setText(to.get(position).toString().trim());
        holder.dtime.setText(datetime.get(position).toString().trim());
        holder.subject.setText(subject.get(position).toString().trim());
        holder.data.setText(data.get(position).toString().trim());

    }



    @Override
    public int getItemCount() {
        return id.size();
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
