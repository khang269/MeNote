package com.critisys.menote.presentation.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.critisys.menote.presentation.model.HomeViewModel
import com.critisys.menote.presentation.ui.note.DetailNoteScreen
import com.critisys.menote.presentation.ui.note.HomeNotesCreen

sealed class MeNoteScreen(val route: String){
    object HomeNoteScreen: MeNoteScreen("home")
    object DetailNoteScreen: MeNoteScreen("detail/{id}"){
        fun createRoute(id: Int) = "detail/$id"
    }
}

@ExperimentalFoundationApi
@Composable
fun NoteNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MeNoteScreen.HomeNoteScreen.route){
        addHomeGraph(navController = navController)
        addDetailNoteGraph(navController = navController)
    }
}


@ExperimentalFoundationApi
fun NavGraphBuilder.addHomeGraph(navController: NavController){
    composable(route = MeNoteScreen.HomeNoteScreen.route){
        HomeNotesCreen(
            onAddNoteClick = {
                navController.navigate(MeNoteScreen.DetailNoteScreen.createRoute(-1))
            },
            onNoteClick = {
                navController.navigate(MeNoteScreen.DetailNoteScreen.createRoute(it.id))
            })
    }
}

fun NavGraphBuilder.addDetailNoteGraph(navController: NavController){
    composable(route = MeNoteScreen.DetailNoteScreen.route,
        arguments = listOf(
            navArgument("id"){type = NavType.IntType; defaultValue = -1})
    ){
        DetailNoteScreen(navController = navController)
    }
}