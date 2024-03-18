//package udesc;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import udesc.spd.Server;
//
//import static org.junit.Assert.*;
//
//public class ServerTest {
//
//    @Before
//    public void setUp() {
//        // Adicionar algumas pessoas para teste
//        Server.insertPessoa("João", "123456789", "Rua A");
//        Server.insertPessoa("Maria", "987654321", "Rua B");
//    }
//
//    @After
//    public void tearDown() {
//        // Limpar a lista de pessoas após cada teste
//        Server.pessoas.clear();
//    }
//
//    @Test
//    public void testInsertPessoa() {
//        // Inserir uma nova pessoa
//        assertEquals("Pessoa adicionada com sucesso", Server.insertPessoa("Carlos", "111222333", "Rua C"));
//        // Tentar adicionar uma pessoa com CPF existente
//        assertEquals("Já existe uma pessoa com esse CPF cadastrado", Server.insertPessoa("Maria", "987654321", "Rua D"));
//    }
//
//    @Test
//    public void testListPessoas() {
//
//        String expected = "2\nJoão;123456789;Rua A\nMaria;987654321;Rua B\n";
//        assertEquals(expected, Server.listPessoas());
//    }
//
//    @Test
//    public void testGetPessoa() {
//        // Buscar uma pessoa existente
//        String expected = "Maria;987654321;Rua B";
//        assertEquals(expected, Server.getPessoa("987654321"));
//        // Buscar uma pessoa inexistente
//        assertEquals("Nenhuma pessoa encontrada com esse CPF", Server.getPessoa("111222333"));
//    }
//
//    @Test
//    public void testUpdatePessoa() {
//        // Atualizar uma pessoa existente
//        assertEquals("Pessoa atualizada com sucesso", Server.updatePessoa("Pedro", "123456789", "Rua D"));
//        // Tentar atualizar uma pessoa inexistente
//        assertEquals("Pessoa não encontrada", Server.updatePessoa("Ana", "111222333", "Rua E"));
//    }
//
//    @Test
//    public void testDeletePessoa() {
//        // Deletar uma pessoa existente
//        assertEquals("Pessoa removida com sucesso", Server.deletePessoa("123456789"));
//        // Tentar deletar uma pessoa inexistente
//        assertEquals("Pessoa não encontrada", Server.deletePessoa("111222333"));
//    }
//}
