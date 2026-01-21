package com.cdkentertainment.muniversity.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cdkentertainment.muniversity.R

class ScreenManagerViewModel: ViewModel() {
    var screensStack: ArrayDeque<Screens> = ArrayDeque(listOf())
    var selectedScreen: Screens by mutableStateOf(Screens.LOGIN)
        private set
    var showFab: Boolean by mutableStateOf(false)

    fun changeScreen(newScreen: Screens) {
        if (selectedScreen == newScreen) {
            return
        }
        screensStack.addLast(selectedScreen)
        selectedScreen = newScreen
    }

    fun retractScreen() {
        if (screensStack.count() < 2) {
            return
        }
        selectedScreen = screensStack.removeLast()
    }

    fun authorize() {
        changeScreen(Screens.HOME)
    }
}

enum class Screens(val pageName: Int) {
    LOGIN(R.string.app_name),
    HOME(R.string.home_page),
    GRADES(R.string.grade_page),
    TESTS(R.string.tests_page),
    CALENDAR(R.string.schedule_page),
    GROUPS(R.string.class_groups_page),
    PAYMENTS(R.string.payments_page),
    //ATTENDANCE(R.string.attendance_page),
    LECTURERS(R.string.lecturers_page),
    SETTINGS(R.string.settings_page);

    companion object {
        fun fromOrdinal(ordinal: Int): Screens? = Screens.entries.getOrNull(ordinal)
    }
}