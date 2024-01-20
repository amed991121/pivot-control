<h1 align="center">Pivot Machine Control App </h1>

It is an application to control and monitor center pivot irrigation machines for Android devices. It has the following features:
- The home interface is the welcome interface to the designed monitoring and control system. The primary function of this home interface is to control access to the other user interfaces on the system.
- Information interface contains the machine configuration variables and the technical variables of the motor and wheel reducer of the last tower.
- The climate interface's main function is to allow the introduction of meteorological variables, necessary in the calculation of the reference evapotranspiration (ETo) using the Penman-Monteith FAO method. In addition, (Kc) is introduced as a crop variable to calculate crop evapotranspiration (Etc). In addition, it allows real-time monitoring of the different meteorological variables necessary for cultivation.
- The operation interface allows you to configure the operation of the machine sectorally. In this case, the circular irrigation area is divided into four quadrants, allowing you to select in which of these the irrigation will be carried out and at what dosage percentage. It is linked to the control of the pump motor, in such a way that, if watering is needed in quadrant one and quadrant three, the machine would pass over quadrant two without watering, so during this journey the pump automatically turns off and The speed is set to maximum (100%) on this quadrant. Also, it allows monitoring of the state
of the pump (On-Off) and as well as the direction of rotation of the motor.
- In the session interface we can view the user's data, in addition to being able to modify it and log out of the application

It is a native app developed in Kotlin, with the following technologies:
- Room Database and Datastore
- Retrofit
- Kotlin Flows and Corroutines
- DI with Koin
- Jetpack Compose
- MVVM

<h3 align="left">
  <img align="left" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/1.jpg">
  <img align="center" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/2.jpg">
  <img align="center" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/3.jpg">
  <img align="center" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/4.jpg">
  <img align="center" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/5.jpg">
  <img align="center" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/6.jpg">
  <img align="center" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/7.jpg">
  img align="center" width="200" src="https://github.com/amed991121/pivot-control/blob/master/screenshots/8.jpg">
</h3>
