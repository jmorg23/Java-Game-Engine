package display;

public class LevelSelect extends GamePanel {

    public LevelSelect() {
        setBackgroundImage("/im/levelsel.jpg");
    }  

    @Override
    public void draw(java.awt.Graphics2D g) {
        g.drawString("Level Select", 100, 100);
    }

    @Override
    public void update() {
      
    }

    @Override
    public void gainFocus() {
      
    }

    @Override
    public void loseFocus() {
  
    }
    
}
