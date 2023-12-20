package com.belajar.capstoneapp.ui.screen.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.belajar.capstoneapp.R
import com.belajar.capstoneapp.ViewModelFactory
import com.belajar.capstoneapp.di.Injection
import com.belajar.capstoneapp.ui.common.UiState
import com.belajar.capstoneapp.ui.component.SectionText
import com.belajar.capstoneapp.ui.theme.Dark100
import com.belajar.capstoneapp.ui.theme.Green100
import com.belajar.capstoneapp.ui.theme.Green200
import com.belajar.capstoneapp.ui.theme.Green300
import com.belajar.capstoneapp.ui.theme.Orange100
import com.belajar.capstoneapp.ui.theme.Teal100
import com.belajar.capstoneapp.ui.theme.White200
import com.belajar.capstoneapp.viewmodel.DetailViewModel
import com.belajar.capstoneapp.viewmodel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    foodId: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    viewModel.getRecipeBySlug(foodId)
    val it = viewModel.detail.observeAsState().value
//    data?.forEach {
    it?.let { it1 ->
        DetailInformation(
            slugs = it1.slugs,
            title = it.title,
            image = it.image_url,
            preparation = it.preparation,
            ingredients = it.ingredients,
            category = it.category,
            calories = it.calories,
            carb = it.carb,
            fat = it.fat,
            protein = it.protein,
            navigateBack = navigateBack
        )
    }
//    }
}

@Composable
fun DetailInformation(
    slugs: String,
    title: String,
    image: String,
    preparation: String,
    ingredients: String,
    category: String,
    calories : String,
    carb: String,
    fat: String,
    protein: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "food photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            SectionText(title)
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = calories + " Kalori",
                    modifier = Modifier
                        .padding(start= 20.dp)
                )
//                Category(category = "Sarapan")
            }
            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = description,
//                modifier = Modifier
//                    .padding(start = 20.dp, end = 20.dp)
//            )
            Spacer(modifier = Modifier.height(24.dp))
            SectionText(stringResource(R.string.section_gizi))
            KandunganGizi("Protein", carb, fat, protein)
            Spacer(modifier = Modifier.height(24.dp))
            SectionText(stringResource(R.string.section_bahan))
            Text(
                text = ingredients,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            SectionText(stringResource(R.string.section_step))
            Text(
                text = preparation,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            )
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("backHome")
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
            )
        }
        IconButton(
            onClick = {
                isClicked = !isClicked
                Log.d("cek data fav: ", isClicked.toString())
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 16.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
                .testTag("favButton")
        ) {
            Icon(
                imageVector = if (isClicked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "favorite_button",
            )
        }
    }
}

@Composable
fun Category(
    category: String
) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Green100)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                fontSize = 16.sp,
                text = category,
                color = Green300,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun KandunganGizi(
    gizi: String,
    carb: String,
    fat: String,
    protein: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .padding(top = 10.dp, start = 10.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemGizi("Karbohidrat", carb + "g", Green200)
            itemGizi("Protein", protein + "g", Orange100)
            itemGizi("Lemak", fat + "g", Teal100)
        }
    }
}

@Composable
fun Bahan(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemBahan()
        }
    }
}

@Composable
fun itemBahan() {
    Card(
        modifier = Modifier
            .padding(start = 20.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Green100)
    ) {
        Column(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            AsyncImage(
                model = "https://i0.wp.com/post.healthline.com/wp-content/uploads/2020/03/oats-oatmeal-1296x728-header.jpg?w=1155&h=1528",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Oatmeal",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Green300
            )
            Text(
                fontSize = 12.sp,
                text = "1 Cangkir",
                color = Dark100
            )
        }
    }
}

@Composable
fun itemGizi(
    gizi: String,
    berat: String,
    colorbck: Color
) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorbck)
                .height(100.dp)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                fontSize = 12.sp,
                text = gizi,
                color = White200
            )
            Text(
                text = berat,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = White200
            )
        }
    }
}