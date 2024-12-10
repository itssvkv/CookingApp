package com.example.cookingapp.presentation.screen.newrecipe

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cookingapp.R
import com.example.cookingapp.presentation.components.CommonHeaderSection
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.NewTextField
import com.example.cookingapp.utils.Common.fromListToString
import com.example.cookingapp.utils.Common.openGalleryForMultipleImages
import com.example.cookingapp.utils.Common.permissionChecker
import com.example.cookingapp.utils.Constants.TAG
import com.example.cookingapp.utils.RealPathUtil.getRealPathFromURI
import onPrimary
import randomColor1

@Composable
fun NewRecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: NewRecipeScreenViewModel = hiltViewModel(),
    onBackIconClicked: () -> Unit = {}

) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    val multiImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val clipData = result.data?.clipData
            val realPaths = mutableListOf<String>()

            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    val realPath = getRealPathFromURI(context, uri)
                    realPath?.let { realPaths.add(it) }
                }
            } else {
                result.data?.data?.let { uri ->
                    val realPath = getRealPathFromURI(context, uri)
                    realPath?.let { realPaths.add(it) }
                }
            }

            if (realPaths.size > 1) {
                viewModel.onRecipeIngredientsImagesChanged(realPaths)
            } else {
                viewModel.onRecipeImageChanged(realPaths[0])
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openGalleryForMultipleImages(context, multiImageLauncher)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    ScreenContent(
        modifier = modifier,
        uiState = uiState,
        onBackIconClicked = onBackIconClicked,
        onRecipeNameChanged = viewModel::onRecipeNameChanged,
        onRecipeAreaChanged = viewModel::onRecipeAreaChanged,
        onRecipeCategoryChanged = viewModel::onRecipeCategoryChanged,
        onPrepTimeChanged = viewModel::onPrepTimeChanged,
        onPrepTimePlusCounter = viewModel::onPrepTimePlusCounter,
        onPrepTimeMinusCounter = viewModel::onPrepTimeMinusCounter,
        onCookTimeChanged = viewModel::onCookTimeChanged,
        onCookTimePlusCounter = viewModel::onCookTimePlusCounter,
        onCookTimeMinusCounter = viewModel::onCookTimeMinusCounter,
        onButtonClicked = {
            viewModel.saveToDatabase()
            Log.d(TAG, "NewRecipeScreen: $uiState")
        },
        onRecipeLinksChanged = viewModel::onRecipeLinksChanged,
        onRecipeIngredientsChanged = viewModel::onRecipeIngredientsChanged,
        onRecipeMeasuresChanged = viewModel::onRecipeMeasuresChanged,
        onRecipeInstructionsChanged = viewModel::onRecipeInstructionsChanged,
        onImageClicked = {
            permissionChecker(context, permissionLauncher, multiImageLauncher)
        },
        onRecipeIngredientsImagesClicked = {
            permissionChecker(context, permissionLauncher, multiImageLauncher)
        }
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    onBackIconClicked: () -> Unit = {},
    uiState: NewRecipeScreenUiState,
    onRecipeNameChanged: (String) -> Unit = {},
    onRecipeAreaChanged: (String) -> Unit = {},
    onRecipeCategoryChanged: (String) -> Unit = {},
    onPrepTimeChanged: (String) -> Unit = {},
    onPrepTimePlusCounter: () -> Unit = {},
    onPrepTimeMinusCounter: () -> Unit = {},
    onCookTimeChanged: (String) -> Unit = {},
    onCookTimePlusCounter: () -> Unit = {},
    onCookTimeMinusCounter: () -> Unit = {},
    onButtonClicked: () -> Unit,
    onRecipeLinksChanged: (String) -> Unit = {},
    onRecipeIngredientsChanged: (String) -> Unit = {},
    onRecipeMeasuresChanged: (String) -> Unit = {},
    onRecipeInstructionsChanged: (String) -> Unit = {},
    onImageClicked: () -> Unit = {},
    onRecipeIngredientsImagesClicked: () -> Unit = {},

    ) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().statusBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            CommonHeaderSection(title = "Add new recipe", onBackIconClicked = onBackIconClicked)
        }
        item {
            UploadRecipeImage(
                onImageClicked = onImageClicked,
                imageFromDevice = uiState.recipeImageFormDevice
            )
        }
        item {
            ScreenBody(
                uiState = uiState,
                onRecipeNameChanged = onRecipeNameChanged,
                onRecipeAreaChanged = onRecipeAreaChanged,
                onRecipeCategoryChanged = onRecipeCategoryChanged,
                onPrepTimeChanged = onPrepTimeChanged,
                onPrepTimePlusCounter = onPrepTimePlusCounter,
                onPrepTimeMinusCounter = onPrepTimeMinusCounter,
                onCookTimeChanged = onCookTimeChanged,
                onCookTimePlusCounter = onCookTimePlusCounter,
                onCookTimeMinusCounter = onCookTimeMinusCounter,
                onRecipeLinksChanged = onRecipeLinksChanged,
                onRecipeIngredientsChanged = onRecipeIngredientsChanged,
                onRecipeMeasuresChanged = onRecipeMeasuresChanged,
                onRecipeInstructionsChanged = onRecipeInstructionsChanged,
                onRecipeIngredientsImagesClicked = onRecipeIngredientsImagesClicked
            )
        }
        item {
            MainButton(
                text = "Save",
                onButtonClicked = onButtonClicked
            )
        }
    }
}

