package io.datatech.gendao;

import io.datatech.gendao.common.ClassMap;
import io.datatech.gendao.common.ClassMapVar;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for parsing the .class file and mapping to a DataTech ClassMap data object
 */
public class ClassMapFactory {

    final static Logger log = Logger.getLogger(ClassMapFactory.class);

    /**
     * Builds a DataTech ClassMap data object from a COMPILED .class file
     *
     * @param java_compile Pass in a .class file
     * @return DataTech ClassMap data object
     */
    public static ClassMap buildClassMap(File java_compile) throws IOException, ClassNotFoundException {

        /* Create a URL Classloader Object based off our compiled .java file */
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{java_compile.toURI().toURL()});

        /* Create the Apache BCEL ClassParse using our compiled .java path */
        ClassParser parser = new ClassParser(java_compile.getAbsolutePath());

        /* Create a BCEL JavaClass parsed from our ClassParser */
        JavaClass jc = parser.parse();

        /* Create our DataTech ClassMap Data Object */
        ClassMap classMap = new ClassMap();

        /* Set the package name */
        classMap.setPackageName(jc.getPackageName());

        /* Set the class name */
        classMap.setClassName("DAO_" + java_compile.getName().replace(".class", "").trim());

        /* Set the Parent Class Name */
        classMap.setParentClassName(java_compile.getName().replace(".class", "").trim());

        /* Set the file name */
        classMap.setFileName(java_compile.getPath().replace(java_compile.getName(), "") + "DAO_" + java_compile.getName().replace(".class", "").trim() + ".java");

        /* Set the class variables */
        classMap.setClassVariables(util.mapVariables(jc));


        return classMap;
    }

    public static class util {

        /**
         * Maps our variables, getters & setters
         */
        private static ClassMapVar[] mapVariables(JavaClass jc) {

        /*
            Notes:
            The idea is to iterate thru the class file parsed Fields
            then thru the methods of that field and create the getter & setter mappings
            then return an array of those mappings
        */

            /* A List<> we'll convert to array later */
            List<ClassMapVar> data = new ArrayList<ClassMapVar>();

            /* Iterate thru our parsed Fields */
            for (Field x : jc.getFields()) {

                /* This lets us only map compatible types */
                if (Java2SQL.UTIL.isCompatibleType(x)) { // <--- CODE ENTRY POINT

                    /* DataTech data class for storing our getter / setter mappings */
                    ClassMapVar tmp = new ClassMapVar();

                    /* Set the name of the Variable */
                    tmp.setVar_name(x.getName());

                    /* Set the Type of the Variable... is it a String, int, Timestamp? */
                    tmp.setVar_type_java_full(x.toString());

                    /* This assigns the short version of the variable name */
                    tmp.setVar_type_java_short(Java2SQL.UTIL.shortenType(x.toString()));


                    tmp.setVar_type_sql_stanard(Java2SQL.UTIL.mapSQL_STANDARD(x.toString()));
                    /* GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- */
                    /* GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- */

                    /* Now we iterate thru that field/variables methods and assign the getters and setter mappings */

                    for (Method gm : jc.getMethods()) {

                        /* We must be a setter */
                        if (gm.getName().substring(0, 3).contains("get")) {
                            String ts = gm.getName().toLowerCase().replaceFirst("get", "").trim();
                            if (ts.matches(x.getName().toLowerCase()))
                                tmp.setVar_getter(gm.getName());

                        }

                        /* We must be a getter */
                        if (gm.getName().substring(0, 3).contains("set")) {
                            String ts = gm.getName().toLowerCase().replaceFirst("set", "").trim();
                            if (ts.matches(x.getName().toLowerCase()))
                                tmp.setVar_setter(gm.getName());
                        }

                        /* We must be a boolean */
                        if (gm.getName().substring(0, 2).contains("is")) {
                            String ts = gm.getName().toLowerCase().replaceFirst("is", "").trim();
                            if (ts.matches(x.getName().toLowerCase()))
                                tmp.setVar_getter(gm.getName());
                        }

                    }
                    /* GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- */
                    /* GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- GETTERS & SETTERS --- */

                    /* add to our List<> */
                    data.add(tmp);
                }
            }

            /* convert to array */
            ClassMapVar[] return_data = new ClassMapVar[data.size()];
            return data.toArray(return_data);
        }

        public static JavaClass parse(File f) throws IOException {

            /* Create a URL Classloader Object based off our compiled .java file */
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{f.toURI().toURL()});

            /* Create the Apache BCEL ClassParse using our compiled .java path */
            ClassParser parser = new ClassParser(f.getAbsolutePath());

            /* Create a BCEL JavaClass parsed from our ClassParser */
            JavaClass j = parser.parse();

            return j;

        }

    }

    private JavaClass j;

    public ClassMapFactory(File f) throws IOException {
        this.j = util.parse(f);
    }


    // --------------- Getters & Setters --------- //

    public JavaClass javaClass() {
        return j;
    }


}
