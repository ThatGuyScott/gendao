package io.datatech.gendao;


import io.datatech.gendao.common.ClassMap;
import io.datatech.gendao.common.ClassMapVar;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for taking a DataTech ClassMap Object and building
 * a String List containing our .java dao file.
 */
public class GENDAOFactory {

    /**
     * @param map Takes a ClassMap data object and builds a List containing our dao .java data
     */
    public static List<String> build(ClassMap map) {

        /* So the .java generated code is stored in a List of strings.
        Each component of the class is added independently via the subclassed build methods
        We return the List to be saved */

        /* This is where our .java generated code goes */
        List<String> data = new ArrayList<String>();

        /* Add each compoentent of our .java class  */
        data.add(build.PackageName(map));
        data.add(build.Imports(map));
        data.add(build.StartClass(map));
        data.add(build.DeclareVariables(map));
        data.add(build.JavaDoc(map));
        data.add(build.Constructor(map));
        data.add(build.Init(map));
        data.add(build.Create_Tables(map));
        data.add(build.Drop_Tables(map));
        data.add(build.Insert(map));
        data.add(build.Update(map));
        data.add(build.Select(map));

        data.add(build.Execute());

        data.add(build.Getters(map));
        data.add(build.Setters(map));

        /* Build our util subclass */
        data.add(build.util.UtilClassOpen());
        data.add(build.util.FILE2BYTE());
        data.add(build.util.BYTE2FILE());
        data.add("}");

        /* Close out our class file with the closing bracket */
        data.add("// === END OF CLASS === END OF CLASS === END OF CLASS === \n}");

        return data;
    }

    /**
     * Each method is responsible for generating the java code for its said name
     */
    public static class build {

        public static String PackageName(ClassMap map) {
            return "package " + map.getPackageName() + ";";
        }

        public static String Imports(ClassMap map) {
            return "import " + map.getPackageName() + "." + map.getParentClassName() + ";" +
                    "import java.io.ByteArrayInputStream;" +
                    "import java.sql.*;" +
//                    "import java.sql.Timestamp;" +
//                    "import java.sql.Connection;" +
//                    "import java.sql.SQLException;" +
//                    "import java.sql.ResultSet;" +
                    "import java.util.ArrayList;" +
                    "import java.util.List;" +
                    "import java.io.ByteArrayInputStream;" +
                    "import java.io.File;" +
                    "import java.io.FileInputStream;" +
                    "import java.io.FileOutputStream;" +
                    "import java.io.IOException;";
        }

        public static String StartClass(ClassMap map) {
            StringBuffer s = new StringBuffer();

            s.append("public class " + map.getClassName() + " {\n");


            return s.toString();
        }

        public static String DeclareVariables(ClassMap map) {
            StringBuffer s = new StringBuffer();
            s.append("private Connection c;");
            s.append("private util util;");

            return s.toString();
        }

        public static String JavaDoc(ClassMap map) {
            StringBuffer s = new StringBuffer();
            /* A basic java doc of the dao object, this really needs to be updated */
            s.append("/**" +
                    "<center><p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p>" +
                    "<p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p>" +
                    "<p>This class may not contain all your variables but should be a good starting point." +
                    "Be sure to review the code if this class if not working correctly." +
                    "<p>Email bug/suggestions to <b><i><u>angelwilliamscott@gmail.com</u></i></b></p>" +
                    "<p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p>" +
                    "<p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p></center>" +
                    "<p>Known Variables:</p>");

            for (ClassMapVar v : map.getClassVariables()) {
//                s.append("<p>Var Name: [<b>" + v.getVar_name() + "</b>] Java Type: [<i>" + v.getVar_type_java_short() + "</i>] SQL Type: [<i>" + v.get() + "</i>] Getter: [<i>" + v.getGETTER_METHOD() + "</i>] Setter: [<i>" + v.getSETTER_METHOD() + "</i>] </p>");
                s.append("<p>Var Name: [<b>" + v.getVar_name() + "</b>] Java Type: [<i>" + v.getVar_type_java_short() + "</i> SQL Type: [<i>" + v.getVar_type_sql() + "</i>]] ");
            }
            s.append("*/"); // <--- close our comments

            return s.toString();
        }

        public static String Constructor(ClassMap map) {

            StringBuffer s = new StringBuffer();

            /* Start Contructor */
            s.append("public " + map.getClassName() + "(Connection c){");
            s.append("this.c = c;");
            s.append("init();");
            s.append("}");

            return s.toString();
        }

        public static String Init(ClassMap map) {

            StringBuffer s = new StringBuffer();

            s.append("private void init(){");
            s.append("util = new util();");
            s.append("}");

            return s.toString();

        }

