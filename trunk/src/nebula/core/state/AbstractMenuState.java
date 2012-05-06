package nebula.core.state;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;
import org.newdawn.slick.state.StateBasedGame;

import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;


/**
 * Abstract menu class
 */
public abstract class AbstractMenuState extends AbstractNebulaState
{
    // Images path
    public static String imgPath = "ressources/images/menus/";
    
    protected float itemsSpace = 16.0f;
    protected float titleSpace = 80.0f;
    
    private Font itemFont, titleFont;
    private int selectedIndex;
    private String menuTitle;
        
    // Menu properties
    private List<String> textList        = new ArrayList<String>();
    private List<Boolean> selectableList = new ArrayList<Boolean>();
    

    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);
        
        // Reset menu
        resetMenu();

        // Load fonts
        itemFont  = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);
        titleFont = NebulaFont.getFont(FontName.Batmfa, FontSize.Large);
    }
    
    
    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);
        
        Input input = gc.getInput();

        // Menu navigation
        if (input.isKeyPressed(Input.KEY_DOWN))
            selectNextIndex(selectedIndex+1);
        else if (input.isKeyPressed(Input.KEY_UP))
            selectPreviousIndex(selectedIndex-1);
        else if (input.isKeyPressed(Input.KEY_ENTER) ||
                 input.isKeyPressed(Input.KEY_SPACE))
            indexSelectedEvent(trueIndex(selectedIndex), game);
        else if (input.isKeyPressed(Input.KEY_ESCAPE))
            indexSelectedEvent(-1, game);
    }

    
    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);
        
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
    
    /**
     * Get the true index by ignoring non selectables items
     * @param index The false index
     * @return The true index
     */
    private int trueIndex (int index)
    {
        for (int i = index; i >= 0; i--)
            if (!selectableList.get(i).booleanValue())
                index--;
        
        return index;
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
     * Add some menu spaces
     * @param n The space count
     */
    protected void addMenuSpaces (int n)
    {
        for (int i = 0; i < n; i++)
        {
            textList.add("");
            selectableList.add(false);
        }
        
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
    protected abstract void indexSelectedEvent (int index, StateBasedGame game);
}