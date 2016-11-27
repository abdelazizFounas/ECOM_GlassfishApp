/*
 * Ecom
 function distance(lat_a, lon_a, lat_b, lon_b)
{
a = Math.PI / 180;
lat1 = lat_a * a;
lat2 = lat_b * a;
lon1 = lon_a * a;
lon2 = lon_b * a;

t1 = Math.sin(lat1) * Math.sin(lat2);
t2 = Math.cos(lat1) * Math.cos(lat2);
t3 = Math.cos(lon1 - lon2);
t4 = t2 * t3;
t5 = t1 + t4;
rad_dist = Math.atan(-t5/Math.sqrt(-t5 * t5 +1)) + 2 * Math.atan(1);

return (rad_dist * 3437.74677 * 1.1508) * 1.6093470878864446;
}

private double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) {
    float pk = (float) (180/3.14169);

    float a1 = lat_a / pk;
    float a2 = lng_a / pk;
    float b1 = lat_b / pk;
    float b2 = lng_b / pk;

    float t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*FloatMath.cos(b1)*FloatMath.cos(b2);
    float t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*FloatMath.cos(b1)*FloatMath.sin(b2);
    float t3 = FloatMath.sin(a1)*FloatMath.sin(b1);
    double tt = Math.acos(t1 + t2 + t3);

    return 6366000*tt;
}
http://maps.google.com/maps/api/geocode/json?address=196 avenue de la republique loriol
 */

ecom_app = angular.module('ecomapp');

ecom_app.factory('location', [
  function() {

    var service = {
      isReady: false,
      gpsAvailable: true
    };

    var readyCallbacks = [];

    // Get Current Location
    service.get = function(s_cb, e_cb) {

      // Request location from the navigator service
      navigator.geolocation.getCurrentPosition(function(location) {
        // Geolocation is available
        service.gpsAvailable = true;

        // parse current location
        service.current = {
          latitude: location.coords.latitude,
          longitude: location.coords.longitude
        };

        // Location is ready
        service.isReady = true;
        // Execute the on ready tasks
        service.onReadyTasks();
        // success callback
        s_cb();

      }, function(error) {
        service.gpsAvailable = false;
        console.log('code: ' + error.code + ' message: ' + error.message);
        // error callback
        e_cb();
      });
    };

    // Execute registered tasks
    service.onReadyTasks = function() {
      for (var i = readyCallbacks.length - 1; i >= 0; i--) {
        readyCallbacks[i]();
      };
    };

    // Execute registered tasks if ready
    // or register tasks if not ready yet
    service.ready = function(callback) {
      if (service.isReady) {
        callback();
      } else {
        // If not ready yet, add it to this array
        // which will be called once location is ready
        readyCallbacks.push(callback);
      }
    };

    return service;
  }
]);

