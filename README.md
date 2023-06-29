# Catawiki Android Assignment

### Screenshots
<table>
  <tr>
    <th>Feature</th>
    <th>Light Theme</th>
    <th>Dark Theme</th>
  </tr>
  <tr>
    <td>Species List</td>
    <td><img src=""   height="550"  width="250"/></td>
    <td><img src=""   height="550"  width="250"/></td>
  </tr>
    <tr>
    <td>Species Detail</td>
    <td><img src=""   height="550"  width="250"/></td>
    <td><img src=""   height="550"  width="250"/></td>
  </tr>
</table>

### Description:
- Name: **Pokemon Species**
- Functional features:
    - View a list of all pokemon species (includes name and image)
    - View specific details of a species by selecting from the list
    - Specific details include:
        - **Name**
        - **Image**
        - **Description**
        - **Evolves to**: Includes the name and image of the first species it evolves to as indicated from the evolution chain. In case a species has reached its final form in the evolution chain, it will be mentioned so.
        - **Capture rate difference**: Is difference between the capture rate of the current species and the species it evolves to. A positive difference is highlighted in red while a negative difference is highlighted in green. In case a species has reached its final form, the difference will just be stated as 0.
- Non functional features:
    - The species list supports offline viewing till the last loaded page
    - Portrait and landscape support
    - Dark theme support

### Technical specifications:
- Programming language: Kotlin
- Architecture: MVVM + RxJava
- DI implemented using Hilt
- Jetpack components:
    - Paging3 using RemoteMediator (To load data from network and cache in local db)
    - Room for local persistence
    - Navigation component for navigating between fragments
- Third party SDKs:
    - Coil for image loading
    - Retrofit for networking
    - Lottie for animations
    - Junit4 + Mockk for testing

### Design notes:
- RecyclerView with GridlayoutManager to display the list of species
- I used a DialogFragment to display the details of the species. The intention behind that is to emulate a physical pokemon card. (The UI of this layout can be further improved to achieve the goal)
- I used a pokemon related [lottie animation](https://lottiefiles.com/96855-pokeball-loading-animation) as a loader when the details of the species are loading to add an element of fun.
- I generated a material theme file using https://m3.material.io/theme-builder#/custom to support both Light and Dark modes.

### Test coverage:
- Instrumented test for RemoteMediator implementation
- Unit tests for SpeciesDetailViewModel class and Mapper classes

### TODOs:
- Local caching of species details to avoid multiple network calls
- Extend test coverage to handle additional cases
- Improve design layouts


### Potential new functionalities
- Ability to search for species
- Ability to sort based on different metrics of species like capture rate
- Ability to mark species as favorite

### Additional notes

1. The link provided in the assignment description for loading images did not work me originally. Upon digging into the issue, I came across a [thread on Reddit](https://www.reddit.com/r/webdev/comments/113k71y/why_am_i_getting_this_error_trying_to_get_an/) which indicated that my ISP could be blocking links from raw.githubusercontent.com. Since this could be an issue just on my end, I have reverted to using the link provided in the assignment hoping the you won't be facing the same issue.
   In case you do, you can swap out the link in the codebase with the following link which worked for me:
   https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${speciesId}.png
2. Since the species detail API returns a list of flavor text entries, I decided to display to first English entry from the list as the description.


### Java Version

To run the project on Android Studio, you will need to be using the JDK 17. You can change the JDK via `Preferences > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK`.

