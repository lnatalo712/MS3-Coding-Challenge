import java.sql.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MS3 {
      
      // Function used to connect to the SQLite database throughout the program
      private Connection connect() {
           // SQLite connection string
           String url = "jdbc:sqlite:MS3.db";
           Connection c = null;
           try {
               c = DriverManager.getConnection(url);
           } catch (Exception e) {
               System.out.println(e.getMessage());
           }
           return c;
       }
      
      // Function used to insert data into the SQLite table
      public void insert(String A, String B, String C, String D, String E, String F, String G, String H, String I, String J) {
           String sql = "INSERT INTO validRecords(A, B, C, D, E, F, G, H, I, J) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
           
           // Establish the database connection and exexute the SQL code
           // Each string parameter is connected to a 'VALUES' question mark which corresponds to a 'validRecords' column 
           try (Connection c = this.connect();
                PreparedStatement pstmt = c.prepareStatement(sql)) {
               pstmt.setString(1, A);
               pstmt.setString(2, B);
               pstmt.setString(3, C);
               pstmt.setString(4, D);
               pstmt.setString(5, E);
               pstmt.setString(6, F);
               pstmt.setString(7, G);
               pstmt.setString(8, H);
               pstmt.setString(9, I);
               pstmt.setString(10, J);
               pstmt.executeUpdate();
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
       }
       
       // Function used to create the table 'validRecords' with columns A through J
       public void createTable() {
            String sql = "CREATE TABLE IF NOT EXISTS validRecords (\n"
                + "    A text NOT NULL,\n"
                + "    B text NOT NULL,\n"
                + "    C text PRIMARY KEY NOT NULL,\n"
                + "    D text NOT NULL,\n"
                + "    E blob NOT NULL,\n"
                + "    F text NOT NULL,\n"
                + "    G text NOT NULL,\n"
                + "    H text NOT NULL,\n"
                + "    I text NOT NULL,\n"
                + "    J text NOT NULL\n"
                + ");";
            
            // Establish the database connection and exexute the SQL code
            try (Connection c = this.connect();
                 Statement stmt = c.createStatement()) {
               stmt.execute(sql);
               System.out.println("Table created.");
            } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
       }
       
       // Function used to drop the validRecords table
       public void dropTable() {
            String sql = "DROP TABLE IF EXISTS 'validRecords'";
            
            // Establish the database connection and exexute the SQL code
            try (Connection c = this.connect();
                 PreparedStatement pstmt = c.prepareStatement(sql)){
               pstmt.executeUpdate();
               System.out.println("Table dropped.");               
            } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
       }

  public static void main( String args[] ) {
        MS3 app = new MS3();
        
        // Initialize file locations, parameters, and counters
        String csvFile = "ms3Interview.csv";
        // 'csvSplitBy' will split the csv data file by commas but won't seperate cells that have commas as part of the data
        String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        String line = "";
        BufferedReader br = null;
        int count = 0;
        int good = 0;
        int bad = 0;
        
        // Drop 'validRecords' and create it again so that its empty
        app.dropTable();
        app.createTable();

        try {
            // Initialize the csv reader, csv writer, and string builder
            br = new BufferedReader(new FileReader(csvFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter("rejected-bad.csv"));
            StringBuilder sb = new StringBuilder();
            
            // While loop to read entire unsorted csv      
            while ((line = br.readLine()) != null) {
               
                // Use comma as separator
                String[] data = line.split(cvsSplitBy, -1);
                
                // Filter out the bad data by checking if there are too many or too little cells per row or if any cells are null
                if ((data.length != 10) || data[0].isEmpty()  || data[1].isEmpty() || data[2].isEmpty() || data[3].isEmpty() || data[4].isEmpty() || data[5].isEmpty() || data[6].isEmpty() || data[7].isEmpty() || data[8].isEmpty() || data[9].isEmpty()) {
                  // Append strings from the 'data' array
                  for (String element : data) {
                     sb.append(element);
                     sb.append(",");
                  }
                  sb.append("\n");
                  // Write the bad data to the 'rejected-bad.csv' file and increment the failed data counter
                  bw.write(sb.toString());
                  sb.setLength(0);
                  bad ++;
                } else {
                  // Insert the good data into the SQLite table and increment the succesful data counter
                  app.insert(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9]);
                  good ++;
                }
               // Increment the total records received counter
               count ++;
            }
            
        bw.close();
        // Catch and print file not found and IO exceptions  
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the reader when the csv file is completed
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
      
      // Print the total records processed, total successful records, and total failed records
      System.out.println("Total records recieved: " + (count - 2 )+ ".");
      System.out.println("Total records successful: " + (good - 1) + ".");
      System.out.println("Total records failed: " + (bad - 1) + ".");
    }
}





