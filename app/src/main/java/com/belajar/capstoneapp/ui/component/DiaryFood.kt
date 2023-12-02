package com.belajar.capstoneapp.ui.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.belajar.capstoneapp.model.FoodData
import com.belajar.capstoneapp.ui.theme.Dark100
import com.belajar.capstoneapp.ui.theme.Green100
import com.belajar.capstoneapp.ui.theme.Green300

@Composable
fun DiaryFood(
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.padding(top = 10.dp)) {
        val listState = rememberLazyListState()
        Column (
        ) {
            FoodData.food.forEach { food ->
                DiaryItem(
                    name = food.name,
                    cal = food.id,
                    category = food.category,
                    navigateToDetail = navigateToDetail,
                    modifier = Modifier
                    .clickable { navigateToDetail("1") }
                )
            }
        }
    }
}

@Composable
fun DiaryItem(
    name: String,
    cal: String,
    category : String,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Green100)) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                navigateToDetail("1")
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://cardamomandcoconut.com/wp-content/uploads/2019/02/Instant-Pot-Banana-Oatmeal-10.jpg",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Column(
                modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            ) {
                Text(
                    text = "Oatmeal with banana",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green300
                )
                Text(
                    fontSize = 12.sp,
                    text = "300 Kalori",
                    color = Dark100
                )
                OutlinedButton(
                    onClick = { },
                ) {
                    Text("Sarapan")
                }
            }
        }
    }
}