package com.belajar.capstoneapp.ui.screen.list

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.belajar.capstoneap.model.Recipe
import com.belajar.capstoneapp.ViewModelFactory
import com.belajar.capstoneapp.di.Injection
import com.belajar.capstoneapp.ui.screen.camera.CameraViewModel
import com.belajar.capstoneapp.utils.uriToFile
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.belajar.capstoneap.model.Food
import com.belajar.capstoneapp.model.FoodData
import com.belajar.capstoneapp.ui.common.UiState
import com.belajar.capstoneapp.ui.screen.diary.DiaryViewModel
import com.belajar.capstoneapp.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Composable
fun SearchScreen(
    navController: NavController,
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit,
    viewModel: DiaryViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    val searchKey = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
        "searchKey").toString()
//    val query by viewModel.query
    Log.d("searchkey:", searchKey)
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(searchKey)
            }
            is UiState.Success -> {
                ListContentSearch(listRecipe = uiState.data, navigateBack = navigateBack, navigateToDetail = navigateToDetail)
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListContentSearch(
    listRecipe: List<Food>,
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val listState = rememberLazyListState()
        LazyColumn (
            state = listState,
            modifier = Modifier.testTag("foodList")
        ) {
            if (listRecipe != null) {
                if (listRecipe.isNotEmpty()) {
                    items(listRecipe, key = { it.id }) { f ->
                        RecipeListItemSearch(
                            id = f.slugs,
                            title = f.name,
                            photoUrl = f.photoUrl,
                            navigateToDetail = navigateToDetail,
                            modifier = Modifier
                                .animateItemPlacement(tween(durationMillis = 200))
                                .clickable { navigateToDetail(f.slugs) }
                        )
                    }
                } else {
                    item {
                        Text(
                            text = "Resep tidak ditemukan",
                            modifier = Modifier.testTag("emptyText")
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeListItemSearch(
    id: String,
    title: String,
    photoUrl: String,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                navigateToDetail(id)
            }
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "food photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}