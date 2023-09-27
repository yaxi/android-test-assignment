package com.example.shacklehotelbuddy.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.shacklehotelbuddy.R
import com.example.shacklehotelbuddy.search.state.SearchViewState
import com.example.shacklehotelbuddy.ui.theme.ShackleHotelBuddyTheme
import java.util.Calendar

@Composable
fun MainScreen(
    viewState: SearchViewState,
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillWidth
            ),
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier
                    .padding(top = 142.dp),
                text = stringResource(id = R.string.title_main_page),
                style = ShackleHotelBuddyTheme.typography.h3,
                color = ShackleHotelBuddyTheme.colors.white
            )

            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .background(
                        color = ShackleHotelBuddyTheme.colors.white,
                        shape = RoundedCornerShape(16.dp)
                    )

            ) {
                Column {
                    SearchDataRow(
                        iconRes = R.drawable.event_upcoming,
                        titleRes = R.string.title_check_in_date,
                        data = viewState.checkInDate.toString(),
                        onClick = { showDatePicker = true }
                    )
                    Divider()
                    SearchDataRow(
                        iconRes = R.drawable.event_available,
                        titleRes = R.string.title_check_out_date,
                        data = viewState.checkOutDate.toString(),
                        onClick = {}

                    )
                    Divider()
                    SearchDataRow(
                        iconRes = R.drawable.person,
                        titleRes = R.string.title_adults,
                        data = viewState.adults.toString(),
                        onClick = {}
                    )
                    Divider()
                    SearchDataRow(
                        iconRes = R.drawable.supervisor_account,
                        titleRes = R.string.title_children,
                        data = viewState.children.toString(),
                        onClick = {}
                    )
                }

            }

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(id = R.string.title_recent_searches),
                style = ShackleHotelBuddyTheme.typography.h6,
                color = ShackleHotelBuddyTheme.colors.white
            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        color = ShackleHotelBuddyTheme.colors.white,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                ProvideTextStyle(
                    value = TextStyle(
                        fontSize = 12.sp,
                        color = ShackleHotelBuddyTheme.colors.grayText,
                    ),
                ) {

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.manage_history),
                            contentDescription = "recent search icon"
                        )
                        DividerVert(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(48.dp)
                        )
                        Text(modifier = Modifier, text = "03 07 / 2024 - 08 / 07 / 2024")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "1 adult, 0 children")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.teal),
                        shape = RoundedCornerShape(20.dp)
                    )

                    .height(60.dp),
                colors = ButtonDefaults.textButtonColors(),
                onClick = { /*TODO*/ }) {
                Text(
                    fontSize = 18.sp,
                    color = ShackleHotelBuddyTheme.colors.white,
                    fontWeight = FontWeight(450),
                    text = stringResource(id = R.string.action_search)
                )
            }
        }
    }

    val state = rememberDatePickerState(
        initialSelectedDateMillis = null,
        initialDisplayedMonthMillis = null,
        initialDisplayMode = DisplayMode.Picker
    )
    if (showDatePicker) {
        DatePicker(
            state = state
        )
    }
}

@Composable
fun SearchDataRow(
    modifier: Modifier = Modifier,
    iconRes: Int,
    iconDescription: String? = null,
    titleRes: Int,
    data: String,
    onClick: () -> Unit,
) {
    ProvideTextStyle(
        value = TextStyle(
            color = ShackleHotelBuddyTheme.colors.grayText,
            fontSize = 14.sp
        )
    ) {
        ConstraintLayout(
            modifier = modifier
                .height(52.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (imageRef, textRef, dividerRef, textFieldRef) = createRefs()
            val midGuide = createGuidelineFromStart(0.5f)
            Image(
                modifier = Modifier
                    .constrainAs(imageRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }
                    .padding(start = 16.dp),
                painter = painterResource(id = iconRes),
                contentDescription = iconDescription
            )
            Text(
                modifier = Modifier
                    .constrainAs(textRef) {
                        start.linkTo(imageRef.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(start = 8.dp),
                text = stringResource(id = titleRes)
            )
            DividerVert(
                modifier = Modifier.constrainAs(dividerRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.value(52.dp)
                })
            Text(
                modifier = Modifier
                    .constrainAs(textFieldRef) {
                        start.linkTo(midGuide)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable(enabled = true, onClick = onClick),
                text = data
            )
        }
    }
}

@Composable
fun DividerVert(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    Box(
        modifier
            .width(width = thickness)
            .background(color = color)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShackleHotelBuddyTheme {
        MainScreen()
    }
}