package test.com.test;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


// this Class Used For Manage And HAndle RecyclerView For Send Message
public class RecyclerManagerForSend extends RecyclerView.Adapter<RecyclerManagerForSend.ViewHolder> {

    View rootView;
    CheckBox check_all;
    ArrayList<DriverInfo> list2;

    public RecyclerManagerForSend(ArrayList<DriverInfo> list, View view) {
        rootView = view;
        list2 = list; //Catched List ...Type of MessagesInfo


    }

    @Override
    public RecyclerManagerForSend.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.template_list_item, parent, false));
    }

    //Bind Data To Field
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtMenu.setText(list2.get(position).lname);
        holder.txtu.setText(list2.get(position).name);
        holder.checkBox.setChecked(list2.get(position).check);
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Declare Recycler Field...
        TextView txtMenu, txtu;
        CheckBox checkBox;

        public ViewHolder(final View itemView) {
            super(itemView);


            txtMenu = (TextView) itemView.findViewById(R.id.txttxt);
            txtu = (TextView) itemView.findViewById(R.id.txttxttxt);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);

            //Checked For State
            check_all = (CheckBox) rootView.findViewById(R.id.check_all);
            check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int i = 0; i < list2.size(); i++) {
                        list2.get(i).check = isChecked;
                        //set checked for Items
                    }

                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //set check item
                    list2.get(getAdapterPosition()).check = isChecked;
                }
            });
//            img = (ImageView) itemView.findViewById(R.id.imageView2);
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(itemView.getContext(),"یک ایتم حذف شد",Toast.LENGTH_SHORT).show();
//                    list2.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    notifyItemRangeChanged(getAdapterPosition(),list2.size());
//                }
//            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Toast.makeText(itemView.getContext(),list.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
//
//                    AlertDialog.Builder d  = new AlertDialog.Builder(itemView.getContext());
//                    LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                    View view =  inflater.inflate(R.layout.alert,null);
//                    ImageView btn = (ImageView) view.findViewById(R.id.btn);
//
//                    TextView txt = (TextView) view.findViewById(R.id.txtLname);
//                    TextView txt2 = (TextView) view.findViewById(R.id.txtName);
//                    TextView txt3 = (TextView) view.findViewById(R.id.txtPass);
//                    TextView txt4 = (TextView) view.findViewById(R.id.txtUsername);
//
//                    txt.setText(list2.get(getAdapterPosition()).lname);
//                    txt2.setText(list2.get(getAdapterPosition()).name);
//                    txt3.setText(list2.get(getAdapterPosition()).password);
//                    txt4.setText(list2.get(getAdapterPosition()).username);
//                    d.setView(view);
////                    d.setMessage("Lname : "+list2.get(getAdapterPosition()).lname).setTitle("MessageBox")
//                    final AlertDialog dialog =  d.create();
//                    dialog.getWindow().getAttributes().windowAnimations=R.style.Save_Alert;
//                    dialog.show();
//
//                    btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.hide();
//                            dialog.dismiss();
//                        }
//                    });
//
//                }
//            });
        }
    }


}

