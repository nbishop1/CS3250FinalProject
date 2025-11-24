import java.io.InputStream;
import java.util.HashMap;
import javafx.scene.text.Font;

public class FontLibrary {
	
	private static final HashMap<String, Font> fontMap = new HashMap<>();
	private static double defaultSize = 12;
	
	public static void addFont(String name, String resourcePath) {
		
		InputStream stream = FontLibrary.class.getResourceAsStream(resourcePath);
		
		if (stream != null ) {
			Font font = Font.loadFont(stream, defaultSize);
			
			System.out.println(font.getFamily());
			
			if (font != null) {
				fontMap.put(name, font);
			}
		}
				
	}
	
	public static Font getFont(String name, double size) {
		Font font = fontMap.get(name);
		
		if (font != null) {
			return Font.font(font.getName(), size);
		}
		
		return Font.font("System", 12);
	}

}
