package com.dailyquote.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dailyquote.app.platform.HapticFeedback
import kotlin.random.Random

val quotes = listOf(
    "The only way to do great work is to love what you do." to "Steve Jobs",
    "Believe you can and you're halfway there." to "Theodore Roosevelt",
    "The future belongs to those who believe in the beauty of their dreams." to "Eleanor Roosevelt",
    "It does not matter how slowly you go as long as you do not stop." to "Confucius",
    "Act as if what you do makes a difference. It does." to "William James",
    "Success is not final, failure is not fatal: it is the courage to continue that counts." to "Winston Churchill",
    "The best time to plant a tree was 20 years ago. The second best time is now." to "Chinese Proverb",
    "Your time is limited, don't waste it living someone else's life." to "Steve Jobs",
    "The only impossible journey is the one you never begin." to "Tony Robbins",
    "Everything you've ever wanted is on the other side of fear." to "George Addair",
    "Happiness is not something ready made. It comes from your own actions." to "Dalai Lama",
    "In the middle of every difficulty lies opportunity." to "Albert Einstein",
    "Do what you can, with what you have, where you are." to "Theodore Roosevelt",
    "The mind is everything. What you think you become." to "Buddha",
    "An investment in knowledge pays the best interest." to "Benjamin Franklin",
)

@Composable
fun MainScreen() {
    var index by remember { mutableIntStateOf(Random.nextInt(quotes.size)) }
    var isFavorited by remember { mutableStateOf(false) }
    var favorites by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    val haptic = remember { HapticFeedback() }

    Column(Modifier.fillMaxSize().background(Color(0xFFF8F6F2)).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(40.dp))
        Text("Daily Inspiration", fontSize = 14.sp, fontWeight = FontWeight.Medium,
             color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 2.sp)
        Spacer(Modifier.height(40.dp))
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
            Column(Modifier.padding(32.dp)) {
                Text("\"", fontSize = 48.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text(quotes[index].first, fontSize = 18.sp, lineHeight = 30.sp, textAlign = TextAlign.Start)
                Spacer(Modifier.height(16.dp))
                Text("— {quotes[index].second}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
            }
        }
        Spacer(Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FilledTonalIconButton(onClick = { isFavorited = !isFavorited; haptic.light()
                if (isFavorited) favorites = favorites + quotes[index] else favorites = favorites.filter { it != quotes[index] } }) {
                Icon(if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder, "Favorite")
            }
            FilledTonalIconButton(onClick = { index = (index + 1) % quotes.size; isFavorited = favorites.contains(quotes[index]); haptic.light() }) {
                Icon(Icons.Default.Shuffle, "Next quote")
            }
            FilledTonalIconButton(onClick = { /* share */ haptic.light() }) {
                Icon(Icons.Default.Share, "Share")
            }
        }
        Spacer(Modifier.weight(1f))
        if (favorites.isNotEmpty()) {
            Text("Favorites ({favorites.size})", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            favorites.takeLast(3).forEach { q ->
                Text(q.first.take(50) + "...", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
