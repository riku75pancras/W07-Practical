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

        String dbUrl = "jdbc:sqlite:" + dbFileName;
        Connection connection = DriverManager.getConnection(dbUrl);

        switch (queryAction) {
            case "create":
                createTable(connection);
                readInputFile(connection);
                connection.close();
                break;

            case "query1":
                listAllRecords(connection);
                break;

            case "query2":
                printTotalSurvivors(connection);
                break;

            case "query3":
                printQuery3(connection);
                break;

            case "query4":
                break;

            default:
                System.out.println("The input arguments are incorrect");
        }
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
        wholeLine = reader.readLine();
        List<String> attributesList = Arrays.asList(wholeLine.split(","));
        //to print out the key names

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
            insertIntoDatabase += "')";
            statement.executeUpdate(insertIntoDatabase);
            numberOfLinesInInputFile ++;
        }
        System.out.println("OK");
        statement.close();
    }

    private void listAllRecords(Connection connection)throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM TitanicDataTable");

        while (resultSet.next()){
            int passengerID = resultSet.getInt("passengerID");
            int survived = resultSet.getInt("survived");
            int pClass = resultSet.getInt("pClass");
            String name = resultSet.getString("name");
            String sex = resultSet.getString("sex");
            float age = resultSet.getFloat("age");
            int sibSP = resultSet.getInt("sibSP");
            int parch = resultSet.getInt("parch");
            String ticket = resultSet.getString("ticket");
            float fare = resultSet.getFloat("fare");
            String cabin = resultSet.getString("cabin");
            String embarked = resultSet.getString("embarked");

            StringBuilder sb = new StringBuilder();
            String separator = ", ";

            sb.append(passengerID);
            sb.append(separator);
            sb.append(survived);
            sb.append(separator);
            sb.append(pClass);
            sb.append(separator);
            sb.append(name);
            sb.append(separator);
            sb.append(sex);
            sb.append(separator);
            sb.append(age);
            sb.append(separator);
            sb.append(sibSP);
            sb.append(separator);
            sb.append(parch);
            sb.append(separator);
            sb.append(ticket);
            sb.append(separator);
            sb.append(fare);
            sb.append(separator);
            sb.append(cabin);
            sb.append(separator);
            sb.append(embarked);

            String recordInLine = sb.toString();
            System.out.println(recordInLine);

        }
    }

    private void printTotalSurvivors(Connection connection)throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT survived FROM TitanicDataTable");
        int totalSurvivors = 0;
        while(resultSet.next()){
            totalSurvivors += resultSet.getInt("survived");
        }

        System.out.println("Number of Survivors");
        System.out.println(totalSurvivors);

    }

    private void printQuery3(Connection connection)throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT pClass, survived FROM TitanicDataTable");
        int [][] individualCount = new int[3][2];

        while(resultSet.next()){
            if (resultSet.getInt("pClass") == 1){
                if (resultSet.getInt("survived") == 0)
                    individualCount[0][0] ++;
                else
                    individualCount[0][1] ++;
            }

            if (resultSet.getInt("pClass") == 2){
                if (resultSet.getInt("survived") == 0)
                    individualCount[1][0] ++;
                else
                    individualCount[1][1] ++;
            }

            if (resultSet.getInt("pClass") == 3){
                if (resultSet.getInt("survived") == 0)
                    individualCount[2][0] ++;
                else
                    individualCount[2][1] ++;
            }
        }

        System.out.println("pClass, survived, count");

        for (int i = 0; i < individualCount.length; i++){
            for (int j = 0; j < individualCount[i].length; j++){
                System.out.println((i+1) + ", " + j + ", " + individualCount[i][j]);
            }
        }

    }

}

//eval:
//1. The manual typing of the attribute types on the table? is it more efficient to use from list?
//2. if the data gets big, cannot sort survived or not easily

//move the arraylist to another method?

//embark might be empty
//null in method or create table?
//result.next calls the next line
