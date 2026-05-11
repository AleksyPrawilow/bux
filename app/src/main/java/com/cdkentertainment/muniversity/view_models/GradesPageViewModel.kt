package com.cdkentertainment.muniversity.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdkentertainment.muniversity.TermId
import com.cdkentertainment.muniversity.models.Course
import com.cdkentertainment.muniversity.models.CourseGrades
import com.cdkentertainment.muniversity.models.CourseUnitData
import com.cdkentertainment.muniversity.models.GradesDistribution
import com.cdkentertainment.muniversity.models.GradesPageModel
import com.cdkentertainment.muniversity.models.LatestGradeContainer
import com.cdkentertainment.muniversity.models.Season
import com.cdkentertainment.muniversity.models.SharedDataClasses
import com.cdkentertainment.muniversity.models.TermGrade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GradesPageViewModel: ViewModel() {
    var gradesDistribution: MutableMap<Int, Map<String, Int>> = mutableStateMapOf()
    var userSubjects: MutableMap<String, Season> = mutableStateMapOf<String, Season>()
    var latestGrades: List<TermGrade>? by mutableStateOf(null)
        private set
    var trueLatestGrades: MutableList<LatestGradeContainer> = mutableStateListOf()
    var latestGradesLoadingMap: MutableMap<String, Boolean> = mutableStateMapOf()
    var trueLatestGradesNameMap: MutableMap<String, CourseUnitData> = mutableStateMapOf()
    var loadingLatestGrades: Boolean by mutableStateOf(false)
    var errorLatestGrades: Boolean by mutableStateOf(false)
    var loadingMap: MutableMap<String, Boolean> = mutableStateMapOf<String, Boolean>()
    var errorMap: MutableMap<String, Boolean> = mutableStateMapOf<String, Boolean>()
    var loadedMap: MutableMap<String, Boolean> = mutableStateMapOf<String, Boolean>()
    var classtypeIdInfo: Map<String, SharedDataClasses.IdAndName>? by mutableStateOf(null)
    val gradesPageModel: GradesPageModel = GradesPageModel()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun initTerms(terms: List<TermId>) {
        if (latestGradesLoadingMap.isNotEmpty()) {
            return
        }
        for (term in terms) {
            latestGradesLoadingMap[term.id] = false
        }
    }

    fun getLatestGradesFromAll(courses: Season): List<LatestGradeContainer> {
        val gradesToAdd: MutableList<LatestGradeContainer> = mutableListOf()
        for (course in courses.courseList) {
            for (unitId in course.courseGrades.course_units_grades.keys) {
                val unit = course.courseGrades.course_units_grades[unitId]
                val unitGradesToAdd: MutableList<Map<String, TermGrade?>> = mutableListOf()
                if (unit != null) {
                    for (grades in unit) {
                        val latestGrade = grades.filterValues { it?.date_modified != null }.maxByOrNull { (_, value) ->
                            LocalDateTime.parse(value!!.date_modified, formatter)
                        }
                        if (latestGrade == null) {
                            continue
                        }
                        if (LocalDateTime.parse(latestGrade.value?.date_modified, formatter).isAfter(
                                LocalDateTime.now().minusDays(7))) {
                            unitGradesToAdd.add(mapOf(latestGrade.key to latestGrade.value))
                        }
                    }
                }
                if (unitGradesToAdd.isNotEmpty()) {
                    val mapToAdd = mapOf(unitId to unitGradesToAdd)
                    val gradeToAdd: LatestGradeContainer = LatestGradeContainer(
                        course = Course(
                            courseId = course.courseId,
                            courseGrades = CourseGrades(
                                course_units_grades = mapToAdd,
                                course_grades = course.courseGrades.course_grades
                            )
                        ),
                        seasonId = courses.seasonId
                    )
                    gradesToAdd.add(gradeToAdd)
                }
            }
        }
        viewModelScope.launch {
            delay(25)
            latestGradesLoadingMap[courses.seasonId] = true
        }
        return gradesToAdd
    }

    suspend fun suspendFetchSemesterGrades(semester: String) {
        if (userSubjects.containsKey(semester) || loadingMap[semester] == true || loadedMap[semester] == true) return
        loadingMap[semester] = true
        errorMap[semester] = false
        loadedMap[semester] = false
        withContext(Dispatchers.IO) {
            try {
                val semesterCourses: Season = gradesPageModel.fetchUserGrades(semester)
                trueLatestGradesNameMap.putAll(semesterCourses.courseUnitIds!!)
                trueLatestGrades.addAll(getLatestGradesFromAll(semesterCourses))
                if (gradesPageModel.checkIfSeasonHasGrades(semesterCourses)) {
                    userSubjects[semester] = semesterCourses
                }
                errorMap[semester] = false
                loadedMap[semester] = true
            } catch (e: Exception) {
                e.printStackTrace()
                errorMap[semester] = true
                loadedMap[semester] = false
            }
            loadingMap[semester] = false
        }
    }
    fun fetchLatestGrades() {
        if (latestGrades != null) {
            return
        }
        loadingLatestGrades = true
        errorLatestGrades = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    latestGrades = gradesPageModel.fetchLatestGrades()
                    loadingLatestGrades = false
                    errorLatestGrades = false
                } catch (e: Exception) {
                    e.printStackTrace()
                    loadingLatestGrades = false
                    errorLatestGrades = true
                }
            }
        }
    }
    fun fetchSemesterGrades(semester: String) {
        if (userSubjects.containsKey(semester) || loadingMap[semester] == true || loadedMap[semester] == true) return
        loadingMap[semester] = true
        errorMap[semester] = false
        loadedMap[semester] = false
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val semesterCourses: Season = gradesPageModel.fetchUserGrades(semester)
                    trueLatestGradesNameMap.putAll(semesterCourses.courseUnitIds!!)
                    trueLatestGrades.addAll(getLatestGradesFromAll(semesterCourses))
                    if (gradesPageModel.checkIfSeasonHasGrades(semesterCourses)) {
                        userSubjects[semester] = semesterCourses
                    }
                    errorMap[semester] = false
                    loadedMap[semester] = true
                } catch (e: Exception) {
                    errorMap[semester] = true
                    loadedMap[semester] = false
                }
                loadingMap[semester] = false
            }
        }
    }

    suspend fun fetchGradesDistribution(examId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val distribution: GradesDistribution = gradesPageModel.getGivenExamGradesDistribution(examId)
                val map: Map<String, Int> = distribution.grades_distribution.associate { it.grade_symbol to it.percentage }
                gradesDistribution[examId] = map
                return@withContext true
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }
}