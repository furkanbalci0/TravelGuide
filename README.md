
# Travel Guide Mobile Application

This mobile application is a guide application. Users who want to have information about places to visit can benefit from this application.

- You can explore new places in it.
- You can read detailed articles about the places you have discovered.
- You can **like** discovered places or **save** to remember them later.
- You can make new searches and get information about the results.
- You can browse pictures of all places by clicking on them.


### 🗜️ Technologies
- Okhttp
- Retrofit
- Kotlin Coroutines
- ViewModel
- Glide
- Dagger & Hilt
- Room
- DataBinding
- Material Design


#### 💾 Clean Architecture (**MVVM**)
![image](https://user-images.githubusercontent.com/36675566/195124425-2f63ec04-92e5-4d51-bb70-ca8ccad33047.png)
![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### 🏠 Home
- Attention has been paid to the distinction between Fragment & ViewModel.
- Observe operations are done in the fragment.

```kotlin
private fun initializeDeals() {

        //Observer deals.
        homeViewModel.attractionsLiveData.observe(viewLifecycleOwner) {
            binding.dealsRecyclerview.adapter = HomeDealsAdapter(it)
        }

        //Observe loading.
        homeViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.dealsRecyclerview.visibility = View.GONE
                binding.dealsErrorText.visibility = View.GONE
                binding.dealsProgressBar.visibility = View.VISIBLE
            } else {
                binding.dealsRecyclerview.visibility = View.VISIBLE
                binding.dealsErrorText.visibility = View.GONE
                binding.dealsProgressBar.visibility = View.GONE
            }
        }

        //Observe error.
        homeViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                binding.dealsRecyclerview.visibility = View.GONE
                binding.dealsErrorText.visibility = View.VISIBLE
            } else {
                binding.dealsRecyclerview.visibility = View.VISIBLE
                binding.dealsErrorText.visibility = View.GONE
            }
        }
    }
```

| Image  | Comment |
| :--------------- | :---------------|
| <img src="https://user-images.githubusercontent.com/36675566/195149693-43f19fca-3d4c-471b-b157-54bde5bf9e9a.png" width="320"/> | • All buttons are static.<br>• The clicked button updates the tab layout.<br>• Only cars button gives "In Care" error.<br>• The incoming error message can be changed from the `string.xml` file.<br>• When the button is clicked, `SoundEffectConstants.CLICK` sound is heard.|
| <img src="https://user-images.githubusercontent.com/36675566/195153490-3df90795-9343-4de0-8068-3cfb472a32c9.gif" width="320"/> |• TabLayout is used and texts are defined as static.<br>• RecyclerView contents are updated when TabItem is clicked.<br>• Without freezing the UI, all processes are running in the background thanks to `Coroutine`.<br>• It uses custom progress bar while loading all images.<br>• All images are clickable.|
| <img src="https://user-images.githubusercontent.com/36675566/195157181-1ecbdb9f-d8fe-4308-a9c8-5bb15f20181e.gif" width="320"/> |• Clicked items move slightly upwards.<br>• The picture and the background are separate from each other.<br>• It can be scrolled up or down depending on the screen size.<br>• When the keyboard is opened, it is prevented from moving to the top. <br>• It doesn't work in `DetailFragment`.|

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### 🔍 Search
- Updates can be made by searching for `Region, Country, Venue, etc.` in this screen.
- When `Destinations` is scrolled, new items will start loading.
- `SearchView` will transfer `SearchResultFragment` when any text is written and the written text will be transferred there.

`Note:` Example function in view model.

```kotlin
fun addBookmark(
        attraction: Attraction,
        onBookmarkAddButtonClick: (Attraction) -> Unit
    ) {
        viewModelScope.launch {
            dbRepository.insert(attraction).collect() {
                when (it) {

                    is Resource.Loading -> {
                        loading.postValue(true)
                        error.postValue(false)
                    }

                    is Resource.Error -> {
                        loading.postValue(false)
                        error.postValue(true)
                    }

                    is Resource.Success -> {
                        loading.postValue(false)
                        error.postValue(false)
                        bookmarkList.add(attraction.id)
                        onBookmarkAddButtonClick(attraction)
                    }
                }
            }
        }
    }
```

| Image  | Comment |
| :---------------------------------------------------------------- | :---------------|
| <img src="https://user-images.githubusercontent.com/36675566/195167051-bd4cb040-b40c-4c10-9229-77c70f99c1ce.png" width="320"/> | • A completely custom edit text has been created.<br>• As soon as any button is clicked, `SearchResultFragment` will be transferred and the first letter in it will be transferred to the new fragment with `arguments`.<br>• When clicked, it will redirect to a detail page showing information about that region.|
| <img src="https://user-images.githubusercontent.com/36675566/195166589-a5a55c30-a299-49b6-9dba-cf15a9b938e6.gif" width="320"/> | • It lists the important places of the countries thanks to `TriposoAPI`.<br>• All items start loading when we swipe right.|
| <img src="https://user-images.githubusercontent.com/36675566/195169828-be07d240-fe1f-4f7b-b7d0-b11e645a21ce.gif" width="320"/> |• The `Attraction` list of the last selected country is sent to the Adapter.<br>• When the Bookmark button is clicked, the `Room` database is recorded and controlled.<br>• When clicked, it sends an informational message.|

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### 🧳 Trip Plan & Bookmarks
- You can add the places you want to travel and choose your round trip days.
- The `Attractions` you have registered are listed in the `Bookmark` section.

| Image  | Comment |
| :--------------- | :---------------|
| <img src="https://user-images.githubusercontent.com/36675566/195175695-be830b8c-6303-4dfe-bca7-cfcec8da3d4f.png" width="320"/> | • Static tab layout. (Trips and Bookmarks)<br>• When clicked, the recycler view adapter is updated.|
| <img src="https://user-images.githubusercontent.com/36675566/195176122-d28fa73e-a100-47cd-958b-c154ae246b7a.gif" width="320"/> | • Clicking the `FloatActionButton` opens a new `BottomSheets`. The data clicked on this pop-up screen is recorded in `SharedPreferences` and when `Add Trip` button is clicked, `Room` is recorded via **view model**.|
| <img src="https://user-images.githubusercontent.com/36675566/195181070-11516363-ec2e-4682-9d71-1db2e6ca7fe7.png" width="320"/> |• The `Attraction` classes that have been bookmarked in other fragments are listed here.<br>• When clicked, it redirects to the detail page.|

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### ⚜️ Guide
- You can get historical information about countries and read blog posts.
- You can select the country you want to get information from in the `TabLayout` section.

| Image  | Comment |
| :--------------- | :---------------|
| <img src="https://user-images.githubusercontent.com/36675566/195181780-291505e1-55fc-49af-b924-d4cc87d254ee.gif" width="320"/> | • It lists the important places of the countries thanks to `TriposoAPI`.<br>• All items start loading when we swipe right.|
| <img src="https://user-images.githubusercontent.com/36675566/195181868-2c941495-8f17-48b8-8462-0f188211c4c8.png" width="320"/> | • A completely custom edit text has been created.<br>• As soon as any button is clicked, `SearchResultFragment` will be transferred and the first letter in it will be transferred to the new fragment with `arguments`.<br>• When clicked, it will redirect to a detail page showing information about that region.|
| <img src="https://user-images.githubusercontent.com/36675566/195182299-f6c12a92-c7d1-4132-b750-371a1b4343e3.gif" width="320"/> |• A list of countries is drawn by `TriposoApi`.<br> • `Article` is listed related to the clicked country.<br> • When the heart button is clicked, it saves it into `SharedPreferences`.<br>• UI(main) thread is not affected by the processes. |

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### 🔥 Search Results
- You can get historical information about countries and read blog posts.
- You can select the country you want to get information from in the `TabLayout` section.

| Image  | Comment |
| :--------------- | :---------------|
| <img src="https://user-images.githubusercontent.com/36675566/195183430-97fc5458-7c66-42b6-a0dd-7075ab3bfea3.gif" width="320"/> | • When you want to search for a country or region, you need to type in the `SearchView` section.<br>• The transition to this screen will be very 'smooth'.<br>• Results related to the searched word will be listed.<br>• It does not send continuous requests for the searched word and sends a request `1.5 seconds` after the word is typed.<br>• Click the 'Bookmark Button' for the 'Attraction' you want to save.|

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### 📇 Detail
- Shows the classes `Article`, `Attraction`, `Country`, `Destination`, `MockObject` and `Trip` in it. For this, I created an **interface** named `DetailObject` as `Dependecy Revision` and implemented it to all classes.
- We send the implemented class with `DataBinding`.

```xml
<data>
    <variable
        name="detailObject"
        type="com.furkanbalci.travelguide.di.DetailObject" />
</data>
```

| Image  | Comment |
| :--------------- | :---------------|
| <img src="https://user-images.githubusercontent.com/36675566/195188815-45b251e5-df59-4371-be8c-8cfb3fd104f0.gif" width="320"/> | • It places the `DetailObject` class that is sent into it with `DataBinding` where necessary. `Room Database` can be saved.<br>• Selected picture can be enlarged by clicking the enlarge button.|

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

## 💉 Dependency Injection
I created an `annotation class` to return 2 `Retrofit` objects.

![image](https://user-images.githubusercontent.com/36675566/195191977-4d5ebe18-473f-44de-bbb4-2a73d379833e.png)

```kotlin
...
    @MockApi
    @Provides
    @Singleton
    fun provideMockService(@MockApi retrofit: Retrofit): MockApiService {
        return retrofit.create(MockApiService::class.java)
    }

    @TriposoApi
    @Provides
    @Singleton
    fun provideTriposoService(@TriposoApi retrofit: Retrofit): TriposoApiService {
        return retrofit.create(TriposoApiService::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MockApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TriposoApi
```

## 🖼️ Outputs
![image](https://user-images.githubusercontent.com/36675566/195134833-aef08f3d-4f3a-4c5f-bc8b-2f74e7a83652.png) ![image](https://user-images.githubusercontent.com/36675566/195134910-729b67be-05d2-4d8d-84da-5cbca7326d43.png) ![image](https://user-images.githubusercontent.com/36675566/195134952-77db5ee1-b274-4e83-930a-b825f4440065.png) ![image](https://user-images.githubusercontent.com/36675566/195135012-c832bd58-4801-4670-b9b9-5d2fab921b1d.png) ![image](https://user-images.githubusercontent.com/36675566/195135099-1650f918-983e-4dc3-b5ae-8301dd8f2d9f.png) ![studio64_TBDOTAuBsf](https://user-images.githubusercontent.com/36675566/195136119-1a205e9b-f3e3-43e1-9dd7-ba9794d5164d.png)
