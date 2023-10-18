package br.gov.cesarschool.poo.bonusvendas.tela;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.time.DateTimeException;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Combo;

import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;
import br.gov.cesarschool.poo.bonusvendas.negocio.ResultadoInclusaoVendedor;
import br.gov.cesarschool.poo.bonusvendas.negocio.VendedorMediator;

public class TelaManutencaoVendedorGUI {

	protected Shell shlTelaManutVendedor;
	
	static VendedorDAO vendedorDAO = new VendedorDAO();
	static AcumuloResgateMediator acumuloResgateMediator = new AcumuloResgateMediator();
	static VendedorMediator mediator = VendedorMediator.getInstancia(vendedorDAO, acumuloResgateMediator);
	
	private Text txtNome;
	private Text txtRenda;
	private Text txtcpf;
	private Text txtLogradouro;
	private Text txtNumero;
	private Text txtComplemento;
	private Text txtCep;
	private Text txtCidade;
	private Text txtPais;
	private Combo txtEstado;
	private Button btnM;
	private Button btnF;
	private DateTime dateTime;
	
	private Button btnIncluirAlterar;
	private Button btnCancelar;
	private Button btnNovo;
	private Button btnBuscar;
	
	/* Flag para definir ação, Incluir ou Alterar, para o botão btnIncluirAlterar */
	private int acaoFlag = 0;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaManutencaoVendedorGUI window = new TelaManutencaoVendedorGUI();
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
		shlTelaManutVendedor.open();
		shlTelaManutVendedor.layout();
		while (!shlTelaManutVendedor.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlTelaManutVendedor = new Shell();
		shlTelaManutVendedor.setSize(608, 395);
		shlTelaManutVendedor.setText("Tela de cadastro");
		
		// Definição do Botão de IncluirAlterar
		btnIncluirAlterar = new Button(shlTelaManutVendedor, SWT.NONE);
		btnIncluirAlterar.setEnabled(false);
		btnIncluirAlterar.setBounds(75, 301, 112, 30);
		btnIncluirAlterar.setText("Incluir/Alterar");
		
		// Definição do Botão Novo
		btnNovo = new Button(shlTelaManutVendedor, SWT.NONE);
		btnNovo.setBounds(216, 29, 90, 30);
		btnNovo.setText("Novo");	

		// Definição do Botão Buscar
		btnBuscar = new Button(shlTelaManutVendedor, SWT.NONE);
		btnBuscar.setBounds(318, 29, 90, 30);
		btnBuscar.setText("Buscar");
				
		// Definição do Botão Limpar
		Button btnLimpar = new Button(shlTelaManutVendedor, SWT.NONE);
		btnLimpar.setBounds(437, 301, 90, 30);
		btnLimpar.setText("Limpar");
		btnLimpar.setEnabled(true);				
       
		// Definição do Botão Cancelar
		btnCancelar = new Button(shlTelaManutVendedor, SWT.NONE);
		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(267, 301, 90, 30);
		btnCancelar.setText("Cancelar");

		// Definição CPF
		Label lblCpf = new Label(shlTelaManutVendedor, SWT.NONE);
		lblCpf.setText("CPF");
		lblCpf.setBounds(10, 39, 36, 20);
		
		txtcpf = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtcpf.setEnabled(true);
		txtcpf.setEnabled(true);
		txtcpf.setBounds(52, 32, 158, 26);
		
		// Definição Nome
		Label lblNome = new Label(shlTelaManutVendedor, SWT.NONE);
		lblNome.setBounds(10, 71, 112, 20);
		lblNome.setText("Nome Completo");
		
		txtNome = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtNome.setEnabled(false);
		txtNome.setBounds(10, 97, 313, 26);
		
		// Definição Sexo
		Label lblSexo = new Label(shlTelaManutVendedor, SWT.NONE);
		lblSexo.setEnabled(true);
		lblSexo.setText("Sexo");
		lblSexo.setBounds(10, 129, 47, 20);
		
		btnM = new Button(shlTelaManutVendedor, SWT.RADIO);
		btnM.setEnabled(false);
		btnM.setBounds(64, 129, 36, 20);
		btnM.setText("M");
		
		// CONDIÇÃO PARA QUE NÃO SEJA POSSÍVEL APERTAR AMBOS M E F
		btnM.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        if (btnM.getSelection()) {
		            // Botão "M" está selecionado
		            btnF.setSelection(false); // Desmarca o botão "F"
		        }
		    }
		});

		btnF = new Button(shlTelaManutVendedor, SWT.RADIO);
		btnF.setBounds(146, 129, 36, 20);
		btnF.setEnabled(false);
		btnF.setText("F");
		
		btnF.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        if (btnF.getSelection()) {
		            // Botão "F" está selecionado
		            btnM.setSelection(false); // Desmarca o botão "M"
		        }
		    }
		});
		
		// Definição Data de Nascimento
		Label lblDataDeNascimento = new Label(shlTelaManutVendedor, SWT.NONE);
		lblDataDeNascimento.setText("Data de nascimento");
		lblDataDeNascimento.setBounds(10, 167, 133, 26);

		// DateTime dateTime = new DateTime(shlTelaManutVendedor, SWT.BORDER);
		dateTime = new DateTime(shlTelaManutVendedor, SWT.BORDER);
		dateTime.setEnabled(false);
		dateTime.setBounds(161, 163, 102, 30);
			
		// Definição Renda
		Label lblRenda = new Label(shlTelaManutVendedor, SWT.NONE);
		lblRenda.setBounds(10, 205, 47, 20);
		lblRenda.setText("Renda");
		
		txtRenda = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtRenda.setEnabled(false);
		txtRenda.setBounds(64, 202, 165, 26);
		
		// Definição Endereço
		Label lblEndereo = new Label(shlTelaManutVendedor, SWT.NONE);
		lblEndereo.setText("Endereço");
		lblEndereo.setBounds(446, 71, 70, 20);

		// Definição Logradouro
		txtLogradouro = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtLogradouro.setMessage("Logradouro");
		txtLogradouro.setToolTipText("");
		txtLogradouro.setEnabled(false);
		txtLogradouro.setBounds(343, 97, 239, 26);

		// Definição Número
		txtNumero = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtNumero.setMessage("N°");
		txtNumero.setEnabled(false);
		txtNumero.setToolTipText("");
		txtNumero.setBounds(343, 129, 47, 26);
		
		// Definição Complemento
		txtComplemento = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtComplemento.setMessage("Complemento");
		txtComplemento.setEnabled(false);
		txtComplemento.setToolTipText("");
		txtComplemento.setBounds(396, 129, 186, 26);

		// Definição CEP
		txtCep = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtCep.setEnabled(false);
		txtCep.setToolTipText("");
		txtCep.setMessage("CEP");
		txtCep.setBounds(343, 163, 239, 26);

		// Definição Cidade
		txtCidade = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtCidade.setEnabled(false);
		txtCidade.setToolTipText("");
		txtCidade.setMessage("Cidade");
		txtCidade.setBounds(343, 202, 148, 26);

		// Definição País
		txtPais = new Text(shlTelaManutVendedor, SWT.BORDER);
		txtPais.setToolTipText("");
		txtPais.setMessage("País");
		txtPais.setEnabled(false);
		txtPais.setText("Brasil");
		txtPais.setBounds(343, 245, 239, 26);
			
		/* Combo para seleção do estado brasileiro */
		txtEstado = new Combo(shlTelaManutVendedor, SWT.DROP_DOWN |SWT.NONE);
		txtEstado.setEnabled(false);
		txtEstado.setBounds(499, 202, 82, 26);
		
		/* Lista dos estados brasileiros - ordem alfabética */
        String[] estados = {
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
            "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
            "RS", "RO", "RR", "SC", "SP","SE", "TO"
        };
        
        txtEstado.setItems(estados);
		
        txtEstado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedState = txtEstado.getText();
			}
		});
        
        
