import java.io.IOException;
import java.sql.SQLException;

public class Testing {
	public static void main(String[] args) throws IOException, SQLException {
		new Stock_Info_System().generateSlip("Nyan",5);
	}
}
