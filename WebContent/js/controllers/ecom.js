/*
 * Ecom
 */

ecom_app = angular.module('ecomapp', ['ngRoute','uiGmapgoogle-maps','ui.bootstrap','ui.bootstrap.datetimepicker','ngMaterial']);

ecom_app.controller("controllerEcom", function ($scope, $http, $location, $mdDialog) {
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

    $scope.answer = function(firstname, lastname, mail, password) {
      console.log(firstname +" "+ lastname  +" "+  mail +" "+ password);
      $scope.connectionInfo.connected = true;
      $scope.connectionInfo.firstname = firstname;
      $scope.connectionInfo.lastname = lastname;
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
        $scope.connectionInfo.connected = true;
        $scope.connectionInfo.firstname = username;
        $scope.connectionInfo.lastname = 'ln';
        $mdDialog.hide();
      }, function errorCallback(response) {
        console.log("NOT CONNECTED");
      });
    };
  }

  $scope.connectionInfo = {
    connected: false
  };

  $scope.connecter = function(e){
    $scope.connectionInfo.connected = true;
    $scope.connectionInfo.firstname = "Abdelaziz";
    $scope.connectionInfo.lastname = "Founas";
  };

  $scope.disconnect = function(e){
    $scope.connectionInfo.connected = false;
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
