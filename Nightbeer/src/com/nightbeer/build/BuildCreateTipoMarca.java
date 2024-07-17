package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.text.AbstractDocument;

import javax.swing.*;

import com.nightbeer.dao.brandsDAO;
import com.nightbeer.dao.typesDAO;

public class BuildCreateTipoMarca {
    private BuildMethods buildMethod = new BuildMethods();
    private Color colorTextWhite = buildMethod.colorTextWhite;
    private Color colorTextBlack = buildMethod.colorTextBlack;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;
    
    private Color colorWhiteClear = buildMethod.colorWhiteClear;

    private Color colorButtonLightGrey = buildMethod.colorButtonLightGrey;
    private Color colorButtonClose = buildMethod.colorButtonClose;
    
    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
	
	private JPanel containerNavBar;
	private JButton buttonClose;
	private JButton buttonGoBack;
	
	private JPanel containerMain;

	private JPanel containerTypes;
	private JLabel labelTextTypeSelectedTitle;
	private JComboBox<String> comboBoxTypes;
	private JLabel labelTextTypeTitle;
	private JTextField textfieldType;
	private JPanel containerButtonsTypes;
	private JButton buttonTypeNew;
	private JButton buttonTypeDel;
	private JButton buttonTypeSave;
	private JButton buttonTypeEdit;

	private JPanel containerBrands;
	private JLabel labelTextBrandSelectedTitle;
	private JComboBox<String> comboBoxBrand;
	private JLabel labelTextBrandTitle;
	private JTextField textfieldBrand;
	private JPanel containerButtonsBrands;
	private JButton buttonBrandNew;
	private JButton buttonBrandDel;
	private JButton buttonBrandSave;
	private JButton buttonBrandEdit;
 
    public void listTypes() throws SQLException{
        typesDAO tDao = new typesDAO();
        List<String> tipos = tDao.listTypes(); 
        comboBoxTypes.removeAllItems(); 
        comboBoxTypes.addItem("");
        
        tipos.forEach(tipo -> comboBoxTypes.addItem(tipo)); 
    }
     
    public void listBrands() throws SQLException {
    	brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listBrand(); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("");
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
    
    public void listBrandsForTypes(String tipo) throws SQLException {
    	brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listBrandsForTypes(tipo); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("");
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
	
	public JPanel contentPaneCreateTypesAndBrands(JFrame frame) throws SQLException {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(buildMethod.createResponsive(40, 30));
        contentPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, colorTextBlack));
        
        contentPane.add(headerContainer(frame), BorderLayout.NORTH);
        contentPane.add(mainContainer(), BorderLayout.CENTER);
        
        listBrands();
        listTypes();
        return contentPane;
	}
	 
	public JPanel headerContainer(JFrame frame) {
		containerNavBar = buildMethod.createPanel(100, 5, new BorderLayout(), colorBackgroundWhite, 0,0,25,0);
		buttonClose = buildMethod.createButton("X", 2.2, 2.5, SwingConstants.CENTER, colorTextWhite, colorButtonClose);
		buttonClose.addActionListener(e -> frame.dispose());
		
		buttonGoBack = buildMethod.createButton("<", 2.2, 2.5, SwingConstants.CENTER, colorTextWhite, colorButtonLightGrey);
		buttonGoBack.setVisible(false);
		buttonGoBack.addActionListener(e -> returnViewer());
		
		containerNavBar.add(buttonClose, BorderLayout.EAST);
		containerNavBar.add(buttonGoBack, BorderLayout.WEST);

		return containerNavBar;
	}
	
	public JPanel mainContainer() {
		containerMain = new JPanel(new GridLayout(1,2));
		containerMain.add(containerTypes());
		containerMain.add(containerBrands());
		return containerMain;
	}
	
