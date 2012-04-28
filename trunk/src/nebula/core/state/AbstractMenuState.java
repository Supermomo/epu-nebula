package nebula.core.state;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import nebula.core.NebulaGame;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;


/**
 * Abstract menu class
 */
public abstract class AbstractMenuState extends BasicGameState
{
    // Images path
    public static String imgPath = "ressources/images/menus/";
    
    protected float itemsSpace = 16.0f;
    protected float titleSpace = 80.0f;
    
    private Font itemFont, titleFont;
    private int selectedIndex;
    private String menuTitle;
    protected NebulaGame nebulaGame;
    
    private static Image imgBackground;
    
    // Menu properties
    private List<String> textList        = new ArrayList<String>();
    private List<Boolean> selectableList = new ArrayList<Boolean>();
    

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Nebula game
        nebulaGame = (NebulaGame)game;
        
        // Reset menu
        resetMenu();

        // Load fonts
        itemFont  = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);
        titleFont = NebulaFont.getFont(FontName.Batmfa, FontSize.Large);
        
        // Load background
        imgBackground = new Image(imgPath + "background.png");
    }
    
    
    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        Input input = gc.getInput();

        // Menu navigation
        if (input.isKeyPressed(Input.KEY_DOWN))
            selectNextIndex(selectedIndex+1);
        else if (input.isKeyPressed(Input.KEY_UP))
            selectPreviousIndex(selectedIndex-1);
        else if (input.isKeyPressed(Input.KEY_ENTER) ||
                 input.isKeyPressed(Input.KEY_SPACE))
            indexSelectedEvent(selectedIndex);
        else if (input.isKeyPressed(Input.KEY_ESCAPE))
            indexSelectedEvent(-1);
    }

    
    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Render background
        for (int x = 0; x < gc.getWidth(); x += imgBackground.getWidth())
            for (int y = 0; y < gc.getHeight(); y += imgBackground.getHeight())
                imgBackground.draw(x, y);
        
        // Compute menu height
        float menuh = 0;
        for (int i = 0; i < textList.size(); i++)
        {
            menuh += itemFont.getHeight(textList.get(i));
            if (i != 0) menuh += itemsSpace;
        }
        
        if (!menuTitle.equals(""))
        {
            menuh += titleFont.getHeight(menuTitle);
            menuh += titleSpace;
        }

        float y = gc.getHeight()/2 - menuh/2;

        // Render title
        if (!menuTitle.equals(""))
        {
            float x = gc.getWidth()/2 - titleFont.getWidth(menuTitle)/2;
            titleFont.drawString(x, y, menuTitle, Color.yellow);
            y += titleFont.getHeight(menuTitle) + titleSpace;
        }
        
        // Render items
        for (int i = 0; i < textList.size(); i++)
        {
            float x = gc.getWidth()/2 - itemFont.getWidth(textList.get(i))/2;
            
            if (i == selectedIndex)
                itemFont.drawString(x, y, textList.get(i), Color.red);
            else
                itemFont.drawString(x, y, textList.get(i), Color.white);
            
            y += itemsSpace + itemFont.getHeight(textList.get(i));
        }
    }
    
    @Override
    public void enter (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        super.enter(gc, game);
        gc.getInput().clearKeyPressedRecord();
    }
    
    /**
     * Select the next valid index
     * @param index Starting index
     */
    private void selectNextIndex (int index)
    {
        int init = index;
        
        while (true)
        {
            if (index >= textList.size())
                index -= textList.size();
            else if  (index < selectableList.size() &&
                      !selectableList.get(index).booleanValue())
                index++;
            else
                break;
            
            if (init == index) break;
        }
        
        selectedIndex = index;
    }
    
    
    /**
     * Select the previous valid index
     * @param index Starting index
     */
    private void selectPreviousIndex (int index)
    {
        int init = index;
        
        while (true)
        {
            if (index < 0)
                index += textList.size();
            else if  (index < selectableList.size() &&
                      !selectableList.get(index).booleanValue())
                index--;
            else
                break;
            
            if (init == index) break;
        }
        
        selectedIndex = index;
    }
    
    
    /**
     * Add a menu item to the list
     * @param text       The item text
     * @param selectable The item selectability
     */
    protected void addMenuItem (String text, boolean selectable)
    {
        textList.add(text);
        selectableList.add(new Boolean(selectable));
        
        selectNextIndex(0);
    }
    
    
    /**
     * Set the menu title
     * @param title The title
     */
    protected void setMenuTitle (String title)
    {
        menuTitle = title;
    }
    
    
    /**
     * Reset the menu
     */
    protected void resetMenu ()
    {
        textList.clear();
        selectableList.clear();
        menuTitle = "";
    }
    
    
    /**
     * Called when the user select a menu item
     * @param index The index selected
     */
    protected abstract void indexSelectedEvent (int index);
}
