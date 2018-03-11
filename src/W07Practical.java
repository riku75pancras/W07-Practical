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

            }

            if(args.length == 3) {
                if (!queryAction.equals("create")) {
                    System.out.println("The program can accept 3 arguments only when the 2nd argument is create");
                } else {
                    String dataFilePath = args[2];
                    databaseStorage.callProgramFunctions(dbFileName, queryAction, dataFilePath);
                }
            }

            else {
                throw new IllegalArgumentException("The program can accept only 2 or 3 arguments");
            }



        } catch (IOException e){
            System.out.println("The input file is not in a correct format");
        } catch (SQLException e){
            System.out.println("The database does not exist");
        }
    }
}
