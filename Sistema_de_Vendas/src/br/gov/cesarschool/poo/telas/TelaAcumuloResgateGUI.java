package br.gov.cesarschool.poo.telas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import br.gov.cesarschool.poo.bonusvendas.entidade.TipoResgate;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class TelaAcumuloResgateGUI {

	protected Shell shell;
	/* atributo mediator, tipo AcumuloResgateMediator, inicializado c/ a instância retornada pelo Singleton*/
	private AcumuloResgateMediator mediator = AcumuloResgateMediator.getInstancia();
	
	private Text txtNrCaixaBonus;
	private Text txtSaldoAtual;
	private Text txtValor;
	private Combo cboTipoResgate;
	private Button btnAcumularResgatar;
    private Button btnVoltar;
    private Button radAcumular;
    private Button radResgatar;
    private Button btnBuscar;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaAcumuloResgateGUI window = new TelaAcumuloResgateGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public TelaAcumuloResgateGUI() {
        mediator = AcumuloResgateMediator.getInstancia();
    }


	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setSize(490, 321);
		shell.setText("Acumulo e Resgate de  Bônus");
		
		/* Botão Buscar */
		btnBuscar = new Button(shell, SWT.NONE);
		btnBuscar.setBounds(220, 239, 90, 25);
		btnBuscar.setText("Buscar");

		/* Botão acumular/resgatar */
		btnAcumularResgatar = new Button(shell, SWT.NONE);
		btnAcumularResgatar.setEnabled(false);
		btnAcumularResgatar.setBounds(23, 239, 151, 25);
		btnAcumularResgatar.setText("Acumular/Resgatar");
		
		/* Botão Voltar */
		btnVoltar = new Button(shell, SWT.NONE);
		btnVoltar.setBounds(359, 239, 75, 25);
		btnVoltar.setText("Voltar");
		
		/* N° Caixa Bonus */ 
		Label lblNrCaixaBonus = new Label(shell, SWT.NONE);
		lblNrCaixaBonus.setBounds(23, 29, 134, 18);
		lblNrCaixaBonus.setText("N° Caixa de Bônus:");
		
		txtNrCaixaBonus = new Text(shell, SWT.BORDER);
		txtNrCaixaBonus.setBounds(176, 26, 134, 21);
		
		/* Tipo de Operação */ 
		Label lblOperacao = new Label(shell, SWT.NONE);
		lblOperacao.setBounds(23, 56, 75, 18);
		lblOperacao.setText("Operação:");
		
		/* Saldo Atual */ 
		Label lblSaldoAtual = new Label(shell, SWT.NONE);
		lblSaldoAtual.setBounds(23, 92, 90, 34);
		lblSaldoAtual.setText("Saldo Atual:");
		
		txtSaldoAtual = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		txtSaldoAtual.setBounds(176, 92, 134, 21);
		
		/* Tipo de Resgate */ 
		Label lblTipoDeResgate = new Label(shell, SWT.NONE);
		lblTipoDeResgate.setBounds(23, 132, 127, 25);
		lblTipoDeResgate.setText("Tipo de Resgate:");
				
		cboTipoResgate = new Combo(shell, SWT.READ_ONLY);
		cboTipoResgate.setBounds(176, 129, 134, 23);
		
		/* Botoes radio Acumular e Resgatar  */ 
		radAcumular = new Button(shell, SWT.RADIO);
		radAcumular.setEnabled(true);
		radAcumular.setBounds(176, 52, 90, 28);
		radAcumular.setText("Acumular");
		
		radResgatar = new Button(shell, SWT.RADIO);
		radResgatar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		radResgatar.setEnabled(true);
		radResgatar.setBounds(316, 54, 90, 25);
		radResgatar.setText("Resgatar");
				/* Valor */ 
		Label lblValor = new Label(shell, SWT.NONE);
		lblValor.setBounds(23, 168, 75, 25);
		lblValor.setText("Valor:");
		
		txtValor = new Text(shell, SWT.BORDER);
		txtValor.setEnabled(false);
		txtValor.setBounds(176, 172, 134, 21);
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 211, 434, 2);

		/* Eventos dos Botões */
		
		/* BUSCAR */
		btnBuscar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String numeroCaixa = txtNrCaixaBonus.getText();
                if (numeroCaixa.isEmpty()) {
                    showMessage("Nr caixa de bonus nao pode esta vazio.");
                    return;
                }
                
                if (!radAcumular.getSelection() && !radResgatar.getSelection()) {
                    showMessage("Selecione uma operação: Acumular ou Resgatar Bônus.");
                    return;
                }
                
                /* Converter a String num long */
                long numeroCaixaLong = Long.parseLong(numeroCaixa);
				
                /* Pesquisar no mediator para trazer o saldo atual */
                double saldo = mediator.buscarSaldoCaixaDeBonus(numeroCaixaLong);
 
                if (saldo >= 0) {
                    txtSaldoAtual.setText(String.valueOf(saldo));
                    txtNrCaixaBonus.setEnabled(false);
                    radAcumular.setEnabled(false);
                    radResgatar.setEnabled(false);
                    btnBuscar.setEnabled(false);
                    
                    /* Redefinir texto do botão de acordo com a operação */
                    if (radAcumular.getSelection()) {
                        btnAcumularResgatar.setText("Acumular");
                        
                    } else if (radResgatar.getSelection()) {
                        cboTipoResgate.setEnabled(true);                      
                        btnAcumularResgatar.setText("Resgatar");
                    }
                    
                    txtValor.setEnabled(true);
                    btnAcumularResgatar.setEnabled(true);
                    
                } else {               
                    showMessage("Caixa de bonus inexistente");
                }
				
				
			}
		});
		
	    /* VOLTAR */
        btnVoltar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
	            /* Limpar todos os campos da tela */
	            txtNrCaixaBonus.setText("");
	            txtSaldoAtual.setText("");
	            cboTipoResgate.deselectAll();
	            txtValor.setText("");
	            
	            /* Habilitar os campos n° da caixa de bônus, operação e o botão Buscar */
	            txtNrCaixaBonus.setEnabled(true);
	            btnBuscar.setEnabled(true);
	            radAcumular.setEnabled(true);
	            radResgatar.setEnabled(true);
	            
	            /* Desabilitar os demais campos */
	            cboTipoResgate.setEnabled(false);

	            cboTipoResgate.setEnabled(false);
	            txtValor.setEnabled(false);
	            btnAcumularResgatar.setText("Acumular/Resgatar");
	        }
		});
        	
		/* ACUMULO OU RESGATE CAIXA BONUS */
        btnAcumularResgatar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String numeroCaixa = txtNrCaixaBonus.getText();
                String valorStr = txtValor.getText();
                if (valorStr.isEmpty()) {
                    showMessage("Digite um valor valido.");
                    return;
                }

                double valor = 0;
                try {
                    valor = Double.parseDouble(valorStr);
                } catch (NumberFormatException ex) {
                    showMessage("Digite um valor valido.");
                    return;
                }

                /* conversão de tipos para uso na chamada dos metodos */
                long numeroCaixaLong = Long.parseLong(numeroCaixa);
                
                if (btnAcumularResgatar.getText().equals("Acumular")) {
                    /* Chamada do método de acumular no mediator */
                    String resultado = mediator.acumularBonus(numeroCaixaLong, valor);
                    if (resultado == null) {
                        showMessage("Acúmulo bem-sucedido.");
                    } else {
                        showMessage("Erro ao acumular: " + resultado);
                    }
                } else if (btnAcumularResgatar.getText().equals("Resgatar")) {
                    /* Obter o tipo de resgate selecionado */
                	//String tipoResgateSelecionado = cboTipoResgate.getText();
                	int tipoResgateIndex = cboTipoResgate.getSelectionIndex();
                	
                	if (tipoResgateIndex == -1) {
                		showMessage("Selecione um tipo de resgate.");
                		return;
                	}               	
                	TipoResgate tipoResgateSelecionado = TipoResgate.values()[tipoResgateIndex];
                	
                    /* Chamada do método de resgaste no mediator */
                    String resultado = mediator.resgatar(numeroCaixaLong, valor, tipoResgateSelecionado);
                    if (resultado == null) {
                        showMessage("Resgate bem-sucedido.");
                        btnVoltar.notifyListeners(SWT.Selection, new Event());
                    } else {
                        showMessage("Erro ao resgatar: " + resultado);
                    }
                }
               
            }
        });
        

/* Opção de 'Tipo de Resgate' preenchida com os 3 tipos definidos no enum */
		TipoResgate[] tiposResgate = TipoResgate.values();
		String[] operacoes = new String[tiposResgate.length];
		
		for(int i = 0; i < tiposResgate.length; i++) {
			operacoes[i] = tiposResgate[i].getDescricao();
		}
		
		cboTipoResgate.setItems(operacoes);
		//cboTipoResgate.setItems(new String[] { "Cash", "Produto", "Serviço" });
        
	}
	
	/* Exibir caixa de mensagem de erros */
	private void showMessage(String message) {
        
        org.eclipse.swt.widgets.MessageBox messageBox = new org.eclipse.swt.widgets.MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
        messageBox.setText("Erro");
        messageBox.setMessage(message);
        messageBox.open();
    }
}
