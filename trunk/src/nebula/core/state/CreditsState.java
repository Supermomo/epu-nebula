package nebula.core.state;

import nebula.core.NebulaGame.NebulaState;
import nebula.core.NebulaGame.TransitionType;
import nebula.core.helper.NebulaFont;
import nebula.core.helper.NebulaFont.FontName;
import nebula.core.helper.NebulaFont.FontSize;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Credits state
 */
public class CreditsState extends AbstractState
{
    // Credits string
    private static final String credits =
        "Crédits\n" +
        " \n" +
        " \n" +
        "Nebula - Projet DeViNT 2012\n" +
        "Polytech'Nice-Sophia\n" +
        " \n" +
        " \n" +
        "Créateurs / Développeurs :\n" +
        " \n" +
        "Gwenn Aubert\n" +
        "Thomas Di'Meco\n" +
        "Matthieu Maugard\n" +
        "Gaspard Perrot";


    // Credits properties
    private static final float CREDITS_SPEED = 0.04f;
    private static final float CREDITS_SPACE = 4.0f;

    private float y;
    private float strHeight;
    private String[] strings;
    private Font font;
    private Image imgTopFade, imgBottomFade;


    @Override
    public void init (GameContainer gc, StateBasedGame game)
        throws SlickException
    {
        // Call super method
        super.init(gc, game);

        // No background
        setUseDefaultBackground(false);

        // Images
        imgTopFade = new Image("ressources/images/common/fadeTop.png");
        imgBottomFade = new Image("ressources/images/common/fadeBottom.png");

        // Font
        font  = NebulaFont.getFont(FontName.Batmfa, FontSize.Medium);

        // Music
        initMusic("ressources/sons/common/credits.ogg", 0.4f, true);

        // Split credits string
        strings = credits.split("\n");

        y = gc.getHeight();
        strHeight = font.getHeight("Azerty");
    }


    @Override
    public void update (GameContainer gc, StateBasedGame game, int delta)
        throws SlickException
    {
        // Call super method
        super.update(gc, game, delta);

        Input input = gc.getInput();

        // Credits speed
        if (input.isKeyDown(Input.KEY_UP) ||
            input.isKeyDown(Input.KEY_LSHIFT) ||
            input.isKeyDown(Input.KEY_RSHIFT))
            y -= CREDITS_SPEED * 4 * delta;
        else
            y -= CREDITS_SPEED * delta;

        // Escape
        if (input.isKeyPressed(Input.KEY_ENTER) ||
            input.isKeyPressed(Input.KEY_SPACE) ||
            input.isKeyPressed(Input.KEY_ESCAPE) ||
            y + strings.length * (CREDITS_SPACE + strHeight) < 0.0f)
            nebulaGame.enterState(NebulaState.MainMenu.id, TransitionType.Fade);
    }


    @Override
    public void render (GameContainer gc, StateBasedGame game, Graphics g)
        throws SlickException
    {
        // Call super method
        super.render(gc, game, g);

        // Render credits
        for (int i = 0; i < strings.length; i++)
        {
            String str = strings[i];

            // Draw
            font.drawString(
                gc.getWidth()/2 - font.getWidth(str)/2,
                y + i*(strHeight + CREDITS_SPACE),
                str);
        }

        // Render fades
        imgTopFade.draw(0, 0, gc.getWidth(), imgTopFade.getHeight());
        imgBottomFade.draw(0, gc.getHeight()-imgBottomFade.getHeight(), gc.getWidth(), imgBottomFade.getHeight());
    }


    @Override
    public int getID() { return NebulaState.Credits.id; }
}