/* EVENTO BOTAO BUSCAR - Para alterar cadastro vendedor */ 
		btnBuscar.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	acaoFlag = 2;
		        String cpf = txtcpf.getText();

		        if (cpf.isEmpty() || cpf == null) {	        	
		            JOptionPane.showMessageDialog(null, "CPF nao informado.");
		            return;
		        } else {
		        	
		        	/* Validação CPF */
		        	if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
		        	    JOptionPane.showMessageDialog(null, "Formato do campo CPF invalido. Deve conter 11 digitos.");
		        	    return;
		        	}
		        
			        VendedorMediator vendedorMediator = VendedorMediator.getInstancia(vendedorDAO, acumuloResgateMediator);
		            Vendedor vendedorEncontrado = vendedorMediator.buscar(cpf);
	
			        if (vendedorEncontrado  != null) {		        	
				    	/* Habilita/desabilita os botoes */ 					
						btnIncluirAlterar.setEnabled(true);
				    	btnCancelar.setEnabled(true);
				    	btnIncluirAlterar.setText("Alterar");	
				    	btnNovo.setEnabled(false);
				    	btnBuscar.setEnabled(false);
				    	
				    	/* Habilita/desabilita campos para preenchimento */ 
				    	txtcpf.setEnabled(false);
				    	txtNome.setEnabled(true);
				    	btnM.setEnabled(true);
				    	btnF.setEnabled(true);
				    	dateTime.setEnabled(true);
				    	txtRenda.setEnabled(true);
				    	txtLogradouro.setEnabled(true);
				    	txtNumero.setEnabled(true);
				    	txtComplemento.setEnabled(true);
				    	txtCep.setEnabled(true);
				    	txtCidade.setEnabled(true);
				    	txtEstado.setEnabled(true);	   
			        	
			            /* Preenche os campos com os dados do vendedor salvos */
			            txtNome.setText(vendedorEncontrado.getNomeCompleto());
			            txtRenda.setText(Double.toString(vendedorEncontrado.getRenda()));
				    	
				    	LocalDate dataDeNascimento = vendedorEncontrado.getDataNascimento();
				    	dateTime.setDate(dataDeNascimento.getYear(), dataDeNascimento.getMonthValue() - 1, dataDeNascimento.getDayOfMonth());
				    	
				    	txtLogradouro.setText(vendedorEncontrado.getEndereco().getLogradouro());
				    	txtNumero.setText(String.valueOf(vendedorEncontrado.getEndereco().getNumero()));
				    	txtComplemento.setText(vendedorEncontrado.getEndereco().getComplemento());
				    	txtCep.setText(vendedorEncontrado.getEndereco().getCep());
				    	txtCidade.setText(vendedorEncontrado.getEndereco().getCidade());
				    	txtEstado.setText(vendedorEncontrado.getEndereco().getEstado());	   
				    	
				    	if (vendedorEncontrado.getSexo() == Sexo.MASCULINO) {
				    		btnM.setSelection(true);
			                btnF.setSelection(false);
		                } else if (vendedorEncontrado.getSexo() == Sexo.FEMININO) {
		                	btnM.setSelection(false);
		                    btnF.setSelection(true);
		                }	            
			        } else {
			            JOptionPane.showMessageDialog(null, "Vendedor inexistente");
			        }
		        }
		    }
		});
		
