![GitHub Cards Preview](https://github.com/Biswajeet-23/News_Planet/blob/master/Designs/PLANET.png)

# News Planet

News Planet connects to the [News API](https://newsapi.org/) to retrieve Breaking News Feed based on the particular endpoint used and then displays them as a list.

## App Compatibility

Android device running with Android OS 6.0 ([API Level 23](https://developer.android.com/about/versions/marshmallow/android-6.0)) or above. Designed for Phones and NOT for Tablets.

## Getting Started

* Register for the Developer API Key from the [News API Platform](https://newsapi.org/). Registration is free of cost.
* Create a new package named **myApplication.utils** in the Project's root folder.
* Define a property named **API_KEY** and assign it the value of the API Key obtained from the Registration process.
* If the above API Key is not defined, then the App will use the default **"test"** API Key which is heavily rate-limited. Whenever the rate-limit is hit, the app may crash or not display proper results.
* The Developer API Key is also rate-limited, but not as limited as the default **"test"** API Key.

## Rubric followed for the Project

* App queries the `newsapi.org` API to fetch news stories and properly parses the JSON Response.
* Each List item displayed by the App for the News stories should contain relevant text and information about the story. This includes -
	* Title of the article.
	* Source name of the article if present.
	* Published date and time of the article if present.
* Default text field is remains empty when any of the above parsed information is not available.
* Networking operations are handled by Retrofit library.
* Clicking on a story, redirects and opens the story in user's browser.
* On device rotation
	* The layout remains scrollable.
	* The app saves state and restores the list back to the previously scrolled position.
	* The UI properly adjusts so that all contents of each list item is still visible and not truncated/overlapped.
* When new News data is fetched, the main screen properly updates the new data.
* Check whether connected to internet or not. Also, validate for any occurrence of bad server response or lack of response.

### Things explored/developed in addition to the above defined Rubric

* Used `RecyclerView` in place of `ListView` (to display the News stories) for its advantages in performance and easy placeholders for custom item decoration.
* `CardView` for displaying the News stories content for each News List items.
* List of libraries used:
	- [Retrofit](https://square.github.io/retrofit/) : For handling HTTP request and responses.
	- [Glide](https://github.com/bumptech/glide) : Image Loader library.
	- [Room](https://developer.android.com/training/data-storage/room) :  For more robust database access.
	- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) : Navigating between destinations. 
* Custom Fonts for `TextViews` using `Google Fonts`.
* Explored `CoordinatorLayout`.
* Used `RecyclerView` in a `SwipeRefreshLayout` to use the integrated Progress/Refresh indicator.

## Design

Breaking News Page | Search News Page | Bookmarks Page
--- | --- | ---
![](https://github.com/Biswajeet-23/News_Planet/blob/master/Designs/Breaking%20News.png) | ![](https://github.com/Biswajeet-23/News_Planet/blob/master/Designs/Search.png) | ![](https://github.com/Biswajeet-23/News_Planet/blob/master/Designs/Bookmarks.png)
