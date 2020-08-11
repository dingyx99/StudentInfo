package me.dingyx99.studentinfo;

public class Student {
    public int ID = -1;
    public String Name;
    public String Num;
    public String Class;

    @Override
    public String toString(){
        String result = "";
        result += this.ID + "               ";
        result += this.Class + "               ";
        result += this.Num + "               ";
        result += this.Name + "               ";
        return result;
    }
}
