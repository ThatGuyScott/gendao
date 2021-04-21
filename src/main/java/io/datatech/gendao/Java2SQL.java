package io.datatech.gendao;

import org.apache.bcel.classfile.Field;

public class Java2SQL {

    /* We base our variables of Java types */
    public static class JAVA {

        public final static String BOOLEAN = "boolean";
        public final static String STRING = "String";
        public final static String INT = "int";
        public final static String LONG = "long";
        public final static String DOUBLE = "double";
        public final static String FLOAT = "float";
        public final static String TIMESTAMP = "Timestamp";
        public final static String TIME = "Time";
        public final static String DATE = "Date";
        public final static String BYTE_ARRAY = "byte[]";
        public final static String BYTE = "byte";

    }

    public static class SQL {

        public final static String BOOLEAN = "BOOLEAN";
        public final static String STRING = "VARCHAR";
        public final static String INT = "INT";
        public final static String LONG = "DOUBLE";
        public final static String DOUBLE = "DOUBLE";
        public final static String FLOAT = "FLOAT";
        public final static String TIMESTAMP = "TIMESTAMP";
        public final static String TIME = "TIME";
        public final static String DATE = "DATE";
        public final static String BYTE_ARRAY = "BLOB";
        public final static String BYTE = "TINYINT";

    }

    public static class UTIL {

        /**
         * This method is where we allow data types to be mapped... true or false
         */
        public static boolean isCompatibleType(Field x) {

            /* In order to map Java Variables to SQL Variables, they must be entered into this boolean, it acts as a boolean */
            if (x.toString().contains(" " + JAVA.STRING + " ")) return true; // <--- Needs the spaces
            if (x.toString().contains(Java2SQL.JAVA.BOOLEAN)) return true;
            if (x.toString().contains(Java2SQL.JAVA.DOUBLE)) return true;
            if (x.toString().contains(Java2SQL.JAVA.FLOAT)) return true;
            if (x.toString().contains(Java2SQL.JAVA.INT)) return true;
            if (x.toString().contains(JAVA.TIMESTAMP)) return true;
            if (x.toString().contains(JAVA.TIME)) return true;
            if (x.toString().contains(JAVA.DATE)) return true;
            if (x.toString().contains(JAVA.BYTE_ARRAY)) return true;
            if (x.toString().contains(JAVA.BYTE)) return true;

            return false;

        }

        /* This is where we shorten the variable name. we can't have things like, "private String x" We just need "String" */
        public static String shortenType(String s) {

            if (s.contains(JAVA.STRING)) return JAVA.STRING;
            if (s.contains(JAVA.BOOLEAN)) return JAVA.BOOLEAN;
            if (s.contains(JAVA.DOUBLE)) return JAVA.DOUBLE;
            if (s.contains(JAVA.FLOAT)) return JAVA.FLOAT;
            if (s.contains(JAVA.INT)) return JAVA.INT;
            if (s.contains(JAVA.LONG)) return JAVA.LONG;
            if (s.contains(JAVA.TIMESTAMP)) return JAVA.TIMESTAMP;
            if (s.contains(JAVA.TIME)) return JAVA.TIME;
            if (s.contains(JAVA.DATE)) return JAVA.DATE;
            if (s.contains(JAVA.BYTE_ARRAY)) return JAVA.BYTE_ARRAY;
            if (s.contains(JAVA.BYTE)) return JAVA.BYTE;

            return "";

        }

        /**
         * Method responsible for mapping Java Types to SQL
         * All the types that we can map are Mapped here
         * <p>
         * <p>
         * Found in Table Creation.
         */
        public static String mapSQL_STANDARD(String s) {

            if (s.contains(JAVA.STRING)) return SQL.STRING;
            if (s.contains(JAVA.BOOLEAN)) return SQL.BOOLEAN;
            if (s.contains(JAVA.DOUBLE)) return SQL.DOUBLE;
            if (s.contains(JAVA.FLOAT)) return SQL.FLOAT;
            if (s.contains(JAVA.INT)) return SQL.INT;
            if (s.contains(JAVA.TIMESTAMP)) return SQL.TIMESTAMP;
            if (s.contains(JAVA.TIME)) return SQL.TIME;
            if (s.contains(JAVA.DATE)) return SQL.DATE;
            if (s.contains(JAVA.BYTE_ARRAY)) return SQL.BYTE_ARRAY;
            if (s.contains(JAVA.BYTE)) return SQL.BYTE;

            return "";

        }

        public static String mapSQL_ResultSetType(String s) {

            if (s.contains(JAVA.STRING)) return "String";
            if (s.contains(JAVA.BOOLEAN)) return "Boolean";
            if (s.contains(JAVA.INT)) return "Int";
            if (s.contains(JAVA.LONG)) return "Long";
            if (s.contains(JAVA.DOUBLE)) return "Double";
            if (s.contains(JAVA.FLOAT)) return "Float";
            if (s.contains(JAVA.TIMESTAMP)) return "Timestamp";
            if (s.contains(JAVA.TIME)) return "Time";
            if (s.contains(JAVA.DATE)) return "Date";
            if (s.contains(JAVA.BYTE_ARRAY)) return "Blob";
            if (s.contains(JAVA.BYTE)) return "Byte";

            return "";
        }

    }

}
