package conexaojdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class SingleConnection {

	private static String url = "jdbc:postgresql://localhost:5432/postgres";
	private static String password = "postgres";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				System.out.println("Conexão realizada com sucesso"); //teste para ver se a conexão deu certo
			} else {
				System.out.println("Deu erro");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection()
	{
		return connection;
	}
	
	
}
