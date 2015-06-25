'use strict';

/* Controllers */

var auctionControllers = angular.module('auctionControllers', []);

auctionControllers.controller('ReportCtrl', ['$scope', '$filter', 'Report',
  function($scope, $filter, Report) {
	var EMPTY_VALUE = "N/A";
  	$scope.granularity = "day";
  	$scope.sum = EMPTY_VALUE;
  	$scope.embeddedDate = new Date();

    $scope.updateSum = function(date) {
		if ($scope.reportForm.$valid) {
			date = formatDate($scope.granularity, date);
			$scope.sum = EMPTY_VALUE;
			$scope.report = Report.getSum({granularity: $scope.granularity, product: $scope.product, date: date},
			function(report) {
				$scope.sum = report.totalCost;
			});
		}
	};

	function formatDate(granularity, date) {
		if(granularity === 'week') {
			date = getMonday(date);
		} else if (granularity === 'month'){
			date = firstDayOfMonth(date);
		}
		return $filter('date')(date, "_yyyy-MM-dd");
	}

  }]);

