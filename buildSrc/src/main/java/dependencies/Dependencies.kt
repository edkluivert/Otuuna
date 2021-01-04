package dependencies

object Dependencies {
    val roomPersistenceLibraryRuntime = "androidx.room:room-runtime:${Versions.room_version}"
    val lifecyleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_version}"
    val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}"
    val cardView = "androidx.cardview:cardview:${Versions.cardview_version}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView_version}"
    val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.fragment_ktx_versions}"
    val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation_ui_ktx_versions}"
    val intuitsdp = "com.intuit.sdp:sdp-android:${Versions.intuit_sdp_version}"
    val intuitssp = "com.intuit.ssp:ssp-android:${Versions.intuit_ssp_version}"
    val googleMaterial = "com.google.android.material:material:${Versions.google_material}"
    val daggerHilt = "com.google.dagger:hilt-android:${Versions.hilt_android_version}"
    val hiltViewModels = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModels}"
    val room = "androidx.room:room-ktx:${Versions.room_version}"
    val kotlinJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_jdk}"
    val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines_version}"
    val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines_version}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val markdown_processor = "com.yydcdut:markdown-processor:${Versions.markdown_processor}"
    val navigation_dynamic = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation_ui_ktx_versions}"
    val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_ui_ktx_versions}"
    val navigation_runtime = "androidx.navigation:navigation-runtime:${Versions.navigation_ui_ktx_versions}"
    val room_runtime = "androidx.room:room-runtime:${Versions.room_version}"
    val coil = "io.coil-kt:coil:${Versions.coil}"

    //Firebase
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx${Versions.firebaseAuth}"
    const val fireStorage = "com.google.firebase:firebase-storage-ktx${Versions.fireStorage}"
    const val fireStore = "com.google.firebase:firebase-firestore-ktx${Versions.firebaseFirestore}"

    //Datastore

    const val datastorePrefs = "androidx.datastore:datastore-preferences:${Versions.datastore}"


}