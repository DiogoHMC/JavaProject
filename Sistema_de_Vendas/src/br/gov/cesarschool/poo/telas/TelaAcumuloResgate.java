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

public class TelaAcumuloResgate {

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
			TelaAcumuloResgate window = new TelaAcumuloResgate();
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
	
	public TelaAcumuloResgate() {
        mediator = AcumuloResgateMediator.getInstancia();
    }


	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setSize(450, 300);
		shell.setText("Acumulo e Resgate de  Bônus");
		
		/* Botão Buscar */
		btnBuscar = new Button(shell, SWT.NONE);
		btnBuscar.setBounds(334, 24, 90, 25);
		btnBuscar.setText("Buscar");

		/* Botão acumular/resgatar */
		btnAcumularResgatar = new Button(shell, SWT.NONE);
		btnAcumularResgatar.setEnabled(false);
		btnAcumularResgatar.setBounds(107, 226, 114, 25);
		btnAcumularResgatar.setText("Acumular/Resgatar");
		
		/* Botão Voltar */
		btnVoltar = new Button(shell, SWT.NONE);
		btnVoltar.setBounds(254, 226, 75, 25);
		btnVoltar.setText("Voltar");
		
		/* N° Caixa Bonus */ 
		Label lblNrCaixaBonus = new Label(shell, SWT.NONE);
		lblNrCaixaBonus.setBounds(23, 26, 114, 15);
		lblNrCaixaBonus.setText("N° Caixa de Bônus :");
		
		txtNrCaixaBonus = new Text(shell, SWT.BORDER);
		txtNrCaixaBonus.setBounds(138, 26, 134, 21);
		
		/* Tipo de Operação */ 
		Label lblOperacao = new Label(shell, SWT.NONE);
		lblOperacao.setBounds(23, 56, 55, 15);
		lblOperacao.setText("Operação :");
		
		/* Saldo Atual */ 
		Label lblSaldoAtual = new Label(shell, SWT.NONE);
		lblSaldoAtual.setBounds(23, 81, 75, 15);
		lblSaldoAtual.setText("Saldo Atual:");
		
		txtSaldoAtual = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		txtSaldoAtual.setBounds(138, 77, 134, 21);
		
		/* Tipo de Resgate */ 
		Label lblTipoDeResgate = new Label(shell, SWT.NONE);
		lblTipoDeResgate.setBounds(23, 132, 84, 15);
		lblTipoDeResgate.setText("Tipo de Resgate:");
				
		cboTipoResgate = new Combo(shell, SWT.READ_ONLY);
		cboTipoResgate.setBounds(138, 127, 134, 23);
		
		/* Valor */ 
		Label lblValor = new Label(shell, SWT.NONE);
		lblValor.setBounds(23, 168, 55, 15);
		lblValor.setText("Valor :");
		
		txtValor = new Text(shell, SWT.BORDER);
		txtValor.setEnabled(false);
		txtValor.setBounds(138, 162, 134, 21);
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 211, 434, 2);

		/* EVENTO BOTÃO BUSCAR */
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
		
/* Evento do botão Acumular/Resgatar */
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

                if (btnAcumularResgatar.getText().equals("Acumular")) {
                    /* Chamada do método de acumular no mediator */
                    String resultado = mediator.acumular(numeroCaixa, valor);
                    if (resultado == null) {
                        showMessage("Acúmulo bem-sucedido.");
                    } else {
                        showMessage("Erro ao acumular: " + resultado);
                    }
                } else if (btnAcumularResgatar.getText().equals("Resgatar")) {
                    /* Verificar o tipo de resgate selecionado */
                    String tipoResgate = cboTipoResgate.getText();

                    /* Chamada do método de resgaste no mediator */
                    String resultado = mediator.resgatar(numeroCaixa, valor, tipoResgate);
                    if (resultado == null) {
                        showMessage("Resgate bem-sucedido.");
                    } else {
                        showMessage("Erro ao resgatar: " + resultado);
                    }
                }
                btnVoltar.notifyListeners(SWT.Selection, new Event());
            }
        });
        
/* EVENTO BOTÃO VOLTAR */
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
        
/* Evento para Tipo de Resgate */
        /* Opção de resgate preenchida com os 3 tipos de resgate do enum */
		TipoResgate[] tiposResgate = TipoResgate.values();
		String[] operacoes = new String[tiposResgate.length];
		for(int i = 0; i < tiposResgate.length; i++) {
			operacoes[i] = tiposResgate[i].getDescricao();
		}
		cboTipoResgate.setItems(operacoes);
		
		Button radAcumular = new Button(shell, SWT.RADIO);
		radAcumular.setEnabled(true);
		radAcumular.setBounds(138, 55, 90, 16);
		radAcumular.setText("Acumular");
		
		Button radResgatar = new Button(shell, SWT.RADIO);
		radResgatar.setEnabled(true);
		radResgatar.setBounds(239, 55, 90, 16);
		radResgatar.setText("Resgatar");
		//cboTipoResgate.setItems(new String[] { "Cash", "Produto", "Serviço" });
		
        
	}
	
	private void showMessage(String message) {
        // Exibir uma caixa de mensagem com o texto fornecido
        org.eclipse.swt.widgets.MessageBox messageBox = new org.eclipse.swt.widgets.MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
        messageBox.setText("Erro");
        messageBox.setMessage(message);
        messageBox.open();
    }
}
