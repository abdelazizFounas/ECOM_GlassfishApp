/*
 * Ecom
 */

ecom_app = angular.module('ecomapp');

ecom_app.controller("controllerAbout", function ($scope, $http, $compile) {
  $scope.about();
});