ecom_app.controller("controllerReservation", function ($scope, $http, location, uiGmapIsReady, uiGmapGoogleMapApi, $mdDialog) {
  $scope.reservation();

  // instantiate google map objects for directions
  $scope.directionsService = null;
  $scope.directionsDisplay = null;
  $scope.directions = null;

  $scope.selectedTrip = 0;

  $scope.selectTrip = function (index) {
    $scope.selectedTrip = index;
    $mdDialog.show({
      controller: SelectTripController,
      scope: this,
      preserveScope: true,
      templateUrl: 'select-trip.html',
      parent: angular.element(document.body),
      clickOutsideToClose:false,
      fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
    });
  }

  function SelectTripController($scope, $mdDialog) {
    $scope.hide = function() {
      $mdDialog.hide();
    };

    $scope.cancel = function() {
      $mdDialog.cancel();
    };

    $scope.answer = function() {

      $mdDialog.hide();
    };
  }

  // get directions using google maps api
  $scope.research = function () {
    uiGmapIsReady.promise().then(function (maps) {
      //if we initialize directionsDisplay in every call, then we will have the issue with the previous route not cleared.
      // use this and the logic below to setMap to null and directionsDisplay to null.
      if ($scope.directionsDisplay == null) {
        $scope.directionsService = new google.maps.DirectionsService();
        $scope.directionsDisplay = new google.maps.DirectionsRenderer();
      }
      return uiGmapIsReady.promise(1);
    }).then(function (instances) {
      var instanceMap = instances[0].map;

      if ($scope.directionsDisplay !== null) {
        // fallback to clear the previous directions
        $scope.directionsDisplay.setMap(null);
        $scope.directionsDisplay = null;
      }

      $scope.directionsDisplay = new google.maps.DirectionsRenderer();
      $scope.directionsDisplay.setMap(instanceMap);

      var request = {
        origin: $scope.departure,
        destination: $scope.arrival,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
      };

      $scope.DisplayDrivingDirections();
    });
  }

  $scope.DisplayDrivingDirections = function (){
    var directionsServiceRequest = {
      origin: $scope.departure,
      destination: $scope.arrival,
      waypoints: [],
      optimizeWaypoints: true,
      provideRouteAlternatives: false,
      travelMode: google.maps.TravelMode.DRIVING,
      unitSystem: google.maps.UnitSystem.METRIC
    };

    var directions =
    {
      TotalDistance: 0,
      TotalDuration: {},
      Segments: []
    };
    $scope.directionsService.route(directionsServiceRequest, function (response, status) {
      if (status == google.maps.DirectionsStatus.OK){
        $scope.directionsDisplay.setDirections(response);

        directions.TotalDistance = 0;
        directions.Segments = [];
        var totalDuration = 0;

        var route = response.routes[0];

        // For each route, display summary information.
        for (var i = 0; i < route.legs.length; i++) {
          var routeSegment = i + 1;

          var legDistance = route.legs[i].distance.value; // distance in meters need to devide by 1000 for Km
          directions.TotalDistance = directions.TotalDistance + legDistance / 1000;

          var legDuration = route.legs[i].duration.value; // travel time in seconds
          totalDuration = totalDuration + legDuration;

          directions.Segments.push({
            Start: route.legs[i].start_address,
            End: route.legs[i].end_address,
            Travel: route.legs[i].distance.text,
            Duration: route.legs[i].duration.text
          });
        }

        directions.TotalDuration = secondsToTime(totalDuration);
        $scope.directions = directions;
        console.log($scope.directions);
      }
    });
  }

  function secondsToTime(totalDuration) {
    var hours   = Math.floor(totalDuration / 3600);
    var minutes = Math.floor((totalDuration - (hours * 3600)) / 60);
    var seconds = totalDuration - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    return hours+':'+minutes+':'+seconds;
  };

  $scope.modeTransport = "taxi";

  $scope.trips = [
    {
      departure: "Loriol 1",
      arrival: "Lyon 1",
      date: "Lundi 03/12/2016 à 12:30",
      price: "300"
    },{
      departure: "Loriol 2",
      arrival: "Lyon 2",
      date: "Lundi 03/12/2016 à 12:30",
      price: "350"
    },{
      departure: "Loriol 3",
      arrival: "Lyon 3",
      date: "Lundi 03/12/2016 à 12:30",
      price: "400"
    },{
      departure: "Loriol 4",
      arrival: "Lyon 4",
      date: "Lundi 03/12/2016 à 12:30",
      price: "500"
    }
  ];

  $scope.classTaxi = "md-primary";
  $scope.classCovoiturage = "";

  $scope.selectTaxi = function() {
    $scope.classTaxi = "md-primary";
    $scope.classCovoiturage = "";
    $scope.trips = [];
  };

  $scope.selectCovoiturage = function() {
    $scope.classTaxi = "";
    $scope.classCovoiturage = "md-primary";
    $scope.trips = [
      {
        departure: "Loriol 1",
        arrival: "Lyon 1",
        date: "Lundi 03/12/2016 à 12:30",
        price: "300"
      },{
        departure: "Loriol 2",
        arrival: "Lyon 2",
        date: "Lundi 03/12/2016 à 12:30",
        price: "350"
      },{
        departure: "Loriol 3",
        arrival: "Lyon 3",
        date: "Lundi 03/12/2016 à 12:30",
        price: "400"
      }
    ];
  };

  $scope.picker = {
    date: new Date(),
    open: false
  };

  $scope.openCalendar = function(e) {
    $scope.picker.open = true;
  };

  $scope.arrival = "";
  $scope.departure = "";
  $scope.markers = [];

  $scope.scb = function(){
    console.log("SUCESS latitude : " + location.current.latitude + " longitude : " + location.current.longitude);
    $http({
      url: "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + location.current.latitude + "," + location.current.longitude + "&key=AIzaSyB-diDYJHF62_VW-X7FPlPLfscq27jcSG8",
      method: "GET"
    }).success(function(data, status, headers, config) {
      console.log(data);
      $scope.departure = data.results[0].formatted_address;
      $scope.map = { center: { latitude: location.current.latitude, longitude: location.current.longitude }, zoom: 10 };

      $scope.markers.push({
        latitude: location.current.latitude,
        longitude: location.current.longitude,
        title: 'You are here',
        options: {labelClass:'you_are_here',labelAnchor:'51 80',labelContent:'Vous êtes ici'},
        id: 1
      });
    }).error(function(data, status, headers, config) {
      console.log(data);
    });
  };

  $scope.ecb = function(){
    console.log("ERROR getting latitude and longitude");
  };

  location.get($scope.scb, $scope.ecb);

  $scope.map = {
    control: {},
    center: {
      latitude: 46.610771,
      longitude: 2.680664
    },
    zoom: 5
  };


}).config(
    ['uiGmapGoogleMapApiProvider', function(GoogleMapApiProviders) {
        GoogleMapApiProviders.configure({
          key: 'AIzaSyB-diDYJHF62_VW-X7FPlPLfscq27jcSG8',
          v: '3.21',
          libraries: 'weather,geometry,visualization'
        });
    }]
);
