package run.nabla.qr.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import run.nabla.qr.extensions.QRType
import run.nabla.qr.extensions.generateQR
import run.nabla.qr.extensions.renderImage
import java.util.concurrent.Executors

@Composable
fun QRImage(
    modifier: Modifier = Modifier,
    type: QRType = QRType.SQUARE,
    imageSize: Size = Size(height = 300f, width = 300f),
    onBitmapCreate: (Bitmap) -> Unit = {},
    text: String
) {
    var qrCodeImage by remember(text) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(text) {
        val qrCode = withContext(Executors.newSingleThreadExecutor().asCoroutineDispatcher()) {
            text.generateQR()
        }

        val qrCodeBitmap = qrCode.renderImage(
            type = type,
            width = imageSize.width.toInt(),
            height = imageSize.height.toInt(),
            quietZone = 4
        ).also(onBitmapCreate)

        withContext(Dispatchers.Main) {
            qrCodeImage = qrCodeBitmap.asImageBitmap()
        }
    }

    Box(
        modifier = Modifier
    ) {
        qrCodeImage?.let { imageBitmap ->
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = modifier
            )
        } ?: run {
            Spacer(
                modifier = Modifier
                    .size(imageSize.width.dp, imageSize.height.dp)
            )
        }
    }
}