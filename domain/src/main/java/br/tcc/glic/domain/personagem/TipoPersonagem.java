package br.tcc.glic.domain.personagem;

import android.content.Context;
import android.graphics.BitmapFactory;

import br.tcc.glic.domain.R;
import br.tcc.glic.domain.enums.EstadoPersonagem;
import br.tcc.glic.domain.utils.SpriteSheet;

/**
 * Enumerador de tipos de personagens disponíveis
 * Created by André on 27/01/2016.
 */
public enum TipoPersonagem {
    Alpha,
    Beta,
    Gama;

    public static final int MAX_BABY_CHAR_LEVEL = 4;

    public SpriteSheet getSpriteSheet(Context context, EstadoPersonagem estado, int charLevel){
        if(charLevel <= MAX_BABY_CHAR_LEVEL)
            return getSpriteSheetBaby(context, estado);
        else
            return getSpriteSheet(context, estado);
    }

    public SpriteSheet getEvolutionSpriteSheet(Context context){
        switch (this){
            case Alpha:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_alpha_evolution),
                        20, 120, 90);
            case Beta:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_beta_evolution),
                        20, 120, 90);
            case Gama:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_evolution),
                        20, 110, 90);
            default:
                throw new RuntimeException("Tipo não reconhecido");
        }
    }

    public SpriteSheet getSpriteSheetFalando(Context context, boolean feliz, int charLevel){
        if(charLevel <= MAX_BABY_CHAR_LEVEL)
            return getSpriteSheetBabyFalando(context, feliz);
        else
            return getSpriteSheetFalando(context, feliz);
    }

    private SpriteSheet getSpriteSheetBaby(Context context, EstadoPersonagem estado) {
        switch (this)
        {
            case Alpha:
                return getSpriteSheetBabyAlpha(context, estado);
            case Beta:
                return getSpriteSheetBabyBeta(context, estado);
            case Gama:
                return getSpriteSheetBabyGama(context, estado);
            default:
                throw new RuntimeException("Tipo não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheet(Context context, EstadoPersonagem estado){
        switch (this)
        {
            case Alpha:
                return getSpriteSheetAlpha(context, estado);
            case Beta:
                return getSpriteSheetBeta(context, estado);
            case Gama:
                return getSpriteSheetGama(context, estado);
            default:
                throw new RuntimeException("Tipo não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetFalando(Context context, boolean feliz){
        switch (this)
        {
            case Alpha:
                return getSpriteSheetAlphaFalando(context, feliz);
            case Beta:
                return getSpriteSheetBetaFalando(context, feliz);
            case Gama:
                return getSpriteSheetGamaFalando(context, feliz);
            default:
                throw new RuntimeException("Tipo não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetBabyFalando(Context context, boolean feliz){
        switch (this)
        {
            case Alpha:
                return getSpriteSheetBabyAlphaFalando(context, feliz);
            case Beta:
                return getSpriteSheetBabyBetaFalando(context, feliz);
            case Gama:
                return getSpriteSheetBabyGamaFalando(context, feliz);
            default:
                throw new RuntimeException("Tipo não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetGama(Context context, EstadoPersonagem estado) {
        switch (estado){
            case MuitoMal:
            case Mal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_stand_sad),
                        4, 95, 90);
            case Normal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_stand_normal),
                        4, 110, 90);
            case Bem:
            case MuitoBem:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_stand_happy),
                        4, 110, 90);
            default:
                throw new RuntimeException("Estado de personagem não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetBabyGama(Context context, EstadoPersonagem estado) {
        switch (estado){
            case MuitoMal:
            case Mal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_gama_stand_sad),
                        4, 95, 90);
            case Normal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_gama_stand_normal),
                        4, 95, 90);
            case Bem:
            case MuitoBem:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_gama_stand_happy),
                        4, 95, 90);
            default:
                throw new RuntimeException("Estado de personagem não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetBeta(Context context, EstadoPersonagem estado) {
        switch (estado){
            case MuitoMal:
            case Mal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_beta_stand_sad),
                        4, 100, 90);
            case Normal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_beta_stand_normal),
                        4, 120, 90);
            case Bem:
            case MuitoBem:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_beta_stand_happy),
                        4, 120, 90);
            default:
                throw new RuntimeException("Estado de personagem não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetBabyBeta(Context context, EstadoPersonagem estado) {
        switch (estado){
            case MuitoMal:
            case Mal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_beta_stand_sad),
                        4, 95, 90);
            case Normal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_beta_stand_normal),
                        4, 95, 90);
            case Bem:
            case MuitoBem:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_beta_stand_happy),
                        4, 95, 90);
            default:
                throw new RuntimeException("Estado de personagem não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetAlpha(Context context, EstadoPersonagem estado) {
        switch (estado){
            case MuitoMal:
            case Mal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_alpha_stand_sad),
                        4, 100, 90);
            case Normal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_alpha_stand_normal),
                        4, 120, 90);
            case Bem:
            case MuitoBem:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_alpha_stand_happy),
                        4, 120, 90);
            default:
                throw new RuntimeException("Estado de personagem não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetBabyAlpha(Context context, EstadoPersonagem estado) {
        switch (estado){
            case MuitoMal:
            case Mal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_alpha_stand_sad),
                        4, 95, 90);
            case Normal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_alpha_stand_normal),
                        4, 95, 90);
            case Bem:
            case MuitoBem:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_alpha_stand_happy),
                        4, 95, 90);
            default:
                throw new RuntimeException("Estado de personagem não reconhecido");
        }
    }

    private SpriteSheet getSpriteSheetGamaFalando(Context context, boolean feliz) {
        if (!feliz)
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_talking_serious),
                    2, 110, 90);
        else
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_talking_happy),
                    2, 110, 90);
    }

    private SpriteSheet getSpriteSheetBabyGamaFalando(Context context, boolean feliz) {
        if (!feliz)
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_gama_talking_serious),
                    2, 95, 90);
        else
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_gama_talking_happy),
                    2, 95, 90);
    }

    private SpriteSheet getSpriteSheetBetaFalando(Context context, boolean feliz) {
        if (!feliz)
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_beta_talking_serious),
                    2, 120, 90);
        else
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_beta_talking_happy),
                    2, 120, 90);
    }

    private SpriteSheet getSpriteSheetBabyBetaFalando(Context context, boolean feliz) {
        if (!feliz)
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_beta_talking_serious),
                    2, 95, 90);
        else
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_beta_talking_happy),
                    2, 95, 90);
    }

    private SpriteSheet getSpriteSheetAlphaFalando(Context context, boolean feliz) {
        if (!feliz)
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_alpha_talking_serious),
                    2, 120, 90);
        else
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_alpha_talking_happy),
                    2, 120, 90);
    }

    private SpriteSheet getSpriteSheetBabyAlphaFalando(Context context, boolean feliz) {
        if (!feliz)
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_alpha_talking_serious),
                    2, 95, 90);
        else
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_baby_alpha_talking_happy),
                    2, 95, 90);
    }
}
