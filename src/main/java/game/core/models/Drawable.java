package game.core.models;

import java.awt.*;

public interface Drawable {

    /**
     * PRE: Drawable object has been initialized
     * POST: Returns the image object if image is not null and else null
     *
     * @return Image to be drawn or null if image was not null
     */
    Image getImage();
}
