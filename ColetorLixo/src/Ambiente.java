import java.awt.Color;
import java.util.Vector;

import javax.swing.JButton;


public class Ambiente {

	int eixo_x = 20, eixo_y = 20; // tamanho eixo x e y da matriz
	int capacidade_seco=5, capacidade_organico = 5; // capacidade das lixeiras
	//int capacidade_seco_usada=0, capacidade_organico_usada = 0; // quanto da capacidade já foi usada
	
	int executando = 0; // se o programa esta executando ou deve parar.
	
	JButton m[][] ; // matriz ambiente
	
	int debug;
	boolean debugando;
	
	Vector<Coordenada> pos_lixeiras_organico = new Vector<Coordenada>();
	Vector<Coordenada> pos_lixeiras_seco = new Vector<Coordenada>();
	
	
	public Ambiente(JButton m[][], int eixo_x, int eixo_y, int capacidade_seco, int capacidade_organico  ) {
		this.m = m; // matriz ambiente
		this.eixo_x = eixo_x; // tamanho matriz
		this.eixo_y = eixo_y; // tamanho matriz
		
		this.capacidade_seco = capacidade_seco;
		this.capacidade_organico = capacidade_organico;
		

		pos_lixeiras_organico.clear();
		pos_lixeiras_seco.clear();
	}
	
	public void add_lixeira_organico(Coordenada pos){
		pos_lixeiras_organico.add(pos);
	}
	public void add_lixeira_seco(Coordenada pos){
		pos_lixeiras_seco.add(pos);
	}
	
	public Vector<Coordenada> getPos_lixeiras_organico() {
		return pos_lixeiras_organico;
	}

	public Vector<Coordenada> getPos_lixeiras_seco() {
		return pos_lixeiras_seco;
	}
	
	
	
	public void setText(Coordenada pos, String texto){
		
		  
		
		  // define o texto da celula
		  JButton bt = (JButton) m[pos.getX()][pos.getY()];
		  bt.setText(texto);
		  bt.setToolTipText(null);
		 
		  
		  
		  
		  // se for lixeira, adiciona na lista de lixeiras
		  if (texto.equals("Lo")) {
			  pos.setCapacidade(capacidade_organico, bt);
			  add_lixeira_organico(pos);
		  }
		  if (texto.equals("Ls")) {
			  pos.setCapacidade(capacidade_seco, bt);
			  add_lixeira_seco(pos);
			  
			  
		  }
		  
		  if (texto.equals("A")) {
			  bt.setForeground(Color.RED);
		  } else{
			  bt.setForeground(Color.BLACK);
		  }
		  
			
		}

	public int getEixo_x() {
		return eixo_x;
	}

	public int getEixo_y() {
		return eixo_y;
	}
	
	public JButton getBT(int x , int y) {
		if ((x < 0) | (x > eixo_x-1) | (y < 0) | (y > eixo_y-1)) {
			return null;
		}else {
			  return (JButton) m[x][y];
         }
	}
	
	public String getText(int x, int y){
		
		if ((x<0)|(x>eixo_x-1)| (y<0)|(y>eixo_y-1)) {
			return "";
		}else {
		  JButton bt_diag = (JButton) m[x][y];		
		  return bt_diag.getText();
		}
	}
	
	public int verifica_diagonal(Coordenada pos){
		// verifica diagonal se não tem outra lixeira
		int x = pos.getX();
		int y = pos.getY();
		
		  if ((x == 0) | (y == 0) | (x == eixo_x-1) | (y == eixo_y-1)){			  
			  if ((getText(x-1, y-1).equals("Ls")) | 
				  (getText(x+1, y+1).equals("Ls")) |
				  (getText(x-1, y+1).equals("Ls")) |
				  (getText(x+1, y-1).equals("Ls")) |				  
				  (getText(x-1, y-1).equals("Lo")) | 
				  (getText(x+1, y+1).equals("Lo")) |
				  (getText(x-1, y+1).equals("Lo")) |
				  (getText(x+1, y-1).equals("Lo")) 
				  ){
				  return 1; // celula da diagonal contem lixeira				  
			  }
		  }
		return 0;		
	}
	
	public void decrementaDebug(){
		   debug--;
	   }	
	
	public int possoExecutar(){
		   return debug;
	   }

	public void setDebug(int coletores) {
		debug = coletores;
	}
	
	public boolean isDebugando() {
		return debugando;
	}

	public void setDebugando(boolean debugando) {
		this.debugando = debugando;
	}
	
	public int getExecutando() {
		return executando;
	}

	public void setExecutando(int executando) {
		this.executando = executando;
	}

}
