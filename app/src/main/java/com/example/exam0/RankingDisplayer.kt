package com.example.exam0

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/*
Composant de classement
Ce composant nommé RankingDisplayer permet d'afficher un classement
(List<CountryValue>). Il affiche une liste défilante de pays sous la forme de
CountryDisplayer. Deux variantes d'affichage sont possibles :
une variante affichant simplement les pays
une autre variante affichant en plus aux positions intermédiaires ainsi qu'avant le 1er pays et
après le dernier des boutons permettant de sélectionner une position
RankingDisplayer est de signature suivante :
@Composable
fun RankingDisplayer(data: List<CountryValue>, onClick: ((Int) -> Unit)? = null)
Si onClick est null, la variante sans boutons intermédiaires est affichée, sinon les boutons
sont affichés et onClick est appelé lorsque l'on clique sur l'un deux. L'indice passé en
paramètre à onClick correspond à la position d'insertion choisie (allant de 0 à data.size en
cas d'ajout en dernière position).
 */

@Composable
fun RankingDisplayer(data: List<CountryValue>, onClick: ((Int) -> Unit)? = null) {
    LazyColumn{

        items(data){ item ->
            if(onClick == null)
                CountryDisplayer(code = item.code, name = item.name, value = item.valeur )
            else
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = {
                        onClick(data.indexOf(item)-1)

                    }) {
                        Text(text = "Place here")
                    }
                    CountryDisplayer(code = item.code, name = item.name, value = item.valeur )
                    Button(onClick = { onClick(data.indexOf(item))}) {
                        Text(text = "Place here")
                    }

                }
        }

    }
}
@Preview
@Composable
fun test4(){
    var countriesList = RankingData.loadRandomRankingData(LocalContext.current,"rankings").countries
    var onCountryClicked: (CountryValue) -> Unit
    RankingDisplayer(countriesList, onClick = {
        indexVal ->
    })

}