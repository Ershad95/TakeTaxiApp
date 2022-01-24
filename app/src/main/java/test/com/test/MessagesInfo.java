package test.com.test;


// this Class Used For Get and Set Driver Data...
//this Class USed for Gson Lib...

public class MessagesInfo {
    //Template Class For Convert Json To Object
    String PassengerName;
    String PassengerTel;
    String Address;
    String Destination;
    String Time;
    String number;
    Boolean seen;
    public String Price;

    public MessagesInfo() {
        seen=true;
    }

    public MessagesInfo(String name, String tel, String addr, String date, String num, String title,String des) {
        PassengerName = name;
        PassengerTel = tel;
        Address = addr;
        Time = date;
        number = num;
        Destination =des;
        seen=true;
    }
}
