package com.belajar.capstoneapp.ui.screen.diary

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.belajar.capstoneapp.PieChart
import com.belajar.capstoneapp.R
import com.belajar.capstoneapp.ViewModelFactory
import com.belajar.capstoneapp.di.Injection
import com.belajar.capstoneapp.model.FoodData
import com.belajar.capstoneapp.navigation.Screen
import com.belajar.capstoneapp.ui.common.UiState
import com.belajar.capstoneapp.ui.component.DiaryFood
import com.belajar.capstoneapp.ui.component.DiaryItem
import com.belajar.capstoneapp.ui.component.SectionText
import com.belajar.capstoneapp.ui.theme.Dark100
import com.belajar.capstoneapp.ui.theme.Green100
import com.belajar.capstoneapp.ui.theme.Green200
import com.belajar.capstoneapp.ui.theme.Green300
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen (
    modifier: Modifier = Modifier,
    viewModel: DiaryViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFav()
            }

            is UiState.Success -> {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    TopAppBar(
                        modifier = Modifier.padding(5.dp),
                        title = {
                            Text(
                                text = stringResource(R.string.title_diary),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Green300
                            )
                        }
                    )
                    Tanggal()
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
                    SectionText(stringResource(R.string.section_diary))
                    Box(modifier = modifier.padding(top = 10.dp)) {
                        val listState = rememberLazyListState()
                        Column (
                        ) {
                            Log.d("cek uistate: ", uiState.data.isNotEmpty().toString())
                            if (!uiState.data.isEmpty()) {
                                uiState.data.forEach { food ->
                                    DiaryItem(
                                        slugs = food.slugs,
                                        name = food.name,
                                        cal = food.description,
                                        photoUrl = food.photoUrl,
                                        category = food.category,
                                        navigateToDetail = navigateToDetail,
                                        isFav = food.isFavorite,
                                        modifier = Modifier
                                            .clickable { navigateToDetail(food.slugs) }
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .width(400.dp)
                                        .height(150.dp)
                                        .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Green100)) {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                                        ) {
                                            Text(
                                                text = "Tidak ada data",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Green300,
                                                modifier = Modifier.width(300.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {}
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
                navigateToDetail("1")
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
                    text = "2000 cal",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green300
                )
            }
        }
    }
}

@Composable
fun Tanggal(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp, start = 15.dp)
    ) {
        Row (
        ) {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)

            itemTanggal("Tgl", (day.toString().toInt() - 6).toString())
            itemTanggal("Tgl", (day.toString().toInt() - 5).toString())
            itemTanggal("Tgl", (day.toString().toInt() - 4).toString())
            itemTanggal("Tgl", (day.toString().toInt() - 3).toString())
            itemTanggal("Tgl", (day.toString().toInt() - 2).toString())
            itemTanggal("Tgl", (day.toString().toInt() - 1).toString())
            itemTanggal("Tgl", day.toString())
        }
    }
}

@Composable
fun itemTanggal(
    hari: String,
    tanggal: String
) {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp),
        ) {
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)

            if (day.toString() == tanggal) {
                Column(
                    modifier = Modifier
                        .background(color = Green200)
                        .clip(RoundedCornerShape(10.dp))
                        .padding(start = 10.dp, end = 10.dp),
                ) {
                    Text(
                        fontSize = 12.sp,
                        text = "Tgl",
                        color = Green300
                    )
                    Text(
                        text = tanggal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Green300
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(start = 10.dp, end = 10.dp),
                ) {
                    Text(
                        fontSize = 12.sp,
                        text = "Tgl",
                        color = Green300
                    )
                    Text(
                        text = tanggal,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Green300
                    )
                }
            }
        }
}