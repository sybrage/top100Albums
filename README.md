# top100Albums
Android Kotlin mobile app that reads the Apple iTunes RSS top albums feed and displays the information. Implements COIL and Realm.

UI & Navigation Design:
1. Implements a 3-screen activiy with JetPack Compose - MainActivity, AlbumDetailView, AlbumPageView.
2. Implements a 2-page navigation controller in MainActivity to display AlbumGridView and ConnectionView.
3. Implements a web view to display AlbumPageView, which is the iTunes album page - launched from AlbumDetailView.
4. The AlbumGridView utilises a lazy grid with COIL to asynchronously loads and caches the album images.
5. The status bar colours have also been customised to match the Zeplin/Figma design prototypes.

Connectivity:
1. Utilises the text stream reader to consume the iTunes RSS feed.
2. Utilises the GSON library to convert the RSS feed stream into a JSON object.
3. Utilises the connection manager to determine the internet connectivity status.

Database & App Storage:
1. Implements a Realm database utilising the Realm Kotlin SDK.
2. Only the album information is saved in the database and is overwritten upon every successful RSS read.
3. When offline, the album information is read from the database after the first successfull RSS read.
4. The Apple copyright notice is stored in the shared preferences of the app.

Functionality:
1. The app attempts to read the iTunes RSS feed.
2. Upon a successful read, the album information is displayed.
3. Upon selection of an album image, the album details are displayed.
4. The album's iTunes page can be launched from this details page.
5. In the event of non-connectivity, an attempt is made to read from the Realm database, if previously saved.
6. If no data is found in the Realm database, a reconnection page is displayed to facilitate retries.
7. A refresh button is also included in the top bar of the grid page to facilitate an RSS reload.