        public static String Create_Tables(ClassMap map) {

            StringBuffer s = new StringBuffer();

            s.append("/** Create a new " + map.getParentClassName() + " Table containing ");
            for (ClassMapVar x : map.getClassVariables()) {
                s.append("* <p>Name:<b>" + x.getVar_name() + "</b> Type: " + x.getVar_type_java_short() + "</p>");
            }
            s.append("*/");
                /* Create the first part of our create table method */
            s.append("public void CREATE_TABLE(){");
            s.append("try {");
            s.append("c.prepareStatement(\"CREATE TABLE IF NOT EXISTS " + map.getParentClassName().toUpperCase() + "(");
            s.append("ID INT AUTO_INCREMENT PRIMARY KEY");

            /* Iterate thru our variables and create the neccessary table entries */
            for (ClassMapVar x : map.getClassVariables()) {
                if (!x.getVar_name().matches("ID"))
                    s.append("," + x.getVar_name() + " " + x.getVar_type_sql());
            }

            /* close out our method */
            s.append(");\").execute();} catch (SQLException e) { e.printStackTrace(); }}");

            return s.toString();
        }

        public static String Drop_Tables(ClassMap map) {

            StringBuffer s = new StringBuffer();
            s.append("/** Drops " + map.getParentClassName() + " Table from the database */");
            s.append("public void DROP_TABLE(){");
            s.append("try { c.prepareStatement(\"DROP TABLE IF EXISTS " + map.getParentClassName().toUpperCase() + "\").execute();} catch (SQLException e) {}} ");
            return s.toString();
        }

        public static String Insert(ClassMap map) {

            StringBuffer s = new StringBuffer();
            s.append("/**@param o Insert a " + map.getParentClassName() + " Object to the database */");
            s.append("public void INSERT(" + map.getParentClassName() + " o){");
            s.append("try {");
            s.append("PreparedStatement s = c.prepareStatement(\"INSERT INTO " + map.getParentClassName().toUpperCase() + "(");

            /* We iterate thru our variables grabbing all the names */
            for (ClassMapVar x : map.getClassVariables())
                if (!x.getVar_name().matches("ID")) // <--- Ommit the ID as it's already created
                    s.append("," + x.getVar_name());

            s.append(") VALUES(");

            /* Iterate thru the tmp list of names and add commas if not at the end */
            int counter = 2;
            int byte_array_counter = 0;
            List<String> byte_array_gettername = new ArrayList<String>();
            for (ClassMapVar x : map.getClassVariables()) {
                if (!x.getVar_name().matches("ID")) {

                    /* Insert a Question mark vs the getter */
                    if (x.getVar_type_sql().matches(Java2SQL.SQL.BYTE_ARRAY)) {
                        s.append("?");
                        byte_array_gettername.add(x.getVar_getter());
                        byte_array_counter++;
                    } else {
                        s.append("'\"+ o." + x.getVar_getter() + "() +\"'");
                    }

                    if (counter != map.getClassVariables().length) s.append(",");
                    counter++;
                }
            }

            s.append(");\");");

            /* If we have byte arrays */
            if (byte_array_counter > 0) {
                for (int i = 0; i < byte_array_counter; i++) {
                    s.append("s.setBinaryStream(" + (i + 1) + ", new ByteArrayInputStream(o." + byte_array_gettername.get(i) + "()));");
                }
            }


            s.append("s.execute();");
            s.append("}catch(SQLException e){ e.printStackTrace(); }}");

            return s.toString().replaceFirst(",", "").trim();
        }

        public static String Update(ClassMap map) {

            /*
                Notes:

                PreparedStatement s = c.prepareStatement("UPDATE DummyObject SET dummy_title='" + o.getDummy_title() + "',dummy_double='" + o.getDummy_double() + "',dummy_boolean='" + o.isDummy_boolean() + "',dummy_byte='" + o.getDummy_byte() + "',dummy_date='" + o.getDummy_date() + "',dummy_data=? WHERE ID=" + o.getID() + ";");
                s.setBinaryStream(1, new ByteArrayInputStream(o.getDummy_data()));
                s.execute();

            */

            StringBuffer s = new StringBuffer();
            s.append("/**@param o Updates the database with the given " + map.getParentClassName() + " object */");
            s.append("public void UPDATE(" + map.getParentClassName() + " o){");
            s.append("try{");
            s.append("PreparedStatement s = c.prepareStatement(\"UPDATE " + map.getParentClassName().toUpperCase() + " SET ");

            int counter = 2;
            int byte_array_counter = 0;
            List<String> byte_array_gettername = new ArrayList<String>();
            for (ClassMapVar x : map.getClassVariables()) {
                if (!x.getVar_name().matches("ID")) {

                    /* Insert a Question mark vs the getter */
                    if (x.getVar_type_sql().matches(Java2SQL.SQL.BYTE_ARRAY)) {
                        s.append(x.getVar_name() + "=?");
                        byte_array_gettername.add(x.getVar_getter());
                        byte_array_counter++;
                    } else {
                        s.append(x.getVar_name() + "='\" + o." + x.getVar_getter() + "() + \"'");
                    }

                    if (counter != map.getClassVariables().length) s.append(",");
                    counter++;


                }
            }
            s.append(" WHERE ID=\" + o.getID() + \"");
            s.append(";\");");

                        /* If we have byte arrays */
            if (byte_array_counter > 0) {
                for (int i = 0; i < byte_array_counter; i++) {
                    s.append("s.setBinaryStream(" + (i + 1) + ", new ByteArrayInputStream(o." + byte_array_gettername.get(i) + "()));");
                }
            }

            s.append("s.execute(); ");
            s.append("} catch(SQLException e){ e.printStackTrace(); }}");

            return s.toString();
        }

