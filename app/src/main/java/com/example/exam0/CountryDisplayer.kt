package com.example.exam0

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/*
Composant de pays
Ecrivez un composant CountryDisplayer représentant un pays. Ce composant affichera sur
une ligne le drapeau du pays, le nom de celui-ci ainsi qu'un nombre flottant alignésur la
droite
 */

@Composable
fun CountryDisplayer(code: String, name: String, value: Float?){
    val v =  value.toString()
    Log.i(ContentValues.TAG,"####### est ## : "+ v)

    Row(Modifier.fillMaxSize().padding(5.dp),verticalAlignment = Alignment.CenterVertically) {
        FlagDisplayer(code = code,Modifier)
        Text(text = "   Name : $name", fontSize = 20.sp)
        Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = "$v", fontSize = 15.sp)
        }

    }

}

@Preview
@Composable
fun test3(){
    CountryDisplayer("fr","FRance",1.5f)
}