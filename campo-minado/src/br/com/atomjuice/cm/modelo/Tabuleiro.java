package br.com.atomjuice.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.atomjuice.cm.excecao.ExplosaoException;

public class Tabuleiro {
	
	private int qtdLinhas;
	private int qtdColunas;
	private int qtdMinas;

	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int qtdLinhas, int qtdColunas, int qtdMinas) {
		super();
		this.qtdLinhas = qtdLinhas;
		this.qtdColunas = qtdColunas;
		this.qtdMinas = qtdMinas;
		
		gerarCampos();
		associarOsVizinhos();
		generateMinas();
	}
	
	public void abrir(int linha, int coluna)
	{
		try {
			campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.abrirCampo());			
		} 
		catch (ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	

	public void alterarMarcacao(int linha, int coluna){
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst().ifPresent(c -> c.alternarMarcacao());;
	}
	
	private void gerarCampos() {
		for (int linhas = 0; linhas < qtdLinhas;  linhas++) {
			for (int colunas = 0; colunas < qtdColunas; colunas++) {
				campos.add(new Campo( linhas, colunas));
			}
		}
	}

	private void associarOsVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos){
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void generateMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			//Cast tem prioridade sobre a multiplicacao, como o Math.random() gera um valor entre 0 e 1, o cast transforma esse valor para 0. Por isso realizamos a multiplicacao primeiro
			int aleatorio = (int) (Math.random() * campos.size());
			
			campos.get(aleatorio).minarCampo();		
			minasArmadas = campos.stream().filter(minado).count();
		
		} while (minasArmadas < qtdMinas);
	}
	
	public boolean objetivoAlcancado(){
		return campos.stream().allMatch(c->c.objetivoAlcancado());
	}
	
	public void reiniciar(){
		campos.stream().forEach(c-> c.reiniciar());
		generateMinas();
	}
	
	public String toString(){
		//Bom para criar uma string que surge a partir de muitas concatanacoes

		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for(int colunas = 0; colunas < qtdColunas; colunas++){
			sb.append(" ");
			sb.append(colunas);
			sb.append(" ");
		}
		sb.append("\n");
		
		int i = 0;
		for(int linhas = 0; linhas < qtdLinhas; linhas++){
			sb.append(linhas);
			sb.append(" ");
			for(int colunas = 0; colunas < qtdColunas; colunas++){
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
