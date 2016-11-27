/*
 * Ecom
 */

ecom_app = angular.module('ecomapp');

ecom_app.controller("controllerMyReservations", function ($scope, $http, $compile) {
  $scope.myReservations();
});
