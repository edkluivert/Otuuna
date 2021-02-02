package com.kluivert.otuuna.data

import androidx.paging.PagingSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FirestorePagingSource(
        private val db: FirebaseFirestore
) : PagingSource<QuerySnapshot, OtuunaEvents>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, OtuunaEvents> {
        return try {
            // Step 1
            val currentPage = params.key ?: db.collection("Events")
                    .limit(10)
                    .get()
                    .await()

            // Step 2
            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            // Step 3
            val nextPage = db.collection("Events").limit(10).startAfter(lastDocumentSnapshot)
                    .get()
                    .await()

            // Step 4
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