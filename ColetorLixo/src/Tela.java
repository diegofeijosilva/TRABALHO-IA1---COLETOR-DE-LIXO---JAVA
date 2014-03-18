import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.util.Vector;

import javax.swing.JButton;

/*
 *  Alunos:
 *    Daniel dos Santos Leite
 *    Thiago dos Santos Leite 
 *  
 * */

public class Tela extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	Ambiente ambiente;		
		
	Vector<Agente> listaAgentes = new Vector<Agente>();
	
	
	JPanel pAmbiente = new JPanel();	
	JPanel pConfig = new JPanel();
	JPanel pBotoes= new JPanel();
	JPanel pBaixo = new JPanel();
				
	
	JButton btParar = new JButton("Parar");
	class BTPararListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ambiente.setExecutando(0); // sinaliza para as threads parar
			btParar.setEnabled(false);			
			btAtualiza.setEnabled(true);
			btIniciarColeta.setEnabled(true);
			btDebug.setEnabled(false);
		}			
	}
	
	JButton btAtualiza = new JButton("Atualizar Ambiente");
	class AtualizaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			atualizaAmbiente();
			
		}			
	}
	
	JButton btIniciarColeta = new JButton("Iniciar Coleta");
	class IniciarColetaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			iniciarColeta();				
		}			
	}	
	
	JCheckBox CBdebug = new JCheckBox("Modo Debug");
	class CBDebugListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ambiente.setDebugando(CBdebug.isSelected());
			btDebug.setEnabled((CBdebug.isSelected()) & (ambiente.getExecutando() == 1) );
			
		}			
	}
	
	JButton btDebug = new JButton("Debug");			
	class DebugListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {				
			//ambiente.setDebugando(true); 
			ambiente.setDebug(listaAgentes.size()); // cada agente vai diminuir esse contador até zerar.
			//System.out.print(ambiente.possoExecutar());		
			
		}			
	}		
	
	
	
	
	
	
	
	JTextField txtx = new JTextField("10");
	JTextField txty = new JTextField("10");	
	JTextField txtlixeiras_seco = new JTextField("2");
	JTextField txtlixeiras_organico = new JTextField("2");
	JTextField txtLix_capac_seco = new JTextField("5");
	JTextField txtLix_capac_organico = new JTextField("5");
	JTextField txtqtdeAgentes = new JTextField("3");
	JTextField txtsacoOrganico = new JTextField("2");
	JTextField txtSacoSeco = new JTextField("2");
	JTextField txtLixoOrganico = new JTextField("2");
	JTextField txtLixoSeco = new JTextField("2");
	
	

	
	
	
	
	

	Tela() {
				
		super("Coletor de lixo");		
		
		
		btAtualiza.addActionListener(new AtualizaListener());
		btIniciarColeta.addActionListener(new IniciarColetaListener());	
		btDebug.addActionListener(new DebugListener());
		CBdebug.addActionListener(new CBDebugListener());
		btParar.addActionListener(new BTPararListener());
		
		btDebug.setEnabled(false);
		btParar.setEnabled(false);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		
		pConfig.setLayout(new GridLayout(6,4));
		pConfig.add(new JLabel("Tamanho do eixo X"));
		pConfig.add(txtx);
		//pConfig.add(new JLabel());
		pConfig.add(new JLabel("Tamanho do eixo Y"));
		pConfig.add(txty);
		//pConfig.add(new JLabel());
		
		
		
		pConfig.add(new JLabel("Qtde lixeiras(seco)"));
		pConfig.add(txtlixeiras_seco);
		//pConfig.add(new JLabel());
		pConfig.add(new JLabel("Qtde lixeiras(orgânico)"));		
		pConfig.add(txtlixeiras_organico);
		//pConfig.add(new JLabel());
		pConfig.add(new JLabel("Capac. lixeira seco"));
		pConfig.add(txtLix_capac_seco);
		//pConfig.add(new JLabel());
		//pConfig.add(new JLabel());
		pConfig.add(new JLabel("Capac. lixeira orgânico"));
		pConfig.add(txtLix_capac_organico);		
		
		pConfig.add(new JLabel("Qtde lixo seco"));
		pConfig.add(txtLixoSeco);
		pConfig.add(new JLabel("Qtde lixo orgânico"));
		pConfig.add(txtLixoOrganico);
		
		
		//pConfig.add(new JLabel());
		//pConfig.add(new JLabel());
		
		
		//pConfig.add(new JLabel());
		//pConfig.add(new JLabel());
		
		
		
		pConfig.add(new JLabel("Capac. saco seco"));
		pConfig.add(txtSacoSeco);
		pConfig.add(new JLabel("Capac. saco orgânico"));
		pConfig.add(txtsacoOrganico);
		
		pConfig.add(new JLabel("Qtde coletores"));
		pConfig.add(txtqtdeAgentes);
		
		pConfig.add(CBdebug);

		
		
		pBotoes.add(btAtualiza);
		pBotoes.add(btIniciarColeta);		
		pBotoes.add(btDebug);
		pBotoes.add(btParar);
		
		
		
		
		
		
		

		// inicializa o ambiente
		atualizaAmbiente();
		
		this.add(pAmbiente,BorderLayout.NORTH);
		this.add(pConfig,BorderLayout.CENTER);
		this.add(pBotoes,BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
		
	}
	
	public void atualizaAmbiente(){
		// Atualiza variaveis do ambiente , monta a matriz e coloca na panel ambiente
		
				
				
		
		int eixo_x = Integer.parseInt(txtx.getText());
		int eixo_y = Integer.parseInt(txty.getText());
		int saco_seco = Integer.parseInt(txtSacoSeco.getText());
		int saco_organico = Integer.parseInt(txtsacoOrganico.getText());
		int lixeiras_seco = Integer.parseInt(txtlixeiras_seco.getText());
		int lixeiras_organico = Integer.parseInt(txtlixeiras_organico.getText());
		int Lix_Capacidade_seco = Integer.parseInt(txtLix_capac_seco.getText());
		int Lix_Capacidade_organico = Integer.parseInt(txtLix_capac_organico.getText());

		int lixo_seco= Integer.parseInt(txtLixoSeco.getText());
		int lixo_organico = Integer.parseInt(txtLixoOrganico.getText()); 	
		
		int qtde_agentes = Integer.parseInt(txtqtdeAgentes.getText());
		
		
		// cria nova matriz		
		JButton matriz[][] = new JButton[eixo_x][eixo_y];
				 
		pAmbiente.removeAll(); // limpa da tela a matriz anterior				
		pAmbiente.setLayout(new GridLayout(eixo_y,eixo_x)); // configura a tela
		
		Coordenada pos;
		
		// monta a matriz e joga na tela
		for (int yy = 0; yy < eixo_y; yy++) {
			for (int xx = 0; xx < eixo_x; xx++) {			  
			  matriz[xx][yy] = new JButton(); //popula a matriz para mostrar na tela
			  pAmbiente.add(matriz[xx][yy]); // adiciona na tela
			}
		}
		
		ambiente = new Ambiente(matriz, eixo_x, eixo_y,Lix_Capacidade_seco, Lix_Capacidade_organico );
		
		
		
		// distribui as lixeiras de lixo seco na tela
		for (int i = 0; i < lixeiras_seco; i++) {
			
		  pos = getCelulaRandomico();
		  
		  
		  // se estiver na primeira/ultima linha/coluna então verifica se diagonal não tem outra lixeira
		  if (ambiente.verifica_diagonal(pos)==1){
			  i--;
			  continue;
		  };
		  
		  ambiente.setText(pos, "Ls");		  
		  
		}
		
		
		
		// distribui as lixeiras de lixo organico na tela
		for (int i = 0; i < lixeiras_organico; i++) {		  
		  pos = getCelulaRandomico();
		  
		  // se estiver na primeira/ultima linha/coluna então verifica se diagonal não tem outra lixeira
		  if (ambiente.verifica_diagonal(pos)==1){
			  i--;
			  continue;
		  };
		  
		  ambiente.setText(pos, "Lo"); 
		  
		}
		
		// distribui o lixo organico na tela
		for (int i = 0; i < lixo_organico ; i++) {		  
		  pos = getCelulaRandomico();		  
		  ambiente.setText(pos, "O");
		}
		
		// distribui o lixo seco na tela
		for (int i = 0; i < lixo_seco ; i++) {		  
			pos = getCelulaRandomico();		  
			ambiente.setText(pos, "S");
		}
		
		// distribui os agentes na tela
		listaAgentes.clear();
		for (int i = 0; i < qtde_agentes ; i++) {		  
			pos = getCelulaRandomico();		  
			ambiente.setText(pos, "A");
			
			listaAgentes.add(i,new Agente(pos, ambiente, saco_organico, saco_seco));			
		}
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.pack();
		
	}
	
	public Coordenada getCelulaRandomico(){
		// sorteio de celulas randomicamente
		
		int nro_randomico_y= -1, nro_randomico_x= -1;
		
		int i = 0;		
		while (i==0) {		  
		  nro_randomico_y = (int)(ambiente.getEixo_y()*Math.random()); // numero randomico para celula y
		  nro_randomico_x = (int)(ambiente.getEixo_x()*Math.random()); // numero randomico para celula x
		  // se a celula esta vazia retorna a coordenada, senão sortear de novo.
		  if (ambiente.getText(nro_randomico_x,nro_randomico_y).length()==0){
	        i=1;	        
	        break;
		  }
		}
		
		return new Coordenada(nro_randomico_x, nro_randomico_y);
		
	}
	
	public void iniciarColeta(){
		Agente a;
		
		ambiente.setExecutando(1) ;
		
		btParar.setEnabled(true);
		btAtualiza.setEnabled(false);
		btIniciarColeta.setEnabled(false);
		
		btDebug.setEnabled(CBdebug.isSelected());
		ambiente.setDebugando(CBdebug.isSelected());
		
		
		for (int i=0; i<listaAgentes.size();i++){
		  a = listaAgentes.get(i);
		  a.start();
		}
		
		
	}
	
	
   
	
	

	
	
	public static void main(String[] args) {

       new Tela();
       
	}
	

}
