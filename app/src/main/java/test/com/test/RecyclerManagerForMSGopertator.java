package test.com.test;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;


public class RecyclerManagerForMSGopertator extends RecyclerView.Adapter<RecyclerManagerForMSGopertator.ViewHolder> {

    View rootView;
    ArrayList<MessagesInfo> msgList;

    public RecyclerManagerForMSGopertator(ArrayList<MessagesInfo> list, View view) {
        rootView = view;
        msgList = list; //Catched List ...Type of MessagesInfo
    }

    @Override
    public RecyclerManagerForMSGopertator.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerManagerForMSGopertator.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_operator_template, parent, false));
    }

    //Bind Data To Field
    @Override
    public void onBindViewHolder(RecyclerManagerForMSGopertator.ViewHolder holder, int position) {
        holder.txtTime.setText(msgList.get(position).Time);
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    //Declare Recycler Field...
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder d  = new AlertDialog.Builder(itemView.getContext());
                    final LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View view =  inflater.inflate(R.layout.alert_show_details_msg,null);
                    FloatingActionButton fabResend = (FloatingActionButton) view.findViewById(R.id.fabResend);
                    fabResend.setVisibility(View.VISIBLE);
                    LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.parent_msg_deatils);
                    ImageView img_msg_details = (ImageView)view.findViewById(R.id.img_msg_details);
                    img_msg_details.setImageResource(R.drawable.message_box);
                    linearLayout.setBackgroundResource(R.drawable.operator_back);

                    TextView txt =  (TextView) view.findViewById(R.id.txtPassengerName);
                    TextView txt2 = (TextView) view.findViewById(R.id.txtPassemgerTel);
                    TextView txt3 = (TextView) view.findViewById(R.id.txtPassemgerAddress);
                    TextView txt4 = (TextView) view.findViewById(R.id.txtPassemgerDestination);

                    txt.setText(msgList.get(getAdapterPosition()).PassengerName);
                    txt2.setText(msgList.get(getAdapterPosition()).PassengerTel);
                    txt3.setText(msgList.get(getAdapterPosition()).Address);
                    txt4.setText(msgList.get(getAdapterPosition()).Destination);

                    d.setView(view);
                    final AlertDialog dialog =  d.create();
                    dialog.getWindow().getAttributes().windowAnimations=R.style.Save_Alert;
                    dialog.show();

                    fabResend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           String destination =  msgList.get(getAdapterPosition()).Destination;
                           String address =  msgList.get(getAdapterPosition()).Address;
                           String name =  msgList.get(getAdapterPosition()).PassengerName;
                           String tel =  msgList.get(getAdapterPosition()).PassengerTel;

                            Intent intent = new Intent(v.getContext(),Operator.class);
                            intent.putExtra("ReSend",true);
                            intent.putExtra("name",name);
                            intent.putExtra("tel",tel);
                            intent.putExtra("addr",address);
                            intent.putExtra("destination",destination);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            v.getContext().startActivity(intent);

                        }
                    });

                }
            });
        }

    }

}
