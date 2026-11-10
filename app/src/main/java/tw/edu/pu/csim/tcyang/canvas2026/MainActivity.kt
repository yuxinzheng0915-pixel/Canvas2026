package tw.edu.pu.csim.tcyang.canvas2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import tw.edu.pu.csim.tcyang.canvas2026.ui.theme.Canvas2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Canvas2026Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun  CanvasScreen (modifier: Modifier = Modifier) {
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    var CanvasWidth  by remember { mutableStateOf(0f) }
    var CanvasHeigth by remember {mutableStateOf(0f) }
    var minionsX by remember { mutableStateOf(0f) }
    var minionsY by remember { mutableStateOf(0f) }
    var gameOver by remember { mutableStateOf(false) }

    // 載入圖片資源 (小小兵)
    val minionsImage: ImageBitmap =
        ImageBitmap.imageResource(id = R.drawable.minions)

    LaunchedEffect (Unit) {
        while (!gameOver) {
            delay(1000L)
            minionsX += 50f
            minionsY += 100f

            if (canvasSize.width > 0 && canvasSize.height > 0) {
                if (minionsX > canvasSize.width || minionsY > canvasSize.height) {
                    // 超出邊界，重設回原點 (0, 0)
                    minionsX = 0f
                    minionsY = 0f
                }
            }
        }
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged{ size ->
                canvasSize = size

            }
    ){
        Canvas(
            modifier = modifier.fillMaxSize()
        ){
            //繪製洋紅色的圓, 半徑 100, 圓心 (100,100)
            drawCircle(
                Color.Magenta,
                100f,
                Offset(300f, 100f),

                )

            //繪製藍色空心矩形, 左上角 (300,200), 寬度與高度 200
            drawRect(
                Color.Blue,
                Offset(300f, 200f),
                Size(200f, 200f),
                style = Stroke(10f)
            )
            CanvasWidth = size.width
            CanvasHeigth = size.height

            drawLine(
                Color.Red,
                Offset(200f, 800f),
                Offset(size.width, size.height),
                strokeWidth = 10f
            )
            translate(minionsX, minionsY) {
                drawImage(minionsImage)
            }
        }
        Column {
            Text("畫布寬度 $CanvasWidth, 長度$CanvasHeigth")
            Button(
                onClick = {
                    gameOver = !gameOver
                }
            ) {
                if(gameOver){Text("繼續遊戲")}
                else{Text("停止遊戲")}
            }
        }


    }

}


