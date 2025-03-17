package GUI;

import BUS.SupplierBUS;
import DTO.SupplierDTO;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GUI_SupplierGUI {
    private JPanel panel;

    public GUI_SupplierGUI(JPanel panel){
        this.panel = panel;
        initComponent();
    }
    
    public void initComponent(){
        panel.setBackground(Color.RED);
    }
}
