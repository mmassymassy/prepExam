package com.example.exam0

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
Ecrivez un composant FlagDisplayer prenant en paramètre un code ISO à 2 lettres et
affichant une image du drapeau correspondant (récupéré dans le répertoire flags des
assets).
 */

suspend fun loadImageFromAssets(context: Context, path: String): ImageBitmap? {
    return withContext(Dispatchers.IO) {
        context.assets.open(path).use {
            BitmapFactory.decodeStream(it).asImageBitmap()
        }
    }
}
@Composable
fun getAssetImage(path: String): ImageBitmap? {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(path) {
        bitmap = loadImageFromAssets(context, path)
    }
    return bitmap
}
@Composable
fun FlagDisplayer(code: String, modifier: Modifier =Modifier) {
    val bitmap = getAssetImage("flags/$code.png")
    bitmap?.let {
        Image(bitmap=bitmap, "Flag for $code", modifier=modifier)
    } ?: run {
// display a placeholder if the bitmap is not available
        Box(modifier=modifier.background(color= Color.Gray))
    }
}


@Preview
@Composable
fun test2(){
    
    FlagDisplayer(code = "fr",Modifier.fillMaxSize())
}
