import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class Programa {

	public static void main(String[] args) throws Exception {

		
		
		Connection conn = getDBConnection();
	

	//select id_cliente, sum(case tipo_operacion when 'C' then monto when 'D' then (monto * (-1)) end) as balance from reg1 group by id_cliente
	
        FileReader fr = new FileReader("C:\\Users\\Yo\\Desktop\\movimientos_1000000.txt");
		
		BufferedReader br = new BufferedReader(fr);
		
		
		long inicio =  System.currentTimeMillis();
		
		createDbUserTable();

	    ProcesoFile.cargarTabla(br, conn);
	     
	    long finProc =  System.currentTimeMillis();
			
		System.out.println("Tardo: " + (finProc - inicio) );
			
		
		
		
	}
	
	
	private static Connection getDBConnection() {

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			

		}


		Connection connection = null;

		try {

			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/postgres", "postgres",
					"giane");
			
			return connection;
			

		} catch (SQLException e) {

			e.printStackTrace();
			

		}

	

		return connection;

	}

	
	private static void createDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String createTableSQL = "create table Reg2 (id_cliente VARCHAR(255), monto REAL, tipo_operacion VARCHAR(1))";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(createTableSQL);
                        // execute the SQL stetement
			statement.execute(createTableSQL);
//Xmx2048m
			System.out.println("Table Reg2 is created!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
}