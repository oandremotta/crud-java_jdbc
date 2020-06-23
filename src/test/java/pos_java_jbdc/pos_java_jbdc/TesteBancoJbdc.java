package pos_java_jbdc.pos_java_jbdc;

import java.util.List;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJbdc {

	@Test
	public void initBanco() {
		UserPosDAO userPosDao = new UserPosDAO();
		Userposjava userposjava= new Userposjava();
		userposjava.setNome("Teste");
		userposjava.setEmail("teste@gmail.com");
		userPosDao.salvar(userposjava);
	} 
	
	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		List<Userposjava> list = dao.listar();
		for (Userposjava userposjava : list) {
			System.out.println(userposjava);
			System.out.println("");
		}
	}
	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();
		Userposjava userposjava = dao.buscar(2L);
		System.out.println(userposjava);
	}
	
	@Test
	public void initAlterar() {
		try {
		UserPosDAO dao = new UserPosDAO();
		Userposjava userposjava = dao.buscar(2L);
		userposjava.setNome("Alterado");
		dao.atualizar(userposjava);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initDeletar() {
		try {
			UserPosDAO dao = new UserPosDAO();
			dao.deletar(2L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(12)-99999-9999");
		telefone.setTipo("Celular");
		telefone.setUsuario(1L);
		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);
	}
	
	@Test
	public void initTesteFone() {
		UserPosDAO dao = new UserPosDAO();
		List<BeanUserFone> beanUserFones = dao.listaUserFone(1L);
		for (BeanUserFone beanUserFone : beanUserFones) {
			System.out.println(beanUserFone);
		}
	}
	
	@Test
	public void deleteTelefone()
	{
		UserPosDAO dao = new UserPosDAO();
		dao.deleteFones(1L);
	}
}
