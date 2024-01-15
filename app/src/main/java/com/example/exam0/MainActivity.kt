package com.example.exam0

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exam0.ui.theme.Exam0Theme
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exam0Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    test7()
                }
            }
        }
    }
}

//Graphics
@Preview
@Composable
fun BoxRotation(){

    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .border(2.dp, Color.Blue)) {
        var w by remember {
            mutableStateOf(this.maxWidth)
        }
        var h by remember {
            mutableStateOf(this.maxHeight)
        }

        var ncols = w.div(100.dp).toInt()
        Column(Modifier.fillMaxSize()) {
            repeat(5){
                Row(
                    Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState())){

                    repeat(ncols){
                        Card(
                            Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .background(
                                    Color(
                                        Random.nextFloat(),
                                        Random.nextFloat(),
                                        Random.nextFloat()
                                    )
                                )) {
                            // Text(text = "$it")
                        }
                    }
                    Divider(color = Color.Black, modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp))
                }
            }
        }



    }
}
//LazyRow and LazyColumn : to display list of elements, scrollable by default
@Preview
@Composable
fun lazyCol(){
    LazyColumn(Modifier.fillMaxSize()){
        repeat(60){
            item{
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(Random.nextFloat(), 1f, 0f))) {
                    Text(text = "$it")
                }
            }

        }
    }

}
@Preview
@Composable
fun lazyRow(){
    LazyRow(Modifier.fillMaxSize()){
        repeat(60){
            item{
                Row(
                    Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .background(Color(Random.nextFloat(), 1f, 0f))) {
                    Text(text = "$it")
                }
            }

        }
    }

}

//Scaffold
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun scaffoldTest(){
    var t by remember {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
       TopAppBar(
           title = { Text(text = "tOP BAR TITLE") },
           actions = {
               Button(onClick = { t+=1}) {
                   Text(text = "Increment by 1")
               }
            },
           navigationIcon = { IconButton(onClick = { t+=5 }) {
               Icon(Icons.Filled.AccountBox, contentDescription = "menu") } },
       )
    },
        bottomBar = {
            BottomAppBar {
                Text("Edit:")
                TextField("$t", onValueChange = {  t = it.toIntOrNull() ?: 0 })
            }
        }) {
        Box(modifier=Modifier.fillMaxSize()) {
            Box(modifier=Modifier.align(Alignment.Center)) { Text("this is the content $t", fontSize = 100.sp) }
        }
        
    }
}

//Lambda expressions

@Composable
fun ShowMessage(block : (msg:String)->String){
    Column {
        Text(text = block("Massi") )
    }
}

@Preview
@Composable
fun testLambda(){
    var text = "#"
    ShowMessage{
        //input -> output
        msg -> "hghgh" + msg
    }
}

//send Values to a parent
@Preview
@Composable
fun parentFun(){
    var temp by remember {
        mutableStateOf(0)
    }
    Column{
        Text(text="The value of temp is $temp")
    }
    childFun(onChange = { temp +=1 }, t = temp,10)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun childFun(onChange:()->Unit,t:Int,stopAt:Int){
    LaunchedEffect(key1 = t){
        delay(1000)
        if(t<stopAt)
            onChange()
    }
}
/*
#### Suspend functions  :
    Make functions able to stop and resume
    Not responsible to do bg stuff
    Must be called from another suspend fun or couroutine Builder



#### Call a suspend function :
    ### Couroutine Builder :
        - has a scope (what scope he'll work in),
        - wait result/dont wait for a response,
        - Which thread he starts in
        - Body

        2- Not excpecting anything back

            We can choose a Dispacher, Main mostly for UI
            Default for heavy calculations
            IO for getting data from file, server, db,..
            Unconfined : complicated
            Or give custom thread where to run : newSingleThreadContext
            GlobalScope.launch(Dispatchers.Main,Default,IO,Unconfined){

            }

            Ex : Use IO Dispatcher to modify a property that belong to the main thread : error
            Fix : Switch/change the dispacher :
            GlobaleScope.launch(Dispatchers.IO){
                   //switch to another context
                   withContext(Dispatchers.Main){
                    //code
                   }
            }

### Dispatchers :

 */


//Effect Handlers
//LaunchedEffect
@Preview
@Composable
fun handler1(){
    //launchedEffect
    var text by remember {
        mutableStateOf("")
    }

    //wheneven the text state changes, this couroutine will be canceled and relaunched with the new text value
    LaunchedEffect(key1 = text){
        text = "text changed"

    }
    //
    LaunchedEffect(key1 = true){
        //true : this will execute once, so when there is a recomposition , it will not be executed again
    }
    Column {
        Text(text = text)
        Button(onClick = {text+= "#"}) {
            Text(text = "add # to text")
        }
    }

}

//RememberUpdatedState : to save the value for the next recomposition
@Composable
fun handler2(onTimeout : ()->Unit){
    val updatedOnTimeout by rememberUpdatedState(newValue = onTimeout)
    LaunchedEffect(key1 = true){
        delay(1000)
        updatedOnTimeout()

    }

}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun setTextAfterDelay(text:String){
    var t by remember {
        mutableStateOf(text)
    }
    GlobalScope.launch(Dispatchers.IO) {
        delay(2000)
        withContext(Dispatchers.Default){
            t = "new Text"
        }
    }
    Text(text = "Hello, the text is $t" )

}

@SuppressLint("CoroutineCreationDuringComposition")
@Preview
@Composable
fun test7(){
    var t by remember {
        mutableStateOf("not equals")
    }
   // setTextAfterDelay(text = "The first text")
    GlobalScope.launch {
        var u1 = async { getUser() }
        var u2 = async { getData() }
        if(u1.await() == u2.await()){
            t = "Equals"
        }

    }
    Text(text = t)
}

suspend fun getUser():String{
    delay(2000)
    return "Massi"
}

suspend fun getData():String{
    delay(3000)
    return "Massi"
}