// EVENTO do Botão de Limpar
        btnLimpar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	if (acaoFlag == 0) {  // Limpar só o campo CPF
            		txtcpf.setText("");
            	} else {
	                txtNome.setText("");
	                txtRenda.setText("");
	                btnM.setSelection(false);
	                btnF.setSelection(false);
	                dateTime.setDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());
	                txtLogradouro.setText("");
	                txtNumero.setText("");
	                txtComplemento.setText("");
	                txtCep.setText("");
	                txtCidade.setText("");
	                txtEstado.setText("");
	                //txtPais.setText("");
            	}
            }
        });
				
		
/* EVENTO BOTAO INCLUIRALTERAR - Para inserir novo cadastro vendedor */       	
        btnIncluirAlterar.addSelectionListener(new SelectionAdapter() {
    		    @Override
    		    public void widgetSelected(SelectionEvent e) {
    		        String cpf = txtcpf.getText();
    		        String nome = txtNome.getText();
    		        String rendaStr = txtRenda.getText();
    		        String logradouro = txtLogradouro.getText();
    		        String numeroStr = txtNumero.getText();
    		        String complemento = txtComplemento.getText();
    		        String cep = txtCep.getText();
    		        String cidade = txtCidade.getText();
    		        String estado = txtEstado.getText();
    		        String pais = txtPais.getText();
    		        boolean sexoM = btnM.getSelection();
    		        boolean sexoF = btnF.getSelection();
    		        LocalDate dataNascimento = LocalDate.of(dateTime.getYear(), dateTime.getMonth() + 1, dateTime.getDay());

    		        if (cpf.isEmpty() || nome.isEmpty() || rendaStr.isEmpty() || logradouro.isEmpty() ||
    		            numeroStr.isEmpty() || complemento.isEmpty() || cep.isEmpty() || cidade.isEmpty() ||
    		            estado.isEmpty() || pais.isEmpty() || (!sexoM && !sexoF)) {

    		            JOptionPane.showMessageDialog(null, "Campos obrigatórios faltando.");
    		            return;
    		        }

		        	/* Validação CPF */
		        	if (cpf.length() != 11 || !cpf.matches("\\d{11}")) {
		        	    JOptionPane.showMessageDialog(null, "Formato do campo CPF invalido. Deve conter 11 digitos.");
		        	    return;
		        	}
		        	
		        	/* Validação da Data de Nascimento */
		        	int day = dateTime.getDay();
		        	int month = dateTime.getMonth() + 1; 
		        	int year = dateTime.getYear();

		        	try {
		        	    LocalDate.of(year, month, day); 
		        	} catch (DateTimeException ex) {
		        	    JOptionPane.showMessageDialog(null, "Formato do campo Data de Nascimento invalido. Use o formato dd/mm/yyyy.");
		        	    return;
		        	}

		        	/* Validação Verificação da Renda */
		        	try {
		        	    double renda = Double.parseDouble(rendaStr);
		        	} catch (NumberFormatException ex) {
		        	    JOptionPane.showMessageDialog(null, "Formato do campo Renda invalido. Use um numero decimal valido com ponto como separador.");
		        	    return;
		        	}

		        	/* Validação Verificação do Número */
		        	if (numeroStr.length() > 7 || !numeroStr.matches("\\d{1,7}")) {
		        	    JOptionPane.showMessageDialog(null, "Formato do campo Numero invalido. Deve ser um numero inteiro com até 7 digitos.");
		        	    return;
		        	}

		        	/* Validação do CEP */
		        	if (!cep.matches("\\d{2}\\.\\d{3}-\\d{2}|\\d{8}")) {
		        	    JOptionPane.showMessageDialog(null, "Formato do campo CEP invalido. Use o formato 99.999-99 ou 99999999.");
		        	    return;
		        	}

		            Sexo sexo = null;
		            if (btnM.getSelection()) {
		                sexo = Sexo.MASCULINO;
		            } else if (btnF.getSelection()) {
		                sexo = Sexo.FEMININO;
		            }

		            if (sexo == null) {
		                JOptionPane.showMessageDialog(null, "Selecione o sexo do vendedor.");
		                return;
		            }
		            
		            double renda = Double.parseDouble(rendaStr);
		            Endereco endereco = new Endereco(logradouro, Integer.parseInt(numeroStr), complemento, cep, cidade, estado, pais);

		            /* Verificar ação, Incluir ou Alterar, para o evento  */
		            if (acaoFlag == 1) {  // Incluir
		            	/* Método inserir do VendedorMediator */
    		            Vendedor novoVendedor = new Vendedor(cpf, nome, sexo, dataNascimento, renda, endereco);
    		            ResultadoInclusaoVendedor resultado = mediator.incluir(novoVendedor);

    		            if (resultado.getNumeroCaixaDeBonus() > 0) {
    		                JOptionPane.showMessageDialog(null, "Vendedor incluido com sucesso. Numero do caixa de bonus: " + resultado.getNumeroCaixaDeBonus());
    		            } else {
    		                JOptionPane.showMessageDialog(null, "Erro ao incluir o vendedor: " + resultado.getMensagemErroValidacao());
    		            }
		            } else {  // Alterar
		            	/* Método alterar do VendedorMediator */
				        Vendedor vendedor = new Vendedor(cpf, nome, sexo, dataNascimento, renda, endereco);
				        String resultado = mediator.alterar(vendedor);

				        if (resultado == null) {
				            JOptionPane.showMessageDialog(null, "Dados vendedor alterado com sucesso!");
				        } else {
				            JOptionPane.showMessageDialog(null, "Erro ao alterar o vendedor: " + resultado);
				        }
		            	
		            }
    		            
    		    }
    		});
            
