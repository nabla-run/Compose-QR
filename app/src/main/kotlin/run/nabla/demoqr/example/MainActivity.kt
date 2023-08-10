package run.nabla.demoqr.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import run.nabla.qr.extensions.QRType
import run.nabla.qr.view.QRImage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "COMPOSE QR",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.size(25.dp))
                QRImage(
                    type = QRType.ROUND,
                    text = "demo",
                    imageSize = Size(700f, 700f)
                )
                QRImage(
                    type = QRType.SQUARE,
                    text = "demo",
                    imageSize = Size(700f, 700f)
                )
            }
        }
    }
}