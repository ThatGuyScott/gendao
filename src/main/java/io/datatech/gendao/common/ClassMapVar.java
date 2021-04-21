package io.datatech.gendao.common;

public class ClassMapVar {

    private int ID;
    private String var_name;
    private String var_type_java_short;
    private String var_type_java_full;
    private String var_type_sql_stanard;

    private String var_getter;
    private String var_setter;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }

    public String getVar_type_java_full() {
        return var_type_java_full;
    }

    public void setVar_type_java_full(String var_type_java_full) {
        this.var_type_java_full = var_type_java_full;
    }

    public String getVar_type_java_short() {
        return var_type_java_short;
    }

    public void setVar_type_java_short(String var_type_java_short) {
        this.var_type_java_short = var_type_java_short;
    }

    public String getVar_type_sql() {
        return var_type_sql_stanard;
    }

    public void setVar_type_sql_stanard(String var_type_sql_stanard) {
        this.var_type_sql_stanard = var_type_sql_stanard;
    }

    public String getVar_getter() {
        return var_getter;
    }

    public void setVar_getter(String var_getter) {
        this.var_getter = var_getter;
    }

    public String getVar_setter() {
        return var_setter;
    }

    public void setVar_setter(String var_setter) {
        this.var_setter = var_setter;
    }
}