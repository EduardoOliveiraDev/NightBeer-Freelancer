package com.nightbeer.build;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;
import com.nightbeer.dao.brandsDAO;
import com.nightbeer.dao.itemsDAO;
import com.nightbeer.dao.typesDAO;
import com.nightbeer.view.mAdmin;
import com.nightbeer.view.mPrincipal;

public class BuildCreateTipoMarca {
    private BuildMethods buildMethod = new BuildMethods();
    private Color colorTextWhite = buildMethod.colorTextWhite;
    private Color colorTextBlack = buildMethod.colorTextBlack;
    private Color colorBlackBackground = buildMethod.colorBackgroundBlack;
    private Color colorBackgroundWhite = buildMethod.colorBackgroundWhite;
    
    private Color colorWhiteClear = buildMethod.colorWhiteClear;

    private Color colorButton = buildMethod.colorButton;
    private Color colorButtonLightGrey = buildMethod.colorButtonLightGrey;
    private Color colorButtonGreen = buildMethod.colorButtonGreen;
    private Color colorButtonClose = buildMethod.colorButtonClose;
    
    private Font FontRobotoPlainSmall = buildMethod.FontRobotoPlain16;
	
	private JPanel containerNavBar;
	private JButton buttonClose;
	private JButton buttonGoBack;

	private JFrame thisFrameA = mAdmin.getInstance().getFrame();
	
	private JPanel containerCenter;

	private JPanel containerCenterWest;
	private JLabel labelTextTypeSelectedTitle;
	private JComboBox<String> comboBoxTypes;
	private JLabel labelTextTypeTitle;
	private JTextField textfieldType;
	private JPanel containerButtonsTypes;
	private JButton buttonTypeNew;
	private JButton buttonTypeDel;
	private JButton buttonTypeSave;
	private JButton buttonTypeEdit;

	private JPanel containerCenterEast;
	private JLabel labelTextBrandSelectedTitle;
	private JComboBox<String> comboBoxBrand;
	private JLabel labelTextBrandTitle;
	private JTextField textfieldBrand;
	private JPanel containerButtonsBrands;
	private JButton buttonBrandNew;
	private JButton buttonBrandDel;
	private JButton buttonBrandSave;
	private JButton buttonBrandEdit;
 
    public void listarTipos() {
        typesDAO tDao = new typesDAO();
        List<String> tipos = tDao.listarTipos(); 
        comboBoxTypes.removeAllItems(); 
        comboBoxTypes.addItem("");
        
        tipos.forEach(tipo -> comboBoxTypes.addItem(tipo)); 
    }
    
    public void listarMarcas() {
    	brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listarMarcas(); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("");
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
    
    public void listarMarcasPorTipo(String tipo) {
    	brandsDAO mDao = new brandsDAO();
        List<String> marcas = mDao.listarMarcasPorTipo(tipo); 
        comboBoxBrand.removeAllItems(); 
        comboBoxBrand.addItem("");
        
        marcas.forEach(marca -> comboBoxBrand.addItem(marca)); 
    }
	
	public JPanel containerMain(JFrame frame) {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(buildMethod.createResponsive(40, 30));
        contentPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, colorTextBlack));
        
        contentPane.add(containerNavbar(frame), BorderLayout.NORTH);
        contentPane.add(containerCenter(), BorderLayout.CENTER);
        
