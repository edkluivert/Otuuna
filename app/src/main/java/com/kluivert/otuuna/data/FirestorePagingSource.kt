package com.kluivert.otuuna.data


import androidx.paging.PagingSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FirestorePagingSource(private val db: FirebaseFirestore) : PagingSource<QuerySnapshot, OtuunaEvents>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, OtuunaEvents> {
        return try {
            val currentPage = params.key ?: db.collection("countries").limit(10).get().await()

            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]
            val nextPage = db.collection("countries").limit(10).startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(
                data = currentPage.toObjects(OtuunaEvents::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}