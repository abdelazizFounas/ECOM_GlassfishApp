/*
 * Ecom
 */

ecom_app = angular.module('ecomapp');

ecom_app.controller("controllerMyReservations", function ($scope, $http, $compile) {
  $scope.myReservations();

  $scope.tripsTaxi = "pending";
  $scope.tripsCarpooling = "pending";

  $(function() {
     $http({
       method: 'GET',
       url: '/AutomaticAuto/api/reservation/getCarpoolingReservation'
     }).then(function successCallback(response) {
       console.log(response);
       for (var i = 0; i < response.data.arrayCarpoolingReservationBean.length; i++) {
         var dateReserved = new Date(response.data.arrayCarpoolingReservationBean[i].dateReserved);
         var day = new Date(response.data.arrayCarpoolingReservationBean[i].day);
         response.data.arrayCarpoolingReservationBean[i].dateOfReservation = dateReserved.getDate() + "/" + (dateReserved.getMonth()+1) + "/" + dateReserved.getFullYear() + " à " +  dateReserved.getHours() + ":"+  dateReserved.getMinutes() + ":"+  dateReserved.getSeconds();
         response.data.arrayCarpoolingReservationBean[i].date = day.getDate() + "/" + (day.getMonth()+1) + "/" + day.getFullYear() + " à " +  response.data.arrayCarpoolingReservationBean[i].departureTime;
       }

       $scope.tripsCarpooling = response.data.arrayCarpoolingReservationBean;
     }, function errorCallback(response) {
       $scope.tripsCarpooling = [];
       console.log(response);
     });

     $http({
       method: 'GET',
       url: '/AutomaticAuto/api/reservation/getCarpoolingTaxi'
     }).then(function successCallback(response) {
       console.log(response);
       for (var i = 0; i < response.data.arrayTaxiReservationBean.length; i++) {
         var dateReserved = new Date(response.data.arrayTaxiReservationBean[i].dateReserved);
         var departureDateTime = new Date(response.data.arrayTaxiReservationBean[i].departureDateTime);
         response.data.arrayTaxiReservationBean[i].dateOfReservation = dateReserved.getDate() + "/" + (dateReserved.getMonth()+1) + "/" + dateReserved.getFullYear() + " à " +  dateReserved.getHours() + ":"+  dateReserved.getMinutes() + ":"+  dateReserved.getSeconds();
         response.data.arrayTaxiReservationBean[i].date = departureDateTime.getDate() + "/" + (departureDateTime.getMonth()+1) + "/" + departureDateTime.getFullYear() + " à " +  departureDateTime.getHours() + ":"+  departureDateTime.getMinutes() + ":"+  departureDateTime.getSeconds();
       }

       $scope.tripsTaxi = response.data.arrayTaxiReservationBean;
     }, function errorCallback(response) {
       $scope.tripsTaxi = [];
       console.log(response);
     });
  });
});
