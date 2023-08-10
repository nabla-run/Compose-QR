package run.nabla.qr.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import com.google.zxing.qrcode.encoder.QRCode
import java.lang.Integer.max
import java.lang.Integer.min

enum class QRType {
    SQUARE,
    ROUND
}

internal fun String.generateQR(): QRCode {
    val hints = HashMap<EncodeHintType?, Any?>()
    hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
    return Encoder.encode(
        this,
        ErrorCorrectionLevel.H,
        hints
    )
}

internal fun QRCode.renderImage(
    type: QRType,
    width: Int,
    height: Int,
    quietZone: Int
): Bitmap {
    val input = this.matrix ?: throw IllegalStateException("QR code matrix is null")
    val qrWidth = input.width + quietZone * 2
    val qrHeight = input.height + quietZone * 2
    val outputWidth = max(width, qrWidth)
    val outputHeight = max(height, qrHeight)

    val bitmap = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
    }

    val multiple = min(outputWidth / qrWidth, outputHeight / qrHeight)
    val leftPadding = (outputWidth - input.width * multiple) / 2
    val topPadding = (outputHeight - input.height * multiple) / 2
    val circleSize = multiple

    for (inputY in 0 until input.height) {
        val outputY = topPadding + multiple * inputY
        for (inputX in 0 until input.width) {
            val outputX = leftPadding + multiple * inputX
            if (input.get(inputX, inputY) == 1.toByte() &&
                !((inputX <= 7 && inputY <= 7) ||
                        (inputX >= input.width - 7 && inputY <= 7) ||
                        (inputX <= 7 && inputY >= input.height - 7))
            ) {
                when (type) {
                    QRType.ROUND -> canvas.drawOval(
                        RectF(
                            outputX.toFloat(),
                            outputY.toFloat(),
                            (outputX + circleSize).toFloat(),
                            (outputY + circleSize).toFloat()
                        ),
                        paint
                    )

                    QRType.SQUARE -> canvas.drawRect(
                        RectF(
                            outputX.toFloat(),
                            outputY.toFloat(),
                            (outputX + circleSize).toFloat(),
                            (outputY + circleSize).toFloat()
                        ),
                        paint
                    )
                }

            }
        }
    }

    drawFinderPatternStyle(
        type,
        canvas,
        paint,
        leftPadding,
        topPadding,
        multiple
    )
    drawFinderPatternStyle(
        type,
        canvas,
        paint,
        leftPadding + (input.width - 7) * multiple,
        topPadding,
        multiple
    )
    drawFinderPatternStyle(
        type,
        canvas,
        paint,
        leftPadding,
        topPadding + (input.height - 7) * multiple,
        multiple
    )

    return bitmap
}

private fun drawFinderPatternStyle(
    type: QRType,
    canvas: Canvas,
    paint: Paint,
    x: Int,
    y: Int,
    multiple: Int
) {
    val circleDiameter = 7 * multiple
    val whiteCircleDiameter = 5 * multiple
    val whiteCircleOffset = multiple
    val middleDotDiameter = 3 * multiple
    val middleDotOffset = 2 * multiple

    paint.color = Color.BLACK
    canvas.drawRoundRect(
        RectF(
            x.toFloat(),
            y.toFloat(),
            (x + circleDiameter).toFloat(),
            (y + circleDiameter).toFloat()
        ),
        if (type == QRType.ROUND) 15f else 0f,
        if (type == QRType.ROUND) 15f else 0f,
        paint
    )

    paint.color = Color.WHITE
    canvas.drawRoundRect(
        RectF(
            (x + whiteCircleOffset).toFloat(),
            (y + whiteCircleOffset).toFloat(),
            (x + whiteCircleOffset + whiteCircleDiameter).toFloat(),
            (y + whiteCircleOffset + whiteCircleDiameter).toFloat()
        ),
        if (type == QRType.ROUND) 10f else 0f,
        if (type == QRType.ROUND) 10f else 0f,
        paint
    )

    paint.color = Color.BLACK
    canvas.drawOval(
        RectF(
            (x + middleDotOffset).toFloat(),
            (y + middleDotOffset).toFloat(),
            (x + middleDotOffset + middleDotDiameter).toFloat(),
            (y + middleDotOffset + middleDotDiameter).toFloat()
        ),
        paint
    )
}
