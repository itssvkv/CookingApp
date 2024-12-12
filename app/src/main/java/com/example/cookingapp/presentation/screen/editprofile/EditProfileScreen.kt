package com.example.cookingapp.presentation.screen.editprofile

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookingapp.R
import com.example.cookingapp.presentation.components.CommonHeaderSection
import com.example.cookingapp.presentation.components.MainButton
import com.example.cookingapp.presentation.components.MainTextField
import com.example.cookingapp.presentation.screen.profile.ProfileImageBox
import com.example.cookingapp.presentation.screen.profile.ProfileScreen
import com.example.cookingapp.utils.Common.openGalleryForMultipleImages
import com.example.cookingapp.utils.Common.permissionChecker
import com.example.cookingapp.utils.Constants
import com.example.cookingapp.utils.RealPathUtil.getRealPathFromURI

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: EditProfileScreenViewModel = hiltViewModel(),
    userId: String? = "",
    userPhoto: Uri? = null,
    userName: String? = "",
    userEmail: String? = "",
    onBackIconClicked: () -> Unit = {},
    onUpdateUserInfoSuccess: () -> Unit = {},
    onUpdatedEmailSuccess: () -> Unit = {}
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true) {
        viewModel.onReceiveUserData(
            userId = userId,
            userPhoto = userPhoto,
            userName = userName,
            userEmail = userEmail
        )
    }
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
            viewModel.onPhotoChanged(Uri.parse(realPaths[0]))
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
    EditProfileScreenContent(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { focusManager.clearFocus() },
        uiState = uiState,
        onBackIconClicked = onBackIconClicked,
        onButtonClicked = viewModel::onSaveButtonClicked,
        onCoverClicked = {
            permissionChecker(context, permissionLauncher, multiImageLauncher)
        },
        onNameChanged = viewModel::onNameChanged,
        onChangeClicked = viewModel::onChangeClicked,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        focusRequester = focusRequester
    )

    LaunchedEffect(
        key1 = uiState.isUserInfoUpdatedSuccessfully,
        key2 = uiState.isUserEmailUpdatedSuccessfully,
        key3 = uiState.error
    ) {
        if (uiState.isUserInfoUpdatedSuccessfully &&
            !uiState.isUserEmailUpdatedSuccessfully && uiState.error == null
        ) {
            onUpdateUserInfoSuccess()
        }
    }
    LaunchedEffect(key1 = uiState.isUserEmailUpdatedSuccessfully, key2 = uiState.error) {
        if (uiState.isUserEmailUpdatedSuccessfully) {
            onUpdatedEmailSuccess()
        }
        if (uiState.error != null && !uiState.isUserEmailUpdatedSuccessfully) {
            Toast.makeText(context, uiState.error, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun EditProfileScreenContent(
    modifier: Modifier = Modifier,
    uiState: EditProfileScreenUiState,
    onBackIconClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    onCoverClicked: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    onChangeClicked: () -> Unit = {},
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit = {},
    focusRequester: FocusRequester
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditProfileScreenHeader(
            onButtonClicked = onButtonClicked,
            onBackIconClicked = onBackIconClicked,
            isLoading = uiState.isLoading,
            isEnabled = !(uiState.userName.isNullOrEmpty() ||
                    uiState.userPhoto == null)
        )
        EditProfileScreenBody(
            userPhoto = uiState.userPhoto,
            name = uiState.userName ?: "",
            email = uiState.userEmail ?: "",
            updatedEmail = uiState.updatedEmail ?: "",
            onCoverClicked = onCoverClicked,
            onNameChanged = onNameChanged,
            onChangeClicked = onChangeClicked,
            onEmailChanged = onEmailChanged,
            isChangeClicked = uiState.isChangeClicked,
            password = uiState.password,
            onPasswordChanged = onPasswordChanged,
            focusRequester = focusRequester
        )
    }
}

@Composable
fun EditProfileScreenHeader(
    modifier: Modifier = Modifier,
    onBackIconClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    isLoading: Boolean = false,
    isEnabled: Boolean = false,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        CommonHeaderSection(
            modifier = Modifier.weight(5f),
            title = "",
            onBackIconClicked = onBackIconClicked
        )
        MainButton(
            modifier = Modifier
                .weight(1.8f)
                .height(40.dp),
            onButtonClicked = onButtonClicked,
            text = "Save",
            isLoading = isLoading,
            isEnabled = isEnabled
        )

    }
}

@Composable
fun EditProfileScreenBody(
    modifier: Modifier = Modifier,
    userPhoto: Uri? = null,
    name: String = "",
    email: String = "",
    updatedEmail: String = "",
    password: String = "",
    onCoverClicked: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onChangeClicked: () -> Unit = {},
    isChangeClicked: Boolean = false,
    onEmailChanged: (String) -> Unit = {},
    focusRequester : FocusRequester
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ProfileImageBox(
            showTheImageCover = true,
            onCoverClicked = onCoverClicked,
            userPhoto = userPhoto,
        )
        Spacer(modifier = Modifier.height(24.dp))
        MainTextField(
            value = name,
            onValueChange = onNameChanged,
            label = Constants.CustomTextFieldLabel.NAME,
            leadingIcon = null,
            focusRequester = focusRequester
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Email",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isChangeClicked) {
                Box(
                    modifier = Modifier.weight(5f)
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp), // Optional padding to separate the line
                        value = updatedEmail,
                        onValueChange = onEmailChanged,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        singleLine = true
                    )
                    // Add the line below
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray) // Change to your desired color
                            .align(Alignment.BottomCenter)
                    )
                }

            } else {
                Text(
                    textAlign = TextAlign.Center,
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isChangeClicked) "Cancel" else "Change",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    onChangeClicked()
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (isChangeClicked) {
            var isShowPassword: Boolean by remember { mutableStateOf(false) }
            var visualTransformation: VisualTransformation by remember {
                mutableStateOf(
                    VisualTransformation.None
                )
            }
            visualTransformation =
                if (!isShowPassword) PasswordVisualTransformation() else VisualTransformation.None
            Text(
                text = "Password",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(5f)
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp), // Optional padding to separate the line
                        value = password,
                        onValueChange = onPasswordChanged,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = visualTransformation,
                        singleLine = true
                    )
                    // Add the line below
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray) // Change to your desired color
                            .align(Alignment.BottomCenter)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { isShowPassword = !isShowPassword },
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = if (isShowPassword) painterResource(id = R.drawable.hide_pass)
                        else painterResource(id = R.drawable.show_pass),
                        contentDescription = "back"
                    )

                }
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        EditProfileScreen()
    }
}