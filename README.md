# GENDAO

<p>Java POJO ==> SQL Object mapping</p>

<hr>
<center>

### This documentation is very basic as this project has been deprecated and not maintained for several years.

### I highly recommend using Spring Data JPA to access your database
</center>

<hr>

#### What does this library do?
Generates Java code to interact with an H2 database system using a simple java pojo.

##### How does it work?

Start with a simple pojo. Must have an integer of ID for the primary key. Must have getters and setters.

##### Available Java to SQL types:

boolean, String, int, long, double, float, Timestamp, Time, Date, byte[], byte

### Basic Example

<p>We'll start with a simple Student java pojo</p>

    package com.sandbox;
    
    public class Student {
    
        private int ID;
        private String firstName;
        private String lastName;
        private int age;
    
        public int getID() {
            return ID;
        }
    
        public void setID(int ID) {
            this.ID = ID;
        }
    
        public String getFirstName() {
            return firstName;
        }
    
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    
        public String getLastName() {
            return lastName;
        }
    
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    
        public int getAge() {
            return age;
        }
    
        public void setAge(int age) {
            this.age = age;
        }
    }

<p>Next we'll generate our dao class object for database interaction</p> 

    java -jar gendao.jar Student.java

This will generate the following code.

    package com.sandbox;

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    
    public class DAO_Student {
    
        private Connection c;
        private util util;
    
        /**
         * <center><p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p><p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p><p>This class may not contain all your variables but should be a good starting point.Be sure to review the code if this class if not working correctly.<p>Email bug/suggestions to <b><i><u>angelwilliamscott@gmail.com</u></i></b></p><p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p><p><b>*** WARNING *** THIS CLASS MAY NOT BE COMPLETE *** WARNING ***</b></p></center><p>Known Variables:</p><p>Var Name: [<b>ID</b>] Java Type: [<i>int</i> SQL Type: [<i>INT</i>]] <p>Var Name: [<b>firstName</b>] Java Type: [<i>String</i> SQL Type: [<i>VARCHAR</i>]] <p>Var Name: [<b>lastName</b>] Java Type: [<i>String</i> SQL Type: [<i>VARCHAR</i>]] <p>Var Name: [<b>age</b>] Java Type: [<i>int</i> SQL Type: [<i>INT</i>]]
         */
        public DAO_Student(Connection c) {
            this.c = c;
            init();
        }
    
        private void init() {
            util = new util();
        }
    
        /**
         * Create a new Student Table containing * <p>Name:<b>ID</b> Type: int</p>* <p>Name:<b>firstName</b> Type: String</p>* <p>Name:<b>lastName</b> Type: String</p>* <p>Name:<b>age</b> Type: int</p>
         */
        public void CREATE_TABLE() {
            try {
                c.prepareStatement("CREATE TABLE IF NOT EXISTS STUDENT(ID INT AUTO_INCREMENT PRIMARY KEY,firstName VARCHAR,lastName VARCHAR,age INT);").execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        /**
         * Drops Student Table from the database
         */
        public void DROP_TABLE() {
            try {
                c.prepareStatement("DROP TABLE IF EXISTS STUDENT").execute();
            } catch (SQLException e) {
            }
        }
    
        /**
         * @param o Insert a Student Object to the database
         */
        public void INSERT(Student o) {
            try {
                PreparedStatement s = c.prepareStatement("INSERT INTO STUDENT(firstName,lastName,age) VALUES('" + o.getFirstName() + "','" + o.getLastName() + "','" + o.getAge() + "');");
                s.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        /**
         * @param o Updates the database with the given Student object
         */
        public void UPDATE(Student o) {
            try {
                PreparedStatement s = c.prepareStatement("UPDATE STUDENT SET firstName='" + o.getFirstName() + "',lastName='" + o.getLastName() + "',age='" + o.getAge() + "' WHERE ID=" + o.getID() + ";");
                s.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        /**
         * @param SQL <p>Pass in an SQL query statement and get a List containing your results</p>
         */
        public List<Student> SELECT(String SQL) {
            List<Student> data = new ArrayList();
            ResultSet rs = null;
            try {
                rs = c.prepareStatement(SQL).executeQuery();
                while (rs.next()) {
                    Student o = new Student();
                    try {
                        o.setID(rs.getInt("ID"));
                    } catch (Exception e) {
                    }
                    try {
                        o.setFirstName(rs.getString("FIRSTNAME"));
                    } catch (Exception e) {
                    }
                    try {
                        o.setLastName(rs.getString("LASTNAME"));
                    } catch (Exception e) {
                    }
                    try {
                        o.setAge(rs.getInt("AGE"));
                    } catch (Exception e) {
                    }
                    data.add(o);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return data;
        }
    
        public void EXECUTE(String SQL) {
            try {
                c.prepareStatement(SQL).execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        public util UTIL() {
            return util;
        }
    
        public class util {
    
            /**
             * @param f Pass in a local file to be converted to a byte[]
             */
            public byte[] read_FILE2BYTE(File f) throws IOException {
                FileInputStream fileInputStream = new FileInputStream(f);
                byte[] data = new byte[(int) f.length()];
                fileInputStream.read(data);
                return data;
            }
    
            /**
             * @param file_path file path where you want the file to be saved@param data This is the byte array you want to save
             */
            public void save_BYTE2FILE(String file_path, byte[] data) throws IOException {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(file_path));
                fileOutputStream.write(data);
                fileOutputStream.close();
            }
        }
    // === END OF CLASS === END OF CLASS === END OF CLASS ===
    }

All that needs to be done now is pass in the Connection object to save, update, and run SQL queries. You will need to change the package info for your generated
file as it will put your generated code in a directory called dao. example: com.sandbox.dao

I tend to create a Class called MasterDAO that is used to provide access to these generated class files thru static methods.

    public class MasterDAO {

        public MasterDAO(){
            init();
        }
    
        public static Connection getConnection() {
    
            Connection c = null;
    
            try {
                c = HikariCPDataSource.getConnection();
                log.debug("Hikari Connection established. " + new Timestamp(System.currentTimeMillis()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return c;
        }
        
        public static void closeConnection(Connection c) {
            try {
                c.close();
                log.debug("database connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        public void init(){
            Connection c = getConnection();
            Student(c).CREATE_TABLE();
        }
    
        public static DAO_Student Student(Connection c){
            return new DAO_Student(c);
        }

    }
