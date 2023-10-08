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
		
		Button btnNovo = new Button(shlTelaDeCdastro, SWT.NONE);
		btnNovo.setBounds(135, 223, 90, 30);
		btnNovo.setText("Novo");
		
		Label lblNome = new Label(shlTelaDeCdastro, SWT.NONE);
		lblNome.setBounds(10, 61, 47, 20);
		lblNome.setText("Nome");
		
		txtNome = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtNome.setTouchEnabled(true);
		txtNome.setEnabled(true);
		txtNome.setBounds(64, 58, 225, 26);
		
		Label lblRenda = new Label(shlTelaDeCdastro, SWT.NONE);
		lblRenda.setBounds(10, 187, 47, 20);
		lblRenda.setText("Renda");
		
		txtRenda = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtRenda.setTouchEnabled(true);
		txtRenda.setEnabled(true);
		txtRenda.setBounds(64, 184, 118, 26);
		
		Button btnCancelar = new Button(shlTelaDeCdastro, SWT.NONE);

		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(245, 223, 90, 30);
		btnCancelar.setText("Cancelar");
		
		Button btnLimpar = new Button(shlTelaDeCdastro, SWT.NONE);

		btnLimpar.setBounds(363, 223, 90, 30);
		btnLimpar.setText("Limpar");
		
		Label lblCpf = new Label(shlTelaDeCdastro, SWT.NONE);
		lblCpf.setText("CPF");
		lblCpf.setBounds(10, 24, 36, 20);
		
		txtcpf = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtcpf.setTouchEnabled(true);
		txtcpf.setEnabled(true);
		txtcpf.setBounds(64, 21, 225, 26);
		
		Label lblSexo = new Label(shlTelaDeCdastro, SWT.NONE);
		lblSexo.setTouchEnabled(true);
		lblSexo.setText("Sexo");
		lblSexo.setBounds(10, 105, 47, 20);
		
		Label lblDataDeNascimento = new Label(shlTelaDeCdastro, SWT.NONE);
		lblDataDeNascimento.setText("Data");
		lblDataDeNascimento.setBounds(10, 139, 36, 20);
		
		Label lblEndereo = new Label(shlTelaDeCdastro, SWT.NONE);
		lblEndereo.setText("Endereço");
		lblEndereo.setBounds(383, 24, 70, 20);
		
		txtLogradouro = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtLogradouro.setText("Logradouro");
		txtLogradouro.setToolTipText("");
		txtLogradouro.setTouchEnabled(true);
		txtLogradouro.setEnabled(true);
		txtLogradouro.setBounds(305, 58, 239, 26);
		
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
		
		DateTime dateTime = new DateTime(shlTelaDeCdastro, SWT.BORDER);
		dateTime.setBounds(64, 139, 118, 28);

		java.time.LocalDate initialDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth() + 1, dateTime.getDay());
		
		txtNumero = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtNumero.setText("Número");
		txtNumero.setTouchEnabled(true);
		txtNumero.setToolTipText("");
		txtNumero.setEnabled(true);
		txtNumero.setBounds(305, 102, 80, 26);
		
		txtComplemento = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtComplemento.setText("Complemento");
		txtComplemento.setTouchEnabled(true);
		txtComplemento.setToolTipText("");
		txtComplemento.setEnabled(true);
		txtComplemento.setBounds(391, 102, 153, 26);
		
		txtCep = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtCep.setTouchEnabled(true);
		txtCep.setToolTipText("");
		txtCep.setText("CEP");
		txtCep.setEnabled(true);
		txtCep.setBounds(305, 139, 239, 26);
		
		txtCidade = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtCidade.setTouchEnabled(true);
		txtCidade.setToolTipText("");
		txtCidade.setText("Cidade");
		txtCidade.setEnabled(true);
		txtCidade.setBounds(305, 184, 148, 26);
		
		txtEstado = new Text(shlTelaDeCdastro, SWT.BORDER);
		txtEstado.setTouchEnabled(true);
		txtEstado.setToolTipText("");
		txtEstado.setText("Estado");
		txtEstado.setEnabled(true);
		txtEstado.setBounds(462, 184, 82, 26);
		
		
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
		
		// BOTÃO DE LIMPAR
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