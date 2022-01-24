package test.com.test;

// this Enum used for Detect Whitch Operation in Progreccing..

public enum Flag {
    Driver, // userTypes
    Operator,

    // this Items use when in one Activty used More than one Connection(Async Task)
    Add_Operator, // insert new Operator In DB
    ShowAllDriver, // Show All Driver

    Login, // set login or logout mode for Driver Or Operator
    LogOut,

    Ready, // show Status Driver in Ready Mode
    Busy  // show Status Driver in Busy Mode


}
