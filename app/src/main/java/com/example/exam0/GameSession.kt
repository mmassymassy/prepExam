package com.example.exam0

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/*
En haut le nom du critère joué avec son unité
En dessous le score actuel
Plus bas un pays à placer dans le classement
Enfin le composant de classement RankingDisplayer absorbe le reste de l'espace avec tous
les items déjà classés ainsi que des boutons cliquables aux emplacement intermédiaires
pour indiquer la position du pays à placer. En cas de fin de partie, les boutons ne sont plus
affichés : un bouton pour rejouer est affiché sous la bannière de score.
☞ En fin de partie, un message Game Over doit être affiché ainsi qu'un bouton pour
relancer une partie.
 */

@Composable
fun GameSession(
    critere: String,
    unit: String,
    countryToPlace: CountryValue,
    rankingCountries: List<CountryValue>,
    score: Int,
    onSelectedPos: (Int) -> Unit,
    isGameOver: Boolean,
    onStartNewGame: () -> Unit,
    timeLeft : Int
){

    Column (Modifier.fillMaxSize()){

        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$critere    : $unit")
            Text(text = "score : $score")
            if(isGameOver){
                Button(onClick = { onStartNewGame.invoke()
                }) {
                    Text(text = "Start again")
                }
            }else{
                Text(text = "Time left $timeLeft s")
                Text(text = "Rank this country:",)
                CountryDisplayer(code = countryToPlace.code, name = countryToPlace.name, value = null)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(4f)) {
            if(!isGameOver)
                RankingDisplayer(data = rankingCountries, onClick = {
                    selectedPosition -> onSelectedPos(selectedPosition)
                })
            else
                RankingDisplayer(data = rankingCountries)

        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@Preview
fun test5(){
    val context = LocalContext.current
    var randomData by remember {
        mutableStateOf(RankingData.loadRandomRankingData(context, "rankings"))
    }



    var countryToPlace by remember {
        mutableStateOf(randomData.countries.random())
    }

    var placedCountries = remember {
        mutableListOf<CountryValue>(randomData.countries.filter { it.code != countryToPlace.code }.random())
    }
    var selectedPlaceIndex by remember {
        mutableStateOf(5)
    }
    var isGameOver by rememberSaveable{
        mutableStateOf(true)
    }
    var running by remember {
        mutableStateOf(false)
    }
    var timeLeft by remember {
        mutableStateOf(20)
    }
    var score by remember {
        mutableStateOf(1)
    }
    LaunchedEffect(key1 = running){
        while (!isGameOver && timeLeft>0){
            delay(1000L)
            timeLeft-=1
        }
        isGameOver = true
    }




    if(isGameOver){
        Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
            Button(onClick = { running =!running
            isGameOver = false
                timeLeft=20
            }) {
                Text(text = "Start a new game")
            }
        }
    }else{
        Column {
            Text(text = "selectedINdex $selectedPlaceIndex")
            GameSession(
                randomData.critere,
                randomData.unit,
                countryToPlace,
                placedCountries,
                score,
                {
                    index -> selectedPlaceIndex = index
                    var temp = placedCountries
                    if(selectedPlaceIndex<0 ) {
                        if (countryToPlace.valeur <= placedCountries[selectedPlaceIndex + 1].valeur)
                            placedCountries.add(0,countryToPlace)
                        score *= 2
                        countryToPlace =
                            randomData.countries.filter { it.code != countryToPlace.code }.random()
                    }else{
                        if (countryToPlace.valeur >= placedCountries[selectedPlaceIndex].valeur){
                            if(selectedPlaceIndex>=placedCountries.size){
                                selectedPlaceIndex = placedCountries.size - 1
                            }
                            placedCountries.add(countryToPlace)
                            score*=2
                            countryToPlace = randomData.countries.filter { it.code != countryToPlace.code }.random()

                        }
                    }





                },
                isGameOver,
                {/*
                randomData = RankingData.loadRandomRankingData(context,"rankings")
                countryToPlace = randomData.countries.random()
                placedCountries.clear()
                placedCountries.add(randomData.countries.filter { it.code != countryToPlace.code }.random())

*/
                },
                timeLeft
            )



        }

    }

}