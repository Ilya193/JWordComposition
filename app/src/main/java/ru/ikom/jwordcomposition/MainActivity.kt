package ru.ikom.jwordcomposition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ru.ikom.jwordcomposition.ui.theme.JWordCompositionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            JWordCompositionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val words by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(words, key = { word -> word.id }) {
                WordItem(it)
                Divider()
            }
        }
    }
}

@Composable
private fun WordItem(word: WordUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
    ) {
        WordByComposition(
            prefix = word.letters.prefix,
            root = word.letters.root,
            suffix = word.letters.suffix,
            ending = word.letters.end
        )
    }
}

@Composable
fun WordByComposition(
    prefix: String? = null,
    root: String? = null,
    suffix: String? = null,
    ending: String? = null,
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            },
    ) {
        prefix?.let { PrefixMode(it) }

        root?.let { RootMode(root) }

        suffix?.let {
            it.split(" ").forEach {
                SuffixMode(it)
            }
        }

        ending?.let { EndingMode(it) }
    }
}

@Composable
private fun PrefixMode(prefix: String) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, -5.dp.toPx()),
                    end = Offset(size.width, -5.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )

                drawLine(
                    color = Color.Black,
                    start = Offset(size.width, -5.dp.toPx()),
                    end = Offset(size.width, 5.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )
            },
        text = prefix,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun RootMode(letters: String) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                val rect = Rect(
                    left = 0f,
                    top = size.height - size.width / 2,
                    right = size.width,
                    bottom = size.height
                )

                drawArc(
                    color = Color.Black,
                    startAngle = 200f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = rect.topLeft,
                    size = rect.size,
                    style = Stroke(1.dp.toPx())
                )
            },
        text = letters,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun SuffixMode(letters: String) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 5.dp.toPx()),
                    end = Offset(size.width / 2, -size.height / 2),
                    strokeWidth = 1.dp.toPx()
                )

                drawLine(
                    color = Color.Black,
                    start = Offset(size.width / 2, -size.height / 2),
                    end = Offset(size.width, 5.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )
            },
        text = letters,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun EndingMode(letters: String) {
    Text(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(0f, 0f),
                    style = Stroke(1.dp.toPx())
                )
            },
        text = letters,
        style = MaterialTheme.typography.bodyLarge
    )
}