package com.example.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.VectorDrawable;

/**
 * Класс утилит
 */
public class CommonUtils {

    /**
     * Масштабирование изображения
     * @param source исходное изображение
     * @param size коэффициент изменения
     * @return преобразованное изображение
     */
    private static Bitmap scaleTo(Bitmap source, int size)
    {
        int destWidth = source.getWidth();
        int destHeight = source.getHeight();

        destHeight = destHeight * size / destWidth;
        destWidth = size;

        if (destHeight < size)
        {
            destWidth = destWidth * size / destHeight;
            destHeight = size;
        }

        Bitmap destBitmap = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);
        canvas.drawBitmap(source, new Rect(0, 0, source.getWidth(), source.getHeight()), new Rect(0, 0, destWidth, destHeight), new Paint(Paint.ANTI_ALIAS_FLAG));
        return destBitmap;
    }

    /**
     * Сделать круглое изображение из квадратного
     * @param source исходное изображение
     * @param radius радиус круга
     * @return преобразованное изображение
     */
    public static Bitmap getCircleMaskedBitmapUsingClip(Bitmap source, int radius)
    {
        if (source == null)
        {
            return null;
        }

        int diam = radius << 1;
        Bitmap scaledBitmap = scaleTo(source, diam);

        final Path path = new Path();
        path.addCircle(radius, radius, radius, Path.Direction.CCW);

        Bitmap targetBitmap = Bitmap.createBitmap(diam, diam, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);

        canvas.clipPath(path);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawBitmap(scaledBitmap, 0, 0, paint);

        return targetBitmap;
    }

    public static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

}