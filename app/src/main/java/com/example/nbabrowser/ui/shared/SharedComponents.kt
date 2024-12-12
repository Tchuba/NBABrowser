package com.example.nbabrowser.ui.shared

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nbabrowser.R
import com.example.nbabrowser.ui.theme.NBABrowserTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ValueWithLabel(@StringRes title: Int, text: String, modifier: Modifier = Modifier) {
    Column (modifier = modifier.fillMaxWidth().padding(top = 8.dp)){
        SimpleLabel(title)
        Text(
            text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun SimpleLabel(@StringRes title: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(title),
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    NBABrowserTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    NBABrowserTheme {
        ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ValueWithLabelPreview() {
    NBABrowserTheme {
        ValueWithLabel(
            R.string.position_label,
            "G"
        )
    }
}