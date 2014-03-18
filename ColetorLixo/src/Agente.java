import java.util.Vector;

import javax.swing.JButton;


public class Agente extends Thread {

	
	final int FRENTE = 1;
	final int ATRAS = 2;
	final int ABAIXO = 3;
	final int ACIMA = 4;
	
	boolean procurando_lixeira = false;
	
	
	String valor_anterior = ""; // conteudo anterior da celula; (usado quando o agente está de saco cheio e passa por cima do lixo)
	
	Ambiente ambiente;
	Coordenada pos_atual; // posicao do agente
	int capacidade_organico, capacidade_seco; // qual a capacidade do agente
	// para cada lixo

	int usado_organico = 0, usado_seco = 0; // qual a capacidade USADA do
											// agentepara cada saco de lixo
	Vector<Coordenada> pos_lixeiras_organico = new Vector<Coordenada>(); // posição
																			// das
																			// lixeiras
																			// no
																			// ambiente
	Vector<Coordenada> pos_lixeiras_seco = new Vector<Coordenada>(); // posição
																		// das
																		// lixeiras
																		// no
																		// ambiente

	public Agente(Coordenada pos, Ambiente ambiente, int capacidade_organico,
			int capacidade_seco) {
		// seta a posição inicial
		this.pos_atual = pos;

		this.ambiente = ambiente;

		// seta a lista inicial de lixeiras disponiveis
		this.pos_lixeiras_organico = ambiente.getPos_lixeiras_organico();
		this.pos_lixeiras_seco = ambiente.getPos_lixeiras_seco();

		this.capacidade_organico = capacidade_organico;
		this.capacidade_seco = capacidade_seco;
	}

