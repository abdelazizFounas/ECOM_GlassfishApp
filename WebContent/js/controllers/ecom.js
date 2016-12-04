/*
 * Ecom
 */

ecom_app = angular.module('ecomapp', ['ngRoute','uiGmapgoogle-maps','ui.bootstrap','ui.bootstrap.datetimepicker','ngMaterial']);

ecom_app.controller("controllerEcom", function ($scope, $http, $location, $mdDialog) {
  $scope.myDate = new Date();

  $scope.maxDate = new Date(
      $scope.myDate.getFullYear()-18,
      $scope.myDate.getMonth(),
      $scope.myDate.getDate());

  $scope.connect = function(ev) {
    $mdDialog.show({
      controller: LoginDialogController,
      scope: this,
      preserveScope: true,
      templateUrl: 'login-dialog.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true,
      fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
    });
  };

  $scope.createAcc = function(ev) {
    $mdDialog.show({
      controller: CreateDialogController,
      scope: this,
      preserveScope: true,
      templateUrl: 'create-dialog.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true,
      fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
    });
  };

  function CreateDialogController($scope, $mdDialog) {
    $scope.hide = function() {
      $mdDialog.hide();
    };

    $scope.cancel = function() {
      $mdDialog.cancel();
    };

    $scope.answer = function(firstname, lastname, mail, password, birthday, address, city, zip, country, phone) {
      console.log(firstname +" "+ lastname  +" "+  mail +" "+ password);
      console.log(birthday +" "+ address  +" "+  city +" "+ zip);
      console.log(country +" "+ phone);

      $http({
        method: 'POST',
        url: '/AutomaticAuto/api/connexion/newAccount',
        data: {
          address: address,
          birthDate: birthday,
          city: city,
          country: country,
          email: mail,
          firstName: firstname,
          lastName: lastname,
          passwdHash: password,
          phone: phone,
          zip: zip
        }
      }).then(function successCallback(response) {
        console.log("NEW ACCOUNT");
        console.log(response);
        $scope.connectionInfo.connected = true;
        $scope.connectionInfo.firstname = firstname;
        $scope.connectionInfo.lastname = lastname;
        $mdDialog.hide();
      }, function errorCallback(response) {
        console.log("NO NEW ACCOUNT");
        console.log(response);
      });

      $mdDialog.hide();
    };
  }

  function LoginDialogController($scope, $mdDialog) {
    $scope.hide = function() {
      $mdDialog.hide();
    };

    $scope.cancel = function() {
      $mdDialog.cancel();
    };

    $scope.answer = function(username, password) {
      console.log(username +" "+ password);
      $http({
        method: 'POST',
        url: '/AutomaticAuto/api/connexion/connexion',
        data: {
          mail: username,
          pwd: password
        }
      }).then(function successCallback(response) {
        console.log("CONNECTED");
        console.log(response);
        $scope.connectionInfo.connected = true;
        $scope.connectionInfo.firstname = response.data.firstName;
        $scope.connectionInfo.lastname = response.data.lastName;
        $mdDialog.hide();
      }, function errorCallback(response) {
        console.log("NOT CONNECTED");
        console.log(response);
      });
    };
  }

  $scope.connectionInfo = {
    connected: false
  };

  $scope.disconnect = function(e){
    $scope.connectionInfo.connected = false;

    $http({
      method: 'GET',
      url: '/AutomaticAuto/api/connexion/deconnexion'
    }).then(function successCallback(response) {
      console.log("DISCONNECTED");
      console.log(response);
    }, function errorCallback(response) {
      console.log("DISCONNECTED");
      console.log(response);
    });
  };

  $scope.reservation = function(){
    removeActiveClass();
    $scope.classReservation = "active";
  };

  $scope.myReservations = function(){
    removeActiveClass();
    $scope.classMyReservations = "active";
  };

  $scope.preferences = function(){
    removeActiveClass();
    $scope.classPreferences = "active";
  };

  $scope.ourcars = function(){
    removeActiveClass();
    $scope.classCars = "active";
  };

  $scope.about = function(){
    removeActiveClass();
    $scope.classAbout = "active";
  };

  removeActiveClass = function(){
    $scope.classReservation = "";
    $scope.classMyReservations = "";
    $scope.classPreferences = "";
    $scope.classCars = "";
    $scope.classAbout = "";
  };

  $(function() {
     var pgurl = window.location.href.substr(window.location.href.lastIndexOf("/")+1);

     if(pgurl == "reservation"){
       $scope.reservation();
     }
     else if(pgurl == "myReservations"){
       if($scope.connectionInfo.connected == false){
         $location.path("/reservation");
       }
       else{
         $scope.myReservations();
       }
     }
     else if(pgurl == "preferences"){
       if($scope.connectionInfo.connected == false){
         $location.path("/reservation");
       }
       else{
         $scope.preferences();
       }
     }
     else if(pgurl == "cars"){
       $scope.ourcars();
     }
     else if(pgurl == "about"){
       $scope.about();
     }

     $http({
       method: 'GET',
       url: '/AutomaticAuto/api/connexion/checkConnexion'
     }).then(function successCallback(response) {
       console.log("CONNECTED");
       console.log(response);
       $scope.connectionInfo.connected = true;
       $scope.connectionInfo.firstname = response.data.firstName;
       $scope.connectionInfo.lastname = response.data.lastName;
       $mdDialog.hide();
     }, function errorCallback(response) {
       console.log("NOT CONNECTED");
       console.log(response);
     });

     $scope.$on( "$routeChangeStart", function(event, next, current) {
       if ( $scope.connectionInfo.connected == false ) {
         if ( next.templateUrl == "myReservations.html" || next.templateUrl == "preferences.html" ) {
           $location.path("/reservation");
         }
       }
     });
  });
}).config(['$routeProvider',
    function($routeProvider) {
      $routeProvider
      .when('/reservation', {
          templateUrl: 'reservation.html',
          controller: 'controllerReservation'
      }).when('/myReservations', {
          templateUrl: 'myReservations.html',
          controller: 'controllerMyReservations'
      }).when('/preferences', {
          templateUrl: 'preferences.html',
          controller: 'controllerPreferences'
      }).when('/cars', {
          templateUrl: 'ourCars.html',
          controller: 'controllerCars'
      }).when('/about', {
          templateUrl: 'aPropos.html',
          controller: 'controllerAbout'
      }).otherwise({
          redirectTo: '/reservation'
      });
    }
]);
