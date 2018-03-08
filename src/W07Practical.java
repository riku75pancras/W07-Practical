import java.io.IOException;
import java.sql.SQLException;

public class W07Practical {
    public static void main(String[] args){
        try{
            String fileNameOfDB = args[0];
            String queryAction = args[1];


        } catch (IOException e){
            System.out.println("The input file is not in a correct format");
        } catch (SQLException e){
            System.out.println("The database doesn't exist or it cannot be accessed");
        }
    }
}
