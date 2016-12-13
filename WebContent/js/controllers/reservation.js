/*
 * Ecom
https://maps.google.com/maps/api/geocode/json?address=196 avenue de la republique loriol
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

ecom_app.controller("controllerReservation", function ($scope, $http, location, uiGmapIsReady, uiGmapGoogleMapApi, $mdDialog, growl) {
  $scope.reservation();

  // instantiate google map objects for directions
  $scope.directionsService = null;
  $scope.directionsDisplay = null;
  $scope.directions = {};

  $scope.selectedTrip = 0;

  $scope.selectedDate = null;

  $scope.selectedArrival = "";
  $scope.selectedDeparture = "";

  $scope.tripInfo = {};

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
      if($scope.modeTransport === "covoiturage"){
        $http({
          method: 'POST',
          url: '/AutomaticAuto/api/way/reserveCarpoolingWay',
          data: {
            departure: $scope.selectedDate,
            predefinedJourneyId: $scope.trips[$scope.selectedTrip].id
          }
        }).then(function successCallback(response) {
          if(response.data.taxiPresent){
            growl.success("La réservation du covoiturage a été effectuée avec succès.",{title: 'Succès !', ttl: 3000});
            $mdDialog.hide();
          }
          else{
            growl.error("La réservation du covoiturage n'a pas été effectuée.",{title: 'Error !', ttl: 3000});
            console.log("NO CARPOOLING RESERVATION");
          }
        }, function errorCallback(response) {
          growl.error("La réservation du covoiturage n'a pas été effectuée.",{title: 'Error !', ttl: 3000});
          console.log("NO CARPOOLING RESERVATION");
          console.log(response);
        });
      }
      else{
        $http({
          url: 'https://maps.google.com/maps/api/geocode/json',
          params: {
            sensor  : false,
            address : $scope.selectedDeparture
          }
        }).then(function successCallback(response) {
          $scope.tripInfo.latdu = response.data.results[0].geometry.location.lat;
          $scope.tripInfo.londu = response.data.results[0].geometry.location.lng;
          $http({
            url: 'https://maps.google.com/maps/api/geocode/json',
            params: {
              sensor  : false,
              address : $scope.selectedArrival
            }
          }).then(function successCallback(response) {
            $scope.tripInfo.latau = response.data.results[0].geometry.location.lat;
            $scope.tripInfo.lonau = response.data.results[0].geometry.location.lng;
            $http({
              method: 'POST',
              url: '/AutomaticAuto/api/way/reserveTaxi',
              data: {
                arrivalCity: $scope.selectedArrival,
                arrivalLocation: $scope.tripInfo.latau+";"+$scope.tripInfo.lonau,
                departureCity: $scope.selectedDeparture,
                departureLocation: $scope.tripInfo.latdu+";"+$scope.tripInfo.londu,
                departureDateTime: $scope.selectedDate,
                duration: $scope.directions.TotalDuration,
                price: $scope.trips[0].price
              }
            }).then(function successCallback(response) {
              if(response.data.taxiPresent){
                growl.success("La réservation du taxi a été effectuée avec succès.",{title: 'Succès !', ttl: 3000});
                $mdDialog.hide();
              }
              else{
                growl.error("La réservation du taxi n'a pas été effectuée.",{title: 'Erreur !', ttl: 3000});
                console.log("NO TAXI RESERVATION");
              }
            }, function errorCallback(response) {
              growl.error("La réservation du taxi n'a pas été effectuée.",{title: 'Erreur !', ttl: 3000});
              console.log("NO TAXI RESERVATION");
              console.log(response);
            });
          }, function errorCallback(response) {
            console.log("NO TAXI RESERVATION");
            console.log(response);
          });
        }, function errorCallback(response) {
          console.log("NO TAXI RESERVATION");
          console.log(response);
        });
      }
    };
  }

  // get directions using google maps api
  $scope.research = function () {
    $scope.trips = "pending";
    $scope.selectedArrival = $scope.arrival;
    $scope.selectedDeparture = $scope.departure;
    $scope.selectedDate = $scope.picker.date;

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

    if($scope.classCovoiturage === "md-primary"){
      $scope.modeTransport = "covoiturage";
      $http({
        url: 'https://maps.google.com/maps/api/geocode/json',
        params: {
          sensor  : false,
          address : $scope.departure
        }
      }).then(function successCallback(response) {
        $scope.tripInfo.latdu = response.data.results[0].geometry.location.lat;
        $scope.tripInfo.londu = response.data.results[0].geometry.location.lng;
        $http({
          url: 'https://maps.google.com/maps/api/geocode/json',
          params: {
            sensor  : false,
            address : $scope.arrival
          }
        }).then(function successCallback(response) {
          $scope.tripInfo.latau = response.data.results[0].geometry.location.lat;
          $scope.tripInfo.lonau = response.data.results[0].geometry.location.lng;
          $http({
            method: 'POST',
            url: '/AutomaticAuto/api/way/getCarpoolingWays',
            data: {
              departure: $scope.picker.date,
              latdu: $scope.tripInfo.latdu,
              londu: $scope.tripInfo.londu,
              latau: $scope.tripInfo.latau,
              lonau: $scope.tripInfo.lonau
            }
          }).then(function successCallback(response) {
            console.log("CARPOOLING");
            console.log(response);

            if(response.data.arrayPredefinedJourneyBean.length > 0){
              for (var i = 0; i < response.data.arrayPredefinedJourneyBean.length; i++) {
                response.data.arrayPredefinedJourneyBean[i].date = $scope.picker.date.getDate() + "/" + ($scope.picker.date.getMonth()+1) + "/" + $scope.picker.date.getFullYear() + " à " +  response.data.arrayPredefinedJourneyBean[i].departureTime;
              }

              $scope.trips = response.data.arrayPredefinedJourneyBean;
            }
            else{
              $scope.trips = "nothing";
            }
          }, function errorCallback(response) {
            $scope.trips = "nothing";
            console.log("NO CARPOOLING");
            console.log(response);
          });
        }, function errorCallback(response) {
          $scope.trips = "nothing";
          console.log("NO CARPOOLING");
          console.log(response);
        });
      }, function errorCallback(response) {
        $scope.trips = "nothing";
        console.log("NO CARPOOLING");
        console.log(response);
      });
    }
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

        directions.TotalDuration = secondsToMinute(totalDuration);
        $scope.directions = directions;
        console.log($scope.directions);

        if($scope.classTaxi === "md-primary"){
          $scope.modeTransport = "taxi";
          $http({
            method: 'POST',
            url: '/AutomaticAuto/api/way/taxiAvailable',
            data: {
              departure: $scope.picker.date,
              duration: $scope.directions.TotalDuration
            }
          }).then(function successCallback(response) {
            console.log("TAXI");
            console.log(response);
            if(response.data.taxiPresent){
              $scope.trips = [{
                departureCity: $scope.departure,
                arrivalCity: $scope.arrival,
                date: $scope.picker.date.getDate() + "/" + ($scope.picker.date.getMonth()+1) + "/" + $scope.picker.date.getFullYear() + " à " +  $scope.picker.date.getHours() + ":"+  $scope.picker.date.getMinutes() + ":"+  $scope.picker.date.getSeconds(),
                price: Math.round($scope.directions.TotalDistance*80)/100
              }];
            }
            else{
              $scope.trips = "nothing";
            }
          }, function errorCallback(response) {
            $scope.trips = "nothing";
            console.log("NO TAXI");
            console.log(response);
          });
        }
      } else {
        console.log("NO TAXI");
        console.log(response);
        $scope.trips = "nothing";
      }
    });
  }

  function secondsToMinute(totalDuration) {
    return ""+(totalDuration/60);
  };

  $scope.modeTransport = "taxi";

  $scope.trips = "";

  $scope.classTaxi = "md-primary";
  $scope.classCovoiturage = "";

  $scope.selectTaxi = function() {
    $scope.classTaxi = "md-primary";
    $scope.classCovoiturage = "";
  };

  $scope.selectCovoiturage = function() {
    $scope.classTaxi = "";
    $scope.classCovoiturage = "md-primary";
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
