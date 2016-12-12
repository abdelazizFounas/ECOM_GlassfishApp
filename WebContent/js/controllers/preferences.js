/*
 * Ecom
 */

ecom_app = angular.module('ecomapp');

ecom_app.controller("controllerPreferences", function ($scope, $http, $compile, growl) {
  $scope.preferences();

  $scope.user = {};

  $scope.mail = "";
  $scope.pwd1 = "";

  $scope.phone = "";
  $scope.pwd2 = "";

  $scope.newpwd1 = "";
  $scope.newpwd2 = "";
  $scope.pwd3 = "";

  $(function() {
    $http({
      method: 'GET',
      url: '/AutomaticAuto/api/connexion/checkConnexion'
    }).then(function successCallback(response) {
      console.log(response);
      $scope.user = response.data;

      $scope.mail = $scope.user.email;
      $scope.phone = $scope.user.phone;
    }, function errorCallback(response) {
      console.log(response);
    });
  });

  $scope.changeMail = function(){
    $http({
      method: 'POST',
      url: '/AutomaticAuto/api/account/changeMail',
      data: {
        infobean: $scope.mail,
        password: $scope.pwd1
      }
    }).then(function successCallback(response) {
      console.log(response);
      if(response.data.taxiPresent){
        $scope.user.email = $scope.mail;
        $scope.pwd1 = "";
        growl.success("Mail modifié avec succès.",{title: 'Succès !', ttl: 2000});
      }
      else{
        $scope.mail = $scope.user.email;
        growl.error("Mail non modifié.",{title: 'Erreur !', ttl: 2000});
      }
    }, function errorCallback(response) {
      console.log(response);
    });
  };

  $scope.changePhone = function(){
    $http({
      method: 'POST',
      url: '/AutomaticAuto/api/account/changePhone',
      data: {
        infobean: $scope.phone,
        password: $scope.pwd2
      }
    }).then(function successCallback(response) {
      console.log(response);
      if(response.data.taxiPresent){
        $scope.user.phone = $scope.phone;
        $scope.pwd2 = "";
        growl.success("Téléphone modifié avec succès.",{title: 'Succès !', ttl: 2000});
      }
      else{
        $scope.phone = $scope.user.phone;
        growl.error("Téléphone non modifié.",{title: 'Erreur !', ttl: 2000});
      }
    }, function errorCallback(response) {
      console.log(response);
    });
  };

  $scope.changePassword = function(){
    $http({
      method: 'POST',
      url: '/AutomaticAuto/api/account/changePassword',
      data: {
        infobean: $scope.newpwd1,
        password: $scope.pwd3
      }
    }).then(function successCallback(response) {
      console.log(response);
      if(response.data.taxiPresent){
        $scope.newpwd1 = "";
        $scope.newpwd2 = "";
        $scope.pwd3 = "";
        growl.success("Mot de passe modifié avec succès.",{title: 'Succès !', ttl: 2000});
      }
      else{
        $scope.pwd3 = "";
        growl.error("Mot de passe non modifié.",{title: 'Erreur !', ttl: 2000});
      }
    }, function errorCallback(response) {
      console.log(response);
    });
  };
});
