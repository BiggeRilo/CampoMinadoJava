package br.com.atomjuice.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.atomjuice.cm.excecao.ExplosaoException;


//TODO adicionar cobertura de pelo menos 80%
public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo possivelVizinho)
	{
		boolean linhaDiferente = linha != possivelVizinho.linha;
		boolean colunaDiferente = coluna != possivelVizinho.coluna;
		boolean isDiagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - possivelVizinho.linha);
		int deltaColuna = Math.abs(coluna - possivelVizinho.coluna);
		
		int totalDelta = deltaColuna + deltaLinha;
		
		if(totalDelta == 1 && !isDiagonal)
		{
			vizinhos.add(possivelVizinho);
			return true;
		}
		else if(totalDelta == 2 && isDiagonal)
		{
			vizinhos.add(possivelVizinho);

			return true;
		}
		else
		{
			return false;
		}
	}

	void alternarMarcacao(){
		if(!aberto){
			marcado = !marcado;
		}
	}
	
	boolean expandAbrir(){
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	
	boolean abrirCampo(){
		if(!aberto && !marcado) {
			aberto = true;
			if(minado){
				throw new ExplosaoException();
			}
			
			if(expandAbrir()){
				vizinhos.forEach(v -> v.abrirCampo());
			}
			return true;
		}
		
		return false;
	}

	void minarCampo()
	{
		minado = true;
	}
	
	public boolean isMarcado(){
		return marcado;
	}

	public boolean isAberto(){
		return aberto;
	}

	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isFechado(){
		return !isAberto();
	}

	public boolean isMinado()
	{
		return minado;
	}	
	
	public int getLinha() {
		return linha;
	}

	
	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado(){
		boolean desvendado = !minado && aberto;
		boolean protegido = marcado && minado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhaca()
	{
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar()
	{
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	public String toString()
	{
		if(marcado){
			return "X";
		}
		else if(aberto && minado){
			return "*";
		}
		else if(aberto && minasNaVizinhaca() > 0){
			return Long.toString(minasNaVizinhaca());
		}else if(aberto){
			return " ";
		}else{
			return "?";
		}
	}
	

}


