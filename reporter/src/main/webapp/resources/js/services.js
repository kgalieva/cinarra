'use strict';

/* Services */

var auctionServices = angular.module('auctionServices', ['ngResource']);

auctionServices.factory('Report', ['$resource',
  function($resource){
    return $resource('/:granularity/:product:date', {}, {
      getSum: {method:'GET'}
    });
  }]);