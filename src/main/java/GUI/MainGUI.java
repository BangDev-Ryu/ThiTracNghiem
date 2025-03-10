package GUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import GUI.component.SideBarItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainGUI extends JFrame implements MouseListener {
    private static int WIDTH = 1280, HEIGHT = 720;
    private static Color sideBarColor = Color.decode("#3498db");
    
    private JPanel sideBar, content;
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<SideBarItem> sideBarItems = new ArrayList<>();
    
    public MainGUI() {
        init();
    }
    
    private void init() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Thi trac nghiem");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.sideBar = initSideBar();
        
        this.setLayout(new BorderLayout());
        this.add(this.sideBar, BorderLayout.WEST);
        
        this.setVisible(true);
    }
    
    public JPanel initSideBar() {
        JPanel pn_result = new JPanel(new BorderLayout());
        JPanel pn_nav = new JPanel();
        pn_nav.setLayout(new FlowLayout(1, 0, 0));
        pn_nav.setPreferredSize(new Dimension(200, HEIGHT - 30));
        pn_nav.setBackground(sideBarColor);
       
        
        Color hover = Color.decode("#2980b9");
        int width_item = 200, height_item = 50;
        
        itemNames.add("Home:home.svg");
        itemNames.add("Đề thi:home-black.svg");
        itemNames.add("Câu hỏi:home.svg");
        itemNames.add("Tài khoản:home.svg");
        
        for (int i = 0; i < itemNames.size(); i++) {
            String[] parts = itemNames.get(i).split(":");
            sideBarItems.add(new SideBarItem(width_item, height_item, parts[0], parts[1], sideBarColor, hover));
            sideBarItems.get(i).addMouseListener(this);
        }
        
        for (SideBarItem item : sideBarItems) {
            pn_nav.add(item);
        }
        
        pn_result.add(pn_nav, BorderLayout.CENTER);
        
        return pn_result;
    }
    
    public void changeContent(SideBarItem item) {
        switch (item.getItemName()) {
            case "Home":
                content.removeAll();
//                content.add();
                content.repaint();
                content.validate();
                break;
            case "Đề thi":
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (SideBarItem item : sideBarItems) {
            if (e.getSource() == item) {
                item.actived();
                changeContent(item);
            }
            else {
                item.noActived();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