	public int Anda(int direcao) {
		// sorteia coordenada e move o agente, caso ache lixo ao andar então
		// retorna 1 senão retorna 0,
		// se retornar 3 = então a direação fixa deve ser sorteada novamente ,
		// pois já chegou ao final da matriz

		int nr_randomico = 0, anda_y = 0, anda_x = 0;
		int i = 1;
		String  valor_celula_destino = "";
		JButton bt=null ;
		JButton btLixo = null;
		Coordenada lixeira;

		Coordenada nova_pos = null;
		
		while (true){
			nr_randomico = 0;
			anda_y = 0; 
			anda_x = 0;


			// se não tiver recebido uma direção fixa, então sortea a direção
			if (direcao == 0) {
				while (nr_randomico==0) {
					nr_randomico = (int) (5 * Math.random()); // numero randomico
					
				}
			} else {
				nr_randomico = direcao;
				System.out.print("FIXO ... ");				
			}



			switch (nr_randomico) {
			case FRENTE:
				anda_x = 1;
				System.out.println("andando pra frente");
				break;
			case ATRAS:
				anda_x = -1;
				System.out.println("andando pra trás");
				break;
			case ABAIXO:
				anda_y = 1;
				System.out.println("andando pra baixo");
				break;
			case ACIMA:
				anda_y = -1;
				System.out.println("andando pra cima");
				break;
			}

			bt = ambiente.getBT(pos_atual.getX() + anda_x, pos_atual
					.getY()
					+ anda_y);

			
			// se null então tentou pegar um botão fora o range permitido ou se estiver ocupado por outro objeto
			if ( (bt == null) || (bt.getText() == "A") | bt.getText() == "Lo" | (bt.getText() ==("Ls")) ) {
				// se possui direçao fixa retorna pedindo uma nova direção,
				// senão sorteia outra celula
				if (direcao > 0) {
					return 3;
				} else {
					continue;
				}
			}else{
				break;
			}

		}
		
		
		
			
			
			// se a celula esta vazia ou contem lixo, pega a coordenada,
			// senão sortea de novo.
			if (bt.getText().equals("S")) {
				if (capacidade_seco > usado_seco) {
					System.out.println(" --> Coletei lixo seco");
					usado_seco++;
					
					if (capacidade_seco == usado_seco) {
						i = 4; // sinaliza que acabou de encher o saco e deve iniciar procura por lixeira
					}
				} else {
					System.out.println(" --> Não coletei (saco cheio)");
					valor_celula_destino = bt.getText();// guarda para devolver depois o valor anterior da celula
				}

				nova_pos = new Coordenada(pos_atual.getX() + anda_x, pos_atual
						.getY()
						+ anda_y);
				
			} else if (bt.getText().equals("O")) {
				if (capacidade_organico > usado_organico) {
					System.out.println(" --> Coletei lixo orgânico");
					usado_organico++;
					
					if (capacidade_organico == usado_organico) {
						i = 4;  // sinaliza que acabou de encher o saco e deve iniciar procura por lixeira
					}
				} else {
					System.out.println(" --> Não coletei (saco cheio)");
					valor_celula_destino = bt.getText();// guarda para devolver depois o valor anterior da celula
				}
				nova_pos = new Coordenada(pos_atual.getX() + anda_x, pos_atual.getY() + anda_y);
				
			} else if (bt.getText().length() == 0) {
				nova_pos = new Coordenada(pos_atual.getX() + anda_x, pos_atual.getY() + anda_y);
				i = 0; // andou e não achou lixo
				
			} 
			
		ambiente.setText(pos_atual, valor_anterior);
		valor_anterior = valor_celula_destino; // guarda para devolver depois que o agente sair.
		ambiente.setText(nova_pos, "A");
		pos_atual = nova_pos;
		
		// Inicia o HTML do hint
		String hint = "<HTML><FONT FACE=\"Tahoma\" SIZE=\"-2\">";		 
		if (direcao > 0) {
			hint += "Andando em direção fixa<br>";
		}else{
			hint += "Andando Aleatóriamente<br>";
		}
		
		hint += "<B>Saco lixo seco usado: </B>" + usado_seco + "<BR>" 
			   +"<B>Saco lixo orgânico usado: </B>"+ usado_organico + "<BR>";
		if (procurando_lixeira){
		  hint += "<B>Procurando lixeira: </B>Sim<BR>";
		}else{
			hint += "<B>Procurando lixeira: </B>Não<BR>"	;
		}
		
		
		
		
		
		
		
		
		// após andar verifica se está ao lado de lixeira e então esvazia se o saco estiver cheio
		if ((usado_seco >= capacidade_seco ) & (procurando_lixeira)) {			
			if (( ambiente.getText(pos_atual.getX() + 1,pos_atual.getY()) == "Ls") |
					( ambiente.getText(pos_atual.getX() - 1,pos_atual.getY()) == "Ls") |
					( ambiente.getText(pos_atual.getX() ,pos_atual.getY()+ 1) == "Ls") |
					( ambiente.getText(pos_atual.getX() ,pos_atual.getY()- 1) == "Ls") ){
				
				lixeira = getLixeiraMaisPerto("Ls");
				
				btLixo = ambiente.getBT(lixeira.getX(), lixeira.getY());
				
				if (lixeira.getCapacidade() > usado_seco) {
					System.out.println("----> Achei Lixeira, esvaziando saco lixo seco...");
					hint += "<B>Achei Lixeira, esvaziando saco lixo seco...</B><BR>"	;
					lixeira.DecrementaCapacidade(usado_seco, btLixo);
					usado_seco = 0;					
					i = 5; // sinaliza que não deve mais procurar lixeira.
				}else {
					System.out.println("----> Achei Lixeira, esvaziando saco lixo seco...");
					hint += "<B>Achei Lixeira, esvaziando saco lixo seco...</B><BR>"	;
					usado_seco = usado_seco - lixeira.getCapacidade() ;
					lixeira.setCapacidade(0, btLixo);
					removeLixeiraDaLista(lixeira, "Ls");
					i = 5; // sinaliza que não deve mais procurar lixeira.
				}
				
			}
		} 
		
		if ((usado_organico >= capacidade_organico )& (procurando_lixeira)) {			
			if (( ambiente.getText(pos_atual.getX() + 1,pos_atual.getY()) == "Lo") |
					( ambiente.getText(pos_atual.getX() - 1,pos_atual.getY()) == "Lo") |
					( ambiente.getText(pos_atual.getX() ,pos_atual.getY()+ 1) == "Lo") |
					( ambiente.getText(pos_atual.getX() ,pos_atual.getY()- 1) == "Lo") ){
				
				lixeira = getLixeiraMaisPerto("Lo");
				btLixo = ambiente.getBT(lixeira.getX(), lixeira.getY());
				
				if (lixeira.getCapacidade() > usado_organico) {
					System.out.println("----> Achei Lixeira, esvaziando saco lixo orgânico...");
					hint += "<B>Achei Lixeira, esvaziando saco lixo orgânico...</B><BR>"	;
					lixeira.DecrementaCapacidade(usado_organico,btLixo);
					usado_organico = 0;					
					i = 5; // sinaliza que não deve mais procurar lixeira.
				}else {
					System.out.println("----> Achei Lixeira, esvaziando saco lixo orgânico...");
					hint += "<B>Achei Lixeira, esvaziando saco lixo orgânico...</B><BR>"	;
					usado_organico = usado_organico - lixeira.getCapacidade() ;
					lixeira.setCapacidade(0,btLixo);
					removeLixeiraDaLista(lixeira, "Lo");
					i = 5; // sinaliza que não deve mais procurar lixeira.
				}
				
			}
		}
		
		
		// Fecha o HTML do hint
		hint += "</FONT></HTML>";		
		bt.setToolTipText(hint);

		return i;

	}

