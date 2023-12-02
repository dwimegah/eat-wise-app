package com.belajar.capstoneapp.ui.screen.diary

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.belajar.capstoneapp.navigation.Screen
import com.belajar.capstoneapp.ui.component.DiaryFood
import com.belajar.capstoneapp.ui.component.SectionText
import com.belajar.capstoneapp.ui.theme.Dark100
import com.belajar.capstoneapp.ui.theme.Green100
import com.belajar.capstoneapp.ui.theme.Green300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen (
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
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
        SectionText(stringResource(R.string.section_goals))
        Banner(
            navigateToDetail = navigateToDetail
        )
        // Preview with sample data
        PieChart(
            data = mapOf(
                Pair("Carb", 150),
                Pair("Protein", 120),
                Pair("Less", 100),
                Pair("Fat", 110)
            )
        )
        SectionText(stringResource(R.string.section_diary))
        DiaryFood(
            navigateToDetail = navigateToDetail,
            modifier = Modifier
                .clickable { navigateToDetail("1") }
        )
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