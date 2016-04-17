package br.tcc.glic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import br.tcc.glic.R;
import br.tcc.glic.utils.BitmapUtils;

/**
 * View que comporta uma animação com spritesheet
 * Created by André on 16/04/2016.
 * Baseado em http://gamecodeschool.com/android/coding-android-sprite-sheet-animations/
 */
public class SpriteView extends SurfaceView implements Runnable {

    private Thread spriteThread;
    private SurfaceHolder holder;
    private volatile boolean playing;
    private Canvas canvas;
    private Paint paint;

    private Bitmap spriteBitmap;
    private int frameWidth = 50;
    private int frameHeight = 100;
    private int framesCount = 5;
    private int animationSpeed = 100;
    private boolean grayscaleWhenPaused = true;
    private boolean autoplay = true;

    private int currentFrame = 0;
    private long lastFrameChangeTime = 0;
    private Rect frameToDraw;

    private RectF whereToDraw = new RectF(0, 0, frameWidth, frameHeight);

    public SpriteView(Context context, @DrawableRes int spriteId) {
        this(context, spriteId, false);
    }

    public SpriteView(Context context, @DrawableRes int spriteId, boolean autoplay) {
        super(context);

        this.autoplay = autoplay;
        setSprite(spriteId);

        initComponents();
        if(autoplay)
            resume();
        else
            drawFirstFrame();
    }

    private void drawFirstFrame() {
        Thread drawFirstFrameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (holder == null || !holder.getSurface().isValid()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                canvas = holder.lockCanvas();

                canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

                whereToDraw.set(
                        0,
                        0,
                        frameWidth,
                        frameHeight);

                if(grayscaleWhenPaused)
                    canvas.drawBitmap(BitmapUtils.toGrayscale(spriteBitmap),
                            frameToDraw,
                            whereToDraw, paint);
                else
                    canvas.drawBitmap(spriteBitmap,
                            frameToDraw,
                            whereToDraw, paint);

                holder.unlockCanvasAndPost(canvas);
            }
        });

        drawFirstFrameThread.start();
    }

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SpriteView, 0, 0);
        try {
            setFrameHeight(ta.getDimensionPixelSize(R.styleable.SpriteView_frameHeight, frameHeight));
            setFrameWidth(ta.getDimensionPixelSize(R.styleable.SpriteView_frameWidth, frameWidth));
            setFramesCount(ta.getInt(R.styleable.SpriteView_framesCount, framesCount));
            setAnimationSpeed(ta.getInt(R.styleable.SpriteView_animationSpeed, animationSpeed));
            setSprite(ta.getResourceId(R.styleable.SpriteView_sprite, 0));
            grayscaleWhenPaused = ta.getBoolean(R.styleable.SpriteView_grayscaleWhenPaused, grayscaleWhenPaused);
            autoplay = ta.getBoolean(R.styleable.SpriteView_autoplay, autoplay);
        } finally {
            ta.recycle();
        }

        initComponents();
        if(autoplay)
            resume();
        else
            drawFirstFrame();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(frameWidth, frameHeight);
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;

        frameToDraw = new Rect(
                0,
                0,
                frameWidth,
                frameHeight);
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;

        frameToDraw = new Rect(
                0,
                0,
                frameWidth,
                frameHeight);
    }

    public void setFramesCount(int framesCount) {
        this.framesCount = framesCount;
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    private void setSprite(@DrawableRes int spriteId)
    {
        spriteBitmap = BitmapFactory.decodeResource(this.getResources(), spriteId);
        spriteBitmap = Bitmap.createScaledBitmap(spriteBitmap,
                frameWidth * framesCount,
                frameHeight,
                false);
    }

    public void setSprite(@DrawableRes int spriteId,
                          int frameWidth,
                          int frameHeight,
                          int framesCount,
                          int speedInMilliseconds){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.animationSpeed = speedInMilliseconds;
        this.framesCount = framesCount;

        setSprite(spriteId);
    }

    private void initComponents() {
        setZOrderOnTop(true);

        holder = getHolder();
        holder.setFormat(PixelFormat.TRANSLUCENT);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
    }

    @Override
    public void run() {
        while (playing) {
            try {
                update();
                draw();
            } catch (Exception ex) { }
        }
        draw();
    }

    public void update() {
        long time = System.currentTimeMillis();

        if (time > lastFrameChangeTime + animationSpeed) {
            lastFrameChangeTime = time;
            currentFrame++;
            if (currentFrame >= framesCount)
                currentFrame = 0;
        }

        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }

    public void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

            whereToDraw.set(
                    0,
                    0,
                    frameWidth,
                    frameHeight);

            if(!playing && grayscaleWhenPaused)
                canvas.drawBitmap(BitmapUtils.toGrayscale(spriteBitmap),
                        frameToDraw,
                        whereToDraw, paint);
            else
                canvas.drawBitmap(spriteBitmap,
                        frameToDraw,
                        whereToDraw, paint);

            holder.unlockCanvasAndPost(canvas);
        }

    }

    public void pause() {
        if(playing) {
            playing = false;
            try {
                spriteThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resume() {
        if(!playing) {
            playing = true;
            spriteThread = new Thread(this);
            spriteThread.start();
        }
    }
}

