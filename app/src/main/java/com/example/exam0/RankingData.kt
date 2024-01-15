package com.example.exam0

import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.io.File
import java.io.InputStream

/*
uis une deuxième classe Kotlin RankingData conservant les
informations de classement de pays pour un critère (nom du critère, unité des valeurs et liste
de CountryValue).
 */
data class RankingData(
    var critere : String,
    var unit:String,
    var countries : List<CountryValue>
){
    /*
    Dans le companion object de RankingData, écrivez une méthode loadFromInputStream dont
    la tâche est de charger en mémoire un fichier contenant un classement ; cette méthode
    prendra en paramètre un InputStream et retournera un RankingData.
     */
    companion object {
        fun loadFromInputStream(fichier: InputStream): RankingData {
            var lineList = mutableListOf<String>()
            fichier.bufferedReader().forEachLine { lineList.add(it) }
            val critere: String = lineList[0].split("#ranking=").last()
            val unit = lineList[1]
            var c = arrayListOf<CountryValue>()
            for (i in 2..lineList.size - 1) {
                //ng  Nigeria	0.03
                val(code,name,value) = lineList[i].split("\t")
                Log.i(TAG,"before : "+value.toFloat())
                c.add(CountryValue(name,code,value.toFloat()))

                Log.i(TAG,"La valeur est ## : "+c.last().valeur)

            }
            Log.i(TAG,"La valeur est finalllllll ## : "+c.last().valeur)



            return RankingData(critere,unit,c.toList())
        }
        fun  loadRandomRankingData(context: Context, assetDir: String):RankingData{
            var assetManager = context.assets;
            var assetDirFiles = assetManager.list(assetDir)
            var randomFile = assetDirFiles?.random()
            var inpuStream = assetManager.open("$assetDir/$randomFile")

            return RankingData.loadFromInputStream(inpuStream);

        }

    }
}

@Preview
@Composable
fun test(){
    val data = RankingData.loadRandomRankingData(LocalContext.current,"rankings")

    Column(Modifier.fillMaxSize()) {
        Text(text = "${LocalContext.current.assets.open("rankings/Communications2").bufferedReader().readLine()}")
        Text(text = "Size : ${data.countries}")

    }
}