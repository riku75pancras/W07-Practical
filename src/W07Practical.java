import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

public class W07Practical {
    public static void main(String[] args){
        try{
            String dbFileName = args[0];
            String queryAction = args[1];

            DatabaseStorage databaseStorage = new DatabaseStorage();

            if(args.length == 2){
                String dataFilePath = null;
                databaseStorage.callProgramFunctions(dbFileName, queryAction, dataFilePath);
            }

            else if(args.length == 3) {
                if (!queryAction.equals("create")) {
                    throw new IllegalArgumentException("Usage: java -cp sqlite-jdbc.jar:. W07Practical <db_file> <action> [input_file]");
                } else {
                    String dataFilePath = args[2];
                    databaseStorage.callProgramFunctions(dbFileName, queryAction, dataFilePath);
                }
            } else {
                throw new IllegalArgumentException("Usage: java -cp sqlite-jdbc.jar:. W07Practical <db_file> <action> [input_file]");
            }

        } catch (IOException e){
            System.out.println("The input file is not in a correct format");
        } catch (SQLException e){
            System.out.println("The database does not exist");
        }
    }
}
