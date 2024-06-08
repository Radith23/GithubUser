# Github User App
GitHub User is an Android app that lets you search for GitHub profiles using the GitHub API. You can easily find profiles by entering a username and save your favorite ones for quick access later.
## Features
- **Search Profiles** : Allows you to find Github profiles by entering the username.
- **Favorite Features** : Provides save button to help manage your favorite profiles for quick access later on.
- **Theme Management** : Customize the app's appearance by switching between themes.
## Technical Details
1. **Github API Integration** : GitHub User uses Retrofit to retrieve data from the GitHub API, ensuring efficient and reliable communication with GitHub's servers. <br><br>
2. **Favorite Functionality** : For the favorite feature, the app utilizes Room Database, enabling users to save and manage their favorite profiles locally on their phones. This ensures that bookmarked data is quickly accessible and remains available even without an internet connection. <br><br>
3. **Android Architecture Component** : GitHub User integrates Android Architecture Components like ViewModel and LiveData to enhance its performance and responsiveness.
   - **ViewModel** : Handles UI-related data in a lifecycle-aware manner, ensuring that data persists through configuration alterations like screen rotations.
   - **LiveData** : Offers a reactive method to manage data, ensuring automatic UI updates when data undergoes changes. <br><br>
4. **Theme Management** : GitHub User integrates DataStore to handle theme management and switching. DataStore offers a reliable solution for storing key-value pairs or typed objects, ensuring that theme preferences are stored securely and applied consistently.
## Screenshots
