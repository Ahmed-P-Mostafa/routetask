package com.polotika.routetask.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.polotika.routetask.R
import com.polotika.routetask.core.ext.toPriceFormat
import com.polotika.routetask.domain.entities.ProductEntity
import com.polotika.routetask.presentation.home.HomeViewState
import com.polotika.routetask.ui.theme.blue1
import com.polotika.routetask.ui.theme.blue2
import com.polotika.routetask.ui.theme.blue3
import com.polotika.routetask.ui.theme.textColor
import com.polotika.routetask.ui.theme.yellow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val viewState = viewModel.viewState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.getProducts()
    }
    Scaffold(
        topBar = {
            MainTopBar()
        },
        content = { paddingValues ->
            HomeContent(
                paddingValues= paddingValues,
                query = viewModel.query,
                onQueryChanged = { viewModel.onQueryChanged(it) },
                onSearch = { viewModel.onSearch(it) },
                viewState = viewState.value
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar() {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.route_banner),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(40.dp)
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    query: MutableState<String>,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    viewState: HomeViewState
) {
    val isLoading = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        ) {
        when (viewState) {
            is HomeViewState.Error -> {
                isLoading.value = false
                showToast(viewState.message, context)
            }

            HomeViewState.Loading -> {
                isLoading.value = true
            }

            is HomeViewState.Success -> {
                isLoading.value = false
                Column( modifier = Modifier.padding(horizontal = 12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        CustomSearchBar(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp),
                            query = query.value,
                            onQueryChange = onQueryChanged,
                            onSearch = onSearch,
                            active = false,
                            onActiveChange = {},
                            placeHolder = {
                                Text(
                                    text = "What do you search for?",
                                    style = TextStyle(color = Color.Gray)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = blue3, modifier = Modifier.size(38.dp)
                                )
                            },
                            content = {},

                            )
                        Icon(
                            painter = painterResource(id = R.drawable.shopping_cart),
                            contentDescription = null,
                            tint = blue3, modifier = Modifier
                                .weight(0.1f)
                                .size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 12.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        items(viewState.products) { productEntity ->
                            ProductItem(productModel = productEntity)
                        }
                    }
                }

            }
        }

    }
    LoadingIndicator(isVisible = isLoading.value)

}

@Composable
fun ProductItem(productModel: ProductEntity) {
    Column(
        Modifier.border(
            border = BorderStroke(1.dp, color = blue2),
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            AsyncImage(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .height(110.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center),
                model = productModel.image,
                placeholder = painterResource(id = R.drawable.placeholder_image_large),
                error = painterResource(id = R.drawable.placeholder_image_large),
                contentDescription = "The delasign logo",
                contentScale = ContentScale.Fit,

                )
            FloatingActionButton(
                modifier = Modifier
                    .padding(top = 8.dp, end = 8.dp)
                    .size(30.dp),
                onClick = { },
                containerColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = blue3
                )

            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = productModel.title,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = productModel.description,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
        Row(horizontalArrangement = Arrangement.Start) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "EGP ${productModel.price.toInt().toPriceFormat()}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500

            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "${productModel.price.toInt().toPriceFormat()} EGP",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = blue1,
                fontSize = 13.sp,
                textDecoration = TextDecoration.LineThrough,
                fontWeight = FontWeight.W500
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Review (${productModel.rating})",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400
                )
                Icon(imageVector = Icons.TwoTone.Star, contentDescription = "", tint = yellow)
            }
            FloatingActionButton(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(35.dp),
                onClick = { },
                containerColor = blue3,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainTopBarPreview() {
    MainTopBar()
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    HomeContent(
        paddingValues = PaddingValues(12.dp),
        query = mutableStateOf(""),
        onQueryChanged = {},
        onSearch = {},
        viewState = HomeViewState.Success(
            listOf(
                ProductEntity(id = 0, price = 1900.0, rating = 4.2, image = ""),
                ProductEntity(id = 0, price = 19.5, rating = 4.2, image = ""),
                ProductEntity(id = 0, price = 19.5, rating = 4.2, image = ""),
                ProductEntity(id = 0, price = 19.5, rating = 4.2, image = ""),
                ProductEntity(id = 0, price = 19.5, rating = 4.2, image = ""),
                ProductEntity(id = 0, price = 19.5, rating = 4.2, image = ""),
                ProductEntity(id = 0, price = 19.5, rating = 4.2, image = ""),
            )
        )
    )
}