package io.datatech.gendao.common;

public class ClassMap {

    private int ID;
    private String packageName;
    private String className;
    private String parentClassName;
    private String fileName;
    private ClassMapVar[] ClassVariables;

    private String PackageName;
    private String Imports;
    private String Javadoc;
    private String Constructor;
    private String CreateTable;
    private String Insert;
    private String Update;
    private String Drop;
    private String Select;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getImports() {
        return Imports;
    }

    public void setImports(String imports) {
        Imports = imports;
    }

    public String getJavadoc() {
        return Javadoc;
    }

    public void setJavadoc(String javadoc) {
        Javadoc = javadoc;
    }

    public String getConstructor() {
        return Constructor;
    }

    public void setConstructor(String constructor) {
        Constructor = constructor;
    }

    public String getCreateTable() {
        return CreateTable;
    }

    public void setCreateTable(String createTable) {
        CreateTable = createTable;
    }

    public String getInsert() {
        return Insert;
    }

    public void setInsert(String insert) {
        Insert = insert;
    }

    public String getUpdate() {
        return Update;
    }

    public void setUpdate(String update) {
        Update = update;
    }

    public String getDrop() {
        return Drop;
    }

    public void setDrop(String drop) {
        Drop = drop;
    }

    public String getSelect() {
        return Select;
    }

    public void setSelect(String select) {
        Select = select;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ClassMapVar[] getClassVariables() {
        return ClassVariables;
    }

    public void setClassVariables(ClassMapVar[] classVariables) {
        ClassVariables = classVariables;
    }
}