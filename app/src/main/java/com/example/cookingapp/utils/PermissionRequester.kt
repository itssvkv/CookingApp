package com.example.cookingapp.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.cookingapp.utils.Common.openGalleryForMultipleImages


fun PermissionRequester(
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    multiImageLauncher:  ManagedActivityResultLauncher<Intent, ActivityResult>,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onRecipeImageChanged: (uri: String) -> Unit,
    onRecipeIngredientsImagesChanged: (uris: List<String>) -> Unit,
) {

}