	@SuppressWarnings("deprecation")
	private void extracted() {
		this.stop();
	}
	
	public Coordenada getLixeiraMaisPerto(String tipoLixeira){
		Coordenada pos_lix, lixeira_mais_perto = null;
		double dist , dist_mais_perto = 999999999;
		if (tipoLixeira=="Ls"){			
			for (int i=0;i<pos_lixeiras_seco.size();i++){
				pos_lix = pos_lixeiras_seco.get(i);				
				dist= Math.sqrt(Math.pow(pos_atual.getX() - pos_lix.getX(),2) + Math.pow(pos_atual.getY() - pos_lix.getY(),2));
				if (dist  < dist_mais_perto){
				  dist_mais_perto = dist;
				  lixeira_mais_perto = pos_lix;
				}
			}
		}else if (tipoLixeira=="Lo"){			
			for (int i=0;i<pos_lixeiras_organico.size();i++){
				pos_lix = pos_lixeiras_organico.get(i);				
				dist= Math.sqrt(Math.pow(pos_atual.getX() - pos_lix.getX(),2) + Math.pow(pos_atual.getY() - pos_lix.getY(),2));
				if (dist  < dist_mais_perto){
				  dist_mais_perto = dist;
				  lixeira_mais_perto = pos_lix;
				}
			}
		}
		
		return lixeira_mais_perto;
		
	}

