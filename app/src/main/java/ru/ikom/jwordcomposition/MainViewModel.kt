package ru.ikom.jwordcomposition

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    val uiState = MutableStateFlow(
        listOf(
            WordUi(
                id = 0,
                letters = LettersMode(root = "справ", suffix = "к", end = "a"),
            ),
            WordUi(
                id = 1,
                letters = LettersMode(root = "розетк", end = "a"),
            ),
            WordUi(
                id = 2,
                letters = LettersMode(root = "клави", suffix = "ат ур", end = "a"),
            ),
            WordUi(
                id = 3,
                letters = LettersMode(prefix = "c", root = "цепл", suffix = "ени", end = "e"),
            ),
        )
    )

}

data class LettersMode(
    val prefix: String? = null,
    val root: String? = null,
    val suffix: String? = null,
    val end: String? = null,
)

data class WordUi(val id: Int, val letters: LettersMode)