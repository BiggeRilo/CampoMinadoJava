package br.com.atomjuice.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.atomjuice.cm.excecao.ExplosaoException;

class CampoTeste {

	private Campo c1;
	
	@BeforeEach
	void iniciarCampo()
	{
		c1 = new Campo(3,3);
	}
	
	@Test
	void testeVizinhoRealDistanciaEsquerda() {
		Campo vizinhoReal = new Campo(3,2);
		boolean isVizinho = c1.adicionarVizinho(vizinhoReal);
		assertTrue(isVizinho); 
	}
	
	@Test
	void testeVizinhoRealDireita() {
		Campo vizinhoReal = new Campo(3,4);
		boolean isVizinho = c1.adicionarVizinho(vizinhoReal);
		assertTrue(isVizinho); 
	}
	
	@Test
	void testeVizinhoRealAcima() {
		Campo vizinhoReal = new Campo(2,3);
		boolean isVizinho = c1.adicionarVizinho(vizinhoReal);
		assertTrue(isVizinho); 
	}
	
	@Test
	void testeVizinhoRealAbaixo() {
		Campo vizinhoReal = new Campo(4,3 );
		boolean isVizinho = c1.adicionarVizinho(vizinhoReal);
		assertTrue(isVizinho); 
	}
	
	@Test
	void testeVizinhoRealDiagonal() {
		Campo vizinhoReal = new Campo(2,2);
		boolean isVizinho = c1.adicionarVizinho(vizinhoReal);
		assertTrue(isVizinho); 
	}
	
	@Test
	void testeVizinhoFalso() {
		Campo vizinhoReal = new Campo(1,1);
		boolean isVizinho = c1.adicionarVizinho(vizinhoReal);
		assertFalse(isVizinho); 
	}

	@Test
	void testeDefaultValueMarcacao()
	{
		assertFalse(c1.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacao()
	{	
		c1.alternarMarcacao();
		assertTrue(c1.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas()
	{	
		c1.alternarMarcacao();
		c1.alternarMarcacao();
		assertFalse(c1.isMarcado());
	}
	
	@Test 
	void testeAbrirNaoMinadoNaoMarcado(){
		assertTrue(c1.abrirCampo());
	}
	
	@Test 
	void testeAbrirNaoMinadoMarcado(){
		c1.alternarMarcacao();
		assertFalse(c1.abrirCampo());
	}
	
	@Test 
	void testeAbrirMinadoMarcado(){
		c1.alternarMarcacao();
		c1.minarCampo();
		assertFalse(c1.abrirCampo());
	}
	
	@Test 
	void testeAbrirMinadonaoMarcado()
	{
		c1.minarCampo();
		
		assertThrows(ExplosaoException.class, () -> {c1.abrirCampo();});
	}
	
	@Test 
	void testeAbrirComVizinhos(){
		
		Campo vizinho11 = new Campo(1,2);
		Campo vizinho22 = new Campo(2,2);
		
		vizinho22.adicionarVizinho(vizinho11);
		
		c1.adicionarVizinho(vizinho22);
		
		c1.abrirCampo();
		
		
		assertTrue(vizinho22.isAberto() && vizinho11.isAberto());
		
	}
	
	@Test
	void testeAbrirComVizinhosMinados(){
		
		Campo vizinhoMinado = new Campo(1,1);
		Campo vizinho11 = new Campo(1,1);
		vizinhoMinado.minarCampo();
		
		Campo vizinho22 = new Campo(2,2);		
		vizinho22.adicionarVizinho(vizinho11);
		vizinho22.adicionarVizinho(vizinhoMinado);
		
		c1.adicionarVizinho(vizinho22);
		
		c1.abrirCampo();
		
		
		assertTrue(vizinho22.isAberto() && vizinho11.isFechado());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
