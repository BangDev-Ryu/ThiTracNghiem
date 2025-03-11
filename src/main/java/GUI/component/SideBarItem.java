package GUI.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SideBarItem extends JPanel implements MouseListener {
    private int width, height;
    private JLabel icon, label;
    private Color colorNormal, colorHover;
    private boolean isActive;
    private String itemName, itemIcon;
    
    public SideBarItem(int w, int h, String n, String i, Color normal, Color hover) {
        this.width = w;
        this.height = h;
        this.itemName = n;
        this.itemIcon = i;
        this.colorNormal = normal;
        this.colorHover = hover;
        this.init();
    }
    
    public void init() {
        this.addMouseListener(this);
        Font font = new Font("Segoe UI",Font.BOLD,13);
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setLayout(null);
        this.setBackground(this.colorNormal);
        
        // khoi tao icon va label
        FlatSVGIcon svgIcon = new FlatSVGIcon("svg/" + itemIcon);
        icon = new JLabel("", svgIcon, JLabel.CENTER);
        label = new JLabel(this.itemName);
        
        icon.setBounds(0, 0, 50, 50);
        
        // thiet ke label
        this.label.setBounds(60, 0, 150, 50);
        this.label.setFont(font);
        this.label.setForeground(Color.decode("#FFFFFF"));
        this.label.setBackground(this.colorNormal);
        
        this.add(icon);
        this.add(label);
    }
    
    public void actived() {
        this.isActive = true;
        this.setBackground(this.colorHover);
    }
    
    public void noActived() {
        this.isActive = false;
        this.setBackground(this.colorNormal);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!this.isActive) {
            this.setBackground(this.colorHover);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!this.isActive) {
            this.setBackground(this.colorNormal);
        }
    }
}
