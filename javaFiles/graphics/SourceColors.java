package javaFiles.graphics;

import java.awt.Color;
public enum SourceColors{
    PASTELE_GREEN(new Color(29,113,43)),
    PASTELE_GREEN_LIGHT(new Color(108,217,126)),
    PASTELE_RED(new Color(187,9,0)),
    PASTELE_RED_LIGHT(new Color(255,95,87));
    public Color color;
    SourceColors(Color cl) {
        color = cl;
    }
}