/* EVENTO BOTAO NOVO - Habilitar campos para preenchimento */
		btnNovo.addSelectionListener(new SelectionAdapter() {
 		    @Override
		    public void widgetSelected(SelectionEvent e) { 
 		    	acaoFlag = 1;
		        String cpf = txtcpf.getText();

		        if (cpf.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "CPF nao informado");
		            return;
		        }
		        
		        Vendedor vendedor = mediator.buscar(cpf);    	
		    	if (vendedor != null) {
					JOptionPane.showMessageDialog(null, "CPF ja existe!");
				} else {
					
			    	/* Habilita campos para preenchimento */ 					
					btnIncluirAlterar.setEnabled(true);
			    	btnCancelar.setEnabled(true);
			    	btnIncluirAlterar.setText("Incluir");		    	

			    	txtcpf.setEnabled(false);
			    	txtNome.setEnabled(true);
			    	btnM.setEnabled(true);
			    	btnF.setEnabled(true);
			    	dateTime.setEnabled(true);
			    	txtRenda.setEnabled(true);
			    	txtLogradouro.setEnabled(true);
			    	txtNumero.setEnabled(true);
			    	txtComplemento.setEnabled(true);
			    	txtCep.setEnabled(true);
			    	txtCidade.setEnabled(true);
			    	txtEstado.setEnabled(true);	    	
				}
		    }
        });

  
	}
}
