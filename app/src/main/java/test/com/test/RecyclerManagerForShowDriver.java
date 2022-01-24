package test.com.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FilterReader;
import java.util.ArrayList;



public class RecyclerManagerForShowDriver extends RecyclerView.Adapter<RecyclerManagerForShowDriver.ViewHolder> {

    ArrayList<DriverInfo> showDriverList;
    String token;
    public RecyclerManagerForShowDriver(ArrayList<DriverInfo> list) {
        showDriverList = list;
    }

    @Override
    public RecyclerManagerForShowDriver.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu,parent,false));
    }
    //Bind Data To Field
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtMenu.setText(showDriverList.get(position).name);
        holder.txtu.setText(showDriverList.get(position).lname);
        if(showDriverList.get(position).status.equals("waiting")){
            holder.imgStatus.setImageResource(R.drawable.ready_status);
        }else if(showDriverList.get(position).status.equals("busy"))
        {
            holder.imgStatus.setImageResource(R.drawable.busy_status);
        }
    }

    @Override
    public int getItemCount() {
        return showDriverList.size();
    }


    //Declare Recycler Field...
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMenu,txtu;
        ImageView img,imgStatus;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtMenu  = (TextView) itemView.findViewById(R.id.txttxt);
            txtu  = (TextView) itemView.findViewById(R.id.txttxttxt);
            img = (ImageView) itemView.findViewById(R.id.imageView2);
            imgStatus = (ImageView) itemView.findViewById(R.id.imgstatus);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"یک ایتم حذف شد",Toast.LENGTH_SHORT).show();
                    showDriverList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),showDriverList.size());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(itemView.getContext(),list.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder d  = new AlertDialog.Builder(itemView.getContext());
                    final LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View view =  inflater.inflate(R.layout.alert,null);
                    ImageView btn = (ImageView) view.findViewById(R.id.btn);
                    ImageView img_details = (ImageView) view.findViewById(R.id.btn_details);

                    TextView txt = (TextView) view.findViewById(R.id.txtLname);
                    TextView txt2 = (TextView) view.findViewById(R.id.txtName);
                    TextView txt3 = (TextView) view.findViewById(R.id.txtPass);
                    TextView txt4 = (TextView) view.findViewById(R.id.txtUsername);
                     token =  showDriverList.get(getAdapterPosition()).token;

                    txt.setText(showDriverList.get(getAdapterPosition()).lname);
                    txt2.setText(showDriverList.get(getAdapterPosition()).name);
                    txt3.setText(showDriverList.get(getAdapterPosition()).password);
                    txt4.setText(showDriverList.get(getAdapterPosition()).username);
                    d.setView(view);
//                    d.setMessage("Lname : "+list2.get(getAdapterPosition()).lname).setTitle("MessageBox")
                    final AlertDialog dialog =  d.create();
                    dialog.getWindow().getAttributes().windowAnimations=R.style.Save_Alert;
                    dialog.show();

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                            dialog.dismiss();
                        }
                    });

                    img_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Context context =v.getContext();
                            dialog.hide();
                            dialog.dismiss();
                            Intent intent = new Intent(context,MessageList.class);
                            intent.putExtra("token",token);
                            context.startActivity(intent);

                        }
                    });

                }
            });
        }
    }
}
