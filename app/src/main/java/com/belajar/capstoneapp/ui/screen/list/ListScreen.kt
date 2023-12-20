package com.belajar.capstoneapp.ui.screen.list

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.belajar.capstoneapp.R
import com.belajar.capstoneapp.ui.component.SectionText
import com.belajar.capstoneapp.ui.theme.Green100
import com.belajar.capstoneapp.ui.theme.Green200
import com.belajar.capstoneapp.ui.theme.Green300
import com.belajar.capstoneapp.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Composable
fun ListScreen(
    navController: NavController,
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit,
    cameraViewModel: CameraViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    val capturedImageUri = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
        "uriArg").toString()
    val myFile = uriToFile(Uri.parse(capturedImageUri), LocalContext.current)
    val getFile = myFile
    val file = reduceFileImage(getFile as File)
    val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
    val imageMultipart: MultipartBody.Part =
        MultipartBody.Part.createFormData("files", file.name, requestImageFile)

    cameraViewModel.getRecipeByPhoto(imageMultipart)
    val data = cameraViewModel.recipe.observeAsState().value
    var loading = true
    val load = cameraViewModel.loading.value

    if (data != null) {
        loading = false
        Log.d("inidata", data.toString())
        ListContent(
            listRecipe = data?.recommendations,
            navigateBack = navigateBack,
            navigateToDetail = navigateToDetail
        )
    }

    if (load != true) {
        loading = false
        EmptyContent(
            navigateBack = navigateBack,
            navigateToDetail = navigateToDetail
        )
    }

    if (loading == true) {
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else {
        return
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListContent(
    listRecipe: List<Recipe>?,
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
                    items(listRecipe, key = { it.title }) { f ->
                        RecipeListItem(
                            slugs = f.slugs,
                            title = f.title,
                            img = f.img,
                            category = f.category,
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
                contentDescription = "Back",
            )
        }
    }
}

@Composable
fun EmptyContent(
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 32.dp)
                .testTag("foodList")
        ) {
//            {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Resep tidak ditemukan",
                            modifier = Modifier.testTag("emptyText")
                        )
                    }
                }
//            }
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
                contentDescription = "Back",
            )
        }
    }
}

@Composable
fun RecipeListItem(
    slugs: String,
    title: String,
    img: String,
    category: String,
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
                navigateToDetail(slugs)
            }
        ) {
            AsyncImage(
                model = img,
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
                Category(
                    category = category
                )
            }
        }
    }
}

@Composable
fun Category(
    category: String
) {
    Card(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 10.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Green200)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(
                fontSize = 14.sp,
                text = category,
                color = Green100
            )
        }
    }
}