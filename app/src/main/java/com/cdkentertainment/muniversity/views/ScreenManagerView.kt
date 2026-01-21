package com.cdkentertainment.muniversity.views

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import com.cdkentertainment.muniversity.view_models.ScreenManagerViewModel
import com.cdkentertainment.muniversity.view_models.Screens

@Composable
fun ScreenManager(
    currentScreen: Screens,
    screenManagerViewModel: ScreenManagerViewModel
) {
    val activity: Activity? = LocalActivity.current

    BackHandler(
        enabled = screenManagerViewModel.selectedScreen != Screens.LOGIN
    ) {
        if (screenManagerViewModel.screensStack.count() < 2) {
            activity?.moveTaskToBack(true)
            return@BackHandler
        }
        screenManagerViewModel.retractScreen()
    }
    AnimatedContent(
        transitionSpec = { fadeIn() + slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) togetherWith fadeOut() + slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End) },
        targetState = currentScreen
    ) { target ->
        when (target) {
            Screens.LOGIN -> LoginPageView(
                screenManagerViewModel = screenManagerViewModel
            )
            Screens.HOME -> HomePageView()
            Screens.GRADES -> GradesPageView()
            Screens.TESTS -> TestsPageView()
            Screens.CALENDAR -> SchedulePageView()
            Screens.GROUPS -> ClassGroupsPageView()
            Screens.PAYMENTS -> PaymentsPageView()
            //Screens.ATTENDANCE -> AttendancePageView()
            Screens.LECTURERS -> LecturerRatesPageView()
            Screens.SETTINGS -> SettingsPageView()
        }
    }
}