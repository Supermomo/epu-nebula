package nebula.core.helper;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


/**
 * Fonts accessor for Nebula game
 */
public class NebulaFont
{
    // Available fonts
    public static enum FontName
    {
        // Fonts must have differents ID
        Batmfa(0);
        
        public int id;
        private FontName (int id) { this.id = id; }
    }
    
    // Available font sizes
    public static enum FontSize
    {
        // Predefined font sizes to avoid multiples loadings
        Small  (20),
        Medium (36),
        Large  (64);
        
        public int size;
        private FontSize (int size) { this.size = size; }
    }
    
    // Fonts cache
    private static Map<UniqueFont, Font> fontCache
        = new HashMap<UniqueFont, Font>();
    
    
    /**
     * Get a specified font, and cache this font for the entire application
     * @param fontName The font name
     * @param fontSize The font size
     * @throws SlickException 
     */
    @SuppressWarnings("unchecked")
    public static Font getFont (FontName fontName, FontSize fontSize)
        throws SlickException
    {
        // Fonts paths
        String fontPath = "";
        if (FontName.Batmfa.equals(fontName))
            fontPath = "ressources/fonts/batmfa.ttf";
        
        // Font unique key
        UniqueFont uniqueFont = new UniqueFont(fontName, fontSize.size);

        // Check font cache
        if (fontCache.containsKey(uniqueFont))
            return fontCache.get(uniqueFont);
        
        // Create font
        UnicodeFont font = new UnicodeFont(fontPath, fontSize.size, false, false);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.loadGlyphs();
        
        // Cache font
        fontCache.put(uniqueFont, font);

        return font;
    }
    
    /**
     * Inner class for font key caching
     */
    private static class UniqueFont
    {
        private FontName fontName;
        private int size;
        
        public UniqueFont (FontName fontName, int size)
        {
            this.fontName = fontName;
            this.size = size;
        }
        
        @Override
        public int hashCode ()
        {
            return (fontName.id * 100000 + size);
        }
        
        @Override
        public boolean equals (Object obj)
        {
            if (obj == this) return true;
            if (!(obj instanceof UniqueFont)) return false;
            
            UniqueFont other = (UniqueFont)obj;

            return (fontName.id == other.getFontName().id && size == other.getSize());
        }

        public FontName getFontName ()
        {
            return fontName;
        }

        public int getSize ()
        {
            return size;
        }
    }
}
