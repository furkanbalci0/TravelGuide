
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
Attention has been paid to the distinction between Fragment & ViewModel.
Observe operations are done in the fragment.

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
| ![cH7koaMl19](https://user-images.githubusercontent.com/36675566/195149693-43f19fca-3d4c-471b-b157-54bde5bf9e9a.png) | • All buttons are static.<br>• The clicked button updates the tab layout.<br>• Only cars button gives "In Care" error.<br>• The incoming error message can be changed from the `string.xml` file.<br>• When the button is clicked, `SoundEffectConstants.CLICK` sound is heard.|
| ![studio64_2vazbEGjcU](https://user-images.githubusercontent.com/36675566/195153490-3df90795-9343-4de0-8068-3cfb472a32c9.gif) |• TabLayout is used and texts are defined as static.<br>• RecyclerView contents are updated when TabItem is clicked.<br>• Without freezing the UI, all processes are running in the background thanks to `Coroutine`.<br>• It uses custom progress bar while loading all images.<br>• All images are clickable.|
| ![studio64_nNnPW6melC](https://user-images.githubusercontent.com/36675566/195157181-1ecbdb9f-d8fe-4308-a9c8-5bb15f20181e.gif) |• Clicked items move slightly upwards.<br>• The picture and the background are separate from each other.<br>• It can be scrolled up or down depending on the screen size.<br>• When the keyboard is opened, it is prevented from moving to the top. <br>• It doesn't work in `DetailFragment`.|

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### 🔍 Search
- Updates can be made by searching for `Region, Country, Venue, etc.` in this screen.
- When `Destinations` is scrolled, new items will start loading.
- `SearchView` will transfer `SearchResultFragment` when any text is written and the written text will be transferred there.

`Note:`Example function in view model.
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
| ![studio64_F8a1OKHp6x](https://user-images.githubusercontent.com/36675566/195167051-bd4cb040-b40c-4c10-9229-77c70f99c1ce.png) | • A completely custom edit text has been created.|
| ![studio64_uAKl7VS3ui](https://user-images.githubusercontent.com/36675566/195166589-a5a55c30-a299-49b6-9dba-cf15a9b938e6.gif) | • As soon as any button is clicked, `SearchResultFragment` will be transferred and the first letter in it will be transferred to the new fragment with `arguments`.<br>• When clicked, it will redirect to a detail page showing information about that region.|
| ![studio64_odmRvrY7kB](https://user-images.githubusercontent.com/36675566/195169828-be07d240-fe1f-4f7b-b7d0-b11e645a21ce.gif) |• The `Attraction` list of the last selected country is sent to the Adapter.<br>• When the Bookmark button is clicked, the `Room` database is recorded and controlled.<br>• When clicked, it sends an informational message.|

![image](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

### 🧳 Trip Plan
- You can add the places you want to travel and choose your round trip days.
- The `Attractions` you have registered are listed in the `Bookmark` section.

| Image  | Comment |
| :--------------- | :---------------|
| ![studio64_RwIXrgPRGW](https://user-images.githubusercontent.com/36675566/195175695-be830b8c-6303-4dfe-bca7-cfcec8da3d4f.png) | • Static tab layout. (Trips and Bookmarks)<br>• When clicked, the recycler view adapter is updated.|
| ![studio64_BGo9xn2pf9](https://user-images.githubusercontent.com/36675566/195176122-d28fa73e-a100-47cd-958b-c154ae246b7a.gif) | • Clicking the `FloatActionButton` opens a new `BottomSheets`. The data clicked on this pop-up screen is recorded in `SharedPreferences` and when `Add Trip` button is clicked, `Room` is recorded via **view model**.<br>• |
| ![studio64_odmRvrY7kB](https://user-images.githubusercontent.com/36675566/195169828-be07d240-fe1f-4f7b-b7d0-b11e645a21ce.gif) |• The `Attraction` list of the last selected country is sent to the Adapter.<br>• When the Bookmark button is clicked, the `Room` database is recorded and controlled.<br>• When clicked, it sends an informational message.|
