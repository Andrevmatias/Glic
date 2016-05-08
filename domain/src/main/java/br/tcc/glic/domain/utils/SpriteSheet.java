package br.tcc.glic.domain.utils;

import android.graphics.Bitmap;

/**
 * SpriteSheet para animação
 * Created by André on 19/04/2016.
 */
public class SpriteSheet {
    private Bitmap bitmap;
    private int numeroFrames;
    private int alturaFrame;
    private int larguraFrame;

    public SpriteSheet(Bitmap spriteSheet, int numeroFrames, int alturaFrame, int larguraFrame) {
        this.bitmap = spriteSheet;
        this.numeroFrames = numeroFrames;
        this.alturaFrame = alturaFrame;
        this.larguraFrame = larguraFrame;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getNumeroFrames() {
        return numeroFrames;
    }

    public int getAlturaFrame() {
        return alturaFrame;
    }

    public int getLarguraFrame() {
        return larguraFrame;
    }
}
