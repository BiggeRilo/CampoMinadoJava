package br.com.atomjuice.cm;

import br.com.atomjuice.cm.modelo.Tabuleiro;
import br.com.atomjuice.cm.visao.TabuleiroConsole;

public class Aplicacao {

	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);
		new TabuleiroConsole(tabuleiro);
	}
}
