@file:OptIn(ExperimentalMaterial3Api::class)

package com.belajar.capstoneapp.ui.screen.home

import android.util.Log
import android.view.KeyEvent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.belajar.capstoneapp.PieChart
import com.belajar.capstoneapp.R
import com.belajar.capstoneapp.ui.component.DiaryFood
import com.belajar.capstoneapp.ui.component.SectionText
import com.belajar.capstoneapp.ui.theme.Dark100
import com.belajar.capstoneapp.ui.theme.Dark200
import com.belajar.capstoneapp.ui.theme.Green100
import com.belajar.capstoneapp.ui.theme.Green300
import com.belajar.capstoneapp.viewmodel.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.belajar.capstoneapp.ViewModelFactory
import com.belajar.capstoneapp.di.Injection
import com.belajar.capstoneapp.ui.common.UiState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import com.belajar.capstoneap.model.Goal
import com.belajar.capstoneap.model.Recipe
import com.belajar.capstoneapp.data.pref.UserModel
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit
) {
    val query by viewModel.query
    HomeContent(
        navController = navController,
        query = query,
        //            listGoal = data.data,
        onQueryChange = viewModel::search,
        navigateToDetail = navigateToDetail
    )

//    val dataSession = viewModel.ses.value

//    LaunchedEffect(true) {
//        viewModel.getSession().collect { newUser ->
//            viewModel.getGoal(newUser.token)
//            val data = viewModel.goal.value
//            Log.d("inidata goal", viewModel.getGoal(newUser.token).value.toString())
//            Log.d("cek data session", newUser.toString())
//        }
//    }

//    if (data !=  null) {

//    }
}

@Composable
fun HomeContent(
    navController : NavHostController,
    query: String,
//    listGoal: List<Goal>,
    onQueryChange: (String) -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        ProfileCard(navController = navController, query = query, onQueryChange = onQueryChange)
        Spacer(modifier = Modifier.height(24.dp))
        SectionText(stringResource(R.string.section_goals))
        Banner(
            navigateToDetail = navigateToDetail
        )
        // Preview with sample data
        PieChart(
            data = mapOf(
                Pair("Carb", 30),
                Pair("Protein", 10),
                Pair("Fat", 10),
                Pair("Less", 50),
            )
        )
        SectionText(stringResource(R.string.section_recommend))
        LazyRow(
            modifier = modifier
                .clickable { navigateToDetail("quick-&-healthy-banana-boats") }
        ) {
            items(1) { // Replace 10 with the actual number of items in your DiaryFood
                // DiaryFood item content goes here
                DiaryFood(navigateToDetail = navigateToDetail)
            }
        }
    }
}

    @Composable
    fun ProfileCard(
        navController : NavHostController,
        query: String,
        onQueryChange: (String) -> Unit,
    ) {
        var expanded by remember { mutableStateOf(false) }
        var searchType by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.img),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column {
            Row(
                modifier = Modifier,
            ) {
                SearchBar(
                    navController = navController,
                    query = query,
                    onQueryChange = onQueryChange
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .paint(
                            painterResource(id = R.drawable.avatar_icon_girl)
                        )
                        .clip(CircleShape)
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = "Selamat Datang",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Dwi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBar(
        navController: NavHostController,
        query: String,
        onQueryChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var expanded by remember { mutableStateOf(false) }
        var searchType by remember { mutableStateOf("") }

        Row (modifier = modifier) {
            Column() {
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(50),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(stringResource(id = R.string.search_menu))
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            navController.currentBackStackEntry?.savedStateHandle?.apply {
                                set("searchKey", query)
                            }
                            navController.navigate("search")
                        }
                    ),
                    modifier = modifier
//                        .onKeyEvent {
//                            if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_TAB){
//                                navController.currentBackStackEntry?.savedStateHandle?.apply {
//                                    set("searchKey", query)
//                                }
//                                navController.navigate("search")
//                            }
//                            false
//                        }
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .heightIn(min = 48.dp)
                        .shadow(56.dp)
                        .width(220.dp)
                )
            }
            Column (modifier = Modifier
                .padding(start = 8.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                .heightIn(min = 40.dp)
                .shadow(56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        shape = RoundedCornerShape(50),
                        value = searchType,
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded,
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            disabledIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text("tipe")
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "menu")
                            },
                            onClick = {
                                searchType = "menu"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "bahan")
                            },
                            onClick = {
                                searchType = "bahan"
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Banner(
        modifier: Modifier = Modifier,
        navigateToDetail: (String) -> Unit,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Green100)) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.clickable {
                    navigateToDetail("quick-&-healthy-banana-boats")
                }
            ) {
                Column (
                    modifier = Modifier.padding(start = 20.dp)
                ) {
                    AsyncImage(
                        model = "https://cdn-icons-png.flaticon.com/512/485/485256.png",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
                Column() {
                    Text(
                        fontSize = 12.sp,
                        text = "Calory intake",
                        color = Dark100
                    )
                    Text(
                        text = "800 cal",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Green300
                    )
                }
            }
        }
    }