	@SuppressWarnings("unchecked")
	public JPanel containerTypes() {
		containerTypes = buildMethod.createPanel(0, 0, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		
		labelTextTypeSelectedTitle = buildMethod.createLabel("Selecione o tipo: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		comboBoxTypes = (JComboBox<String>) buildMethod.createComboBox("", 15, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
		
		labelTextTypeTitle = buildMethod.createLabel("Tipo: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		textfieldType = buildMethod.createTextField("", 15, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
		textfieldType.setEnabled(false);
		
		((AbstractDocument) textfieldType.getDocument()).setDocumentFilter(new LimitDocumentFilter(100));
		
		containerButtonsTypes = buildMethod.createPanel(15, 4, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		buttonTypeNew = buildMethod.createButton("Novo", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonTypeDel = buildMethod.createButton("Deletar", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonTypeSave = buildMethod.createButton("Salvar", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonTypeSave.setEnabled(false);
		buttonTypeEdit = buildMethod.createButton("Editar", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);

		comboBoxTypes.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedTipo = (String) comboBoxTypes.getSelectedItem();
		        if (selectedTipo != null && !selectedTipo.isEmpty()) {
		            textfieldType.setText(selectedTipo);
		            try {
		                listBrandsForTypes(selectedTipo);
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            }
		        } else {
		            textfieldType.setText(""); // Limpa o texto se selectedTipo for null ou vazio
		            try {
		                listBrands(); // Carrega marcas de maneira padrão
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            }
		        } 
		    }
		});

		
		startFunctionsTypesButtons();
		containerButtonsTypes.add(buttonTypeNew);
		containerButtonsTypes.add(buttonTypeDel);
		containerButtonsTypes.add(buttonTypeSave);
		containerButtonsTypes.add(buttonTypeEdit);
		
		containerTypes.add(labelTextTypeSelectedTitle);
		containerTypes.add(comboBoxTypes);
		containerTypes.add(labelTextTypeTitle);
		containerTypes.add(textfieldType);
		
		containerTypes.add(containerButtonsTypes);
		return containerTypes;
	}
	
	@SuppressWarnings("unchecked")
	public JPanel containerBrands() {
		containerBrands = buildMethod.createPanel(0, 0, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		labelTextBrandSelectedTitle = buildMethod.createLabel("Selecione a marca: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		comboBoxBrand = (JComboBox<String>) buildMethod.createComboBox("", 15, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
		
		labelTextBrandTitle = buildMethod.createLabel("Marca: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		textfieldBrand = buildMethod.createTextField("", 15, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
		textfieldBrand.setEnabled(false);
		
		((AbstractDocument) textfieldBrand.getDocument()).setDocumentFilter(new LimitDocumentFilter(100));
		
		containerButtonsBrands = buildMethod.createPanel(15, 4, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		buttonBrandNew = buildMethod.createButton("Novo", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonBrandDel = buildMethod.createButton("Deletar", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonBrandSave = buildMethod.createButton("Salvar", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonBrandSave.setEnabled(false);
		buttonBrandEdit = buildMethod.createButton("Editar", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);

		comboBoxBrand.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedMarca = (String) comboBoxBrand.getSelectedItem();
		        if (selectedMarca != null) {
		            textfieldBrand.setText(selectedMarca);
		        }
		    }
		});




		startFunctionsBrandButtons();
		containerButtonsBrands.add(buttonBrandNew);
		containerButtonsBrands.add(buttonBrandDel);
		containerButtonsBrands.add(buttonBrandSave);
		containerButtonsBrands.add(buttonBrandEdit);
		
		containerBrands.add(labelTextBrandSelectedTitle);
		containerBrands.add(comboBoxBrand);
		containerBrands.add(labelTextBrandTitle);
		containerBrands.add(textfieldBrand);
		
		containerBrands.add(containerButtonsBrands);
		return containerBrands;
	}
	
	public void reloadComboBox(JComboBox<String> comboBox, String tipo) throws SQLException {
	    brandsDAO mDao = new brandsDAO();
	    List<String> brands = mDao.listBrandsForTypes(tipo);

	    comboBox.removeAllItems();
	    for (String brand : brands) {
	        comboBox.addItem(brand);
	    }
	}
	
	private void reloadComboBoxTypes() {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	typesDAO tDao = new typesDAO();
	            List<String> updatedTypes = null;
				try {
					updatedTypes = tDao.listTypes();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	            comboBoxTypes.removeAllItems();
	            for (String updatedType : updatedTypes) {
	                comboBoxTypes.addItem(updatedType);
	            }
	        }
	    });
	}
	
	public void startFunctionsBrandButtons() {

		buttonBrandNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGoBack.setVisible(true);
				labelTextBrandTitle.setText("Criar a marca:");
				labelTextBrandSelectedTitle.setVisible(false);
				comboBoxBrand.setVisible(false);
				textfieldBrand.setEnabled(true);
				
				labelTextTypeTitle.setVisible(false);
				textfieldType.setVisible(false);
				containerButtonsTypes.setVisible(false);
				
				buttonBrandSave.setEnabled(true);
				buttonBrandNew.setEnabled(false);
				buttonBrandDel.setEnabled(false);
				buttonBrandEdit.setEnabled(false);


			}
		});
		
		buttonBrandDel.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String marca = (String) comboBoxBrand.getSelectedItem();
		        String tipo = (String) comboBoxTypes.getSelectedItem();

		        
		        if(comboBoxBrand != null && !marca.isEmpty()) {
		        	if (tipo != null && !tipo.isEmpty()) {						
		        		int response = JOptionPane.showConfirmDialog(null, "Você deseja apagar a marca: " + marca, "Apagar marca", JOptionPane.YES_OPTION);
		        		if (response == JOptionPane.YES_OPTION) {
		        			brandsDAO mDao = new brandsDAO();
			                String tipoSelecionado = (String) comboBoxTypes.getSelectedItem();
			                try {
								mDao.deleteBrand(tipoSelecionado, marca);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
			                
			                comboBoxTypes.setSelectedItem("");
			                comboBoxBrand.setSelectedItem("");
			                try {
								reloadComboBox(comboBoxBrand, tipoSelecionado);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
		        		}
		        		
		        	} else {
		        		JOptionPane.showMessageDialog(null, "Selecione um tipo");
		        	}
		        } else {
		        	JOptionPane.showMessageDialog(null, "Selecione uma marca");
		        }
		        
		    }
		});
		
		buttonBrandSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String type = (String) comboBoxTypes.getSelectedItem();
		        String brand = (String) comboBoxBrand.getSelectedItem();
		        String newBrand = textfieldBrand.getText().trim().toLowerCase();

		        brandsDAO mDao = new brandsDAO();
		        List<String> existingBrands = null;
				try {
					existingBrands = mDao.listBrandsForTypes(type);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

		        existingBrands = existingBrands.stream()
		                                       .map(String::toLowerCase)
		                                       .collect(Collectors.toList());

		        if (comboBoxBrand.isVisible()) { // Edição
		            if (type == null || type.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Selecione um tipo para editar a marca"); // Sem tipo
		                return;
		            }

		            if (brand == null || brand.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Selecione uma marca para editar a marca"); // Sem marca
		                return;
		            }

		            if (newBrand == null || newBrand.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Digite o nome da marca nova"); // Sem marca nova
		                return;
		            }

		            if (existingBrands.contains(newBrand)) {
		                JOptionPane.showMessageDialog(null, "A marca já existe. Por favor, insira uma marca diferente."); // Marca duplicada
		                return;
		            }

		            try {
						mDao.editBrand(type, brand, newBrand);
					} catch (SQLException e1) {
						e1.printStackTrace();
					} // Atualiza a marca no banco de dados
		        } else { // Criando
		            if (type == null || type.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Selecione um tipo para criar a marca"); // Sem tipo
		                return;
		            }

		            if (newBrand == null || newBrand.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Digite o nome da marca nova"); // Sem marca nova
		                return;
		            }

		            if (existingBrands.contains(newBrand)) {
		                JOptionPane.showMessageDialog(null, "A marca já existe. Por favor, insira uma marca diferente."); // Marca duplicada
		                return;
		            }

		            try {
						mDao.saveBrand(type, newBrand);
					} catch (SQLException e1) {
						e1.printStackTrace();
					} 
		        }
		        returnViewer();
		    }
		});

		buttonBrandEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGoBack.setVisible(true);
				String selectedBrand = (String) comboBoxBrand.getSelectedItem();
				String selectedType = (String) comboBoxTypes.getSelectedItem();

				if (comboBoxTypes != null && !selectedType.isEmpty()) {
					if (comboBoxBrand != null && !selectedBrand.isEmpty()) {
						labelTextBrandTitle.setText("Editar a marca:");
						buttonBrandNew.setEnabled(false);
						buttonBrandDel.setEnabled(false);
						buttonBrandEdit.setEnabled(false);
						textfieldBrand.setEnabled(true);
						buttonBrandSave.setEnabled(true);

						
						labelTextTypeTitle.setVisible(false);
						textfieldType.setVisible(false);
						containerButtonsTypes.setVisible(false);
						
					} else {
						JOptionPane.showMessageDialog(null, "Marca não selecionada!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tipo não selecionada!");

				}

					
			}
		});
	
	}
	
	public void startFunctionsTypesButtons() {

		buttonTypeNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGoBack.setVisible(true);
				labelTextTypeTitle.setText("Criar tipo:");
				labelTextTypeSelectedTitle.setVisible(false);
				comboBoxTypes.setVisible(false);
				
				buttonTypeNew.setEnabled(false);
				buttonTypeDel.setEnabled(false);
				buttonTypeSave.setEnabled(true);
				buttonTypeEdit.setEnabled(false);
				textfieldType.setEnabled(true);
				textfieldType.setText("");
				
				labelTextBrandSelectedTitle.setVisible(false);
				containerButtonsBrands.setVisible(false);
				labelTextBrandTitle.setVisible(false);
				textfieldBrand.setVisible(false);
				comboBoxBrand.setVisible(false);

			}
		});
		
		buttonTypeDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) comboBoxTypes.getSelectedItem();
				
				if (comboBoxTypes != null && !type.isEmpty()) {
					int response = JOptionPane.showConfirmDialog(null, "Você deseja apagar o tipo: " + type, "Apagar tipo", JOptionPane.YES_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						typesDAO tDao = new typesDAO();
						String tipoSelecionado = (String) comboBoxTypes.getSelectedItem();
						try {
							tDao.deleteType(tipoSelecionado);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						comboBoxTypes.setSelectedItem("");
						try {
							reloadComboBox(comboBoxTypes, tipoSelecionado);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						reloadComboBoxTypes();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um tipo");
				}
				
			}
		});
		
		buttonTypeSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String oldType = (String) comboBoxTypes.getSelectedItem();
		        String newType = textfieldType.getText().trim().toLowerCase();
		        typesDAO tDao = new typesDAO();
		        
		        if (comboBoxTypes.isVisible()) { // Edição
		            if (oldType == null || oldType.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Selecione um tipo para editar"); // Sem tipo
		                return;
		            }

		            if (newType == null || newType.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Digite o nome do tipo novo"); // Sem tipo novo
		                return;
		            }

		            try {
						tDao.editType(oldType, newType);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		            
		        } else { // Criando
		            if (newType == null || newType.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Digite o nome do novo tipo"); // Sem tipo nova
		                return;
		            }

		            try {
						tDao.saveTypes(newType);
					} catch (SQLException e1) {
						e1.printStackTrace();
					} 
		        }
		        
		        reloadComboBoxTypes(); 
		        returnViewer();
		    }
		});

		buttonTypeEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGoBack.setVisible(true);
				String selectedType = (String) comboBoxTypes.getSelectedItem();

				if (selectedType != null && !selectedType.isEmpty()) {
					labelTextTypeTitle.setText("Editar tipo:");
					
					buttonTypeNew.setEnabled(false);
					buttonTypeDel.setEnabled(false);
					buttonTypeSave.setEnabled(true);
					buttonTypeEdit.setEnabled(false);
					textfieldType.setEnabled(true);
					
					labelTextBrandSelectedTitle.setVisible(false);
					containerButtonsBrands.setVisible(false);
					labelTextBrandTitle.setVisible(false);
					textfieldBrand.setVisible(false);
					comboBoxBrand.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um tipo");
				}
				
					
			}
		});

	}
	
	public void returnViewer() {
		buttonGoBack.setVisible(false);
		// Brands
		labelTextBrandSelectedTitle.setVisible(true);
		comboBoxBrand.setVisible(true);
		labelTextBrandTitle.setVisible(true);
		textfieldBrand.setVisible(true);
		containerButtonsBrands.setVisible(true);
		textfieldBrand.setEnabled(false);
		buttonBrandNew.setEnabled(true);
		buttonBrandDel.setEnabled(true);
		buttonBrandSave.setEnabled(false);
		buttonBrandEdit.setEnabled(true);
		
		// Types
		labelTextTypeSelectedTitle.setVisible(true);
		comboBoxTypes.setVisible(true);
		labelTextTypeTitle.setVisible(true);
		textfieldType.setVisible(true);
		containerButtonsTypes.setVisible(true);
		textfieldType.setEnabled(false);
		buttonTypeNew.setEnabled(true);
		buttonTypeDel.setEnabled(true);
		buttonTypeSave.setEnabled(false);
		buttonTypeEdit.setEnabled(true);

		comboBoxBrand.setSelectedItem("");
		comboBoxTypes.setSelectedItem("");
		labelTextBrandTitle.setText("Marca: ");
		labelTextTypeTitle.setText("Tipo: ");

	}

}
