/*
 * Ecom
 */

ecom_app = angular.module('ecomapp');

ecom_app.controller("controllerCars", function ($scope, $http, $compile) {
  $scope.ourcars();
});
