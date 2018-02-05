package com.kbnt.qam.timeline.helpers;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintsHelper {

    public final Paint line;
    public final Paint edgeSerif;
    public final Paint secondarySerif;
    public final Paint mainSerif;
    public final Paint mainText;
    public final Paint secondaryText;

    public PaintsHelper() {
        line = new Paint();
        line.setColor(Color.RED);
        line.setStrokeWidth(4.0F);

        edgeSerif = new Paint();
        edgeSerif.setColor(Color.YELLOW);
        edgeSerif.setStrokeWidth(4.0F);

        mainSerif = new Paint();
        mainSerif.setColor(Color.GREEN);
        mainSerif.setStrokeWidth(4.0F);

        secondarySerif = new Paint();
        secondarySerif.setColor(Color.LTGRAY);
        secondarySerif.setStrokeWidth(4.0F);

        mainText = new Paint();
        mainText.setColor(Color.GREEN);
        mainText.setTextAlign(Paint.Align.CENTER);
        mainText.setTextSize(18);

        secondaryText = new Paint();
        secondaryText.setColor(Color.LTGRAY);
        secondaryText.setTextAlign(Paint.Align.LEFT);
        secondaryText.setTextSize(18);
    }
}