        listarMarcas();
        listarTipos();
        return contentPane;
	}
	
	public JPanel containerNavbar(JFrame frame) {
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
	
	public JPanel containerCenter() {
		containerCenter = new JPanel(new GridLayout(1,2));
		containerCenter.add(containerWest());
		containerCenter.add(containerEast());
		return containerCenter;
	}
	
	public JPanel containerWest() {
		containerCenterWest = buildMethod.createPanel(0, 0, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		
		labelTextTypeSelectedTitle = buildMethod.createLabel("Selecione o tipo: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		comboBoxTypes = (JComboBox<String>) buildMethod.createComboBox("", 15, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
		
		labelTextTypeTitle = buildMethod.createLabel("Tipo: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		textfieldType = buildMethod.createTextField("", 15, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
		textfieldType.setEnabled(false);
		
		containerButtonsTypes = buildMethod.createPanel(15, 4, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		buttonTypeNew = buildMethod.createButton("New", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonTypeDel = buildMethod.createButton("Del", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonTypeSave = buildMethod.createButton("Save", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonTypeSave.setEnabled(false);
		buttonTypeEdit = buildMethod.createButton("Edit", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);

		comboBoxTypes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTipo = (String) comboBoxTypes.getSelectedItem();
                textfieldType.setText(selectedTipo);
                if (selectedTipo != null && !selectedTipo.isEmpty()) {
                    listarMarcasPorTipo(selectedTipo);
                } else {
                    listarMarcas();
                } 
            }
        });
		
		startFunctionsTypesButtons();
		
		containerButtonsTypes.add(buttonTypeNew);
		containerButtonsTypes.add(buttonTypeDel);
		containerButtonsTypes.add(buttonTypeSave);
		containerButtonsTypes.add(buttonTypeEdit);
		
		containerCenterWest.add(labelTextTypeSelectedTitle);
		containerCenterWest.add(comboBoxTypes);
		containerCenterWest.add(labelTextTypeTitle);
		containerCenterWest.add(textfieldType);
		
		containerCenterWest.add(containerButtonsTypes);
		return containerCenterWest;
	}
	
	public JPanel containerEast() {
		containerCenterEast = buildMethod.createPanel(0, 0, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		labelTextBrandSelectedTitle = buildMethod.createLabel("Selecione a marca: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		comboBoxBrand = (JComboBox<String>) buildMethod.createComboBox("", 15, 4, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 0, 0, 0);
		
		labelTextBrandTitle = buildMethod.createLabel("Marca: ", 15, 2, SwingConstants.LEFT, colorTextBlack, colorBackgroundWhite, FontRobotoPlainSmall, 0, 0, 0, 0);
		textfieldBrand = buildMethod.createTextField("", 15, 4, SwingConstants.LEFT, colorTextBlack, colorWhiteClear, FontRobotoPlainSmall, 0, 10, 0, 10);
		textfieldBrand.setEnabled(false);
		
		containerButtonsBrands = buildMethod.createPanel(15, 4, new FlowLayout(), colorBackgroundWhite, 0,0,0,0);
		buttonBrandNew = buildMethod.createButton("New", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonBrandDel = buildMethod.createButton("Del", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonBrandSave = buildMethod.createButton("Save", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);
		buttonBrandSave.setEnabled(false);
		buttonBrandEdit = buildMethod.createButton("Edit", 3, 3, SwingConstants.CENTER, colorButtonClose, colorBlackBackground);

		comboBoxBrand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String selectedMarca = (String) comboBoxBrand.getSelectedItem();
                textfieldBrand.setText(selectedMarca);
			}
		});

		startFunctionsBrandButtons();
		
		containerButtonsBrands.add(buttonBrandNew);
		containerButtonsBrands.add(buttonBrandDel);
		containerButtonsBrands.add(buttonBrandSave);
		containerButtonsBrands.add(buttonBrandEdit);
		
		containerCenterEast.add(labelTextBrandSelectedTitle);
		containerCenterEast.add(comboBoxBrand);
		containerCenterEast.add(labelTextBrandTitle);
		containerCenterEast.add(textfieldBrand);
		
		containerCenterEast.add(containerButtonsBrands);
		return containerCenterEast;
	}
	
	public void reloadComboBox(JComboBox<String> comboBox, String tipo) {
	    brandsDAO mDao = new brandsDAO();
	    List<String> brands = mDao.listarMarcasPorTipo(tipo);

	    comboBox.removeAllItems();
	    for (String brand : brands) {
	        comboBox.addItem(brand);
	    }
	}
	
	private void reloadComboBoxTypes() {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	typesDAO tDao = new typesDAO();
	            List<String> updatedTypes = tDao.listarTipos();
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
		        		int response = JOptionPane.showConfirmDialog(thisFrameA, "Você deseja apagar a marca: " + marca, "Apagar marca", JOptionPane.YES_OPTION);
		        		if (response == JOptionPane.YES_OPTION) {
		        			brandsDAO mDao = new brandsDAO();
			                String tipoSelecionado = (String) comboBoxTypes.getSelectedItem();
			                mDao.deleteBrand(tipoSelecionado, marca);
			                
			                comboBoxTypes.setSelectedItem("");
			                comboBoxBrand.setSelectedItem("");
			                reloadComboBox(comboBoxBrand, tipoSelecionado);
		        		}
		        		
		        	} else {
		        		JOptionPane.showMessageDialog(thisFrameA, "Selecione um tipo");
		        	}
		        } else {
		        	JOptionPane.showMessageDialog(thisFrameA, "Selecione uma marca");
		        }
		        
		    }
		});
		
		buttonBrandSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String type = (String) comboBoxTypes.getSelectedItem();
		        String brand = (String) comboBoxBrand.getSelectedItem();
		        String newBrand = textfieldBrand.getText().trim().toLowerCase();

		        brandsDAO mDao = new brandsDAO();
		        List<String> existingBrands = mDao.listarMarcasPorTipo(type);

		        // Convert existing brands to lowercase for comparison
		        existingBrands = existingBrands.stream()
		                                       .map(String::toLowerCase)
		                                       .collect(Collectors.toList());

		        if (comboBoxBrand.isVisible()) { // Edição
		            if (type == null || type.isEmpty()) {
		                JOptionPane.showMessageDialog(thisFrameA, "Selecione um tipo para editar a marca"); // Sem tipo
		                return;
		            }

		            if (brand == null || brand.isEmpty()) {
		                JOptionPane.showMessageDialog(thisFrameA, "Selecione uma marca para editar a marca"); // Sem marca
		                return;
		            }

		            if (newBrand == null || newBrand.isEmpty()) {
		                JOptionPane.showMessageDialog(thisFrameA, "Digite o nome da marca nova"); // Sem marca nova
		                return;
		            }

		            if (existingBrands.contains(newBrand)) {
		                JOptionPane.showMessageDialog(thisFrameA, "A marca já existe. Por favor, insira uma marca diferente."); // Marca duplicada
		                return;
		            }

		            mDao.editBrand(type, brand, newBrand); // Atualiza a marca no banco de dados
		        } else { // Criando
		            if (type == null || type.isEmpty()) {
		                JOptionPane.showMessageDialog(thisFrameA, "Selecione um tipo para criar a marca"); // Sem tipo
		                return;
		            }

		            if (newBrand == null || newBrand.isEmpty()) {
		                JOptionPane.showMessageDialog(thisFrameA, "Digite o nome da marca nova"); // Sem marca nova
		                return;
		            }

		            if (existingBrands.contains(newBrand)) {
		                JOptionPane.showMessageDialog(thisFrameA, "A marca já existe. Por favor, insira uma marca diferente."); // Marca duplicada
		                return;
		            }

		            mDao.saveBrand(type, newBrand); // Insere a nova marca no banco de dados
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
						JOptionPane.showMessageDialog(thisFrameA, "Marca não selecionada!");
					}
				} else {
					JOptionPane.showMessageDialog(thisFrameA, "Tipo não selecionada!");

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
					int response = JOptionPane.showConfirmDialog(thisFrameA, "Você deseja apagar o tipo: " + type, "Apagar tipo", JOptionPane.YES_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						typesDAO tDao = new typesDAO();
						String tipoSelecionado = (String) comboBoxTypes.getSelectedItem();
						tDao.deleteType(tipoSelecionado);
						
						comboBoxTypes.setSelectedItem("");
						reloadComboBox(comboBoxTypes, tipoSelecionado);
						reloadComboBoxTypes();
					}
					
				} else {
					JOptionPane.showMessageDialog(thisFrameA, "Selecione um tipo");
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
		                JOptionPane.showMessageDialog(thisFrameA, "Selecione um tipo para editar"); // Sem tipo
		                return;
		            }

		            if (newType == null || newType.isEmpty()) {
		                JOptionPane.showMessageDialog(thisFrameA, "Digite o nome do tipo novo"); // Sem tipo novo
		                return;
		            }

		            
		            tDao.editTypeInTwoTables(oldType, newType);
		            
		        } else { // Criando
		            if (newType == null || newType.isEmpty()) {
		                JOptionPane.showMessageDialog(thisFrameA, "Digite o nome do novo tipo"); // Sem tipo nova
		                return;
		            }

		            tDao.saveTypes(newType); // Insere o novo tipo no banco de dados
		        }
		        
		        reloadComboBoxTypes(); // Recarregar o JComboBox
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
					JOptionPane.showMessageDialog(thisFrameA, "Selecione um tipo");
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
