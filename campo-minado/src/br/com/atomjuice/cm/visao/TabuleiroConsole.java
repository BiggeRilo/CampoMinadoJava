package br.com.atomjuice.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.atomjuice.cm.excecao.ExplosaoException;
import br.com.atomjuice.cm.excecao.SairException;
import br.com.atomjuice.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	
	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar){
				loopDoJogo();
				
				
				System.out.println("Outra partida? (S/n)");
				String resposta = entrada.nextLine();
				
				
				if("n".equalsIgnoreCase(resposta)){
					continuar = false; 
				}
				else{
					tabuleiro.reiniciar();
				}
				
			}
			
		}
		catch (SairException sair) {
			System.out.println("Adios :) ");
		}
		finally {
			entrada.close();
		}
		
	}

	private void loopDoJogo() {
		try {
			
			while(!tabuleiro.objetivoAlcancado()){
				System.out.println(tabuleiro.toString());
				
				String digitado = capturarValorDigitado("Digite a posição do campo (x,y): ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e-> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");

				if("1".equals(digitado)){
					tabuleiro.abrir(xy.next(), xy.next());
				}
				else if("2".equals(digitado)){
					tabuleiro.alterarMarcacao(xy.next(), xy.next());
				}
			}
			System.out.println(tabuleiro);
			System.out.println("Corki no Bobslei!!!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você Perdeu!!!");
		}
	
	}
	
	private String capturarValorDigitado(String texto){
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)){
			throw new SairException();
		}
		
		return digitado;
	}
	
	
	
	
	
	
	
	
}
