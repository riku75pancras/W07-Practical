import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseStorage {

    private String dbFileName;
    private String dataFilePath;
    private ArrayList<String> splitDataList= new ArrayList<>();
    private String wholeLine = null;
    private int numberOfLinesInInputFile = 0;
    private int numberOfAttributes = 12;

    public void callProgramFunctions(String dbFileName, String queryAction, String dataFilePath) throws IOException, SQLException {
        this.dbFileName = dbFileName;
        this.dataFilePath = dataFilePath;

        switch (queryAction) {
            case "create":
                createDatabaseForInputFile();
                break;

            case "query1":
                break;

            case "query2":
                break;

            case "query3":
                break;

            case "query4":
                break;

            default:
                System.out.println("The input arguments are incorrect");
        }
    }

    private void createDatabaseForInputFile() throws SQLException, IOException {
        String dbUrl = "jdbc:sqlite:" + dbFileName;
        Connection connection = DriverManager.getConnection(dbUrl);

        createTable(connection);

        readInputFile(connection);

        connection.close();
    }

    private void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS TitanicDataTable");
        statement.executeUpdate("CREATE TABLE TitanicDataTable (passengerID INT UNSIGNED, survived INT UNSIGNED, pClass INT UNSIGNED, " +
                "name VARCHAR(100), sex VARCHAR(100), age FLOAT, sibSP INT UNSIGNED, parch INT UNSIGNED, ticket VARCHAR(100), fare FLOAT, " +
                "cabin VARCHAR(100), embarked VARCHAR(100))");

        statement.close();
    }

    private void readInputFile(Connection connection) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
        Statement statement = connection.createStatement();
        if((wholeLine = reader.readLine()) != null) {
            List<String> attributeList = Arrays.asList(wholeLine.split(","));
        }

        while((wholeLine = reader.readLine()) != null){
            List<String> splitDataOneLine = Arrays.asList(wholeLine.split(","));
            String insertIntoDatabase = "INSERT INTO TitanicDataTable VALUES ('";
            for (int i = 0; i < splitDataOneLine.size(); i++){
                if (splitDataOneLine.get(i).equals("")){
                    splitDataOneLine.set(i, null);
                }
                if (i > 0) {
                    insertIntoDatabase += "', '";
                }
                insertIntoDatabase += splitDataOneLine.get(i);
            }
            insertIntoDatabase += ")";
            statement.executeUpdate(insertIntoDatabase);
            numberOfLinesInInputFile += 1;
        }
        statement.close();
    }



}

//eval:
//1. The manual typing of the attribute types on the table? is it more efficient to use from list?

//do we need to convert the string into varchar, int???
//move the arraylist to another method?