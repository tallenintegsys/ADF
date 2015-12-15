# ADF
Automatic Direction Finder using BluetoothLE NDBs (Eddystone)

This is some experimental code. I'm working on triangulation as part of a larger project. This version does it without the Google Play Services. The, triangulation is the wrong word, directionality is the key, the user has to be moving and they have to have to be in range of multiple beacons. I'm playing games with inertial nav and signal strengths, I need to know the lat/lon of the beacons to try and synthesize the users lat/lon w/o GPS.
