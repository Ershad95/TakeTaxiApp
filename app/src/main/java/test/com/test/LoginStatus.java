package test.com.test;

import test.com.test.Flag;


 public class LoginStatus {

    private static Flag flag;
    public static void SetStatus(Flag status)
    {
        flag = status;
    }
    public static Flag GetStatus()
    {
        return flag;
    }
}