        public static String Select(ClassMap map) {

            StringBuffer s = new StringBuffer();

            /* Start the methods codee */
            s.append("/** @param SQL <p>Pass in an SQL query statement and get a List containing your results</p> */");
            s.append("public List<" + map.getParentClassName() + "> SELECT(String SQL){" +
                    "List<" + map.getParentClassName() + "> data = new ArrayList();" +
                    "ResultSet rs = null;" +
                    "try {" +
                    "rs = c.prepareStatement(SQL).executeQuery();" +
                    "while(rs.next()){" +
                    map.getParentClassName() + " o = new " + map.getParentClassName() + "();");

            /* Itereate thru our variables and set accordingly */
            for (ClassMapVar x : map.getClassVariables()) {
                s.append("try{");
                /* This is the best I can come up with at the moment. This could be better. The next step would be to map the rs.get portion to a variable in the ClassMapVar or rethink the design */
                if (Java2SQL.UTIL.mapSQL_ResultSetType(x.getVar_type_java_short()).matches("Blob")) {
                    s.append("o." + x.getVar_setter() + "(rs.getBlob(\"" + x.getVar_name().toUpperCase() + "\").getBytes(0, (int) rs.getBlob(\"" + x.getVar_name().toUpperCase() + "\").length()));");
                } else {
                    s.append("o." + x.getVar_setter() + "(rs.get" + Java2SQL.UTIL.mapSQL_ResultSetType(x.getVar_type_java_short()) + "(\"" + x.getVar_name().toUpperCase() + "\"));");
                }
                s.append("}catch(Exception e){}");


            }
            s.append("data.add(o); }} catch(SQLException e) { e.printStackTrace(); } return data; }");

            return s.toString();
        }

        public static String Execute() {

            StringBuffer s = new StringBuffer();

            s.append("public void EXECUTE(String SQL){" +
                    "        try {\n" +
                    "            c.prepareStatement(SQL).execute();\n" +
                    "        } catch (SQLException e) {\n" +
                    "            e.printStackTrace();\n" +
                    "        }}\n");

            return s.toString();
        }

        public static String Getters(ClassMap map) {
            StringBuffer s = new StringBuffer();

            s.append("public util UTIL(){");
            s.append("return util;");
            s.append("}");

            return s.toString();
        }

        public static String Setters(ClassMap map) {
            StringBuffer s = new StringBuffer();

            return s.toString();
        }

        public static class util {

            public static String UtilClassOpen() {
                return "public class util {\n";
            }

            /**
             * File to Byte Array
             */
            public static String FILE2BYTE() {

                StringBuffer s = new StringBuffer();

                s.append("/** @param f Pass in a local file to be converted to a byte[] */");
                s.append("public byte[] read_FILE2BYTE(File f) throws IOException {");
                s.append("FileInputStream fileInputStream = new FileInputStream(f);");
                s.append("byte[] data = new byte[(int) f.length()];");
                s.append("fileInputStream.read(data);");
                s.append("return data;");
                s.append("}");

                return s.toString();
            }

            /**
             * Byte array to saved file
             */
            public static String BYTE2FILE() {
                StringBuffer s = new StringBuffer();
                s.append("/** @param file_path file path where you want the file to be saved" +
                        "@param data This is the byte array you want to save */");
                s.append("public void save_BYTE2FILE(String file_path, byte[] data) throws IOException {");
                s.append("FileOutputStream fileOutputStream = new FileOutputStream(new File(file_path));");
                s.append("fileOutputStream.write(data);");
                s.append("fileOutputStream.close();");
                s.append("}");
                return s.toString();
            }

        }

    }

}
