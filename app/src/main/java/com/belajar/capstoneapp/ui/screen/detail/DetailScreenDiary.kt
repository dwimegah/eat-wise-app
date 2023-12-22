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
import com.belajar.capstoneapp.model.FoodData
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
fun DetailScreenDiary(
    foodId: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    viewModel.getRecipeBySlug(foodId)
//    val it = viewModel.detail.observeAsState().value
//    data?.forEach {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFoodById(foodId)
            }

            is UiState.Success -> {
                val it = uiState.data
                DetailInformation2(
                    slugs = it.slugs,
                    title = it.name,
                    image = it.photoUrl,
                    preparation = it.preparation,
                    ingredients = it.ingredients,
                    category = it.category,
                    calories = it.description,
                    carb = it.carb,
                    fat = it.fat,
                    protein = it.protein,
                    isFav = it.isFavorite,
                    navigateBack = navigateBack
                )
            }
            is UiState.Error -> {}
        }
    }
//    }
}
@Composable
fun DetailInformation2(
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
    isFav: Boolean,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    var isClicked by remember { mutableStateOf(isFav)}

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
                viewModel.updateFav(slugs)
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