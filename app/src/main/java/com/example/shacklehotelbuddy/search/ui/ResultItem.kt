package com.example.shacklehotelbuddy.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shacklehotelbuddy.R
import com.example.shacklehotelbuddy.ui.theme.ShackleHotelBuddyTheme

@Composable
fun ResultItem(
    imageUrl: String,
    hotelName: String,
    hotelRating: String,
    location: String,
    pricePerNight: String,
) {
    ProvideTextStyle(
        value = TextStyle(
            color = ShackleHotelBuddyTheme.colors.black,
            fontSize = 14.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 20.sp
        )
    ) {

        Card(
            colors = CardColors(
                containerColor = ShackleHotelBuddyTheme.colors.white,
                contentColor = CardDefaults.cardColors().contentColor,
                disabledContainerColor = CardDefaults.cardColors().disabledContainerColor,
                disabledContentColor = CardDefaults.cardColors().disabledContentColor,
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    model = imageUrl,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = "hotel image"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = hotelName)
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "hotel rating"
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = hotelRating,
                    fontWeight = FontWeight(450)
                )
            }
            Text(
                text = location,
                color = ShackleHotelBuddyTheme.colors.grayText,
                fontWeight = FontWeight(450)
            )
            Text(
                text = buildAnnotatedString {
                    append("$pricePerNight ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight(450))) {
                        append(stringResource(id = R.string.price_per_night))
                    }
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewResultItem() {
    ShackleHotelBuddyTheme {
        ResultItem(
            imageUrl = "https://images.trvl-media.com/lodging/19000000/18910000/18907300/18907213/5fb381c4.jpg",
            hotelName = "Roberto Weeks",
            hotelRating = "4.5",
            location = "Amsterdam, Netherlands",
            pricePerNight = "£100"
        )
    }
}