	public void run() {
		Coordenada lixeira=null;
		int ciclos_sem_achar_lixo = 0, achou_lixo = 0;
		
		int andar_aleatorio_3ciclos=0; // se estiver procurando lixeira e ficar preso o agente deve andar aleatorio por 3 clicos.
		int direcao = 0; // direção que o agente irá andar quando estiver
							// andando em linha
		
		
		
		boolean andar_direcao =false;

		while (ambiente.getExecutando() == 1) {

			// se esta em modo debug e pode executar
			if ((ambiente.debugando)) {
				if (ambiente.possoExecutar() > 0) {
					// System.out.println("Debugando");
					// System.out.println(ambiente.possoExecutar());
					ambiente.decrementaDebug();
				} else {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					continue;
				}
			}
			
			
			procurando_lixeira = ((usado_organico >= capacidade_organico ) | (usado_seco >= capacidade_seco ));
			
			// se estiver procurando lixeira e não ficou preso (andar_aleatorio_3ciclos)
			if ((procurando_lixeira == true) & (andar_aleatorio_3ciclos <= 0) ) {


				if (capacidade_seco <= usado_seco) {
					lixeira = getLixeiraMaisPerto("Ls");
					System.out.println("Saco de lixo seco lotado, procurando lixeira...");
				}else if (capacidade_organico <= usado_organico) {
					System.out.println("Saco de lixo orgânico lotado, procurando lixeira...");
					lixeira = getLixeiraMaisPerto("Lo");
				}
				if (lixeira ==null) {
					System.out.println("Agente parando... não tenho mais lixeiras na lista");					
					extracted(); // para a thread						  
				}else{						
					// força o agente a andar na direção da lixeira:
					if (lixeira.getX() > pos_atual.getX()){
						direcao = FRENTE;
					}else if (lixeira.getX() < pos_atual.getX()){
						direcao = ATRAS;
					}else if (lixeira.getY() > pos_atual.getY()){
						direcao = ABAIXO;
					}else if (lixeira.getY() < pos_atual.getY()){
						direcao = ACIMA;
					}
				}
			}


			if (procurando_lixeira == false){
				direcao = verificaLixoRedor1("O");
				if (direcao == 0){
					direcao = verificaLixoRedor1("S");
				}
				if (direcao == 0){
					direcao = verificaLixoRedor2("0");
				}
				if (direcao == 0){
					direcao = verificaLixoRedor2("S");
				}
				andar_direcao = direcao > 0;
			}
		
			

			// se não acha lixo a mais de 3 ciclos então anda em linha até achar
			// ou chegar no final da matriz
			if ( /**/(( ((ciclos_sem_achar_lixo >= 3) & (achou_lixo == 0) ) | (procurando_lixeira) /**/ ) & (andar_aleatorio_3ciclos <= 0)) | ((andar_direcao) & (andar_aleatorio_3ciclos <= 0)) )  {
				if (direcao == 0) {
					// sorteia um numero para direção fixo, que deve ser diferente da ultima direção sorteada
					while (direcao == 0) {
						direcao = (int) (5 * Math.random()); // sorteia direção
																	// fixa para
																// andar
						
					}
					
					System.out.print("Andando FIXO na direção = ");
					System.out.println(direcao);
				}
				achou_lixo = Anda(direcao); // anda o agente em direção fixa

				// se achou_lixo = 3 então deve re-sortear a direção pois chegou
				// ao final da matriz
				if (achou_lixo == 3) {
					System.out.println("cheguei no final da matriz ou bati em lixeira");
					achou_lixo = 0;
					direcao = 0;
					ciclos_sem_achar_lixo = 0; // ao chegar no final da matriz
												// anda novamente aleatorio.
					andar_aleatorio_3ciclos = 3; // se estiver procurando lixo, força andar aleatorio por 3 ciclos para não se prender.
					continue;
				}else if (achou_lixo == 4) {
					System.out.println("Saco cheio no próximo ciclo iniciará procura por lixeira...");
					andar_aleatorio_3ciclos = 0; 
					procurando_lixeira = true;
					continue;
				}else if (achou_lixo == 5) {
					System.out.println("Esvaziei saco de lixo");
					ciclos_sem_achar_lixo = 0;
					andar_aleatorio_3ciclos = 0; 
					continue;
				}

			} else {
				achou_lixo = Anda(0); // anda o agente em modo aleatorio
				direcao = 0;
				andar_aleatorio_3ciclos--; // decrementa a variavel contar vezes q andou aleatorio , enquanto procura lixeira.
				System.out.println("Andei aleatorio");
			}
			// controla se achou lixo ou não
			if (achou_lixo == 1) {
				ciclos_sem_achar_lixo = 0;
			} else {
				ciclos_sem_achar_lixo++;
			}

			System.out.print("Cliclos=");
			System.out.print(ciclos_sem_achar_lixo);
			System.out.print("......Achou lixo=");
			System.out.println(achou_lixo);

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("===================");

		}
		
		

	}
	
	
	public int verificaLixoRedor1(String tipoLixo){
		int direcao = 0;
		// se não estiver procurando lixeira, verifica celulas ao redor se tem lixo
		
		if ( ambiente.getText(pos_atual.getX() + 1,pos_atual.getY()) == tipoLixo){
			direcao = FRENTE;
			System.out.print("Lixo a frente... tendenciando movimentação do agente") ;
		}else if ( ambiente.getText(pos_atual.getX() - 1,pos_atual.getY()) == tipoLixo){ 
			direcao = ATRAS;
			System.out.print("Lixo atrás... tendenciando movimentação do agente") ;
		}else if ( ambiente.getText(pos_atual.getX() ,pos_atual.getY()+ 1) == tipoLixo){ 
			direcao = ABAIXO;
			System.out.print("Lixo abaixo... tendenciando movimentação do agente") ;
		}else if ( ambiente.getText(pos_atual.getX() ,pos_atual.getY()- 1) == tipoLixo){ 
			direcao = ACIMA;		
			System.out.print("Lixo acima... tendenciando movimentação do agente") ;
		}
		

		return direcao;
	}
	
	
	public int verificaLixoRedor2(String tipoLixo){
		int direcao = 0;
		// se não estiver procurando lixeira, verifica celulas ao redor se tem lixo		
		
		//verifica se a segunda celula ao redor possui lixo, e se a primeira celula ao redor não contem obstaculo.
		if (( ambiente.getText(pos_atual.getX() + 1,pos_atual.getY()) == "") & ( ambiente.getText(pos_atual.getX() + 2,pos_atual.getY()) == tipoLixo)){
			direcao = FRENTE;
			System.out.print("Lixo a frente... tendenciando movimentação do agente") ;
		}else if (( ambiente.getText(pos_atual.getX() - 1,pos_atual.getY()) == "") & ( ambiente.getText(pos_atual.getX() - 2,pos_atual.getY()) == tipoLixo)){ 
			direcao = ATRAS;
			System.out.print("Lixo atrás... tendenciando movimentação do agente") ;
		}else if (( ambiente.getText(pos_atual.getX() ,pos_atual.getY()+ 1) == "") & ( ambiente.getText(pos_atual.getX() ,pos_atual.getY()+ 2) == tipoLixo)){ 
			direcao = ABAIXO;
			System.out.print("Lixo abaixo... tendenciando movimentação do agente") ;
		}else if (( ambiente.getText(pos_atual.getX() ,pos_atual.getY()- 1) == "") &( ambiente.getText(pos_atual.getX() ,pos_atual.getY()- 2) == tipoLixo)){ 
			direcao = ACIMA;	
			System.out.print("Lixo acima... tendenciando movimentação do agente") ;
		}

		return direcao;
	}
	
	public void removeLixeiraDaLista(Coordenada pos, String tipoLixeira){
		Coordenada pos_lix;
		if (tipoLixeira=="Ls"){			
			for (int i=0;i<pos_lixeiras_seco.size();i++){
				pos_lix = pos_lixeiras_seco.get(i);				
				if ((pos_lix.getX() == pos.getX()) & (pos_lix.getY() == pos.getY())){
					pos_lixeiras_seco.remove(i);
					break;
				} 
			}
		}else if (tipoLixeira=="Lo"){			
			for (int i=0;i<pos_lixeiras_organico.size();i++){
				pos_lix = pos_lixeiras_organico.get(i);				
				if ((pos_lix.getX() == pos.getX()) & (pos_lix.getY() == pos.getY())){
					pos_lixeiras_organico.remove(i);
					break;
				} 
			}
		}
		
		
	}
	
	 

	

}
