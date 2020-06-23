package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {

	private Connection connection;
	
	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(Userposjava userposjava)
	{
		try {
		String sql = "INSERT INTO userposjava (nome, email) VALUES (?,?)";
		PreparedStatement insert = connection.prepareStatement(sql);
		insert.setString(1, userposjava.getNome());
		insert.setString(2, userposjava.getEmail());
		insert.execute();
		connection.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Userposjava> listar(){
		List<Userposjava> list = new ArrayList<Userposjava>();
		String sql = "SELECT * FROM userposjava";
		try {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));
			list.add(userposjava);
		}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Userposjava buscar(Long id){
		Userposjava retorno = new Userposjava();
		String sql = "SELECT * FROM userposjava WHERE id = "+id;
		try {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) { //retorna 1 ou nenhum
			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));		}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public void atualizar(Userposjava userposjava)
	{
		try {
			String sql = "UPDATE userposjava SET NOME = ? WHERE id = "+userposjava.getId();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userposjava.getNome());
			preparedStatement.execute();
			connection.commit();
		}catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				}
			e.printStackTrace();
		}
	}

	public void deletar(Long id)
	{
		try {
			String sql = "DELETE FROM userposjava WHERE id = " +id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void salvarTelefone(Telefone telefone) {
		try {
			String sql = "INSERT INTO public.telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?);";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, telefone.getNumero());
			preparedStatement.setString(2, telefone.getTipo());
			preparedStatement.setLong(3, telefone.getUsuario());
			preparedStatement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public List<BeanUserFone> listaUserFone(Long iduser){
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		String sql = "SELECT * FROM telefoneuser as fone inner join userposjava as userp on fone.usuariopessoa = userp.id WHERE userp.id = "+iduser;
		
		try {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			BeanUserFone beanUserFone = new BeanUserFone();
			beanUserFone.setNome(resultSet.getString("nome"));
			beanUserFone.setEmail(resultSet.getString("email"));
			beanUserFone.setNumero(resultSet.getString("numero"));
			beanUserFones.add(beanUserFone);
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return beanUserFones;
	}
	
	public void deleteFones(Long idUser) {
		String sqlFone = "DELETE from TELEFONEUSER where usuariopessoa = "+idUser;
		String sqlUser = "DELETE FROM userposjava WHERE id =" +idUser;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement = connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

