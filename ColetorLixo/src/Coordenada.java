import javax.swing.JButton;


public class Coordenada {
    
	private int x, y; 
	private int capacidade ;
	
	public int getCapacidade() {
		return capacidade;
	}


	public void setCapacidade(int capacidade, JButton bt) {
		String hint ;
		
		this.capacidade = capacidade;
		
		hint = "<HTML><FONT FACE=\"Tahoma\" SIZE=\"-2\">";  
		
		if  (bt.getText().equals("Lo")){
			hint += "<b>Lixeia de lixo orgânico</b><br>";
		}else{
			hint += "<b>Lixeia de lixo seco</b><br>";
		}
		
		hint += "<b>Capacidade disponível: </b>" + capacidade;
		// Fecha o HTML do hint
		hint += "</FONT></HTML>";		
		bt.setToolTipText(hint);
		
		
	}
	
	public void DecrementaCapacidade(int valor, JButton bt) {
		String hint ;
		
		this.capacidade = this.capacidade - valor;
		
        hint = "<HTML><FONT FACE=\"Tahoma\" SIZE=\"-2\">";  
		
        if  (bt.getText().equals("Lo")){
			hint += "<b>Lixeia de lixo orgânico</b><br>";
		}else{
			hint += "<b>Lixeia de lixo seco</b><br>";
		}
		
		hint += "<b>Capacidade disponível: </b>" + capacidade;
		// Fecha o HTML do hint
		hint += "</FONT></HTML>";		
		bt.setToolTipText(hint);
		
	}


	public int getX() {
		return x;
	}

	
	public int getY() {
		return y;
	}

	public Coordenada(int x , int y) {
	  this.x = x;
	  this.y = y;
	}
	
	

}
