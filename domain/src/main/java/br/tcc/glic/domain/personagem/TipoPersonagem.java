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

    public SpriteSheet getSpriteSheet(Context context, EstadoPersonagem estado){
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

    public SpriteSheet getSpriteSheetFalando(Context context, boolean feliz){
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

    private SpriteSheet getSpriteSheetGama(Context context, EstadoPersonagem estado) {
        switch (estado){
            case MuitoMal:
            case Mal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_stand_sad),
                        4, 100, 90);
            case Normal:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_stand_normal),
                        4, 120, 90);
            case Bem:
            case MuitoBem:
                return new SpriteSheet(
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_stand_happy),
                        4, 120, 90);
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

    private SpriteSheet getSpriteSheetGamaFalando(Context context, boolean feliz) {
        if (!feliz)
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_talking_serious),
                    2, 120, 90);
        else
            return new SpriteSheet(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_gama_talking_happy),
                    2, 120, 90);
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
}