@Composable
fun UploadRecipeImage(
    modifier: Modifier = Modifier,
    onImageClicked: () -> Unit = {},
    imageFromDevice: String? = null
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(randomColor1)
            .clickable {
                onImageClicked()
            }
            .padding(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Default.Image,
            contentDescription = "Image"
        )
        if (imageFromDevice != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageFromDevice)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.cr7),
                onSuccess = {
                    Log.d(TAG, "SingleMealCard: Success")
                },
                onError = {
                    Log.d(TAG, "SingleMealCard: ${it.result.throwable.message}")
                },
                contentDescription = "imageFromDevice",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
            )
        }

    }
}

@Composable
fun ScreenBody(
    modifier: Modifier = Modifier,
    uiState: NewRecipeScreenUiState,
    onRecipeNameChanged: (String) -> Unit = {},
    onRecipeAreaChanged: (String) -> Unit = {},
    onRecipeCategoryChanged: (String) -> Unit = {},
    onPrepTimeChanged: (String) -> Unit = {},
    onPrepTimePlusCounter: () -> Unit = {},
    onPrepTimeMinusCounter: () -> Unit = {},
    onCookTimeChanged: (String) -> Unit = {},
    onCookTimePlusCounter: () -> Unit = {},
    onCookTimeMinusCounter: () -> Unit = {},
    onRecipeLinksChanged: (String) -> Unit = {},
    onRecipeIngredientsChanged: (String) -> Unit = {},
    onRecipeMeasuresChanged: (String) -> Unit = {},
    onRecipeInstructionsChanged: (String) -> Unit = {},
    onRecipeIngredientsImagesClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        NewTextField(
            title = "Recipe Name",
            value = uiState.strMeal ?: "",
            onValueChange = onRecipeNameChanged,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NewTextField(
                modifier = Modifier.weight(5f),
                title = "Recipe Area",
                value = uiState.strArea ?: "",
                onValueChange = onRecipeAreaChanged
            )
            Spacer(modifier = Modifier.width(4.dp))
            NewTextField(
                modifier = Modifier.weight(5f),
                title = "Recipe Category",
                value = uiState.strCategory ?: "",
                onValueChange = onRecipeCategoryChanged
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NewTextField(
                modifier = Modifier.weight(5f),
                title = "Prep Time",
                value = uiState.prepTime.toString(),
                onValueChange = onPrepTimeChanged,
                isCounter = true,
                onPlusCounter = onPrepTimePlusCounter,
                onMinusCounter = onPrepTimeMinusCounter
            )
            Spacer(modifier = Modifier.width(4.dp))
            NewTextField(
                modifier = Modifier.weight(5f),
                title = "Cook Time",
                value = uiState.cookTime.toString(),
                onValueChange = onCookTimeChanged,
                isCounter = true,
                onPlusCounter = onCookTimePlusCounter,
                onMinusCounter = onCookTimeMinusCounter
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        NewTextField(
            title = "Recipe Links",
            value = "${uiState.strMealThumb ?: ""} ${uiState.strYoutube ?: ""} ${uiState.strSource ?: ""}",
            onValueChange = onRecipeLinksChanged,
            singleLine = false,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NewTextField(
                modifier = Modifier.weight(7f),
                title = "Ingredients",
                value = fromListToString(uiState.ingredient),
                onValueChange = onRecipeIngredientsChanged,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .weight(3f)
                    .border(width = 1.dp, color = onPrimary, RoundedCornerShape(4.dp))
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                        onRecipeIngredientsImagesClicked()
                    }
                    .padding(8.dp),
                text = "Images",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        NewTextField(
            title = "Measures",
            value = fromListToString(uiState.measure),
            onValueChange = onRecipeMeasuresChanged,
        )
        Spacer(modifier = Modifier.height(8.dp))
        NewTextField(
            title = "Instructions",
            value = uiState.strInstructions ?: "",
            onValueChange = onRecipeInstructionsChanged,
            singleLine = false,
        )
    }
}