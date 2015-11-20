import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.UUID;



public class ProcesoFile {
	
	

	public static void cargarTabla(BufferedReader br, Connection conn) throws Exception {
		
		 String line;
		 int i = 0;
		 
		 String sql = "INSERT INTO REG2(id_cliente, monto, tipo_operacion) VALUES (?, ?, ?)";

		 
		 	final int batchSize = 2500;
		 	int count = 0;
		 	
		 	PreparedStatement ps = conn.prepareStatement(sql);

		 	while ((line = br.readLine()) != null) {
			    
		
		        

			 String[] partes = line.split(",");

		     String id = partes[0];
		     
		    
		     Double monto = Double.parseDouble(partes[1]);
		     
		     String c = partes[2];
		     
		     ps.setString(1, id);
		 	 ps.setDouble(2, monto);
		 	 ps.setString(3, c);
		 	 
		 	 ps.addBatch();
		     
		    
		     
		     if(++count % batchSize == 0) {
		    	 System.out.println("count:" + count);
		 	        ps.executeBatch();
		 	    }
		 	}
		 
		 ps.executeBatch(); 
		 ps.close();
		 
		     
		 }

	

}
