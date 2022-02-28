package com.apptive.linkit.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.apptive.linkit.LinkItApp
import com.apptive.linkit._enums.AnimationSpec
import com.apptive.linkit._enums.AnimationSpec.*
import com.apptive.linkit.domain.model.EMPTY_BITMAP
import com.apptive.linkit.domain.model.log
import com.apptive.linkit.presentation.model.Size
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

/** Composable 함수 내에서 현재 Context 반환. 축약형으로 쓰기 위함 */
@Composable
fun cxt() : Context = LocalContext.current

@Composable
fun viewModelOwner() : ViewModelStoreOwner = cxt() as ViewModelStoreOwner

/** 간단한 토스트 메시지를 하단에 띄운다. */
fun String.toast(length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(LinkItApp.cxt(), this, Toast.LENGTH_SHORT).show()
}

/** Modifier 관련 익스텐션 */
fun <T>Modifier.longPress(
    key: T,
    onLongClick: () -> Unit,
) = pointerInput(key) {
        detectTapGestures( onLongPress = {onLongClick()} )
    }

/** 애니메이션 Wrapper */
@Composable
fun AnimatePopup(
    visible: Boolean,
    type: AnimationSpec = SLIDE_UP,
    beforeAnimation: () -> Unit = {},
    afterAnimation: () -> Unit = {},
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    beforeAnimation()
    AnimatedVisibility(
        visible = visible,
        enter = type.enter,
        exit = type.exit,
        content = content
    )

    afterAnimation()
}

@Composable
/** 픽셀을 Dp로 변환한다. */
fun Int.toDp()
    = with(LocalDensity.current) {
        this@toDp.toDp()
    }

@Composable
/** 현재 화면의 route를 반환한다. */
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
/** Drawable 이미지를 Bitmap으로 변환한다. */
fun getBitmap(id: Int) : Bitmap {
    val drawable = AppCompatResources.getDrawable(cxt(), id)

    return drawable?.toBitmap() ?: EMPTY_BITMAP
}

@Composable
/** 이미지 파일을 선택하는 런처 */
fun chooseImageLauncher(
    onResult: (Uri?) -> Unit
) = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = onResult
    )

@Composable
/** 다른 액티비티를 실행하는 런처를 반환한다. */
fun activityLauncher(
    onError: () -> Unit = {},
    onSuccess: (Intent) -> Unit = {}
) = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data

            if (result.data != null) onSuccess(intent!!)
            else "ActivityResult: 전달받은 데이터가 없습니다.".log()
        } else {
            onError()
        }
    }

fun Modifier.size(size: Size) = this.then(
    Modifier.size(width = size.width, height = size.height)
)