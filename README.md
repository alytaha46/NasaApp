# Nasa Radar Android App

Nasa Radar is an app to view the asteroids detected by NASA that pass near Earth.

You can view all the detected asteroids given a period of time with data such as the size, velocity,
distance to earth and if they are potentially hazardous.

## Lessons Learned

Fetching data from the internet, saving data to a database, and display the data in a clear,
compelling UI.

## Tech

-**Architecture:** MVVM with Repository Pattern

-**Client:** Retrofit

-**Offline Caching:** Room

-**Background Task:** WorkManager

-**UI:** Jetpack library
  - Binding Adapters
  - LiveData
  - Navigation Component
  - Recycler View
  
## Screenshots

[<img src="/readme/Screenshot_1.png" width="253"
hspace="10" vspace="10">](/readme/Screenshot_1.png)
[<img src="/readme/Screenshot_2.png" width="253"
hspace="10" vspace="10">](/readme/Screenshot_2.png)
[<img src="/readme/Screenshot_3.png" width="253"
hspace="10" vspace="10">](/readme/Screenshot_3.png)


## Contributing

Contributions are always welcome! Here are a few ways you can help:

- [Report bugs and make suggestions.](https://github.com/alytaha46/NasaApp/issues)
  - Write some code. Please follow the code style used in the project to make a review process faster.

## Environment Variables

To run this project, you will need to add the following environment variables to
your `Gradle/local.properties` file

Nasa Api Key `myApiNasa`

## Feedback

If you have any feedback, please reach out to us at alytaha46@gmail.com

