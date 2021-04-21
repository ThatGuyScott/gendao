package io.datatech.gendao;

import io.datatech.gendao.common.ClassMap;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

public class GENDAO {

    final static Logger log = Logger.getLogger(GENDAO.class);

    public static void main(String[] args) {

        for (String arg : args)
            GENDAO.buildDAO(arg);

    }

    /** Main Execution point... where it all starts  */
    public static void buildDAO(String java_file_path) {

        /* Mark the start time of our program */
        long start_time = System.currentTimeMillis();

        try {

            /* Our .java File */
            File java_file = new File(java_file_path);

            /* This actually compiles the .java code to a .class file via external "javac {java_file_path.java}*/
            String compile_java_code = executeCommand("javac " + java_file_path);

            /* Create a file object of our new compiled .class file */
            String java_compile_path = java_file_path.replace(".java", ".class"); // <--- Rename to a .class
            File java_compile_file = new File(java_compile_path); // <--- Our comipled java file object
            java_compile_file.deleteOnExit(); // <--- Delete our .class file on exit

            /* Map our compiled class */
            ClassMap classMap = ClassMapFactory.buildClassMap(java_compile_file);

            /* Build a list of strings containing our DAO{name}.java, all that's left is to save it to a file */
            List<String> data = GENDAOFactory.build(classMap);

            /* Save data */
            String dao_file_name = "DAO_" + java_file.getName(); // <--- Create DAO filename
            String dao_file_path = java_file.getPath().replace(java_file.getName(), "").trim(); // <--- Create DAO file path
            File dao_file = new File(dao_file_path + dao_file_name); // <--- Create DAO file
            BufferedWriter w = new BufferedWriter(new FileWriter(dao_file)); // <--- Create Buffered Writer
            /* Iterate thru the ClassMap data List and save */
            for (String x : data) {
                w.write(x);
                w.newLine();
                w.flush();
            }
            w.close(); // <--- and close... we're done.

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(java_file_path + " DAO generated in " + (System.currentTimeMillis() - start_time) + "ms");

    }

    /* Used to compile our java class */
    public static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

}
