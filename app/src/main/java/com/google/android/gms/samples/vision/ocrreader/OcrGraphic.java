/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private int id;

    private static final int TEXT_COLOR = Color.WHITE;
    private static final int TEXT_COLOR_IMPORTANT = Color.rgb(255, 65, 54);

    private static Paint rectPaint;
    private static Paint textPaint1;
    private static Paint textPaint2;
    private final TextBlock textBlock;

    OcrGraphic(GraphicOverlay overlay, TextBlock text) {
        super(overlay);

        textBlock = text;

        if (rectPaint == null) {
            rectPaint = new Paint();
            rectPaint.setColor(TEXT_COLOR);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(54.0f);
        }

        if (textPaint1 == null) {
            textPaint1 = new Paint();
            textPaint1.setColor(TEXT_COLOR_IMPORTANT);
            textPaint1.setTextSize(54.0f);
        }
        if (textPaint2 == null) {
            textPaint2 = new Paint();
            textPaint2.setColor(TEXT_COLOR);
            textPaint2.setTextSize(44.0f);
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextBlock getTextBlock() {
        return textBlock;
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    public boolean contains(float x, float y) {
        if (textBlock == null) {
            return false;
        }
        RectF rect = new RectF(textBlock.getBoundingBox());
        rect = translateRect(rect);
        return rect.contains(x, y);
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        if (textBlock == null) {
            return;
        }
        // Draws the bounding box around the TextBlock.



        // Break the text into multiple lines and draw each one according to its own bounding box.
        List<? extends Text> textComponents = textBlock.getComponents();
        for(Text currentText : textComponents) {
            if((currentText.getValue().toLowerCase().contains("nr. factura")) || (currentText.getValue().toLowerCase().contains("serie")) || (currentText.getValue().toLowerCase().contains("seria")) || (currentText.getValue().toLowerCase().contains("cif")) || (currentText.getValue().toLowerCase().contains("cui")) || (currentText.getValue().toLowerCase().contains("data"))) {
                float left = translateX(currentText.getBoundingBox().left);
                float bottom = translateY(currentText.getBoundingBox().bottom);
                canvas.drawText(currentText.getValue(), left, bottom, textPaint1);
            }
            if(currentText.getValue().toLowerCase().contains("adresa")){
                float left = translateX(currentText.getBoundingBox().left);
                float bottom = translateY(currentText.getBoundingBox().bottom);
                canvas.drawText(currentText.getValue(), left, bottom, textPaint2);
            }
        }
    }
}
