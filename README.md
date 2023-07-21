### APK [link](https://drive.google.com/file/d/1ua0BaNX4GixJ00Ta4lboporP6OdTcxN1/view?usp=sharing)

### Screenshots
<table>
  <tr>
    <th>Feature</th>
    <th>Light Theme</th>
    <th>Dark Theme</th>
  </tr>
  <tr>
    <td>Species List</td>
    <td><img src="https://github.com/ehabhelmy/cw-android-take-home-test-teril-lewis/assets/83067882/599f30c1-3b20-472d-9f66-a79312a36356"   height="550"  width="250"/></td>
    <td><img src="https://github.com/ehabhelmy/cw-android-take-home-test-teril-lewis/assets/83067882/9f313010-93e5-4f6d-84a6-8ade85d32405"   height="550"  width="250"/></td>
  </tr>
    <tr>
    <td>Species Detail</td>
    <td><img src="https://github.com/ehabhelmy/cw-android-take-home-test-teril-lewis/assets/83067882/f0ab4399-18de-42ba-97dd-9153a9f48642"   height="550"  width="250"/></td>
    <td><img src="https://github.com/ehabhelmy/cw-android-take-home-test-teril-lewis/assets/83067882/8d686f85-74bf-47eb-a149-9f8e3383232e"   height="550"  width="250"/></td>
  </tr>
</table>

### Error/Loader States

<table>
  <tr>
    <th>Feature</th>
    <th>Screenshot</th>
  </tr>
  <tr>
    <td>Empty Error State</td>
    <td><img src=""   height="550"  width="250"/></td>
  </tr>
  <tr>
    <td>Footer Error State</td>
    <td><img src=""   height="550"  width="250"/></td>
  </tr>
<tr>
    <td>Footer Loading State</td>
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
        - **Capture rate difference**: Is the difference between the capture rate of the current species and the species it evolves to. A positive difference is highlighted in red while a negative difference is highlighted in green. In case a species has reached its final form, the difference will just be stated as 0.
- Non-functional features:
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


### Java Version

To run the project on Android Studio, you will need to be using the JDK 17. You can change the JDK via `Preferences > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK`.

