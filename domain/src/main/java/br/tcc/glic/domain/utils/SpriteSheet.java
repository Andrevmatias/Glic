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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getNumeroFrames() {
        return numeroFrames;
    }

    public void setNumeroFrames(int numeroFrames) {
        this.numeroFrames = numeroFrames;
    }

    public int getAlturaFrame() {
        return alturaFrame;
    }

    public void setAlturaFrame(int alturaFrame) {
        this.alturaFrame = alturaFrame;
    }

    public int getLarguraFrame() {
        return larguraFrame;
    }

    public void setLarguraFrame(int larguraFrame) {
        this.larguraFrame = larguraFrame;
    }
}
