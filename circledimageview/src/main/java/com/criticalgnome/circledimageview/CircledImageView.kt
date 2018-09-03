package com.criticalgnome.circledimageview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class CircledImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ImageView(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        val radiusMeasureSize = View.MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(radiusMeasureSize, radiusMeasureSize)
    }

    override fun draw(canvas: Canvas?) {
        if (canvas == null) return
        val edgeSize: Int = canvas.width
        val foregroundBitmap = Bitmap.createBitmap(edgeSize, edgeSize, Bitmap.Config.ARGB_8888)
        val foregroundCanvas = Canvas(foregroundBitmap)
        super.draw(foregroundCanvas)

        val shapedBitmap = Bitmap.createBitmap(edgeSize, edgeSize, Bitmap.Config.ARGB_8888)
        val shapedCanvas = Canvas(shapedBitmap)
        shapedCanvas.drawCircle((edgeSize / 2).toFloat(), (edgeSize / 2).toFloat(), (edgeSize / 2).toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        shapedCanvas.drawBitmap(foregroundBitmap, 0f, 0f, paint)
        paint.xfermode = null

        canvas.drawBitmap(shapedBitmap, 0f, 0f, paint)
        foregroundBitmap.recycle()
        shapedBitmap.recycle()
    }
}