package com.bangkit.vegalicious.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.jetpackcomposesubmission.R
import com.bangkit.jetpackcomposesubmission.ui.theme.JetpackComposeTheme


@Composable
fun ProfileScreen(
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier.fillMaxSize()
	) {
		Spacer(modifier = Modifier.height(64.dp))
		Image(
			painter = painterResource(id = R.drawable.profile),
			contentDescription = "Muhammad Daffa Aldriantama",
			Modifier
				.height(160.dp)
				.clip(CircleShape)
		)
		Text(
			"Muhammad Daffa Aldriantama",
			style = MaterialTheme.typography.titleLarge.copy(
				fontWeight = FontWeight.Bold
			)
		)
		Text(
			"mdaffaaldriantama@gmail.com"
		)
		Text(
			"Github: DuffAldri"
		)
	}
}

@Preview
@Composable
fun ProfilePreview() {
	JetpackComposeTheme() {
		// A surface container using the 'background' color from the theme
		Surface(
			modifier = Modifier.fillMaxSize(),
			color = MaterialTheme.colorScheme.background
		) {
			ProfileScreen()
		}
	}
}