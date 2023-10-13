package br.gov.cesarschool.poo.telas;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.time.LocalDate;
import javax.swing.JOptionPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import br.gov.cesarschool.poo.bonusvendas.dao.VendedorDAO;
import br.gov.cesarschool.poo.bonusvendas.entidade.Vendedor;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Endereco;
import br.gov.cesarschool.poo.bonusvendas.entidade.geral.Sexo;
import br.gov.cesarschool.poo.bonusvendas.negocio.AcumuloResgateMediator;
import br.gov.cesarschool.poo.bonusvendas.negocio.VendedorMediator;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.ViewForm;

public class TelaCadastrogui {

	protected Shell shlTelaDeCdastro;
	
	VendedorDAO vendedorDAO = new VendedorDAO();
	AcumuloResgateMediator acumuloResgateMediator = new AcumuloResgateMediator();
	VendedorMediator mediator = VendedorMediator.getInstance(vendedorDAO, acumuloResgateMediator);
	
	private Text txtNome;
	private Text txtRenda;
	private Text txtcpf;
	private Text txtLogradouro;
	private Text txtNumero;
	private Text txtComplemento;
	private Text txtCep;
	private Text txtCidade;
	private Text txtEstado;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaCadastrogui window = new TelaCadastrogui();
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
		shlTelaDeCdastro.open();
		shlTelaDeCdastro.layout();
		while (!shlTelaDeCdastro.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		shlTelaDeCdastro = new Shell();
		shlTelaDeCdastro.setSize(606, 323);
		shlTelaDeCdastro.setText("Tela de cadastro");
		
		// Definição do Botão Novo
		Button btnNovo = new Button(shlTelaDeCdastro, SWT.NONE);
		btnNovo.setBounds(10, 223, 90, 30);
		btnNovo.setText("Novo");
		
		// Definição do Botão de IncluirAlterar
		Button btnIncluirAlterar = new Button(shlTelaDeCdastro, SWT.NONE);
		btnIncluirAlterar.setEnabled(false);
		btnIncluirAlterar.setBounds(129, 223, 90, 30);
		btnIncluirAlterar.setText("Incluir");
		
		// Definição do Botão Buscar
		Button btnBuscar = new Button(shlTelaDeCdastro, SWT.NONE);
		btnBuscar.setBounds(252, 223, 90, 30);
		btnBuscar.setText("Buscar");

		// Definição do Botão Cancelar
		Button btnCancelar = new Button(shlTelaDeCdastro, SWT.NONE);
		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(488, 223, 90, 30);
		btnCancelar.setText("Cancelar");
		
		// Definição do Botão Limpar
		Button btnLimpar = new Button(shlTelaDeCdastro, SWT.NONE);
		btnLimpar.setBounds(368, 223, 90, 30);
		btnLimpar.setText("Limpar");
		
		// Definição do Label Nome
		Label lblNome = new Label(shlTelaDeCdastro, SWT.NONE);
		lblNome.setBounds(10, 61, 47, 20);
		lblNome.setText("Nome");
		
		txtNome = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtNome.setTouchEnabled(true);
		txtNome.setEnabled(true);
		txtNome.setBounds(64, 58, 225, 26);

		// Definição do Label Renda
		Label lblRenda = new Label(shlTelaDeCdastro, SWT.NONE);
		lblRenda.setBounds(10, 187, 47, 20);
		lblRenda.setText("Renda");
		
		txtRenda = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtRenda.setTouchEnabled(true);
		txtRenda.setEnabled(true);
		txtRenda.setBounds(64, 184, 118, 26);

		// Definição do Label CPF
		Label lblCpf = new Label(shlTelaDeCdastro, SWT.NONE);
		lblCpf.setText("CPF");
		lblCpf.setBounds(10, 24, 36, 20);
		
		txtcpf = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtcpf.setTouchEnabled(true);
		txtcpf.setEnabled(true);
		txtcpf.setBounds(64, 21, 225, 26);

		// Definição do Label e Botão Check Sexo
		Label lblSexo = new Label(shlTelaDeCdastro, SWT.NONE);
		lblSexo.setTouchEnabled(true);
		lblSexo.setText("Sexo");
		lblSexo.setBounds(10, 105, 47, 20);
		
		Button btnCheckButton = new Button(shlTelaDeCdastro, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		
		Button btnM = new Button(shlTelaDeCdastro, SWT.RADIO);
		btnM.setBounds(64, 105, 36, 20);
		btnM.setText("M");

		Button btnF = new Button(shlTelaDeCdastro, SWT.RADIO);
		btnF.setBounds(146, 105, 36, 20);
		btnF.setText("F");
		
		// Definição do Label e Inserir Data
		Label lblDataDeNascimento = new Label(shlTelaDeCdastro, SWT.NONE);
		lblDataDeNascimento.setText("Data");
		lblDataDeNascimento.setBounds(10, 139, 36, 20);

		DateTime dateTime = new DateTime(shlTelaDeCdastro, SWT.BORDER);
		dateTime.setBounds(64, 139, 118, 28);

		java.time.LocalDate initialDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth() + 1, dateTime.getDay());
		
		// Definição do Label Endereço
		Label lblEndereo = new Label(shlTelaDeCdastro, SWT.NONE);
		lblEndereo.setText("Endereço");
		lblEndereo.setBounds(418, 24, 70, 20);

		// Definição do Label Logradouro
		txtLogradouro = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtLogradouro.setText("Logradouro");
		txtLogradouro.setToolTipText("");
		txtLogradouro.setTouchEnabled(true);
		txtLogradouro.setEnabled(true);
		txtLogradouro.setBounds(339, 58, 239, 26);

		// Definição do Label Número
		txtNumero = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtNumero.setText("Número");
		txtNumero.setTouchEnabled(true);
		txtNumero.setToolTipText("");
		txtNumero.setEnabled(true);
		txtNumero.setBounds(339, 102, 70, 26);
		
		// Definição do Label Complemento
		txtComplemento = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtComplemento.setText("Complemento");
		txtComplemento.setTouchEnabled(true);
		txtComplemento.setToolTipText("");
		txtComplemento.setEnabled(true);
		txtComplemento.setBounds(418, 102, 160, 26);

		// Definição do Texto CEP
		txtCep = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtCep.setTouchEnabled(true);
		txtCep.setToolTipText("");
		txtCep.setText("CEP");
		txtCep.setEnabled(true);
		txtCep.setBounds(339, 136, 239, 26);

		// Definição do Texto Cidade
		txtCidade = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtCidade.setTouchEnabled(true);
		txtCidade.setToolTipText("");
		txtCidade.setText("Cidade");
		txtCidade.setEnabled(true);
		txtCidade.setBounds(340, 181, 148, 26);

		// Definição do Texto Estado
		txtEstado = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtEstado.setTouchEnabled(true);
		txtEstado.setToolTipText("");
		txtEstado.setText("Estado");
		txtEstado.setEnabled(true);
		txtEstado.setBounds(496, 181, 82, 26);

		// Código do Botão de IncluirAlterar do Exemplo
/*		btnIncluirAlterar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Vendedor ent = new Vendedor(txtcpf.getText(), txtNome.getText(),
						Double.parseDouble(txtRenda.getText()));
				
				String msg = null;
				if (btnIncluirAlterar.getText().equals("Incluir")) {
					msg = mediator.incluir(ent);
				} else {
					msg = mediator.alterar(ent);
				}
				if (msg != null) {
					JOptionPane.showMessageDialog(null, 
					msg);					
				} else {
					btnIncluirAlterar.setEnabled(false);
					btnCancelar.setEnabled(false);
					txtNome.setEnabled(false);
					txtRenda.setEnabled(false);
					btnNovo.setEnabled(true);
					btnBuscar.setEnabled(true);
					txtCodigo.setEnabled(true);
					txtCodigo.setText("");
					txtRenda.setText("");
					txtNome.setText("");
					btnIncluirAlterar.setText("Incluir");
				}
			}
		}); */

		// Código do Botão de Buscar do Exemplo
/*		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Entidade ent = TelaExemploCadastro.mediator.buscar(txtCodigo.getText());
				if (ent == null) {
					JOptionPane.showMessageDialog(null, 
					"Entidade n o existente!");
				} else {
					txtNome.setText(ent.getNome());
					txtRenda.setText(ent.getRenda() + "");
					btnIncluirAlterar.setText("Alterar");
					btnIncluirAlterar.setEnabled(true);
					btnCancelar.setEnabled(true);
					txtNome.setEnabled(true);
					txtRenda.setEnabled(true);
					btnNovo.setEnabled(false);
					btnBuscar.setEnabled(false);
					txtCodigo.setEnabled(false);
				}			
			}
		}); */
		
		// Código do Botão de Novo WIP
/*		btnNovo.addMouseListener(new MouseAdapter() { 
		    @Override
		    public void mouseDown(MouseEvent e) {
		        if (txtcpf.getText().isEmpty() || txtNome.getText().isEmpty() || txtRenda.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
		        } else {
		            String cpf = txtcpf.getText();
		            String nome = txtNome.getText();
		            double renda = Double.parseDouble(txtRenda.getText());
		            Sexo sexo = btnM.getSelection() ? Sexo.MASCULINO : Sexo.FEMININO;
		            LocalDate dataNascimento = LocalDate.of(dateTime.getYear(), dateTime.getMonth() + 1, dateTime.getDay());
		            Endereco endereco = new Endereco(txtEndereco.getText(), 0, nome, nome, nome, nome, nome);
		            
		            Vendedor novoVendedor = new Vendedor(cpf, nome, sexo, dataNascimento, renda, endereco);
		            
		            TelaCadastrogui.mediator.salvarVendedor(novoVendedor);
		            
		            txtcpf.setText("");
		            txtNome.setText("");
		            txtRenda.setText("");
		            txtEndereco.setText("");
		            btnM.setSelection(false);
		            btnF.setSelection(false);
		            
		            btnCancelar.setEnabled(false);
		            txtNome.setEnabled(false);
		            txtRenda.setEnabled(false);
		            btnNovo.setEnabled(true);
		        }
		    }
		}); */

		// Código do Botão de Cancelar WIP
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				btnCancelar.setEnabled(false);
				txtNome.setEnabled(false);
				txtRenda.setEnabled(false);
				btnNovo.setEnabled(true);
				txtRenda.setText("");
				txtNome.setText("");
			}
		});
		
		// Código do Botão de Limpar
		btnLimpar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtcpf.setText("");
				txtNome.setText("");
				txtRenda.setText("");
				btnM.setSelection(false);
				btnF.setSelection(false);
				dateTime.setDate(initialDate.getYear(), initialDate.getMonthValue() - 1, initialDate.getDayOfMonth());
				txtLogradouro.setText("Logradouro");
				txtNumero.setText("Número");
				txtComplemento.setText("Complemento");
				txtCep.setText("CEP");
				txtCidade.setText("Cidade");
				txtEstado.setText("Estado");

			}
		});
		
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
		btnF.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        if (btnF.getSelection()) {
		            // Botão "F" está selecionado
		            btnM.setSelection(false); // Desmarca o botão "M"
		        }
		    }
		});
	}
}