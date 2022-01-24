package test.com.test;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


// this Class Used For Manage And HAndle RecyclerView For Message
public class RecyclerManagerForMessage extends  RecyclerView.Adapter<RecyclerManagerForMessage.ViewHolder> {
    View rootView;
    Flag flag;
    ArrayList<MessagesInfo> msgList;

    public RecyclerManagerForMessage(ArrayList<MessagesInfo> list, View view,Flag flag) {
        rootView = view;
        msgList = list;//Catched List ...Type of MessagesInfo
        this.flag = flag;

    }


    @Override
    public RecyclerManagerForMessage.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(flag==Flag.Driver)
        {
            return new RecyclerManagerForMessage.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.template_list_msg_driver, parent, false));
        }
        return new RecyclerManagerForMessage.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.template_list_msg_operator, parent, false));
    }

    //Bind Data To Field
    @Override
    public void onBindViewHolder(RecyclerManagerForMessage.ViewHolder holder, int position) {
        holder.txtName.setText(msgList.get(position).PassengerName);
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    //Declare Recycler Field...
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtMsgNumber;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtTemplateNameMsg);



                itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder d  = new AlertDialog.Builder(itemView.getContext());
                    LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view;
                    if(flag==Flag.Operator)
                    {
                         view =  inflater.inflate(R.layout.alert_show_details_msg,null);
                        FloatingActionButton fabResend = (FloatingActionButton) view.findViewById(R.id.fabResend);
                        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.parent_msg_deatils);
                        ImageView img_msg_details = (ImageView)view.findViewById(R.id.img_msg_details);
                        img_msg_details.setImageResource(R.drawable.msg_icon_operator);
                        linearLayout.setBackgroundResource(R.drawable.operator_back);
                    }else
                    {
                         view =  inflater.inflate(R.layout.alert_show_details_msg,null);
                    }


                    TextView txt = (TextView) view.findViewById(R.id.txtPassengerName);
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


                }
            });
        }

    }
}
