package com.techfortyone.datastorage.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.techfortyone.datastorage.data.model.PostsItemDto


@Composable
fun PostLists(modifier: Modifier = Modifier, postListData: State<List<PostsItemDto>?>) {
    LazyColumn(modifier = modifier) {
        postListData.value?.size?.let {
            items(it, key = { postListData.value!![it].id }) {
                PostScreen(modifier, postListData.value!![it])
            }
        }
    }
}

@Composable
fun PostScreen(modifier: Modifier = Modifier, postsItemDto: PostsItemDto) {
    Column(modifier = modifier.height(300.dp).fillMaxWidth()) {
        Row {
            Text(text = postsItemDto.title)
        }
        Row {
            Text(text = postsItemDto.body)
        }
    }
}