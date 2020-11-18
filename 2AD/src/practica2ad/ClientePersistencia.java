package practica2ad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class ClientePersistencia {

	public static int createCliente(String nombre, String apellidos, String email, String dni, String clave) {
		Connection con = conectar();
		int respues = 0;
		try 
		{

			String cadenaSQL = "INSERT INTO clientes (nombre,apellidos,email,DNI,clave) "
					+ "VALUES ('" + nombre + "','"+ apellidos +"','"+ email +"','"+ dni +"','"+ clave +"')";
			//mostrar la cadena por pantalla
			System.out.println(cadenaSQL);
			Statement sta = con.createStatement();
			//ejecuta la cadena en la base de datos
			sta.executeUpdate(cadenaSQL);



			//sentencia para buscar la id
			String cadenaSQL2 = "SELECT idCliente FROM clientes where apellidos= '"+apellidos+"'";
			//ejecuta la sentencia 2
			ResultSet rs= sta.executeQuery(cadenaSQL2);


			while (rs.next()) {
				respues =rs.getInt("idCliente");

			}


			System.out.println("la id es "+ respues);
			//cierra rs
			rs.close();
			//cierra el Statement
			sta.close();
			desconectar(con);
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();

		}

		return respues;

		/* Devuelve el id del nuevo cliente */
	}

	public static String readCliente(int idCliente, String campo) {
		//sentencia 
		String sqlSelect = "SELECT "+campo +" FROM clientes where idCliente= "+idCliente;
		String devolver = null;
		try {
			Connection con = conectar();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				devolver= rs.getString(campo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		return devolver;
		/* Devuelve el valor de la columna "campo" del cliente identificado por "idCliente" */
	}

	public static boolean updateCliente(int idCliente, String campo, String nuevoValor) {
		Boolean respuesta = false;
		String sql= "update clientes set "+campo+" ='"+nuevoValor+"'";
		System.out.println(sql);
		try 
		{
			Connection con = conectar();
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			sta.executeUpdate(sql);
			sta.close();
			respuesta = true;
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Update");
			ex.printStackTrace();
			respuesta = false;
		}
		if (respuesta = true) {
			System.out.println("Cliente actualizado");
		}else {
			System.out.println("Cliente no encontrado o no se a podido modificar");
		}

		return respuesta;

		/* Actualiza el valor de la columna "campo" del cliente identificado por "idCliente". Devuelve true si se ha logrado actualizar. */
	}

	public static boolean deleteCliente(int idCliente) {
		Boolean respuesta = false;
		String sql = "DELETE FROM Clientes WHERE idCliente = " + idCliente;
		System.out.println(sql);
		try 
		{
			Connection con = conectar();
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			sta.executeUpdate(sql);
			sta.close();
			respuesta = true;
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();

		}
		if (respuesta = true) {
			System.out.println("Cliente eliminado");
		}else {
			System.out.println("Cliente no encontrado o no se a podido modificar");
		}

		return respuesta;
		/* Elimina el cliente identificado por "idCliente". Devuelve true si se ha logrado eliminar. */
	}

	//conectar a base de datos



	public static Connection conectar()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/hotel?autoReconnect=true&useSSL=false";
		String user = "root2";
		String password = "12345678A";
		Connection con = null;
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, user, password);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}

		return con;
	}
	public static void desconectar(Connection con)
	{
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}
}
